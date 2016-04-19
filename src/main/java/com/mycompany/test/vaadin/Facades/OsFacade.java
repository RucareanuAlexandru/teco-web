/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Facades;

import com.mycompany.test.vaadin.Entities.Os;
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
public class OsFacade extends AbstractFacade<Os> {

    @PersistenceContext(unitName = "com.mycompany_teco-web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OsFacade() {
        super(Os.class);
    }
    
    public Os findOsByOsAndVersion(String os, String version) {
        Query query = em.createNamedQuery("Os.findByOsAndVersion");
        query.setParameter("os", os);
        query.setParameter("version", version);
        
        List<Os> results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            return results.get(0);
        }
        
        return null;
    }
    
}
