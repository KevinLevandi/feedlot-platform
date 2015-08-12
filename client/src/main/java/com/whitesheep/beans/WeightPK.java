package com.whitesheep.beans;

import java.util.Date;
import java.util.Objects;

public class WeightPK {
    private int animalId;
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
        int hash = 5;
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
        final WeightPK other = (WeightPK) obj;
        if (this.animalId != other.animalId) {
            return false;
        }
        if (!Objects.equals(this.weighingDay, other.weighingDay)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "animalId=" + animalId + ";weighingDay=" + weighingDay;
    }
    
}
