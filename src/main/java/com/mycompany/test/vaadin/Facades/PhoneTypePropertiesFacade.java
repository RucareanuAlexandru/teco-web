/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Facades;

import com.mycompany.test.vaadin.Entities.PhoneTypeProperties;
import com.mycompany.test.vaadin.Entities.PhoneTypes;
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
public class PhoneTypePropertiesFacade extends AbstractFacade<PhoneTypeProperties> {

    @PersistenceContext(unitName = "com.mycompany_teco-web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PhoneTypePropertiesFacade() {
        super(PhoneTypeProperties.class);
    }
    
    public PhoneTypeProperties findPTPByPropertyNameAndCategory(String propertyName, PhoneTypes category) {
        Query query = em.createNamedQuery("PhoneTypeProperties.findByPropertyNameAndCategory");
        query.setParameter("propertyName", propertyName);
        query.setParameter("category", category);
        
        List<PhoneTypeProperties> results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }
    
}
