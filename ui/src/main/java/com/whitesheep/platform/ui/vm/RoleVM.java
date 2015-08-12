package com.whitesheep.platform.ui.vm;

import com.whitesheep.platform.api.Role;
import com.whitesheep.platform.api.annotation.DataType;
import com.whitesheep.platform.api.annotation.Property;
import com.whitesheep.platform.ui.tc.RoleTopComponent;
import com.whitesheep.platform.ui.tc.UserTopComponent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class RoleVM {
    private static final Class[] windows = new Class[]
    {
        RoleTopComponent.class, 
        UserTopComponent.class
    };
    private static final String ACCESS_VIEW = "view"; 
    private static final String ACCESS_CREATE = "create"; 
    private static final String ACCESS_UPDATE = "update"; 
    private static final String ACCESS_DELETE = "delete"; 
    
    @Property
    private Integer roleId;
    @Property
    private String name;
    //@Property(type = DataType.MULTI_SELECTION, showFilter = false)
    //private String permission;
    @Property(type = DataType.MULTI_SELECTION, showFilter = false)
    private List<String> accesses;
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    
    public RoleVM() {
    }

    public RoleVM(Role role){
        this.name = role.getName();
        this.roleId = role.getRoleId();
        this.accesses = transformPermission(role.getPermission());
    }

    public List<String> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<String> accesses) {
        List<String> oldAccesses = this.accesses;
        this.accesses = accesses;
        propertyChangeSupport.firePropertyChange("accesses", oldAccesses, accesses);
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
    
    ////
    
    private List transformPermission(String binaryString){
        char[] binaryChars = binaryString.toCharArray();
        List ret = new ArrayList();
        List options = generateOptions();
        for (int i = 0; i < options.size() && i < binaryChars.length; i++){
            ret.add(options.get(i));
        }
        return ret;
    }
    
    public static List generateOptions(){
        List ret = new ArrayList();
        for (Class window : windows){
            Class typeClass = (Class)((ParameterizedType)window.getGenericSuperclass()).getActualTypeArguments()[0];
            ret.add(window.getSimpleName() + " [" + ACCESS_VIEW + "]");
            ret.add(window.getSimpleName() + " [" + ACCESS_CREATE + "]");
            ret.add(window.getSimpleName() + " [" + ACCESS_UPDATE + "]");
            ret.add(window.getSimpleName() + " [" + ACCESS_DELETE + "]");
            Field[] fields = typeClass.getDeclaredFields();
            for (Field f : fields){
                if (f.isAnnotationPresent(Property.class)) ret.add(window.getSimpleName() + " [" + f.getName() + "]");
            }
        }
        return ret;
    }
}
