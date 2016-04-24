/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.test.vaadin.Entities;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "behaviours")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Behaviours.findAll", query = "SELECT b FROM Behaviours b"),
    @NamedQuery(name = "Behaviours.findById", query = "SELECT b FROM Behaviours b WHERE b.id = :id"),
    @NamedQuery(name = "Behaviours.findByPropertyValue", query = "SELECT b FROM Behaviours b WHERE b.propertyValue = :propertyValue"),
    @NamedQuery(name = "Behaviours.findByModelAndProjectActionProperty",
            query = "SELECT b FROM Behaviours b WHERE b.model = :model AND b.projectActionProperty = :projectActionProperty")})
public class Behaviours implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Size(max = 250)
    @Column(name = "property_value")
    private String propertyValue;
    @JoinColumn(name = "reason", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BehaviourReasons reason;
    @JoinColumn(name = "model", referencedColumnName = "model_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Models model;
    @JoinColumn(name = "project_action_property", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ProjectsActionsProperties projectActionProperty;

    public Behaviours() {
    }

    public Behaviours(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public BehaviourReasons getReason() {
        return reason;
    }

    public void setReason(BehaviourReasons reason) {
        this.reason = reason;
    }

    public Models getModel() {
        return model;
    }

    public void setModel(Models model) {
        this.model = model;
    }

    public ProjectsActionsProperties getProjectActionProperty() {
        return projectActionProperty;
    }

    public void setProjectActionProperty(ProjectsActionsProperties projectActionProperty) {
        this.projectActionProperty = projectActionProperty;
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
        if (!(object instanceof Behaviours)) {
            return false;
        }
        Behaviours other = (Behaviours) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.test.vaadin.Entities.Behaviours[ id=" + id + " ]";
    }
    
}
