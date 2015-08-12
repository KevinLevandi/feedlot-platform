package com.whitesheep.beans;

import java.util.Date;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Dnature extends MyBean {
    private Integer dnatureId;
    private String name;
    private Date tstamp;
    private User userName;

    public Integer getDnatureId() {
        return dnatureId;
    }

    public void setDnatureId(Integer dnatureId) {
        this.dnatureId = dnatureId;
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
        final Dnature other = (Dnature) obj;
        if (!Objects.equals(this.dnatureId, other.dnatureId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dnature{" + "dnatureId=" + dnatureId + ", name=" + name + '}';
    }
    
    @Override
    @XmlTransient
    public String getId() {
        return String.valueOf(dnatureId);
    }
    
}
