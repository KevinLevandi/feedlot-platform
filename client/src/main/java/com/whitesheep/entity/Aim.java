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
@Table(name = "aim")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aim.findAll", query = "SELECT a FROM Aim a"),
    @NamedQuery(name = "Aim.findByAimId", query = "SELECT a FROM Aim a WHERE a.aimId = :aimId"),
    @NamedQuery(name = "Aim.findByName", query = "SELECT a FROM Aim a WHERE a.name = :name"),
    @NamedQuery(name = "Aim.findByTstamp", query = "SELECT a FROM Aim a WHERE a.tstamp = :tstamp")})
public class Aim implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "aim_id")
    private Integer aimId;
    
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

    @OneToMany(mappedBy = "aimId")
    private Collection<Animal> animalCollection;
    
    public Aim() {
    }

    public Aim(Integer aimId) {
        this.aimId = aimId;
    }

    public Aim(Integer aimId, String name, Date tstamp) {
        this.aimId = aimId;
        this.name = name;
        this.tstamp = tstamp;
    }

    public Integer getAimId() {
        return aimId;
    }

    public void setAimId(Integer aimId) {
        Integer oldAimId = this.aimId;
        this.aimId = aimId;
        changeSupport.firePropertyChange("aimId", oldAimId, aimId);
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
        hash += (aimId != null ? aimId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aim)) {
            return false;
        }
        Aim other = (Aim) object;
        if ((this.aimId == null && other.aimId != null) || (this.aimId != null && !this.aimId.equals(other.aimId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + "[" + aimId + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
