/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.platform.api;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByTstamp", query = "SELECT u FROM User u WHERE u.tstamp = :tstamp"),
    @NamedQuery(name = "User.findByUpdatedAt", query = "SELECT u FROM User u WHERE u.updatedAt = :updatedAt"),
    @NamedQuery(name = "User.findByCreatedAt", query = "SELECT u FROM User u WHERE u.createdAt = :createdAt")})
public class User implements Serializable {
    @OneToMany(mappedBy = "userName")
    private Collection<Flag> flagCollection;
    @OneToMany(mappedBy = "userName")
    private Collection<Aim> aimCollection;
    @OneToMany(mappedBy = "userName")
    private Collection<Sex> sexCollection;
    @OneToMany(mappedBy = "userName")
    private Collection<Bnature> bnatureCollection;
    @OneToMany(mappedBy = "userName")
    private Collection<Type> typeCollection;
    @OneToMany(mappedBy = "userName")
    private Collection<Feed> feedCollection;
    @OneToMany(mappedBy = "userName")
    private Collection<Unit> unitCollection;
    @OneToMany(mappedBy = "userName")
    private Collection<Confinement> confinementCollection;
    @OneToMany(mappedBy = "userName")
    private Collection<Supplier> supplierCollection;
    @OneToMany(mappedBy = "userName")
    private Collection<Variant> variantCollection;
    @OneToMany(mappedBy = "userName")
    private Collection<Animal> animalCollection;
    @OneToMany(mappedBy = "userName")
    private Collection<Dnature> dnatureCollection;
    @OneToMany(mappedBy = "userName")
    private Collection<Customer> customerCollection;
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "user_name")
    private String userName;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "tstamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tstamp;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "role", referencedColumnName = "role_id")
    @ManyToOne
    private Role role;

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String password, Date tstamp) {
        this.userName = userName;
        this.password = password;
        this.tstamp = tstamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        String oldUserName = this.userName;
        this.userName = userName;
        changeSupport.firePropertyChange("userName", oldUserName, userName);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String oldPassword = this.password;
        this.password = password;
        changeSupport.firePropertyChange("password", oldPassword, password);
    }

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        Date oldTstamp = this.tstamp;
        this.tstamp = tstamp;
        changeSupport.firePropertyChange("tstamp", oldTstamp, tstamp);
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        Date oldUpdatedAt = this.updatedAt;
        this.updatedAt = updatedAt;
        changeSupport.firePropertyChange("updatedAt", oldUpdatedAt, updatedAt);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        Date oldCreatedAt = this.createdAt;
        this.createdAt = createdAt;
        changeSupport.firePropertyChange("createdAt", oldCreatedAt, createdAt);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        Role oldRole = this.role;
        this.role = role;
        changeSupport.firePropertyChange("role", oldRole, role);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userName != null ? userName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userName == null && other.userName != null) || (this.userName != null && !this.userName.equals(other.userName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.whitesheep.platform.api.User[ userName=" + userName + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    @XmlTransient
    public Collection<Flag> getFlagCollection() {
        return flagCollection;
    }

    public void setFlagCollection(Collection<Flag> flagCollection) {
        this.flagCollection = flagCollection;
    }

    @XmlTransient
    public Collection<Aim> getAimCollection() {
        return aimCollection;
    }

    public void setAimCollection(Collection<Aim> aimCollection) {
        this.aimCollection = aimCollection;
    }

    @XmlTransient
    public Collection<Sex> getSexCollection() {
        return sexCollection;
    }

    public void setSexCollection(Collection<Sex> sexCollection) {
        this.sexCollection = sexCollection;
    }

    @XmlTransient
    public Collection<Bnature> getBnatureCollection() {
        return bnatureCollection;
    }

    public void setBnatureCollection(Collection<Bnature> bnatureCollection) {
        this.bnatureCollection = bnatureCollection;
    }

    @XmlTransient
    public Collection<Type> getTypeCollection() {
        return typeCollection;
    }

    public void setTypeCollection(Collection<Type> typeCollection) {
        this.typeCollection = typeCollection;
    }

    @XmlTransient
    public Collection<Feed> getFeedCollection() {
        return feedCollection;
    }

    public void setFeedCollection(Collection<Feed> feedCollection) {
        this.feedCollection = feedCollection;
    }

    @XmlTransient
    public Collection<Unit> getUnitCollection() {
        return unitCollection;
    }

    public void setUnitCollection(Collection<Unit> unitCollection) {
        this.unitCollection = unitCollection;
    }

    @XmlTransient
    public Collection<Confinement> getConfinementCollection() {
        return confinementCollection;
    }

    public void setConfinementCollection(Collection<Confinement> confinementCollection) {
        this.confinementCollection = confinementCollection;
    }

    @XmlTransient
    public Collection<Supplier> getSupplierCollection() {
        return supplierCollection;
    }

    public void setSupplierCollection(Collection<Supplier> supplierCollection) {
        this.supplierCollection = supplierCollection;
    }

    @XmlTransient
    public Collection<Variant> getVariantCollection() {
        return variantCollection;
    }

    public void setVariantCollection(Collection<Variant> variantCollection) {
        this.variantCollection = variantCollection;
    }

    @XmlTransient
    public Collection<Animal> getAnimalCollection() {
        return animalCollection;
    }

    public void setAnimalCollection(Collection<Animal> animalCollection) {
        this.animalCollection = animalCollection;
    }

    @XmlTransient
    public Collection<Dnature> getDnatureCollection() {
        return dnatureCollection;
    }

    public void setDnatureCollection(Collection<Dnature> dnatureCollection) {
        this.dnatureCollection = dnatureCollection;
    }

    @XmlTransient
    public Collection<Customer> getCustomerCollection() {
        return customerCollection;
    }

    public void setCustomerCollection(Collection<Customer> customerCollection) {
        this.customerCollection = customerCollection;
    }
    
}
