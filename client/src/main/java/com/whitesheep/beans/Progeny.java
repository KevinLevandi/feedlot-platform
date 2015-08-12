/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.beans;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.whitesheep.entity.animal.Animal;
import java.io.IOException;
import org.openide.util.Exceptions;

/**
 *
 * @author timotius
 */
public class Progeny extends MyBean {
    
    public Progeny() {
        ObjectMapper mapper = new ObjectMapper();
        
        String genome = "";
        JsonNode jsonNode = mapper.createObjectNode();
        try {
            jsonNode = mapper.readTree(genome);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } finally {
            
        }
    }

    @Override
    public String getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
