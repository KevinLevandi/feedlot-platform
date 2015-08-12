/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.beans;

import com.whitesheep.entity.Aim;
import com.whitesheep.entity.Confinement;
import com.whitesheep.entity.Customer;
import com.whitesheep.entity.Feed;
import com.whitesheep.entity.Flag;
import com.whitesheep.entity.Supplier;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import java.util.Objects;
import javax.xml.bind.annotation.XmlTransient;

public class Animal extends MyBean {
    private Integer animalId;
    private String name;
    private Date birthDay;
    private Date deathDay;
    private Date buyDay;
    private Date sellDay;
    private Integer priceIn;
    private Integer priceOut;
    private String genome;
    private Date tstamp;
    private Feed feedId;
    private Flag flagId;
    private Confinement confinementId;
    private Variant variantId;
    private Aim aimId;
    private Animal motherId;
    private Animal fatherId;
    private Sex sexId;
    private User userName;
    private Supplier supplierId;
    private Dnature dnatureId;
    private Customer customerId;
    private Bnature bnatureId;
    
    public Animal() {
    }

    public Integer getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Integer animalId) {
        Integer oldAnimalId = this.animalId;
        this.animalId = animalId;
        propertyChangeSupport.firePropertyChange("animalId", oldAnimalId, animalId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        propertyChangeSupport.firePropertyChange("name", oldName, name);
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        Date oldBirthDay = this.birthDay;
        this.birthDay = birthDay;
        propertyChangeSupport.firePropertyChange("birthDay", oldBirthDay, birthDay);
    }

    public Date getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(Date deathDay) {
        Date oldDeathDay = this.deathDay;
        this.deathDay = deathDay;
        propertyChangeSupport.firePropertyChange("deathDay", oldDeathDay, deathDay);
    }

    public Date getBuyDay() {
        return buyDay;
    }

    public void setBuyDay(Date buyDay) {
        Date oldBuyDay = this.buyDay;
        this.buyDay = buyDay;
        propertyChangeSupport.firePropertyChange("buyDay", oldBuyDay, buyDay);
    }

    public Integer getPriceIn() {
        return priceIn;
    }

    public void setPriceIn(Integer priceIn) {
        Integer oldPriceIn = this.priceIn;
        this.priceIn = priceIn;
        propertyChangeSupport.firePropertyChange("priceIn", oldPriceIn, priceIn);
    }

    public Date getSellDay() {
        return sellDay;
    }

    public void setSellDay(Date sellDay) {
        Date oldSellDay = this.sellDay;
        this.sellDay = sellDay;
        propertyChangeSupport.firePropertyChange("sellDay", oldSellDay, sellDay);
    }

    public Integer getPriceOut() {
        return priceOut;
    }

    public void setPriceOut(Integer priceOut) {
        Integer oldPriceOut = this.priceOut;
        this.priceOut = priceOut;
        propertyChangeSupport.firePropertyChange("priceOut", oldPriceOut, priceOut);
    }

    public String getGenome() {
        return genome;
    }

    public void setGenome(String genome) {
        String oldGenome = this.genome;
        this.genome = genome;
        propertyChangeSupport.firePropertyChange("genome", oldGenome, genome);
    }

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        Date oldTstamp = this.tstamp;
        this.tstamp = tstamp;
        propertyChangeSupport.firePropertyChange("tstamp", oldTstamp, tstamp);
    }

    public Feed getFeedId() {
        return feedId;
    }

    public void setFeedId(Feed feedId) {
        Feed oldFeedId = this.feedId;
        this.feedId = feedId;
        propertyChangeSupport.firePropertyChange("feedId", oldFeedId, feedId);
    }

    public Flag getFlagId() {
        return flagId;
    }

    public void setFlagId(Flag flagId) {
        Flag oldFlagId = this.flagId;
        this.flagId = flagId;
        propertyChangeSupport.firePropertyChange("flagId", oldFlagId, flagId);
    }

    public Confinement getConfinementId() {
        return confinementId;
    }

    public void setConfinementId(Confinement confinementId) {
        Confinement oldConfinementId = this.confinementId;
        this.confinementId = confinementId;
        propertyChangeSupport.firePropertyChange("confinementId", oldConfinementId, confinementId);
    }

    public Variant getVariantId() {
        return variantId;
    }

    public void setVariantId(Variant variantId) {
        Variant oldVariantId = this.variantId;
        this.variantId = variantId;
        propertyChangeSupport.firePropertyChange("variantId", oldVariantId, variantId);
    }

    public Aim getAimId() {
        return aimId;
    }

    public void setAimId(Aim aimId) {
        Aim oldAimId = this.aimId;
        this.aimId = aimId;
        propertyChangeSupport.firePropertyChange("aimId", oldAimId, aimId);
    }

    public Animal getMotherId() {
        return motherId;
    }

    public void setMotherId(Animal motherId) {
        Animal oldMotherId = this.motherId;
        this.motherId = motherId;
        propertyChangeSupport.firePropertyChange("motherId", oldMotherId, motherId);
    }

    public Animal getFatherId() {
        return fatherId;
    }

    public void setFatherId(Animal fatherId) {
        Animal oldFatherId = this.fatherId;
        this.fatherId = fatherId;
        propertyChangeSupport.firePropertyChange("fatherId", oldFatherId, fatherId);
    }

    public Sex getSexId() {
        return sexId;
    }

    public void setSexId(Sex sexId) {
        Sex oldSexId = this.sexId;
        this.sexId = sexId;
        propertyChangeSupport.firePropertyChange("sexId", oldSexId, sexId);
    }

    public User getUserName() {
        return userName;
    }

    public void setUserName(User userName) {
        User oldUserName = this.userName;
        this.userName = userName;
        propertyChangeSupport.firePropertyChange("userName", oldUserName, userName);
    }

    public Supplier getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Supplier supplierId) {
        Supplier oldSupplierId = this.supplierId;
        this.supplierId = supplierId;
        propertyChangeSupport.firePropertyChange("supplierId", oldSupplierId, supplierId);
    }

    public Dnature getDnatureId() {
        return dnatureId;
    }

    public void setDnatureId(Dnature dnatureId) {
        Dnature oldDnatureId = this.dnatureId;
        this.dnatureId = dnatureId;
        propertyChangeSupport.firePropertyChange("dnatureId", oldDnatureId, dnatureId);
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        Customer oldCustomerId = this.customerId;
        this.customerId = customerId;
        propertyChangeSupport.firePropertyChange("customerId", oldCustomerId, customerId);
    }

    public Bnature getBnatureId() {
        return bnatureId;
    }

    public void setBnatureId(Bnature bnatureId) {
        Bnature oldBnatureId = this.bnatureId;
        this.bnatureId = bnatureId;
        propertyChangeSupport.firePropertyChange("bnatureId", oldBnatureId, bnatureId);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Animal other = (Animal) obj;
        if (!Objects.equals(this.animalId, other.animalId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Animal{" + "animalId=" + getAnimalId() + ", name=" + getName() + '}';
    }

    @Override
    @XmlTransient
    public String getId() {
        return getAnimalId().toString();
    }
}
