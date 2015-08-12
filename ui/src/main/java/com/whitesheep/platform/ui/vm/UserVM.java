/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.platform.ui.vm;

import com.whitesheep.platform.api.Role;
import com.whitesheep.platform.api.annotation.Property;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

/**
 *
 * @author timotius
 */
public class UserVM {
    private final transient PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    @Property
    private String userName;
    @Property
    private String password;
    private Date tstamp;
    private Date updatedAt;
    private Date createdAt;
    @Property
    private Role role;

    public UserVM() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        String oldUserName = this.userName;
        this.userName = userName;
        changeSupport.firePropertyChange("userName", oldUserName, userName);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String oldPassword = this.password;
        this.password = password;
        changeSupport.firePropertyChange("password", oldPassword, password);
    }

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        Date oldTstamp = this.tstamp;
        this.tstamp = tstamp;
        changeSupport.firePropertyChange("tstamp", oldTstamp, tstamp);
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        Date oldUpdatedAt = this.updatedAt;
        this.updatedAt = updatedAt;
        changeSupport.firePropertyChange("updatedAt", oldUpdatedAt, updatedAt);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        Date oldCreatedAt = this.createdAt;
        this.createdAt = createdAt;
        changeSupport.firePropertyChange("createdAt", oldCreatedAt, createdAt);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        Role oldRole = this.role;
        this.role = role;
        changeSupport.firePropertyChange("role", oldRole, role);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
}
