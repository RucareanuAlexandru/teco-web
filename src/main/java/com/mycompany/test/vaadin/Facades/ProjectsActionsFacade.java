/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Facades;

import com.mycompany.test.vaadin.Entities.Projects;
import com.mycompany.test.vaadin.Entities.ProjectsActions;
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
public class ProjectsActionsFacade extends AbstractFacade<ProjectsActions> {

    @PersistenceContext(unitName = "com.mycompany_teco-web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjectsActionsFacade() {
        super(ProjectsActions.class);
    }
    
    public List<StkActions> getActionsByProjectAndActionType(Projects project, String type) {
        Query query = em.createNamedQuery("ProjectsActions.getActionsByProjectAndActionType")
                .setParameter("project", project)
                .setParameter("type", type);
        return query.getResultList();
    }
    
    public ProjectsActions findByProjectAndAction(Projects project, StkActions action) {
        Query query = em.createNamedQuery("ProjectsActions.findByProjectAndAction")
                .setParameter("project", project)
                .setParameter("action", action);
        List<ProjectsActions> results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }
    
    public List<ProjectsActions> findByProjectAndActionType(Projects project, String type) {
        Query query = em.createNamedQuery("ProjectsActions.findByProjectAndActionType")
                .setParameter("project", project)
                .setParameter("actionType", type);
        
        return query.getResultList();
    }
    
}
