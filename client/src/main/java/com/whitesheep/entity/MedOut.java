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
@Table(name = "med_out")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedOut.findAll", query = "SELECT m FROM MedOut m"),
    @NamedQuery(name = "MedOut.findByEventId", query = "SELECT m FROM MedOut m WHERE m.eventId = :eventId"),
    @NamedQuery(name = "MedOut.findByEventDay", query = "SELECT m FROM MedOut m WHERE m.eventDay = :eventDay"),
    @NamedQuery(name = "MedOut.findByAmount", query = "SELECT m FROM MedOut m WHERE m.amount = :amount"),
    @NamedQuery(name = "MedOut.findByTstamp", query = "SELECT m FROM MedOut m WHERE m.tstamp = :tstamp")})
public class MedOut implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "tstamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tstamp;
    @JoinColumn(name = "actor_id", referencedColumnName = "actor_id")
    @ManyToOne
    private Actor actorId;
    @JoinColumn(name = "ailment_id", referencedColumnName = "ailment_id")
    @ManyToOne
    private Ailment ailmentId;
    @JoinColumn(name = "animal_id", referencedColumnName = "animal_id")
    @ManyToOne
    private Animal animalId;
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    @ManyToOne
    private User userName;
    @JoinColumn(name = "medicine_id", referencedColumnName = "medicine_id")
    @ManyToOne(optional = false)
    private Medicine medicineId;

    public MedOut() {
    }

    public MedOut(Integer eventId) {
        this.eventId = eventId;
    }

    public MedOut(Integer eventId, Date eventDay, double amount, Date tstamp) {
        this.eventId = eventId;
        this.eventDay = eventDay;
        this.amount = amount;
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

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        Date oldTstamp = this.tstamp;
        this.tstamp = tstamp;
        changeSupport.firePropertyChange("tstamp", oldTstamp, tstamp);
    }

    public Actor getActorId() {
        return actorId;
    }

    public void setActorId(Actor actorId) {
        Actor oldActorId = this.actorId;
        this.actorId = actorId;
        changeSupport.firePropertyChange("actorId", oldActorId, actorId);
    }

    public Ailment getAilmentId() {
        return ailmentId;
    }

    public void setAilmentId(Ailment ailmentId) {
        Ailment oldAilmentId = this.ailmentId;
        this.ailmentId = ailmentId;
        changeSupport.firePropertyChange("ailmentId", oldAilmentId, ailmentId);
    }

    public Animal getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Animal animalId) {
        Animal oldAnimalId = this.animalId;
        this.animalId = animalId;
        changeSupport.firePropertyChange("animalId", oldAnimalId, animalId);
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventId != null ? eventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedOut)) {
            return false;
        }
        MedOut other = (MedOut) object;
        if ((this.eventId == null && other.eventId != null) || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.whitesheep.entity.MedOut[ eventId=" + eventId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
