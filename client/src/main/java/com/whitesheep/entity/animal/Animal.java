/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.entity.animal;

import com.whitesheep.entity.Aim;
import com.whitesheep.entity.Bnature;
import com.whitesheep.entity.Confinement;
import com.whitesheep.entity.Customer;
import com.whitesheep.entity.Dnature;
import com.whitesheep.entity.Feed;
import com.whitesheep.entity.Flag;
import com.whitesheep.entity.MedOut;
import com.whitesheep.entity.Sex;
import com.whitesheep.entity.Supplier;
import com.whitesheep.entity.User;
import com.whitesheep.entity.Variant;
import com.whitesheep.entity.weight.Weight;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "animal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Animal.findAll", query = "SELECT a FROM Animal a"),
    @NamedQuery(name = "Animal.findByAnimalId", query = "SELECT a FROM Animal a WHERE a.animalId = :animalId"),
    @NamedQuery(name = "Animal.findByName", query = "SELECT a FROM Animal a WHERE a.name = :name"),
    @NamedQuery(name = "Animal.findByBirthDay", query = "SELECT a FROM Animal a WHERE a.birthDay = :birthDay"),
    @NamedQuery(name = "Animal.findByDeathDay", query = "SELECT a FROM Animal a WHERE a.deathDay = :deathDay"),
    @NamedQuery(name = "Animal.findByBuyDay", query = "SELECT a FROM Animal a WHERE a.buyDay = :buyDay"),
    @NamedQuery(name = "Animal.findByPriceIn", query = "SELECT a FROM Animal a WHERE a.priceIn = :priceIn"),
    @NamedQuery(name = "Animal.findBySellDay", query = "SELECT a FROM Animal a WHERE a.sellDay = :sellDay"),
    @NamedQuery(name = "Animal.findByPriceOut", query = "SELECT a FROM Animal a WHERE a.priceOut = :priceOut"),
    @NamedQuery(name = "Animal.findByGenome", query = "SELECT a FROM Animal a WHERE a.genome = :genome"),
    @NamedQuery(name = "Animal.findByTstamp", query = "SELECT a FROM Animal a WHERE a.tstamp = :tstamp")})
