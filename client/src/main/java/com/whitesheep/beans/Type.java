package com.whitesheep.beans;

import java.util.Date;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement()
public class Type extends MyBean {
    private Integer typeId;
    private String name;
    private Date tstamp;
    private User userName;

    public Type() {
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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
        final Type other = (Type) obj;
        if (!Objects.equals(this.typeId, other.typeId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Type{" + "typeId=" + typeId + ", name=" + name + '}';
    }

    @Override
    @XmlTransient
    public String getId() {
        return String.valueOf(typeId);
    }
    
    
}
