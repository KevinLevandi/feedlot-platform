package com.whitesheep.platform.api;

import java.util.ArrayList;
import java.util.List;

public class Access {
    
    private String id;
    private Class clazz;
    private List<Boolean> visibilities;
    
    public Access(String id, Class clazz, String permissionStr) {
        this.id = id;
        this.clazz = clazz;
        this.visibilities = strToBoolean(permissionStr);
    }

    private List<Boolean> strToBoolean(String s){
        List<Boolean> booleanList = new ArrayList();
        for (int i = 0; i < s.length(); i++){
            char c = s.charAt(i);        
            if (c == '1'){
                booleanList.add(true);
            } else {
                booleanList.add(false);
            }
        }
        return booleanList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public List<Boolean> getVisibilities() {
        return visibilities;
    }

    public void setVisibilities(List<Boolean> visibilities) {
        this.visibilities = visibilities;
    }

    @Override
    public String toString() {
        return "Access{" + "id=" + id + '}';
    }

}

