/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.entity.weight;

import com.whitesheep.entity.animal.Animal;
import com.whitesheep.entity.animal.Animal;
import com.whitesheep.entity.User;
import com.whitesheep.entity.User;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author timotius
 */
@Entity
@Table(name = "weight")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Weight.findAll", query = "SELECT w FROM Weight w"),
    @NamedQuery(name = "Weight.findByAnimalId", query = "SELECT w FROM Weight w WHERE w.weightPK.animalId = :animalId"),
    @NamedQuery(name = "Weight.findByWeighingDay", query = "SELECT w FROM Weight w WHERE w.weightPK.weighingDay = :weighingDay"),
    @NamedQuery(name = "Weight.findByWeight", query = "SELECT w FROM Weight w WHERE w.weight = :weight"),
    @NamedQuery(name = "Weight.findByTstamp", query = "SELECT w FROM Weight w WHERE w.tstamp = :tstamp")})
public class Weight implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WeightPK weightPK;
    @Basic(optional = false)
    @Column(name = "weight")
    private double weight;
    @Basic(optional = false)
    @Column(name = "tstamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tstamp;
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    @ManyToOne
    private User userName;
    @JoinColumn(name = "animal_id", referencedColumnName = "animal_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Animal animal;

    public Weight() {
    }

    public Weight(WeightPK weightPK) {
        this.weightPK = weightPK;
    }

    public Weight(WeightPK weightPK, double weight, Date tstamp) {
        this.weightPK = weightPK;
        this.weight = weight;
        this.tstamp = tstamp;
    }

    public Weight(int animalId, Date weighingDay) {
        this.weightPK = new WeightPK(animalId, weighingDay);
    }

    public WeightPK getWeightPK() {
        return weightPK;
    }

    public void setWeightPK(WeightPK weightPK) {
        this.weightPK = weightPK;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        double oldWeight = this.weight;
        this.weight = weight;
        changeSupport.firePropertyChange("weight", oldWeight, weight);
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

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        Animal oldAnimal = this.animal;
        this.animal = animal;
        changeSupport.firePropertyChange("animal", oldAnimal, animal);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (weightPK != null ? weightPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Weight)) {
            return false;
        }
        Weight other = (Weight) object;
        if ((this.weightPK == null && other.weightPK != null) || (this.weightPK != null && !this.weightPK.equals(other.weightPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.whitesheep.report.weight.Weight[ weightPK=" + weightPK + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
