/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.platform.api;

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
    @OneToMany(mappedBy = "fatherId")
    private Collection<Animal> animalCollection;
    @JoinColumn(name = "father_id", referencedColumnName = "animal_id")
    @ManyToOne
    private Animal fatherId;
    @JoinColumn(name = "variant_id", referencedColumnName = "variant_id")
    @ManyToOne
    private Variant variantId;
    @JoinColumn(name = "aim_id", referencedColumnName = "aim_id")
    @ManyToOne
    private Aim aimId;
    @JoinColumn(name = "feed_id", referencedColumnName = "feed_id")
    @ManyToOne
    private Feed feedId;
    @OneToMany(mappedBy = "motherId")
    private Collection<Animal> animalCollection1;
    @JoinColumn(name = "mother_id", referencedColumnName = "animal_id")
    @ManyToOne
    private Animal motherId;
    @JoinColumn(name = "flag_id", referencedColumnName = "flag_id")
    @ManyToOne
    private Flag flagId;
    @JoinColumn(name = "sex_id", referencedColumnName = "sex_id")
    @ManyToOne
    private Sex sexId;
    @JoinColumn(name = "confinement_id", referencedColumnName = "confinement_id")
    @ManyToOne
    private Confinement confinementId;
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

    public Animal() {
    }

    public Animal(Integer animalId) {
        this.animalId = animalId;
    }

    public Animal(Integer animalId, Date tstamp) {
        this.animalId = animalId;
        this.tstamp = tstamp;
    }

    public Integer getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Integer animalId) {
        this.animalId = animalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Date getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(Date deathDay) {
        this.deathDay = deathDay;
    }

    public Date getBuyDay() {
        return buyDay;
    }

    public void setBuyDay(Date buyDay) {
        this.buyDay = buyDay;
    }

    public Integer getPriceIn() {
        return priceIn;
    }

    public void setPriceIn(Integer priceIn) {
        this.priceIn = priceIn;
    }

    public Date getSellDay() {
        return sellDay;
    }

    public void setSellDay(Date sellDay) {
        this.sellDay = sellDay;
    }

    public Integer getPriceOut() {
        return priceOut;
    }

    public void setPriceOut(Integer priceOut) {
        this.priceOut = priceOut;
    }

    public String getGenome() {
        return genome;
    }

    public void setGenome(String genome) {
        this.genome = genome;
    }

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        this.tstamp = tstamp;
    }

    @XmlTransient
    public Collection<Animal> getAnimalCollection() {
        return animalCollection;
    }

    public void setAnimalCollection(Collection<Animal> animalCollection) {
        this.animalCollection = animalCollection;
    }

    public Animal getFatherId() {
        return fatherId;
    }

    public void setFatherId(Animal fatherId) {
        this.fatherId = fatherId;
    }

    public Variant getVariantId() {
        return variantId;
    }

    public void setVariantId(Variant variantId) {
        this.variantId = variantId;
    }

    public Aim getAimId() {
        return aimId;
    }

    public void setAimId(Aim aimId) {
        this.aimId = aimId;
    }

    public Feed getFeedId() {
        return feedId;
    }

    public void setFeedId(Feed feedId) {
        this.feedId = feedId;
    }

    @XmlTransient
    public Collection<Animal> getAnimalCollection1() {
        return animalCollection1;
    }

    public void setAnimalCollection1(Collection<Animal> animalCollection1) {
        this.animalCollection1 = animalCollection1;
    }

    public Animal getMotherId() {
        return motherId;
    }

    public void setMotherId(Animal motherId) {
        this.motherId = motherId;
    }

    public Flag getFlagId() {
        return flagId;
    }

    public void setFlagId(Flag flagId) {
        this.flagId = flagId;
    }

    public Sex getSexId() {
        return sexId;
    }

    public void setSexId(Sex sexId) {
        this.sexId = sexId;
    }

    public Confinement getConfinementId() {
        return confinementId;
    }

    public void setConfinementId(Confinement confinementId) {
        this.confinementId = confinementId;
    }

    public User getUserName() {
        return userName;
    }

    public void setUserName(User userName) {
        this.userName = userName;
    }

    public Supplier getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Supplier supplierId) {
        this.supplierId = supplierId;
    }

    public Dnature getDnatureId() {
        return dnatureId;
    }

    public void setDnatureId(Dnature dnatureId) {
        this.dnatureId = dnatureId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public Bnature getBnatureId() {
        return bnatureId;
    }

    public void setBnatureId(Bnature bnatureId) {
        this.bnatureId = bnatureId;
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
        return "com.whitesheep.platform.api.Animal[ animalId=" + animalId + " ]";
    }
    
}
