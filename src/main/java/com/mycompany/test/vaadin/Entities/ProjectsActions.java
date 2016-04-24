/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "projects_actions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectsActions.findAll", query = "SELECT p FROM ProjectsActions p"),
    @NamedQuery(name = "ProjectsActions.findById", query = "SELECT p FROM ProjectsActions p WHERE p.id = :id"),
    @NamedQuery(name = "ProjectsActions.getActionsByProjectAndActionType", 
            query = "SELECT pa.action FROM ProjectsActions pa WHERE pa.project = :project "
                    + "and pa.action.type =:type"),
    @NamedQuery(name = "ProjectsActions.findByProjectAndAction", 
            query = "SELECT pa FROM ProjectsActions pa WHERE pa.project = :project AND pa.action = :action"),
    @NamedQuery(name = "ProjectsActions.findByProjectAndActionType", 
            query = "SELECT pa FROM ProjectsActions pa WHERE pa.project = :project AND pa.action.type = :actionType")})
public class ProjectsActions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectAction", fetch = FetchType.LAZY)
    private List<ProjectsActionsProperties> projectsActionsPropertiesList;
    @JoinColumn(name = "project", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Projects project;
    @JoinColumn(name = "action", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private StkActions action;

    public ProjectsActions() {
    }

    public ProjectsActions(Long id) {
        this.id = id;
    }

    public ProjectsActions(Projects project, StkActions action) {
        this.project = project;
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlTransient
    public List<ProjectsActionsProperties> getProjectsActionsPropertiesList() {
        return projectsActionsPropertiesList;
    }

    public void setProjectsActionsPropertiesList(List<ProjectsActionsProperties> projectsActionsPropertiesList) {
        this.projectsActionsPropertiesList = projectsActionsPropertiesList;
    }

    public Projects getProject() {
        return project;
    }

    public void setProject(Projects project) {
        this.project = project;
    }

    public StkActions getAction() {
        return action;
    }

    public void setAction(StkActions action) {
        this.action = action;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProjectsActions)) {
            return false;
        }
        ProjectsActions other = (ProjectsActions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.test.vaadin.Entities.ProjectsActions[ id=" + id + " ]";
    }
    
}
