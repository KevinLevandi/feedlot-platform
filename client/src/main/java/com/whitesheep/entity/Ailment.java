/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.entity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author timotius
 */
@Entity
@Table(name = "ailment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ailment.findAll", query = "SELECT a FROM Ailment a"),
    @NamedQuery(name = "Ailment.findByAilmentId", query = "SELECT a FROM Ailment a WHERE a.ailmentId = :ailmentId"),
    @NamedQuery(name = "Ailment.findByName", query = "SELECT a FROM Ailment a WHERE a.name = :name"),
    @NamedQuery(name = "Ailment.findByTstamp", query = "SELECT a FROM Ailment a WHERE a.tstamp = :tstamp"),
    @NamedQuery(name = "Ailment.findByUserName", query = "SELECT a FROM Ailment a WHERE a.userName = :userName")})
public class Ailment implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ailment_id")
    private Integer ailmentId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "tstamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tstamp;
    @Column(name = "user_name")
    private String userName;
    @OneToMany(mappedBy = "ailmentId")
    private Collection<MedOut> medOutCollection;

    public Ailment() {
    }

    public Ailment(Integer ailmentId) {
        this.ailmentId = ailmentId;
    }

    public Ailment(Integer ailmentId, String name, Date tstamp) {
        this.ailmentId = ailmentId;
        this.name = name;
        this.tstamp = tstamp;
    }

    public Integer getAilmentId() {
        return ailmentId;
    }

    public void setAilmentId(Integer ailmentId) {
        Integer oldAilmentId = this.ailmentId;
        this.ailmentId = ailmentId;
        changeSupport.firePropertyChange("ailmentId", oldAilmentId, ailmentId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        changeSupport.firePropertyChange("name", oldName, name);
    }

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        Date oldTstamp = this.tstamp;
        this.tstamp = tstamp;
        changeSupport.firePropertyChange("tstamp", oldTstamp, tstamp);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        String oldUserName = this.userName;
        this.userName = userName;
        changeSupport.firePropertyChange("userName", oldUserName, userName);
    }

    @XmlTransient
    public Collection<MedOut> getMedOutCollection() {
        return medOutCollection;
    }

    public void setMedOutCollection(Collection<MedOut> medOutCollection) {
        this.medOutCollection = medOutCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ailmentId != null ? ailmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ailment)) {
            return false;
        }
        Ailment other = (Ailment) object;
        if ((this.ailmentId == null && other.ailmentId != null) || (this.ailmentId != null && !this.ailmentId.equals(other.ailmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.whitesheep.entity.Ailment[ ailmentId=" + ailmentId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
