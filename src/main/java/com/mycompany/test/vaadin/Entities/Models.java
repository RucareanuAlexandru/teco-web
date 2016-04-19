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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "models")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Models.findAll", query = "SELECT m FROM Models m"),
    @NamedQuery(name = "Models.findByModelId", query = "SELECT m FROM Models m WHERE m.modelId = :modelId"),
    @NamedQuery(name = "Models.findByBrandName", query = "SELECT m FROM Models m WHERE m.brandName = :brandName"),
    @NamedQuery(name = "Models.findByModelName", query = "SELECT m FROM Models m WHERE m.modelName = :modelName")})
public class Models implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "model_id")
    private String modelId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "brand_name")
    private String brandName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "model_name")
    private String modelName;
    @JoinColumn(name = "os", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Os os;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "model", fetch = FetchType.LAZY)
    private List<Tacs> tacsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "model", fetch = FetchType.LAZY)
    private List<ModelProperties> modelPropertiesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "model", fetch = FetchType.LAZY)
    private List<Behaviours> behavioursList;

    public Models() {
    }

    public Models(String modelId) {
        this.modelId = modelId;
    }

    public Models(String modelId, String brandName, String modelName) {
        this.modelId = modelId;
        this.brandName = brandName;
        this.modelName = modelName;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Os getOs() {
        return os;
    }

    public void setOs(Os os) {
        this.os = os;
    }

    @XmlTransient
    public List<Tacs> getTacsList() {
        return tacsList;
    }

    public void setTacsList(List<Tacs> tacsList) {
        this.tacsList = tacsList;
    }

    @XmlTransient
    public List<ModelProperties> getModelPropertiesList() {
        return modelPropertiesList;
    }

    public void setModelPropertiesList(List<ModelProperties> modelPropertiesList) {
        this.modelPropertiesList = modelPropertiesList;
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
        hash += (modelId != null ? modelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Models)) {
            return false;
        }
        Models other = (Models) object;
        if ((this.modelId == null && other.modelId != null) || (this.modelId != null && !this.modelId.equals(other.modelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.test.vaadin.Entities.Models[ modelId=" + modelId + " ]";
    }
    
}
