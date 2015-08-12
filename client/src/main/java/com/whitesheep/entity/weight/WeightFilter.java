/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.entity.weight;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whitesheep.entity.animal.Animal;
import java.util.Date;

/**
 *
 * @author timotius
 */
public class WeightFilter {
    @JsonIgnore private Animal animal;
    private Date weighingDayFrom;
    private Date weighingDayTo;
    private Double weightFrom;
    private Double weightTo;
    
    private Integer animalId;

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Date getWeighingDayFrom() {
        return weighingDayFrom;
    }

    public void setWeighingDayFrom(Date weighingDayFrom) {
        this.weighingDayFrom = weighingDayFrom;
    }

    public Date getWeighingDayTo() {
        return weighingDayTo;
    }

    public void setWeighingDayTo(Date weighingDayTo) {
        this.weighingDayTo = weighingDayTo;
    }

    public Double getWeightFrom() {
        return weightFrom;
    }

    public void setWeightFrom(Double weightFrom) {
        this.weightFrom = weightFrom;
    }

    public Double getWeightTo() {
        return weightTo;
    }

    public void setWeightTo(Double weightTo) {
        this.weightTo = weightTo;
    }

    public Integer getAnimalId() {
        return animal == null ? null : animal.getAnimalId();
    }

    @Override
    public String toString() {
        return "WeightFilter{" + "animal=" + animal + ", weighingDayFrom=" + weighingDayFrom + ", weighingDayTo=" + weighingDayTo + ", weightFrom=" + weightFrom + ", weightTo=" + weightTo + ", animalId=" + animalId + '}';
    }
}
