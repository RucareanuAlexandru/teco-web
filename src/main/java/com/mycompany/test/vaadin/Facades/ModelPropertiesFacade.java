/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Facades;

import com.mycompany.test.vaadin.Entities.ModelProperties;
import com.mycompany.test.vaadin.Entities.Models;
import com.mycompany.test.vaadin.Entities.PhoneTypeProperties;
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
public class ModelPropertiesFacade extends AbstractFacade<ModelProperties> {

    @PersistenceContext(unitName = "com.mycompany_teco-web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ModelPropertiesFacade() {
        super(ModelProperties.class);
    }
    
    public ModelProperties findByModelAndProperty(Models m, PhoneTypeProperties ptp) {
        Query query = em.createNamedQuery("ModelProperties.findByModelAndProperty")
                .setParameter("model", m)
                .setParameter("property", ptp);
        List<ModelProperties> results = query.getResultList();
        
        if (results != null && !results.isEmpty()) {
            return results.get(0);
        }
        
        return null;
     }
    
}
