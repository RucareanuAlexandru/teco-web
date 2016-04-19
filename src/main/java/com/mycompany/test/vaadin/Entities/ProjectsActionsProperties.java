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
@Table(name = "projects_actions_properties")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProjectsActionsProperties.findAll", query = "SELECT p FROM ProjectsActionsProperties p"),
    @NamedQuery(name = "ProjectsActionsProperties.findById", query = "SELECT p FROM ProjectsActionsProperties p WHERE p.id = :id"),
    @NamedQuery(name = "ProjectsActionsProperties.findByProjectAndAction",
            query = "SELECT pap.property FROM ProjectsActionsProperties pap WHERE pap.projectAction.project = :project AND pap.projectAction.action = :action"),
    @NamedQuery(name = "ProjectsActionsProperties.findByProjectAndActionAndProperty",
            query = "SELECT pap FROM ProjectsActionsProperties pap WHERE pap.projectAction.project = :project AND"
                    + " pap.projectAction.action = :action and pap.property = :property")})
public class ProjectsActionsProperties implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "project_action", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ProjectsActions projectAction;
    @JoinColumn(name = "property", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private StkActionProperties property;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectActionProperty", fetch = FetchType.LAZY)
    private List<Behaviours> behavioursList;

    public ProjectsActionsProperties() {
    }

    public ProjectsActionsProperties(ProjectsActions projectAction, StkActionProperties property) {
        this.projectAction = projectAction;
        this.property = property;
    }

    public ProjectsActionsProperties(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectsActions getProjectAction() {
        return projectAction;
    }

    public void setProjectAction(ProjectsActions projectAction) {
        this.projectAction = projectAction;
    }

    public StkActionProperties getProperty() {
        return property;
    }

    public void setProperty(StkActionProperties property) {
        this.property = property;
    }

    @XmlTransient
    public List<Behaviours> getBehavioursList() {
        return behavioursList;
    }

    public void setBehavioursList(List<Behaviours> behavioursList) {
        this.behavioursList = behavioursList;
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
        if (!(object instanceof ProjectsActionsProperties)) {
            return false;
        }
        ProjectsActionsProperties other = (ProjectsActionsProperties) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.test.vaadin.Entities.ProjectsActionsProperties[ id=" + id + " ]";
    }
    
}
