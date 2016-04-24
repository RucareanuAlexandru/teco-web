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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "behaviour_reasons")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BehaviourReasons.findAll", query = "SELECT b FROM BehaviourReasons b"),
    @NamedQuery(name = "BehaviourReasons.findById", query = "SELECT b FROM BehaviourReasons b WHERE b.id = :id"),
    @NamedQuery(name = "BehaviourReasons.findByReasonDescription", query = "SELECT b FROM BehaviourReasons b WHERE b.reasonDescription = :reasonDescription")})
public class BehaviourReasons implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "reason_description")
    private String reasonDescription;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reason", fetch = FetchType.LAZY)
    private List<Behaviours> behavioursList;

    public BehaviourReasons() {
    }

    public BehaviourReasons(Long id) {
        this.id = id;
    }

    public BehaviourReasons(Long id, String reasonDescription) {
        this.id = id;
        this.reasonDescription = reasonDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReasonDescription() {
        return reasonDescription;
    }

    public void setReasonDescription(String reasonDescription) {
        this.reasonDescription = reasonDescription;
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
        if (!(object instanceof BehaviourReasons)) {
            return false;
        }
        BehaviourReasons other = (BehaviourReasons) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return reasonDescription;
    }
    
}
