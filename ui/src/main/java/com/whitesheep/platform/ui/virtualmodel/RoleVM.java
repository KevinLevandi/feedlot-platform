package com.whitesheep.platform.ui.virtualmodel;

import com.whitesheep.platform.api.Role;
import com.whitesheep.platform.api.annotation.DataType;
import com.whitesheep.platform.api.annotation.Property;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class RoleVM {
    @Property(name = "Role.ID")
    private Integer roleId;
    @Property(name = "Role.ROLE")
    private String name;
    @Property(name = "Role.PERMISSIONS", inputType = DataType.MULTI_SELECTION, hasInputFilter = false)
    private List permissions;
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    
    public RoleVM() {
    }

    public List getPermissions() {
        return permissions;
    }

    public void setPermissions(List permissions) {
        List oldAccesses = this.permissions;
        this.permissions = permissions;
        propertyChangeSupport.firePropertyChange("permissions", oldAccesses, permissions);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        propertyChangeSupport.firePropertyChange("name", oldName, name);
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        Integer oldRoleId = this.roleId;
        this.roleId = roleId;
        propertyChangeSupport.firePropertyChange("roleId", oldRoleId, roleId);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    
}
