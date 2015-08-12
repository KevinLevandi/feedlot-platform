/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.entity;

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
@Table(name = "medicine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medicine.findAll", query = "SELECT m FROM Medicine m"),
    @NamedQuery(name = "Medicine.findByMedicineId", query = "SELECT m FROM Medicine m WHERE m.medicineId = :medicineId"),
    @NamedQuery(name = "Medicine.findByName", query = "SELECT m FROM Medicine m WHERE m.name = :name"),
    @NamedQuery(name = "Medicine.findByTstamp", query = "SELECT m FROM Medicine m WHERE m.tstamp = :tstamp")})
public class Medicine implements Serializable {
    @Basic(optional = false)
    @Column(name = "amount")
    private double amount;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicineId")
    private Collection<MedIn> medInCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicineId")
    private Collection<MedOut> medOutCollection;
    @JoinColumn(name = "method_id", referencedColumnName = "method_id")
    @ManyToOne
    private Method methodId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicineId")
    private Collection<MedicineEvent> medicineEventCollection;
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "medicine_id")
    private Integer medicineId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "tstamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tstamp;
    @JoinColumn(name = "unit_id", referencedColumnName = "unit_id")
    @ManyToOne
    private Unit unitId;
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    @ManyToOne
    private User userName;

    public Medicine() {
    }

    public Medicine(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public Medicine(Integer medicineId, String name, Date tstamp) {
        this.medicineId = medicineId;
        this.name = name;
        this.tstamp = tstamp;
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        Integer oldMedicineId = this.medicineId;
        this.medicineId = medicineId;
        changeSupport.firePropertyChange("medicineId", oldMedicineId, medicineId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        changeSupport.firePropertyChange("name", oldName, name);
    }

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        Date oldTstamp = this.tstamp;
        this.tstamp = tstamp;
        changeSupport.firePropertyChange("tstamp", oldTstamp, tstamp);
    }

    public Unit getUnitId() {
        return unitId;
    }

    public void setUnitId(Unit unitId) {
        Unit oldUnitId = this.unitId;
        this.unitId = unitId;
        changeSupport.firePropertyChange("unitId", oldUnitId, unitId);
    }

    public User getUserName() {
        return userName;
    }

    public void setUserName(User userName) {
        User oldUserName = this.userName;
        this.userName = userName;
        changeSupport.firePropertyChange("userName", oldUserName, userName);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (medicineId != null ? medicineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medicine)) {
            return false;
        }
        Medicine other = (Medicine) object;
        if ((this.medicineId == null && other.medicineId != null) || (this.medicineId != null && !this.medicineId.equals(other.medicineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.whitesheep.md.Medicine[ medicineId=" + medicineId + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

    @XmlTransient
    public Collection<MedicineEvent> getMedicineEventCollection() {
        return medicineEventCollection;
    }

    public void setMedicineEventCollection(Collection<MedicineEvent> medicineEventCollection) {
        this.medicineEventCollection = medicineEventCollection;
    }

    public Method getMethodId() {
        return methodId;
    }

    public void setMethodId(Method methodId) {
        Method oldMethodId = this.methodId;
        this.methodId = methodId;
        changeSupport.firePropertyChange("methodId", oldMethodId, methodId);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        double oldAmount = this.amount;
        this.amount = amount;
        changeSupport.firePropertyChange("amount", oldAmount, amount);
    }

    @XmlTransient
    public Collection<MedIn> getMedInCollection() {
        return medInCollection;
    }

    public void setMedInCollection(Collection<MedIn> medInCollection) {
        this.medInCollection = medInCollection;
    }

    @XmlTransient
    public Collection<MedOut> getMedOutCollection() {
        return medOutCollection;
    }

    public void setMedOutCollection(Collection<MedOut> medOutCollection) {
        this.medOutCollection = medOutCollection;
    }
    
}
