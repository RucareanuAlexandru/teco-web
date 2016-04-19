/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Facades;

import com.mycompany.test.vaadin.Entities.StkActionProperties;
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
public class StkActionPropertiesFacade extends AbstractFacade<StkActionProperties> {

    @PersistenceContext(unitName = "com.mycompany_teco-web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StkActionPropertiesFacade() {
        super(StkActionProperties.class);
    }
    
    public List<StkActionProperties> findStkActionPropertiesByType(String type) {
        Query query = em.createNamedQuery("StkActionProperties.findByType")
                .setParameter("type", type);
        return query.getResultList();
    }
    
    public StkActionProperties findStkActionPropertyByNameAndType(String name, String type) {
        Query query = em.createNamedQuery("StkActionProperties.findByNameAndType")
                .setParameter("name", name)
                .setParameter("type", type);
        List<StkActionProperties> results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }
    
}
