/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.entity.animal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whitesheep.entity.Aim;
import com.whitesheep.entity.Bnature;
import com.whitesheep.entity.Confinement;
import com.whitesheep.entity.Customer;
import com.whitesheep.entity.Dnature;
import com.whitesheep.entity.Feed;
import com.whitesheep.entity.Flag;
import com.whitesheep.entity.Sex;
import com.whitesheep.entity.Supplier;
import com.whitesheep.entity.Variant;
import java.util.Date;

/**
 *
 * @author timotius
 */
public class AnimalFilter {
    
    private String name;
    @JsonIgnore private Variant variant;
    @JsonIgnore private Sex sex;
    private Date birthDayFrom;
    private Date birthDayTo;
    @JsonIgnore private Bnature bnature;
    private Date deathDayFrom;
    private Date deathDayTo;
    @JsonIgnore private Dnature dnature;
    private Date buyDayFrom;
    private Date buyDayTo;
    private Integer priceInFrom;
    private Integer priceInTo;
    @JsonIgnore private Supplier supplier;
    private Date sellDayFrom;
    private Date sellDayTo;
    private Integer priceOutFrom;
    private Integer priceOutTo;
    @JsonIgnore private Customer customer;
    @JsonIgnore private Feed feed;
    @JsonIgnore private Confinement confinement;
    @JsonIgnore private Aim aim;
    @JsonIgnore private Flag flag;
    @JsonIgnore private Animal mother;
    @JsonIgnore private Animal father;

    private Integer variantId;
    private Integer sexId;
    private Integer bnatureId;
    private Integer dnatureId;
    private Integer supplierId;
    private Integer customerId;
    private Integer feedId;
    private Integer confinementId;
    private Integer aimId;
    private Integer flagId;
    private Integer motherId;
    private Integer fatherId;
    
    public AnimalFilter() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getBirthDayFrom() {
        return birthDayFrom;
    }

    public void setBirthDayFrom(Date birthDayFrom) {
        this.birthDayFrom = birthDayFrom;
    }

    public Date getBirthDayTo() {
        return birthDayTo;
    }

    public void setBirthDayTo(Date birthDayTo) {
        this.birthDayTo = birthDayTo;
    }

    public Bnature getBnature() {
        return bnature;
    }

    public void setBnature(Bnature bnature) {
        this.bnature = bnature;
    }

    public Date getDeathDayFrom() {
        return deathDayFrom;
    }

    public void setDeathDayFrom(Date deathDayFrom) {
        this.deathDayFrom = deathDayFrom;
    }

    public Date getDeathDayTo() {
        return deathDayTo;
    }

    public void setDeathDayTo(Date deathDayTo) {
        this.deathDayTo = deathDayTo;
    }

    public Dnature getDnature() {
        return dnature;
    }

    public void setDnature(Dnature dnature) {
        this.dnature = dnature;
    }

    public Date getBuyDayFrom() {
        return buyDayFrom;
    }

    public void setBuyDayFrom(Date buyDayFrom) {
        this.buyDayFrom = buyDayFrom;
    }

    public Date getBuyDayTo() {
        return buyDayTo;
    }

    public void setBuyDayTo(Date buyDayTo) {
        this.buyDayTo = buyDayTo;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Integer getPriceInFrom() {
        return priceInFrom;
    }

    public void setPriceInFrom(Integer priceInFrom) {
        this.priceInFrom = priceInFrom;
    }

    public Integer getPriceInTo() {
        return priceInTo;
    }

    public void setPriceInTo(Integer priceInTo) {
        this.priceInTo = priceInTo;
    }

    public Date getSellDayFrom() {
        return sellDayFrom;
    }

    public void setSellDayFrom(Date sellDayFrom) {
        this.sellDayFrom = sellDayFrom;
    }

    public Date getSellDayTo() {
        return sellDayTo;
    }

    public void setSellDayTo(Date sellDayTo) {
        this.sellDayTo = sellDayTo;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getPriceOutFrom() {
        return priceOutFrom;
    }

    public void setPriceOutFrom(Integer priceOutFrom) {
        this.priceOutFrom = priceOutFrom;
    }

    public Integer getPriceOutTo() {
        return priceOutTo;
    }

    public void setPriceOutTo(Integer priceOutTo) {
        this.priceOutTo = priceOutTo;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Confinement getConfinement() {
        return confinement;
    }

    public void setConfinement(Confinement confinement) {
        this.confinement = confinement;
    }

    public Aim getAim() {
        return aim;
    }

    public void setAim(Aim aim) {
        this.aim = aim;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public Animal getMother() {
        return mother;
    }

    public void setMother(Animal mother) {
        this.mother = mother;
    }

    public Animal getFather() {
        return father;
    }

    public void setFather(Animal father) {
        this.father = father;
    }

    @Override
    public String toString() {
        return "AnimalFilter{" + "name=" + name + ", variant=" + variant + ", sex=" + sex + ", birthDayFrom=" + birthDayFrom + ", birthDayTo=" + birthDayTo + ", bnature=" + bnature + ", deathDayFrom=" + deathDayFrom + ", deathDayTo=" + deathDayTo + ", dnature=" + dnature + ", buyDayFrom=" + buyDayFrom + ", buyDayTo=" + buyDayTo + ", supplier=" + supplier + ", priceInFrom=" + priceInFrom + ", priceInTo=" + priceInTo + ", sellDayFrom=" + sellDayFrom + ", sellDayTo=" + sellDayTo + ", customer=" + customer + ", priceOutFrom=" + priceOutFrom + ", priceOutTo=" + priceOutTo + ", feed=" + feed + ", confinement=" + confinement + ", aim=" + aim + ", flag=" + flag + ", mother=" + mother + ", father=" + father + '}';
    }

    public Integer getVariantId() {
        return variant == null ? null : variant.getVariantId();
    }

    public Integer getSexId() {
        return sex == null ? null : sex.getSexId();
    }

    public Integer getBnatureId() {
        return bnature == null ? null : bnature.getBnatureId();
    }

    public Integer getDnatureId() {
        return dnature == null ? null : dnature.getDnatureId();
    }

    public Integer getSupplierId() {
        return supplier == null ? null : supplier.getSupplierId();
    }

    public Integer getCustomerId() {
        return customer == null ? null : supplier.getSupplierId();
    }

    public Integer getFeedId() {
        return feed == null ? null : feed.getFeedId();
    }

    public Integer getConfinementId() {
        return confinement == null ? null : confinement.getConfinementId();
    }

    public Integer getAimId() {
        return aim == null ? null : aim.getAimId();
    }

    public Integer getFlagId() {
        return flag == null ? null : flag.getFlagId();
    }

    public Integer getMotherId() {
        return mother == null ? null : mother.getAnimalId();
    }

    public Integer getFatherId() {
        return father == null ? null : father.getAnimalId();
    }
    
}
