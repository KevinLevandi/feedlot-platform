/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.ui.crud;

import com.whitesheep.beans.MyBean;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;

/**
 *
 * @author timotius
 */
public class Viewer<T extends MyBean> extends TopComponent implements ListSelectionListener, Lookup.Provider {

    private final Class<T> clazz;
    private List<T> list;
    private final InstanceContent dynamicContent = new InstanceContent();
    
    private JScrollPane scrollPane;
    private JTable table;
    private BindingGroup bindingGroup;
    
    
    public Viewer(Class<T> clazz){
        this.clazz = clazz;
        initComponents();
        associateLookup(new AbstractLookup(dynamicContent));
    }
    
    public List<T> getList() {
        return list;
    }

    private void initComponents(){
        list = ObservableCollections.observableList(new ArrayList<T>());
        table = new JTable();
        table.getSelectionModel().addListSelectionListener(this);
        scrollPane = new JScrollPane(table);
        bindingGroup = new BindingGroup();
        
        configureBinding();
        configureLayout();
    }
    
    private void configureBinding(){
        Field fieldList[] = clazz.getDeclaredFields();
        JTableBinding tableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ_WRITE, list, table);
        JTableBinding.ColumnBinding columnBinding;
        for (Field f : fieldList) {
            boolean isTransient = Modifier.isTransient(f.getModifiers());
            Class attrClass = f.getType();
            String attrName = f.getName();
            
            if (!isTransient){
                columnBinding = tableBinding.addColumnBinding(ELProperty.create("${" + attrName + "}"));
                columnBinding.setColumnName(attrName);
                columnBinding.setColumnClass(attrClass);
                columnBinding.setEditable(false);
            }
        }
        bindingGroup.addBinding(tableBinding);
        bindingGroup.bind();
    }
    
    private void configureLayout(){
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(getHorizontalGroup(layout));
        layout.setVerticalGroup(getVerticalGroup(layout));
    }
    
    private GroupLayout.Group getHorizontalGroup(GroupLayout layout){
        GroupLayout.SequentialGroup group = layout.createSequentialGroup();
        group.addContainerGap();
        group.addGroup(getParallelGroup(layout));
        group.addContainerGap();
        return group;
    }
    
    private GroupLayout.Group getVerticalGroup(GroupLayout layout){
        GroupLayout.SequentialGroup group = layout.createSequentialGroup();
        group.addContainerGap();
        group.addGroup(getSequentialGroup(layout));
        group.addContainerGap();
        return group;
    }
    
    private GroupLayout.Group getParallelGroup(GroupLayout layout){
        GroupLayout.ParallelGroup group = layout.createParallelGroup();
        group.addComponent(scrollPane);
        return group;
    }
    
    private GroupLayout.Group getSequentialGroup(GroupLayout layout){
        GroupLayout.SequentialGroup group = layout.createSequentialGroup();
        group.addComponent(scrollPane);
        return group;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && e.getSource() == table.getSelectionModel() && table.getRowSelectionAllowed()){
            int selectedRowIdx = table.getSelectedRow();
            if (selectedRowIdx > -1) {
                int selectedModelIdx = table.convertRowIndexToModel(selectedRowIdx);
                T selectedEntity = list.get(selectedModelIdx);
                dynamicContent.set(Collections.singleton(selectedEntity), null);
            } else {
                dynamicContent.set(Collections.emptyList(), null);
            }
        }
    }
    
}
