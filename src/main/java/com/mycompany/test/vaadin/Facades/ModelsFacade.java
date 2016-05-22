/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Facades;

import com.mycompany.test.vaadin.Entities.Models;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author alex
 */
@Stateless
public class ModelsFacade extends AbstractFacade<Models> {

    @PersistenceContext(unitName = "com.mycompany_teco-web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ModelsFacade() {
        super(Models.class);
    }
    
    public Models loadLazyCollectionForModel(String modelId) {
        Query query = em.createNamedQuery("Models.loadLazyCollectionForModel")
                .setParameter("modelId", modelId);
        query.setHint(QueryHints.CACHE_USAGE, CacheUsage.DoNotCheckCache);
        List<Models> results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }
    
    public Models findModelByModelNameAndBrandName(String modelName, String brandName) {
        Query query = em.createNamedQuery("Models.findByModelNameAndBrandName")
                .setParameter("brandName", brandName)
                .setParameter("modelName", modelName);
        List<Models> results = query.getResultList();
        
        if (results != null && !results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }
    
}
