package com.whitesheep.platform.ui.component;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

public class MultiSelectModel extends AbstractTableModel {
    private Map<Object, Boolean> selectionMap = new LinkedHashMap();
    private List selecteds = new ArrayList();
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    
    public MultiSelectModel() {
    }

    public Map<Object, Boolean> getSelectionMap() {
        return selectionMap;
    }
    
    public List getSelecteds() {
        return selecteds;
    }
    
    private void setSelecteds(List selecteds){
        List oldSelecteds = this.selecteds;
        this.selecteds = selecteds;
        propertyChangeSupport.firePropertyChange("selecteds", oldSelecteds, selecteds);
    }
    
    @Override
    public int getRowCount() {
        return selectionMap.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        List<Object> keyList = new ArrayList(selectionMap.keySet());
        List<Boolean> valueList = new ArrayList(selectionMap.values());
        switch (columnIndex) {
            case 0 :
                return valueList.get(rowIndex);
            case 1 :
                return keyList.get(rowIndex);
            default :
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0){
            List<Object> keyList = new ArrayList(selectionMap.keySet());
            selectionMap.put(keyList.get(rowIndex), (Boolean)aValue);
            List<Boolean> valueList = new ArrayList(selectionMap.values());
            List currentSelecteds = new ArrayList();
            for (int i = 0; i < valueList.size(); i++){
                if (valueList.get(i)) currentSelecteds.add(keyList.get(i));
            }
            setSelecteds(currentSelecteds);
        } else {
            super.setValueAt(aValue, rowIndex, columnIndex);
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? Boolean.class : super.getColumnClass(columnIndex);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
