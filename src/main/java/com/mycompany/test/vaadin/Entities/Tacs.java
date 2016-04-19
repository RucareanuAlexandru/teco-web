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
@Table(name = "tacs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tacs.findAll", query = "SELECT t FROM Tacs t"),
    @NamedQuery(name = "Tacs.findById", query = "SELECT t FROM Tacs t WHERE t.id = :id"),
    @NamedQuery(name = "Tacs.findByTac", query = "SELECT t FROM Tacs t WHERE t.tac = :tac"),
    @NamedQuery(name = "Tacs.findByTerminalProfile", query = "SELECT t FROM Tacs t WHERE t.terminalProfile = :terminalProfile"),
    @NamedQuery(name = "Tacs.findBySnr", query = "SELECT t FROM Tacs t WHERE t.snr = :snr"),
    @NamedQuery(name = "Tacs.findBySource", query = "SELECT t FROM Tacs t WHERE t.source = :source")})
public class Tacs implements Serializable {

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
    @Size(max = 250)
    @Column(name = "terminal_profile")
    private String terminalProfile;
    @Size(max = 6)
    @Column(name = "snr")
    private String snr;
    @Size(max = 5)
    @Column(name = "source")
    private String source;
    @JoinColumn(name = "model", referencedColumnName = "model_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Models model;

    public Tacs() {
    }

    public Tacs(Long id) {
        this.id = id;
    }

    public Tacs(Long id, String tac) {
        this.id = id;
        this.tac = tac;
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

    public String getTerminalProfile() {
        return terminalProfile;
    }

    public void setTerminalProfile(String terminalProfile) {
        this.terminalProfile = terminalProfile;
    }

    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Models getModel() {
        return model;
    }

    public void setModel(Models model) {
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
        if (!(object instanceof Tacs)) {
            return false;
        }
        Tacs other = (Tacs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.test.vaadin.Entities.Tacs[ id=" + id + " ]";
    }
    
}
