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
@Table(name = "variant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Variant.findAll", query = "SELECT v FROM Variant v"),
    @NamedQuery(name = "Variant.findByVariantId", query = "SELECT v FROM Variant v WHERE v.variantId = :variantId"),
    @NamedQuery(name = "Variant.findByName", query = "SELECT v FROM Variant v WHERE v.name = :name"),
    @NamedQuery(name = "Variant.findByTstamp", query = "SELECT v FROM Variant v WHERE v.tstamp = :tstamp")})
public class Variant implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "variant_id")
    private Integer variantId;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "tstamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tstamp;
    
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    @ManyToOne
    private Type typeId;
    
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    @ManyToOne
    private User userName;
    
    @OneToMany(mappedBy = "variantId")
    private Collection<Animal> animalCollection;

    public Variant() {
    }

    public Variant(Integer variantId) {
        this.variantId = variantId;
    }

    public Variant(Integer variantId, String name, Date tstamp) {
        this.variantId = variantId;
        this.name = name;
        this.tstamp = tstamp;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public void setVariantId(Integer variantId) {
        Integer oldVariantId = this.variantId;
        this.variantId = variantId;
        changeSupport.firePropertyChange("variantId", oldVariantId, variantId);
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

    public Type getTypeId() {
        return typeId;
    }

    public void setTypeId(Type typeId) {
        Type oldTypeId = this.typeId;
        this.typeId = typeId;
        changeSupport.firePropertyChange("typeId", oldTypeId, typeId);
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
        hash += (variantId != null ? variantId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Variant)) {
            return false;
        }
        Variant other = (Variant) object;
        if ((this.variantId == null && other.variantId != null) || (this.variantId != null && !this.variantId.equals(other.variantId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + "[" + variantId + "](" + typeId + ")";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
