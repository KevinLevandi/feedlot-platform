package com.whitesheep.platform.ui.component;

import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MultiSelect extends JScrollPane{
    
    JTable table = new JTable(new MultiSelectModel());
    
    public MultiSelect() {
        setViewportView(table);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.setTableHeader(null);
        table.setShowGrid(false);
    }

    public MultiSelectModel getModel() {
        return (MultiSelectModel)table.getModel();
    }
    
    public void setOptions(List options) {
        for (Object option : options) {
            getModel().getSelectionMap().putIfAbsent(option, Boolean.FALSE);
        }
    }
    
    @Override
    public boolean isEnabled(){
        return table.isEnabled();
    }
    
    @Override
    public void setEnabled(boolean b){
        table.setEnabled(b);
    }
}
