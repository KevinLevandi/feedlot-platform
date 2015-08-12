/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.entity;

import com.whitesheep.entity.animal.Animal;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "dnature")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dnature.findAll", query = "SELECT d FROM Dnature d"),
    @NamedQuery(name = "Dnature.findByDnatureId", query = "SELECT d FROM Dnature d WHERE d.dnatureId = :dnatureId"),
    @NamedQuery(name = "Dnature.findByName", query = "SELECT d FROM Dnature d WHERE d.name = :name"),
    @NamedQuery(name = "Dnature.findByTstamp", query = "SELECT d FROM Dnature d WHERE d.tstamp = :tstamp")})
public class Dnature implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "dnature_id")
    private Integer dnatureId;
    
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
    
    @OneToMany(mappedBy = "dnatureId")
    private Collection<Animal> animalCollection;
    
    public Dnature() {
    }

    public Dnature(Integer dnatureId) {
        this.dnatureId = dnatureId;
    }

    public Dnature(Integer dnatureId, String name, Date tstamp) {
        this.dnatureId = dnatureId;
        this.name = name;
        this.tstamp = tstamp;
    }

    public Integer getDnatureId() {
        return dnatureId;
    }

    public void setDnatureId(Integer dnatureId) {
        Integer oldConfinementId = this.dnatureId;
        this.dnatureId = dnatureId;
        changeSupport.firePropertyChange("dnatureId", oldConfinementId, dnatureId);
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

    public User getUserName() {
        return userName;
    }

    public void setUserName(User userName) {
        User oldUserName = this.userName;
        this.userName = userName;
        changeSupport.firePropertyChange("userName", oldUserName, userName);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dnatureId != null ? dnatureId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dnature)) {
            return false;
        }
        Dnature other = (Dnature) object;
        if ((this.dnatureId == null && other.dnatureId != null) || (this.dnatureId != null && !this.dnatureId.equals(other.dnatureId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + "[" + dnatureId + "]";
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    @XmlTransient
    public Collection<Animal> getAnimalCollection() {
        return animalCollection;
    }

    public void setAnimalCollection(Collection<Animal> animalCollection) {
        this.animalCollection = animalCollection;
    }
}
