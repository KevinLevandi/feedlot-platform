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
@Table(name = "bnature")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bnature.findAll", query = "SELECT b FROM Bnature b"),
    @NamedQuery(name = "Bnature.findByBnatureId", query = "SELECT b FROM Bnature b WHERE b.bnatureId = :bnatureId"),
    @NamedQuery(name = "Bnature.findByName", query = "SELECT b FROM Bnature b WHERE b.name = :name"),
    @NamedQuery(name = "Bnature.findByTstamp", query = "SELECT b FROM Bnature b WHERE b.tstamp = :tstamp")})
public class Bnature implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    
     private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "bnature_id")
    private Integer bnatureId;
    
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

    @OneToMany(mappedBy = "bnatureId")
    private Collection<Animal> animalCollection;
    
    public Bnature() {
    }

    public Bnature(Integer bnatureId) {
        this.bnatureId = bnatureId;
    }

    public Bnature(Integer bnatureId, String name, Date tstamp) {
        this.bnatureId = bnatureId;
        this.name = name;
        this.tstamp = tstamp;
    }

    public Integer getBnatureId() {
        return bnatureId;
    }

    public void setBnatureId(Integer bnatureId) {
        Integer oldConfinementId = this.bnatureId;
        this.bnatureId = bnatureId;
        changeSupport.firePropertyChange("bnatureId", oldConfinementId, bnatureId);
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
        hash += (bnatureId != null ? bnatureId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bnature)) {
            return false;
        }
        Bnature other = (Bnature) object;
        if ((this.bnatureId == null && other.bnatureId != null) || (this.bnatureId != null && !this.bnatureId.equals(other.bnatureId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + "[" + bnatureId + "]";
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
