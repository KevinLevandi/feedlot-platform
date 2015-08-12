package com.whitesheep.beans;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Weight extends MyBean {
    protected WeightPK weightPK;
    private double weight;
    private Date tstamp;
    private User userName;
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
        propertyChangeSupport.firePropertyChange("weight", oldWeight, weight);
    }

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        Date oldTstamp = this.tstamp;
        this.tstamp = tstamp;
        propertyChangeSupport.firePropertyChange("tstamp", oldTstamp, tstamp);
    }

    public User getUserName() {
        return userName;
    }

    public void setUserName(User userName) {
        User oldUserName = this.userName;
        this.userName = userName;
        propertyChangeSupport.firePropertyChange("userName", oldUserName, userName);
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        Animal oldAnimal = this.animal;
        this.animal = animal;
        propertyChangeSupport.firePropertyChange("animal", oldAnimal, animal);
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
        return "Weight[ weightPK=" + weightPK + " ]";
    }

    @Override
    public String getId() {
        return String.valueOf(weightPK);
    }
    
}
