/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Facades;

import com.mycompany.test.vaadin.Entities.Behaviours;
import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Entities.ProjectsActionsProperties;
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
public class BehavioursFacade extends AbstractFacade<Behaviours> {

    @PersistenceContext(unitName = "com.mycompany_teco-web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BehavioursFacade() {
        super(Behaviours.class);
    }
    
    public Behaviours findByModelAndProjectActionProperty(Models model,
            ProjectsActionsProperties projectActionProperty) {
        Query query = em.createNamedQuery("Behaviours.findByModelAndProjectActionProperty")
                .setParameter("model", model)
                .setParameter("projectActionProperty", projectActionProperty);
        List<Behaviours> results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            return results.get(0);
        }
        
        return null;
    }
}
