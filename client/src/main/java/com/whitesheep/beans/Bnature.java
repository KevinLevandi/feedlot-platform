package com.whitesheep.beans;

import java.util.Date;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Bnature extends MyBean {
    private Integer bnatureId;
    private String name;
    private Date tstamp;
    private User userName;

    public Integer getBnatureId() {
        return bnatureId;
    }

    public void setBnatureId(Integer bnatureId) {
        this.bnatureId = bnatureId;
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
        final Bnature other = (Bnature) obj;
        if (!Objects.equals(this.bnatureId, other.bnatureId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bnature{" + "bnatureId=" + bnatureId + ", name=" + name + '}';
    }
    
    @Override
    @XmlTransient
    public String getId() {
        return String.valueOf(bnatureId);
    }
    
}
