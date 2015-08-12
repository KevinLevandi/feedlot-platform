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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "medicine_event")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedicineEvent.findAll", query = "SELECT m FROM MedicineEvent m"),
    @NamedQuery(name = "MedicineEvent.findByEventId", query = "SELECT m FROM MedicineEvent m WHERE m.eventId = :eventId"),
    @NamedQuery(name = "MedicineEvent.findByEventType", query = "SELECT m FROM MedicineEvent m WHERE m.eventType = :eventType"),
    @NamedQuery(name = "MedicineEvent.findByEventDay", query = "SELECT m FROM MedicineEvent m WHERE m.eventDay = :eventDay"),
    @NamedQuery(name = "MedicineEvent.findByAmount", query = "SELECT m FROM MedicineEvent m WHERE m.amount = :amount"),
    @NamedQuery(name = "MedicineEvent.findByPrice1", query = "SELECT m FROM MedicineEvent m WHERE m.price1 = :price1"),
    @NamedQuery(name = "MedicineEvent.findByPrice2", query = "SELECT m FROM MedicineEvent m WHERE m.price2 = :price2"),
    @NamedQuery(name = "MedicineEvent.findByExpiryDay", query = "SELECT m FROM MedicineEvent m WHERE m.expiryDay = :expiryDay"),
    @NamedQuery(name = "MedicineEvent.findByTstamp", query = "SELECT m FROM MedicineEvent m WHERE m.tstamp = :tstamp")})
public class MedicineEvent implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "event_id")
    private Integer eventId;
    @Basic(optional = false)
    @Column(name = "event_type")
    private boolean eventType;
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
    @Column(name = "expiry_day")
    @Temporal(TemporalType.DATE)
    private Date expiryDay;
    @Basic(optional = false)
    @Column(name = "tstamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tstamp;
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    @ManyToOne
    private User userName;
    @JoinColumn(name = "medicine_id", referencedColumnName = "medicine_id")
    @ManyToOne(optional = false)
    private Medicine medicineId;
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplier_id")
    @ManyToOne
    private Supplier supplierId;

    public MedicineEvent() {
    }

    public MedicineEvent(Integer eventId) {
        this.eventId = eventId;
    }

    public MedicineEvent(Integer eventId, boolean eventType, Date eventDay, double amount, int price1, int price2, Date tstamp) {
        this.eventId = eventId;
        this.eventType = eventType;
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

    public boolean getEventType() {
        return eventType;
    }

    public void setEventType(boolean eventType) {
        boolean oldEventType = this.eventType;
        this.eventType = eventType;
        changeSupport.firePropertyChange("eventType", oldEventType, eventType);
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

    public Date getExpiryDay() {
        return expiryDay;
    }

    public void setExpiryDay(Date expiryDay) {
        Date oldExpiryDay = this.expiryDay;
        this.expiryDay = expiryDay;
        changeSupport.firePropertyChange("expiryDay", oldExpiryDay, expiryDay);
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventId != null ? eventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicineEvent)) {
            return false;
        }
        MedicineEvent other = (MedicineEvent) object;
        if ((this.eventId == null && other.eventId != null) || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.whitesheep.entity.MedicineEvent[ eventId=" + eventId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
