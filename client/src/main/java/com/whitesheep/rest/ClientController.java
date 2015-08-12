/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.rest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author timotius
 */
public class ClientController<T> {

    private JerseyClient client;
    private Class<T> clazz;
    
    public ClientController(Class<T> clazz, String path) {
        super();
        this.clazz = clazz;
        client = new JerseyClient(path);
    }

    public List<T> readEntities(){
        Response response = client.findAll_JSON();
        ParameterizedType parameterizedGenericType = new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { clazz };
            }

            @Override
            public Type getRawType() {
                return List.class;
            }

            @Override
            public Type getOwnerType() {
                return List.class;
            }
        };
        GenericType<List<T>> genericType = new GenericType<List<T>>(parameterizedGenericType){};
        return response.readEntity(genericType);
    }
    
    public void updateEntity(T entity, String id) {
        client.edit_JSON(entity, id);
    }

    public void createEntity(T entity) {
        client.create_JSON(entity);
    }

    public void deleteEntity(String id) {
        client.remove(id);
    }
    
}
