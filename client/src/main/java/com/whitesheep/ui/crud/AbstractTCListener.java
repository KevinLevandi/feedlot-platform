package com.whitesheep.ui.crud;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

public abstract class AbstractTCListener implements LookupListener {

    Set<Lookup.Result> resultSet;
    Set<Class> classSet;
    
    public AbstractTCListener() {
        resultSet = new HashSet();
        classSet = new HashSet();
    }
    
    protected void addClass(Class clazz){
        Lookup.Result result = Utilities.actionsGlobalContext().lookupResult(clazz); 
        result.addLookupListener(this);
        resultSet.add(result);
        classSet.add(clazz);
    }
    
    protected void addProvider(TopComponent provider, Class clazz){
        Lookup.Result result = provider.getLookup().lookupResult(clazz);
        result.addLookupListener(this);
        resultSet.add(result);
    }
    
    protected void addProvider(String providerID, Class clazz){
        TopComponent provider = WindowManager.getDefault().findTopComponent(providerID);
        addProvider(provider, clazz);
    }
    
    @Override
    public void resultChanged(LookupEvent le) {
        Lookup.Result res = (Lookup.Result)le.getSource();
        Collection allEntities = res.allInstances(); 
        if (!allEntities.isEmpty()) { 
            Object entity = allEntities.iterator().next();
            entityChanged(entity);
        }
    }
    
    protected abstract void entityChanged(Object neu);
}
