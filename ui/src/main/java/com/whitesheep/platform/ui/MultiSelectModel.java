package com.whitesheep.platform.ui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MultiSelectModel extends AbstractTableModel {
    //private String selecteds;
    private Object[][] matrix = new Object[0][2];
    private Object[] selecteds = new Object[0];
    private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    
    public MultiSelectModel() {
    }

    public MultiSelectModel(Object[] options) {
        setOptions(options);
    }
    
    public MultiSelectModel(List options) {
        setOptions(options);
    }

    public final void setOptions(Object[] options){
        matrix = new Object[options.length][2];
        for (int i = 0; i < options.length; i++){
            matrix[i][0] = false;
            matrix[i][1] = options[i];
        }
    }

    public final void setOptions(List options){
        setOptions(options.toArray(new Object[options.size()]));
    }
    
    public List getSelecteds() {
        return Arrays.asList(selecteds);
    }
    
    public void setSelecteds(List selecteds) {
        for (Object[] row : matrix) {
            row[0] = selecteds.contains(row[1]);
        }
        bound(selecteds.toArray(new Object[selecteds.size()]));
    }
    
    private void bound(Object[] selecteds){
        Object[] oldSelecteds = this.selecteds;
        this.selecteds = selecteds;
        propertyChangeSupport.firePropertyChange("selecteds", oldSelecteds, selecteds);
    }
    
    /*public String getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(String selecteds) {
        String oldSelecteds = this.selecteds;
        this.selecteds = selecteds;
        propertyChangeSupport.firePropertyChange("selecteds", oldSelecteds, selecteds);
        if (selecteds != null) decodeSelecteds(selecteds);
    }
    
    public String encodeSelecteds() {
        StringBuilder sb = new StringBuilder();
        for (Object[] row : matrix) {
            boolean b = (boolean) row[0];
            if (b) sb.append('1');
            else sb.append('0');
        }
        return sb.toString();
    }

    public void decodeSelecteds(String selecteds) {
        char[] chars = selecteds.toCharArray();
        for (int i = 0; i < chars.length; i++){
            matrix[i][0] = chars[i] == '1';
        }
        fireTableDataChanged();
    }*/
    
    @Override
    public int getRowCount() {
        return matrix.length;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return matrix[rowIndex][columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0){
            matrix[rowIndex][0] = (boolean)aValue;
            List currentSelecteds = new ArrayList();
            for (Object[] row : matrix){
                if ((boolean)row[0]) currentSelecteds.add(matrix[1]);
            }
            bound(currentSelecteds.toArray(new Object[currentSelecteds.size()]));
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
