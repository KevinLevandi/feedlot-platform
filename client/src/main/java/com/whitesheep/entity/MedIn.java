/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.entity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "med_in")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedIn.findAll", query = "SELECT m FROM MedIn m"),
    @NamedQuery(name = "MedIn.findByEventId", query = "SELECT m FROM MedIn m WHERE m.eventId = :eventId"),
    @NamedQuery(name = "MedIn.findByEventDay", query = "SELECT m FROM MedIn m WHERE m.eventDay = :eventDay"),
    @NamedQuery(name = "MedIn.findByAmount", query = "SELECT m FROM MedIn m WHERE m.amount = :amount"),
    @NamedQuery(name = "MedIn.findByPrice1", query = "SELECT m FROM MedIn m WHERE m.price1 = :price1"),
    @NamedQuery(name = "MedIn.findByPrice2", query = "SELECT m FROM MedIn m WHERE m.price2 = :price2"),
    @NamedQuery(name = "MedIn.findByTstamp", query = "SELECT m FROM MedIn m WHERE m.tstamp = :tstamp")})
public class MedIn implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "event_id")
    private Integer eventId;
    @Basic(optional = false)
    @Column(name = "event_day")
    @Temporal(TemporalType.DATE)
    private Date eventDay;
    @Basic(optional = false)
    @Column(name = "amount")
    private double amount;
    @Basic(optional = false)
    @Column(name = "price_1")
    private int price1;
    @Basic(optional = false)
    @Column(name = "price_2")
    private int price2;
    @Basic(optional = false)
    @Column(name = "tstamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tstamp;
    @JoinColumn(name = "medicine_id", referencedColumnName = "medicine_id")
    @ManyToOne(optional = false)
    private Medicine medicineId;
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplier_id")
    @ManyToOne
    private Supplier supplierId;
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    @ManyToOne
    private User userName;
    @JoinColumn(name = "actor_id", referencedColumnName = "actor_id")
    @ManyToOne
    private Actor actorId;

    public MedIn() {
    }

    public MedIn(Integer eventId) {
        this.eventId = eventId;
    }

    public MedIn(Integer eventId, Date eventDay, double amount, int price1, int price2, Date tstamp) {
        this.eventId = eventId;
        this.eventDay = eventDay;
        this.amount = amount;
        this.price1 = price1;
        this.price2 = price2;
        this.tstamp = tstamp;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        Integer oldEventId = this.eventId;
        this.eventId = eventId;
        changeSupport.firePropertyChange("eventId", oldEventId, eventId);
    }

    public Date getEventDay() {
        return eventDay;
    }

    public void setEventDay(Date eventDay) {
        Date oldEventDay = this.eventDay;
        this.eventDay = eventDay;
        changeSupport.firePropertyChange("eventDay", oldEventDay, eventDay);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        double oldAmount = this.amount;
        this.amount = amount;
        changeSupport.firePropertyChange("amount", oldAmount, amount);
    }

    public int getPrice1() {
        return price1;
    }

    public void setPrice1(int price1) {
        int oldPrice1 = this.price1;
        this.price1 = price1;
        changeSupport.firePropertyChange("price1", oldPrice1, price1);
    }

    public int getPrice2() {
        return price2;
    }

    public void setPrice2(int price2) {
        int oldPrice2 = this.price2;
        this.price2 = price2;
        changeSupport.firePropertyChange("price2", oldPrice2, price2);
    }

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        Date oldTstamp = this.tstamp;
        this.tstamp = tstamp;
        changeSupport.firePropertyChange("tstamp", oldTstamp, tstamp);
    }

    public Medicine getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Medicine medicineId) {
        Medicine oldMedicineId = this.medicineId;
        this.medicineId = medicineId;
        changeSupport.firePropertyChange("medicineId", oldMedicineId, medicineId);
    }

    public Supplier getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Supplier supplierId) {
        Supplier oldSupplierId = this.supplierId;
        this.supplierId = supplierId;
        changeSupport.firePropertyChange("supplierId", oldSupplierId, supplierId);
    }

    public User getUserName() {
        return userName;
    }

    public void setUserName(User userName) {
        User oldUserName = this.userName;
        this.userName = userName;
        changeSupport.firePropertyChange("userName", oldUserName, userName);
    }

    public Actor getActorId() {
        return actorId;
    }

    public void setActorId(Actor actorId) {
        Actor oldActorId = this.actorId;
        this.actorId = actorId;
        changeSupport.firePropertyChange("actorId", oldActorId, actorId);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventId != null ? eventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedIn)) {
            return false;
        }
        MedIn other = (MedIn) object;
        if ((this.eventId == null && other.eventId != null) || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.whitesheep.entity.MedIn[ eventId=" + eventId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
