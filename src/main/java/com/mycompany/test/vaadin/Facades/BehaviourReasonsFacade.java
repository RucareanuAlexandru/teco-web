/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Facades;

import com.mycompany.test.vaadin.Entities.BehaviourReasons;
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
public class BehaviourReasonsFacade extends AbstractFacade<BehaviourReasons> {

    @PersistenceContext(unitName = "com.mycompany_teco-web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BehaviourReasonsFacade() {
        super(BehaviourReasons.class);
    }
    
    public BehaviourReasons findByDescription(String description) {
        Query query = em.createNamedQuery("BehaviourReasons.findByReasonDescription")
                .setParameter("reasonDescription", description);
        List<BehaviourReasons> results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            return results.get(0);
        }
        
        return null;
    }
}
