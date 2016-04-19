/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Facades;

import com.mycompany.test.vaadin.Entities.Projects;
import com.mycompany.test.vaadin.Entities.ProjectsActionsProperties;
import com.mycompany.test.vaadin.Entities.StkActionProperties;
import com.mycompany.test.vaadin.Entities.StkActions;
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
public class ProjectsActionsPropertiesFacade extends AbstractFacade<ProjectsActionsProperties> {

    @PersistenceContext(unitName = "com.mycompany_teco-web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjectsActionsPropertiesFacade() {
        super(ProjectsActionsProperties.class);
    }
    
    public List<StkActionProperties> findActionPropertiesByProjectAndAction(Projects project, StkActions action) {
        Query query = em.createNamedQuery("ProjectsActionsProperties.findByProjectAndAction")
                .setParameter("project", project)
                .setParameter("action", action);
        
        return query.getResultList();
    }
    
    public ProjectsActionsProperties findByProjectAndActionAndProperty(Projects project, StkActions action, StkActionProperties property) {
        Query query = em.createNamedQuery("ProjectsActionsProperties.findByProjectAndActionAndProperty")
                .setParameter("project", project)
                .setParameter("action", action)
                .setParameter("property", property);
        List<ProjectsActionsProperties> results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }
}
