/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.platform.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author timotius
 */
@Entity
@Table(name = "confinement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Confinement.findAll", query = "SELECT c FROM Confinement c"),
    @NamedQuery(name = "Confinement.findByConfinementId", query = "SELECT c FROM Confinement c WHERE c.confinementId = :confinementId"),
    @NamedQuery(name = "Confinement.findByName", query = "SELECT c FROM Confinement c WHERE c.name = :name"),
    @NamedQuery(name = "Confinement.findByTstamp", query = "SELECT c FROM Confinement c WHERE c.tstamp = :tstamp")})
public class Confinement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "confinement_id")
    private Integer confinementId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "tstamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tstamp;
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    @ManyToOne
    private User userName;
    @OneToMany(mappedBy = "confinementId")
    private Collection<Animal> animalCollection;

    public Confinement() {
    }

    public Confinement(Integer confinementId) {
        this.confinementId = confinementId;
    }

    public Confinement(Integer confinementId, String name, Date tstamp) {
        this.confinementId = confinementId;
        this.name = name;
        this.tstamp = tstamp;
    }

    public Integer getConfinementId() {
        return confinementId;
    }

    public void setConfinementId(Integer confinementId) {
        this.confinementId = confinementId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        this.tstamp = tstamp;
    }

    public User getUserName() {
        return userName;
    }

    public void setUserName(User userName) {
        this.userName = userName;
    }

    @XmlTransient
    public Collection<Animal> getAnimalCollection() {
        return animalCollection;
    }

    public void setAnimalCollection(Collection<Animal> animalCollection) {
        this.animalCollection = animalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (confinementId != null ? confinementId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Confinement)) {
            return false;
        }
        Confinement other = (Confinement) object;
        if ((this.confinementId == null && other.confinementId != null) || (this.confinementId != null && !this.confinementId.equals(other.confinementId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.whitesheep.platform.api.Confinement[ confinementId=" + confinementId + " ]";
    }
    
}
