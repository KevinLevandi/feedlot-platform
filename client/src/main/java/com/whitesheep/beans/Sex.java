/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.beans;

import java.util.Date;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Sex extends MyBean {
    
    private Integer sexId;
    private String name;
    private Date tstamp;
    private User userName;

    public Integer getSexId() {
        return sexId;
    }

    public void setSexId(Integer sexId) {
        this.sexId = sexId;
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
        final Sex other = (Sex) obj;
        if (!Objects.equals(this.sexId, other.sexId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sex{" + "sexId=" + sexId + ", name=" + name + '}';
    }
    
    @Override
    @XmlTransient
    public String getId() {
        return String.valueOf(sexId);
    }
    
}
