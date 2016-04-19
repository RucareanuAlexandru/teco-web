/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Facades;

import com.mycompany.test.vaadin.Entities.StkActions;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author alex
 */
@Stateless
public class StkActionsFacade extends AbstractFacade<StkActions> {

    @PersistenceContext(unitName = "com.mycompany_teco-web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StkActionsFacade() {
        super(StkActions.class);
    }
    
    public List<StkActions> findStkActionsByType(String type) {
        Query query = em.createNamedQuery("StkActions.findByType")
                .setParameter("type", type);
        return query.getResultList();
    }
    
    public StkActions findStkActionByNameAndType(String name, String type) {
        Query query = em.createNamedQuery("StkActions.findByNameAndType")
                .setParameter("name", name)
                .setParameter("type", type);
        List<StkActions> results = query.getResultList();
        if (results != null & !results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }
}