public class Animal implements Serializable {
    @OneToMany(mappedBy = "animalId")
    private Collection<MedOut> medOutCollection;
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "animal_id")
    private Integer animalId;
    @Column(name = "name")
    private String name;
    @Column(name = "birth_day")
    @Temporal(TemporalType.DATE)
    private Date birthDay;
    @Column(name = "death_day")
    @Temporal(TemporalType.DATE)
    private Date deathDay;
    @Column(name = "buy_day")
    @Temporal(TemporalType.DATE)
    private Date buyDay;
    @Column(name = "price_in")
    private Integer priceIn;
    @Column(name = "sell_day")
    @Temporal(TemporalType.DATE)
    private Date sellDay;
    @Column(name = "price_out")
    private Integer priceOut;
    @Column(name = "genome")
    private String genome;
    @Basic(optional = false)
    @Column(name = "tstamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tstamp;
    @JoinColumn(name = "feed_id", referencedColumnName = "feed_id")
    @ManyToOne
    private Feed feedId;
    @JoinColumn(name = "flag_id", referencedColumnName = "flag_id")
    @ManyToOne
    private Flag flagId;
    @JoinColumn(name = "confinement_id", referencedColumnName = "confinement_id")
    @ManyToOne
    private Confinement confinementId;
    @JoinColumn(name = "variant_id", referencedColumnName = "variant_id")
    @ManyToOne
    private Variant variantId;
    @JoinColumn(name = "aim_id", referencedColumnName = "aim_id")
    @ManyToOne
    private Aim aimId;
    @OneToMany(mappedBy = "motherId")
    private Collection<Animal> motherCollection;
    @JoinColumn(name = "mother_id", referencedColumnName = "animal_id")
    @ManyToOne
    private Animal motherId;
    @OneToMany(mappedBy = "fatherId")
    private Collection<Animal> fatherCollection;
    @JoinColumn(name = "father_id", referencedColumnName = "animal_id")
    @ManyToOne
    private Animal fatherId;
    @JoinColumn(name = "sex_id", referencedColumnName = "sex_id")
    @ManyToOne
    private Sex sexId;
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    @ManyToOne
    private User userName;
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplier_id")
    @ManyToOne
    private Supplier supplierId;
    @JoinColumn(name = "dnature_id", referencedColumnName = "dnature_id")
    @ManyToOne
    private Dnature dnatureId;
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne
    private Customer customerId;
    @JoinColumn(name = "bnature_id", referencedColumnName = "bnature_id")
    @ManyToOne
    private Bnature bnatureId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "animal")
    private Collection<Weight> weightCollection;
    
    private Integer loop = 1;

    public Animal() {
    }

    public Animal(Integer animalId) {
        this.animalId = animalId;
    }

    public Animal(Integer animalId, String name, String genome, Date tstamp) {
        this.animalId = animalId;
        this.name = name;
        this.genome = genome;
        this.tstamp = tstamp;
    }

    @XmlTransient
    public Integer getLoop() {
        return loop;
    }

    public void setLoop(Integer loop) {
        Integer oldLoop = this.loop;
        this.loop = loop;
        changeSupport.firePropertyChange("loop", oldLoop, loop);
    }

    public Integer getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Integer animalId) {
        Integer oldAnimalId = this.animalId;
        this.animalId = animalId;
        changeSupport.firePropertyChange("animalId", oldAnimalId, animalId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        changeSupport.firePropertyChange("name", oldName, name);
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        Date oldBirthDay = this.birthDay;
        this.birthDay = birthDay;
        changeSupport.firePropertyChange("birthDay", oldBirthDay, birthDay);
    }

    public Date getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(Date deathDay) {
        Date oldDeathDay = this.deathDay;
        this.deathDay = deathDay;
        changeSupport.firePropertyChange("deathDay", oldDeathDay, deathDay);
    }

    public Date getBuyDay() {
        return buyDay;
    }

    public void setBuyDay(Date buyDay) {
        Date oldBuyDay = this.buyDay;
        this.buyDay = buyDay;
        changeSupport.firePropertyChange("buyDay", oldBuyDay, buyDay);
    }

    public Integer getPriceIn() {
        return priceIn;
    }

    public void setPriceIn(Integer priceIn) {
        Integer oldPriceIn = this.priceIn;
        this.priceIn = priceIn;
        changeSupport.firePropertyChange("priceIn", oldPriceIn, priceIn);
    }

    public Date getSellDay() {
        return sellDay;
    }

    public void setSellDay(Date sellDay) {
        Date oldSellDay = this.sellDay;
        this.sellDay = sellDay;
        changeSupport.firePropertyChange("sellDay", oldSellDay, sellDay);
    }

    public Integer getPriceOut() {
        return priceOut;
    }

    public void setPriceOut(Integer priceOut) {
        Integer oldPriceOut = this.priceOut;
        this.priceOut = priceOut;
        changeSupport.firePropertyChange("priceOut", oldPriceOut, priceOut);
    }

    public String getGenome() {
        return genome;
    }

    public void setGenome(String genome) {
        String oldGenome = this.genome;
        this.genome = genome;
        changeSupport.firePropertyChange("genome", oldGenome, genome);
    }

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        Date oldTstamp = this.tstamp;
        this.tstamp = tstamp;
        changeSupport.firePropertyChange("tstamp", oldTstamp, tstamp);
    }

    public Feed getFeedId() {
        return feedId;
    }

    public void setFeedId(Feed feedId) {
        Feed oldFeedId = this.feedId;
        this.feedId = feedId;
        changeSupport.firePropertyChange("feedId", oldFeedId, feedId);
    }

    public Flag getFlagId() {
        return flagId;
    }

    public void setFlagId(Flag flagId) {
        Flag oldFlagId = this.flagId;
        this.flagId = flagId;
        changeSupport.firePropertyChange("flagId", oldFlagId, flagId);
    }

    public Confinement getConfinementId() {
        return confinementId;
    }

    public void setConfinementId(Confinement confinementId) {
        Confinement oldConfinementId = this.confinementId;
        this.confinementId = confinementId;
        changeSupport.firePropertyChange("confinementId", oldConfinementId, confinementId);
    }

    public Variant getVariantId() {
        return variantId;
    }

    public void setVariantId(Variant variantId) {
        Variant oldVariantId = this.variantId;
        this.variantId = variantId;
        changeSupport.firePropertyChange("variantId", oldVariantId, variantId);
    }

    public Aim getAimId() {
        return aimId;
    }

    public void setAimId(Aim aimId) {
        Aim oldAimId = this.aimId;
        this.aimId = aimId;
        changeSupport.firePropertyChange("aimId", oldAimId, aimId);
    }

    @XmlTransient
    public Collection<Animal> getMotherCollection() {
        return motherCollection;
    }

    public void setMotherCollection(Collection<Animal> motherCollection) {
        this.motherCollection = motherCollection;
    }

    public Animal getMotherId() {
        return motherId;
    }

    public void setMotherId(Animal motherId) {
        Animal oldMotherId = this.motherId;
        this.motherId = motherId;
        changeSupport.firePropertyChange("motherId", oldMotherId, motherId);
    }

    @XmlTransient
    public Collection<Animal> getFatherCollection() {
        return fatherCollection;
    }

    public void setFatherCollection(Collection<Animal> fatherCollection) {
        this.fatherCollection = fatherCollection;
    }

    public Animal getFatherId() {
        return fatherId;
    }

    public void setFatherId(Animal fatherId) {
        Animal oldFatherId = this.fatherId;
        this.fatherId = fatherId;
        changeSupport.firePropertyChange("fatherId", oldFatherId, fatherId);
    }

    public Sex getSexId() {
        return sexId;
    }

    public void setSexId(Sex sexId) {
        Sex oldSexId = this.sexId;
        this.sexId = sexId;
        changeSupport.firePropertyChange("sexId", oldSexId, sexId);
    }

    public User getUserName() {
        return userName;
    }

    public void setUserName(User userName) {
        User oldUserName = this.userName;
        this.userName = userName;
        changeSupport.firePropertyChange("userName", oldUserName, userName);
    }

    public Supplier getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Supplier supplierId) {
        Supplier oldSupplierId = this.supplierId;
        this.supplierId = supplierId;
        changeSupport.firePropertyChange("supplierId", oldSupplierId, supplierId);
    }

    public Dnature getDnatureId() {
        return dnatureId;
    }

    public void setDnatureId(Dnature dnatureId) {
        Dnature oldDnatureId = this.dnatureId;
        this.dnatureId = dnatureId;
        changeSupport.firePropertyChange("dnatureId", oldDnatureId, dnatureId);
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        Customer oldCustomerId = this.customerId;
        this.customerId = customerId;
        changeSupport.firePropertyChange("customerId", oldCustomerId, customerId);
    }

    public Bnature getBnatureId() {
        return bnatureId;
    }

    public void setBnatureId(Bnature bnatureId) {
        Bnature oldBnatureId = this.bnatureId;
        this.bnatureId = bnatureId;
        changeSupport.firePropertyChange("bnatureId", oldBnatureId, bnatureId);
    }

    
    @XmlTransient
    public Collection<Weight> getWeightCollection() {
        return weightCollection;
    }

    public void setWeightCollection(Collection<Weight> weightCollection) {
        this.weightCollection = weightCollection;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (animalId != null ? animalId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Animal)) {
            return false;
        }
        Animal other = (Animal) object;
        if ((this.animalId == null && other.animalId != null) || (this.animalId != null && !this.animalId.equals(other.animalId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + "[" + animalId + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    @XmlTransient
    public Collection<MedOut> getMedOutCollection() {
        return medOutCollection;
    }

    public void setMedOutCollection(Collection<MedOut> medOutCollection) {
        this.medOutCollection = medOutCollection;
    }
    
}
