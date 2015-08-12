/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.entity.weight;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author timotius
 */
@Embeddable
public class WeightPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "animal_id")
    private int animalId;
    @Basic(optional = false)
    @Column(name = "weighing_day")
    @Temporal(TemporalType.DATE)
    private Date weighingDay;

    public WeightPK() {
    }

    public WeightPK(int animalId, Date weighingDay) {
        this.animalId = animalId;
        this.weighingDay = weighingDay;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public Date getWeighingDay() {
        return weighingDay;
    }

    public void setWeighingDay(Date weighingDay) {
        this.weighingDay = weighingDay;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) animalId;
        hash += (weighingDay != null ? weighingDay.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WeightPK)) {
            return false;
        }
        WeightPK other = (WeightPK) object;
        if (this.animalId != other.animalId) {
            return false;
        }
        if ((this.weighingDay == null && other.weighingDay != null) || (this.weighingDay != null && !this.weighingDay.equals(other.weighingDay))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.whitesheep.report.weight.WeightPK[ animalId=" + animalId + ", weighingDay=" + weighingDay + " ]";
    }
    
}
