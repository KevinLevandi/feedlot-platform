/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.platform.api;

/**
 *
 * @author timotius
 */
public class Root {
    
    private static Root instance = null;
    private User currentUser;
    
    private Root(){
        
    }
    
    public static Root getInstance() {
        if(instance == null) {
            instance = new Root();
        }
        return instance;
    }
    
    public User getCurrentUser(){
        return currentUser;
    }
    
    public void setCurrentUser(User user){
        currentUser = user;
    }
}
