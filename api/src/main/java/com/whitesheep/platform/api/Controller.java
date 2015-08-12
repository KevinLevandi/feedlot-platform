package com.whitesheep.platform.api;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class Controller<T> {

    private JerseyClient client;
    private Class<T> clazz;
    
    public Controller(Class<T> clazz, String path) {
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
    
    public List<T> readEntities(Object... args){
        Map<String, Object> map = new LinkedHashMap();
        for (int i = 0; i < args.length; i++){
            map.put(String.valueOf(i), args[i]);
        }
        Response response = client.findQueried_JSON(map);
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
