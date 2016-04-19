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
import javax.persistence.Lob;
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
@Table(name = "teco_reports")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TecoReports.findAll", query = "SELECT t FROM TecoReports t"),
    @NamedQuery(name = "TecoReports.findById", query = "SELECT t FROM TecoReports t WHERE t.id = :id"),
    @NamedQuery(name = "TecoReports.findByReportName", query = "SELECT t FROM TecoReports t WHERE t.reportName = :reportName"),
    @NamedQuery(name = "TecoReports.findByReportDescription", query = "SELECT t FROM TecoReports t WHERE t.reportDescription = :reportDescription")})
public class TecoReports implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "report_name")
    private String reportName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "report_description")
    private String reportDescription;
    @Lob
    @Column(name = "jrxml_csv_file")
    private byte[] jrxmlCsvFile;
    @Lob
    @Column(name = "jrxml_pdf_file")
    private byte[] jrxmlPdfFile;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reportId", fetch = FetchType.LAZY)
    private List<TecoReportParameters> tecoReportParametersList;

    public TecoReports() {
    }

    public TecoReports(Integer id) {
        this.id = id;
    }

    public TecoReports(Integer id, String reportName, String reportDescription) {
        this.id = id;
        this.reportName = reportName;
        this.reportDescription = reportDescription;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public byte[] getJrxmlCsvFile() {
        return jrxmlCsvFile;
    }

    public void setJrxmlCsvFile(byte[] jrxmlCsvFile) {
        this.jrxmlCsvFile = jrxmlCsvFile;
    }

    public byte[] getJrxmlPdfFile() {
        return jrxmlPdfFile;
    }

    public void setJrxmlPdfFile(byte[] jrxmlPdfFile) {
        this.jrxmlPdfFile = jrxmlPdfFile;
    }

    @XmlTransient
    public List<TecoReportParameters> getTecoReportParametersList() {
        return tecoReportParametersList;
    }

    public void setTecoReportParametersList(List<TecoReportParameters> tecoReportParametersList) {
        this.tecoReportParametersList = tecoReportParametersList;
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
        if (!(object instanceof TecoReports)) {
            return false;
        }
        TecoReports other = (TecoReports) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.test.vaadin.Entities.TecoReports[ id=" + id + " ]";
    }
    
}
