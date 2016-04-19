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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "tacs_otap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TacsOtap.findAll", query = "SELECT t FROM TacsOtap t"),
    @NamedQuery(name = "TacsOtap.findById", query = "SELECT t FROM TacsOtap t WHERE t.id = :id"),
    @NamedQuery(name = "TacsOtap.findByTac", query = "SELECT t FROM TacsOtap t WHERE t.tac = :tac"),
    @NamedQuery(name = "TacsOtap.findByModel", query = "SELECT t FROM TacsOtap t WHERE t.model = :model")})
public class TacsOtap implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "tac")
    private String tac;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "model")
    private String model;

    public TacsOtap() {
    }

    public TacsOtap(Long id) {
        this.id = id;
    }

    public TacsOtap(Long id, String tac, String model) {
        this.id = id;
        this.tac = tac;
        this.model = model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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
        if (!(object instanceof TacsOtap)) {
            return false;
        }
        TacsOtap other = (TacsOtap) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.test.vaadin.Entities.TacsOtap[ id=" + id + " ]";
    }
    
}
