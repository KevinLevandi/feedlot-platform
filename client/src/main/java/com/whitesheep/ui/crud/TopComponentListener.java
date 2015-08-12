/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.ui.crud;

import com.whitesheep.beans.Animal;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author timotius
 */
public abstract class TopComponentListener extends TopComponent implements LookupListener {

    public TopComponentListener() {
    }
    
    // LookupListener related
    
    private Lookup.Result<Animal> result;
    private Animal selectedEntity;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public Animal getSelectedEntity() {
        return selectedEntity;
    }

    private void setSelectedEntity(Animal neu) {
        Animal old = this.selectedEntity;
        this.selectedEntity = neu;
        propertyChangeSupport.firePropertyChange("selectedEntity", old, neu);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
    
    @Override
    protected void componentOpened() {
        result = WindowManager.getDefault().findTopComponent("AnimalViewer").getLookup().lookupResult(Animal.class);
        result.addLookupListener(this); 
    }

    @Override
    protected void componentClosed() {
        result.removeLookupListener(this); 
    }

    @Override
    public void resultChanged(LookupEvent le) {
        Lookup.Result res = (Lookup.Result)le.getSource();
        Collection allEntities = res.allInstances(); 
        if (!allEntities.isEmpty()) { 
            Object entity = allEntities.iterator().next();
            if (entity instanceof Animal){
                setSelectedEntity((Animal)entity);
                selectedEntityChanged();
            }
        }
    }
    
    protected abstract void selectedEntityChanged();

}
