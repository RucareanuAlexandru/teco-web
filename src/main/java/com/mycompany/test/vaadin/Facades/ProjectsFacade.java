/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Facades;

import com.mycompany.test.vaadin.Entities.Projects;
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
public class ProjectsFacade extends AbstractFacade<Projects> {

    @PersistenceContext(unitName = "com.mycompany_teco-web_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjectsFacade() {
        super(Projects.class);
    }
    
    public Projects findProjectByProjectName(String projectName) {
        Query query = em.createNamedQuery("Projects.findByProject")
                .setParameter("project", projectName);
        List<Projects> projects = query.getResultList();
        if (projects != null && !projects.isEmpty()) {
            return projects.get(0);
        }
        return null;
    }
}
