package com.whitesheep.platform.ui;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MultiSelect extends JScrollPane{
    
    JTable table = new JTable(new MultiSelectModel());
    
    public MultiSelect() {
        setViewportView(table);
        table.setTableHeader(null);
        table.setShowGrid(false);
    }

    public MultiSelectModel getModel() {
        return (MultiSelectModel)table.getModel();
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
