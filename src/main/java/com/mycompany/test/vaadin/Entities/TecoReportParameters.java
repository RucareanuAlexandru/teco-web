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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alex
 */
@Entity
@Table(name = "teco_report_parameters")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TecoReportParameters.findAll", query = "SELECT t FROM TecoReportParameters t"),
    @NamedQuery(name = "TecoReportParameters.findById", query = "SELECT t FROM TecoReportParameters t WHERE t.id = :id"),
    @NamedQuery(name = "TecoReportParameters.findByParameterName", query = "SELECT t FROM TecoReportParameters t WHERE t.parameterName = :parameterName"),
    @NamedQuery(name = "TecoReportParameters.findByParameterLabel", query = "SELECT t FROM TecoReportParameters t WHERE t.parameterLabel = :parameterLabel"),
    @NamedQuery(name = "TecoReportParameters.findByParameterType", query = "SELECT t FROM TecoReportParameters t WHERE t.parameterType = :parameterType"),
    @NamedQuery(name = "TecoReportParameters.findByParameterValue", query = "SELECT t FROM TecoReportParameters t WHERE t.parameterValue = :parameterValue"),
    @NamedQuery(name = "TecoReportParameters.findByUserDefined", query = "SELECT t FROM TecoReportParameters t WHERE t.userDefined = :userDefined")})
public class TecoReportParameters implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "parameter_name")
    private String parameterName;
    @Size(max = 100)
    @Column(name = "parameter_label")
    private String parameterLabel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "parameter_type")
    private String parameterType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "parameter_value")
    private String parameterValue;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_defined")
    private boolean userDefined;
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TecoReports reportId;

    public TecoReportParameters() {
    }

    public TecoReportParameters(Integer id) {
        this.id = id;
    }

    public TecoReportParameters(Integer id, String parameterName, String parameterType, String parameterValue, boolean userDefined) {
        this.id = id;
        this.parameterName = parameterName;
        this.parameterType = parameterType;
        this.parameterValue = parameterValue;
        this.userDefined = userDefined;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterLabel() {
        return parameterLabel;
    }

    public void setParameterLabel(String parameterLabel) {
        this.parameterLabel = parameterLabel;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public boolean getUserDefined() {
        return userDefined;
    }

    public void setUserDefined(boolean userDefined) {
        this.userDefined = userDefined;
    }

    public TecoReports getReportId() {
        return reportId;
    }

    public void setReportId(TecoReports reportId) {
        this.reportId = reportId;
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
        if (!(object instanceof TecoReportParameters)) {
            return false;
        }
        TecoReportParameters other = (TecoReportParameters) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.test.vaadin.Entities.TecoReportParameters[ id=" + id + " ]";
    }
    
}
