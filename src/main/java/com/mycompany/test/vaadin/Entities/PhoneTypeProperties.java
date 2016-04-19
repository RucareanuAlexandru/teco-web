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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "phone_type_properties")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PhoneTypeProperties.findAll", query = "SELECT p FROM PhoneTypeProperties p"),
    @NamedQuery(name = "PhoneTypeProperties.findById", query = "SELECT p FROM PhoneTypeProperties p WHERE p.id = :id"),
    @NamedQuery(name = "PhoneTypeProperties.findByPropertyName", query = "SELECT p FROM PhoneTypeProperties p WHERE p.propertyName = :propertyName"),
    @NamedQuery(name = "PhoneTypeProperties.findByPropertyNameAndCategory",
            query = "SELECT ptp FROM PhoneTypeProperties ptp WHERE ptp.propertyName = :propertyName"
                    + " and ptp.category = :category")})
public class PhoneTypeProperties implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "property_name")
    private String propertyName;
    @JoinColumn(name = "category", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PhoneTypes category;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "property", fetch = FetchType.LAZY)
    private List<ModelProperties> modelPropertiesList;

    public PhoneTypeProperties() {
    }

    public PhoneTypeProperties(Long id) {
        this.id = id;
    }

    public PhoneTypeProperties(Long id, String propertyName) {
        this.id = id;
        this.propertyName = propertyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public PhoneTypes getCategory() {
        return category;
    }

    public void setCategory(PhoneTypes category) {
        this.category = category;
    }

    @XmlTransient
    public List<ModelProperties> getModelPropertiesList() {
        return modelPropertiesList;
    }

    public void setModelPropertiesList(List<ModelProperties> modelPropertiesList) {
        this.modelPropertiesList = modelPropertiesList;
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
        if (!(object instanceof PhoneTypeProperties)) {
            return false;
        }
        PhoneTypeProperties other = (PhoneTypeProperties) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.test.vaadin.Entities.PhoneTypeProperties[ id=" + id + " ]";
    }
    
}
