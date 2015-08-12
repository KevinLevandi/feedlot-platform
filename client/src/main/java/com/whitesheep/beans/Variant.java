/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.beans;

import com.whitesheep.entity.User;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author timotius
 */
public class Variant extends MyBean{
    private Integer variantId;
    private String name;
    private Date tstamp;
    private Type typeId;
    private User userName;

    public Integer getVariantId() {
        return variantId;
    }

    public void setVariantId(Integer variantId) {
        this.variantId = variantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        this.tstamp = tstamp;
    }

    public Type getTypeId() {
        return typeId;
    }

    public void setTypeId(Type typeId) {
        this.typeId = typeId;
    }

    public User getUserName() {
        return userName;
    }

    public void setUserName(User userName) {
        this.userName = userName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Variant other = (Variant) obj;
        if (!Objects.equals(this.variantId, other.variantId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MyVariant{" + "variantId=" + variantId + ", name=" + name + '}';
    }

    @Override
    public String getId() {
        return variantId.toString();
    }
    
}
