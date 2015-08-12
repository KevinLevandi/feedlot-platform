/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.platform.ui;

import com.whitesheep.platform.api.annotation.DataType;
import com.whitesheep.platform.api.annotation.Property;
import java.awt.GridBagLayout;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.swingx.JXDatePicker;
import org.openide.windows.TopComponent;

/**
 *
 * @author timotius
 */
public abstract class AbstractTopComponent<T> extends TopComponent {
    
    private final JTable table = new JTable();
    private final JScrollPane scrollPane = new JScrollPane(table);
    private final Map<String, JLabel> filterLabels = new LinkedHashMap();
    private final Map<String, JComponent> filterFields = new LinkedHashMap();
    private final Map<String, JLabel> inputLabels = new LinkedHashMap();
    private final Map<String, JComponent> inputFields = new LinkedHashMap();
    private final JButton newButton = new JButton("New");
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");
    private String permission = ":)";
    private Class<T> classReference;
    private final List<T> tableContent = ObservableCollections.observableList(new ArrayList());
    private T filter;
    private int arrangement = 2;
    public static final int FORM_TOP = 0;
    public static final int FORM_LEFT = 1;
    public static final int FORM_BOTTOM = 2;
    public static final int FORM_RIGHT = 3;

    public AbstractTopComponent() {
        initComponents();
    }
    
    public AbstractTopComponent(Class<T> classReference) throws InstantiationException, IllegalAccessException {
        //permission = Root.getInstance().getCurrentUser().getRole() == null ? "" : Root.getInstance().getCurrentUser().getRole().getPermission();
        initComponents();
        setClassReference(classReference);
        this.filter = classReference.newInstance();
    }

    protected final void setClassReference(Class<T> classReference) throws InstantiationException, IllegalAccessException {
        this.classReference = classReference;
        this.filter = classReference.newInstance();
        configurePropertyMaterialization();
    }

    protected final void setArrangement(int arrangement) {
        this.arrangement = arrangement;
    }

    public List<T> getTableContent() {
        return tableContent;
    }

    public Map<String, JLabel> getFilterLabels() {
        return filterLabels;
    }

    public Map<String, JComponent> getFilterFields() {
        return filterFields;
    }

    public Map<String, JLabel> getInputLabels() {
        return inputLabels;
    }

    public Map<String, JComponent> getInputFields() {
        return inputFields;
    }
    
    private void initComponents(){
        draw();
    }

    public T getFilter() {
        return filter;
    }

    public void setFilter(T filter) {
        this.filter = filter;
    }
    
    protected void draw(){
        if (!permission.isEmpty()){
            drawLayout();
        } else {
            drawLayoutAccessDenied();
        }
    }
    
    private void drawLayoutAccessDenied(){
        this.removeAll();
        this.setLayout(new GridBagLayout());
        this.add(new JLabel("You don't have permission to access this component"));
    }
    
    protected void drawLayout(){
        this.removeAll();
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        if (classReference != null){

            GroupLayout.Group hgroup; 
            if (arrangement == 1 || arrangement == 3) {
                hgroup = layout.createSequentialGroup();
            } else {
                hgroup = layout.createParallelGroup();
            }
            GroupLayout.ParallelGroup filterLabelParallelGroup = layout.createParallelGroup();
            for (Map.Entry<String, JLabel> entry : filterLabels.entrySet()){
                filterLabelParallelGroup.addComponent(entry.getValue());
            }
            GroupLayout.ParallelGroup filterFieldParallelGroup = layout.createParallelGroup();
            for (Map.Entry<String, JComponent> entry : filterFields.entrySet()){
                filterFieldParallelGroup.addComponent(entry.getValue());
            }
            GroupLayout.SequentialGroup filterLabelFieldSequentialGroup = layout.createSequentialGroup();
            filterLabelFieldSequentialGroup.addGroup(filterLabelParallelGroup);
            filterLabelFieldSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
            filterLabelFieldSequentialGroup.addGroup(filterFieldParallelGroup);
            GroupLayout.ParallelGroup viewParallelGroup = layout.createParallelGroup();
            viewParallelGroup.addGroup(filterLabelFieldSequentialGroup);
            viewParallelGroup.addComponent(scrollPane);
            GroupLayout.ParallelGroup inputLabelParallelGroup = layout.createParallelGroup();
            for (Map.Entry<String, JLabel> entry : inputLabels.entrySet()){
                inputLabelParallelGroup.addComponent(entry.getValue());
            }
            GroupLayout.ParallelGroup inputFieldParallelGroup = layout.createParallelGroup();
            for (Map.Entry<String, JComponent> entry : inputFields.entrySet()){
                inputFieldParallelGroup.addComponent(entry.getValue());
            }
            GroupLayout.SequentialGroup inputLabelFieldSequentialGroup = layout.createSequentialGroup();
            inputLabelFieldSequentialGroup.addGroup(inputLabelParallelGroup);
            inputLabelFieldSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
            inputLabelFieldSequentialGroup.addGroup(inputFieldParallelGroup);
            GroupLayout.SequentialGroup actionButtonSequentialGroup = layout.createSequentialGroup();
            if (isAbleToCreate()) {
                actionButtonSequentialGroup.addComponent(newButton);
                actionButtonSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
            }
            if (isAbleToEdit()) {
                actionButtonSequentialGroup.addComponent(editButton);
                actionButtonSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
            }
            if (isAbleToDelete()) {
                actionButtonSequentialGroup.addComponent(deleteButton);
                actionButtonSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
            }
            GroupLayout.ParallelGroup formParallelGroup = layout.createParallelGroup();
            formParallelGroup.addGroup(inputLabelFieldSequentialGroup);
            formParallelGroup.addGroup(GroupLayout.Alignment.TRAILING, actionButtonSequentialGroup);
            if (arrangement == 3){
                hgroup.addGroup(viewParallelGroup);
                hgroup.addGap(10);
                hgroup.addGroup(formParallelGroup);
            } else {
                hgroup.addGroup(formParallelGroup);
                hgroup.addGap(10);
                hgroup.addGroup(viewParallelGroup);
            }
            layout.setHorizontalGroup(
                layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(hgroup)
                    .addContainerGap()
            );

            GroupLayout.Group vgroup;
            if (arrangement == 1 || arrangement == 3){
                vgroup = layout.createParallelGroup();
            } else {
                vgroup = layout.createSequentialGroup();
            }
            GroupLayout.SequentialGroup filterPairSequentialGroup = layout.createSequentialGroup();
            Iterator itLabel = filterLabels.entrySet().iterator();
            Iterator itField = filterFields.entrySet().iterator();
            while (itLabel.hasNext() && itField.hasNext()) {
                Map.Entry<String, JLabel> entryLabel = (Map.Entry)itLabel.next();
                Map.Entry<String, JComponent> entryField = (Map.Entry)itField.next();
                GroupLayout.ParallelGroup filterLabelFieldParallelGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING, false);
                filterLabelFieldParallelGroup.addComponent(entryLabel.getValue());
                filterLabelFieldParallelGroup.addComponent(entryField.getValue());
                filterPairSequentialGroup.addGroup(filterLabelFieldParallelGroup);
                if (itLabel.hasNext() && itField.hasNext()) filterPairSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
            }
            GroupLayout.SequentialGroup viewSequentialGroup = layout.createSequentialGroup();
            viewSequentialGroup.addGroup(filterPairSequentialGroup);
            viewSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
            viewSequentialGroup.addComponent(scrollPane);
            GroupLayout.SequentialGroup formSequentialGroup = layout.createSequentialGroup();
            itLabel = inputLabels.entrySet().iterator();
            itField = inputFields.entrySet().iterator();
            while (itLabel.hasNext() && itField.hasNext()) {
                Map.Entry<String, JLabel> entryLabel = (Map.Entry)itLabel.next();
                Map.Entry<String, JComponent> entryField = (Map.Entry)itField.next();
                GroupLayout.ParallelGroup inputLabelFieldParallelGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING, false);
                inputLabelFieldParallelGroup.addComponent(entryLabel.getValue());
                inputLabelFieldParallelGroup.addComponent(entryField.getValue());
                formSequentialGroup.addGroup(inputLabelFieldParallelGroup);
                /*if (itLabel.hasNext() && itField.hasNext())*/ formSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
            }
            GroupLayout.ParallelGroup actionButtonParallelGroup = layout.createParallelGroup();
            actionButtonParallelGroup.addComponent(newButton);
            actionButtonParallelGroup.addComponent(editButton);
            actionButtonParallelGroup.addComponent(deleteButton);
            formSequentialGroup.addGroup(actionButtonParallelGroup);
            if (arrangement == 0){
                vgroup.addGroup(formSequentialGroup);
                vgroup.addGap(10);
                vgroup.addGroup(viewSequentialGroup);
            } else {
                vgroup.addGroup(viewSequentialGroup);
                vgroup.addGap(10);
                vgroup.addGroup(formSequentialGroup);
            }
            layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(vgroup)
                    .addContainerGap()
            );
        }
    }
    
    private boolean isAbleToCreate(){
        return true;
    }
    
    private boolean isAbleToDelete(){
        return true;
    }
    
    private boolean isAbleToEdit(){
        return true;
    }
    
    private void bindWithTable(Field field, JTableBinding tableBinding){
        Class attrClass = field.getType();
        String attrName = field.getName();
        JTableBinding.ColumnBinding columnBinding = tableBinding.addColumnBinding(ELProperty.create("${" + attrName + "}"));
        columnBinding.setColumnName(attrName);
        columnBinding.setColumnClass(attrClass);
        columnBinding.setEditable(false);
    }
    
    private void configurePropertyMaterialization(){
        BindingGroup bindingGroup = new BindingGroup();
        JTableBinding tableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ_WRITE, tableContent, table);
        for (Field field : classReference.getDeclaredFields()) {
            if (field.isAnnotationPresent(Property.class)){
                Property prop = (Property)field.getDeclaredAnnotation(Property.class);
                JComponent inputField = new JTextField();
                JComponent filterField = new JTextField();
                if (prop.type() == DataType.BOOLEAN){
                    inputField = new JCheckBox();
                    filterField = new JCheckBox();
                } else if (prop.type() == DataType.DATE){
                    inputField = new JXDatePicker();
                    filterField = new JXDatePicker();
                } else if (prop.type() == DataType.DOUBLE) {
                    inputField = new JFormattedTextField();
                    filterField = new JFormattedTextField();
                } else if (prop.type() == DataType.INTEGER) {
                    inputField = new JFormattedTextField();
                    filterField = new JFormattedTextField();
                } else if (prop.type() == DataType.LONG) {
                    inputField = new JFormattedTextField();
                    filterField = new JFormattedTextField();
                } else if (prop.type() == DataType.MULTI_SELECTION){
                    inputField = new MultiSelect();
                    filterField = new MultiSelect();
                } else if (prop.type() == DataType.SINGLE_SELECTION){
                    inputField = new JComboBox();
                    filterField = new JComboBox();
                } else if (prop.type() == DataType.TEXT) {
                    inputField = new JTextField();
                    filterField = new JTextField();
                } else if (prop.type() == DataType.TIMESTAMP) {
                    inputField = new JFormattedTextField();
                    filterField = new JFormattedTextField();
                }
                if (prop.showInput()){
                    inputLabels.put(field.getName(), new JLabel(field.getName()));
                    inputFields.put(field.getName(), inputField);
                    bindingGroup.addBinding(Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, table, ELProperty.create("${selectedElement." + field.getName() + "}"), inputField, BeanProperty.create(getBoundProperty(inputField))));
                    bindingGroup.addBinding(Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, table, ELProperty.create("${selectedElement != null}"), inputField, BeanProperty.create("enabled")));
                }
                if (prop.showFilter()){
                    filterLabels.put(field.getName(), new JLabel(field.getName()));
                    filterFields.put(field.getName(), filterField);
                    bindingGroup.addBinding(Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, this, ELProperty.create("${filter." + field.getName() + "}"), filterField, BeanProperty.create(getBoundProperty(filterField))));
                }
                if (prop.showColumn()){
                    bindWithTable(field, tableBinding);
                }
            }
        }
        bindingGroup.addBinding(tableBinding);
        bindingGroup.bind();
    }
    
    private String getBoundProperty(JComponent field){
        if (field.getClass() == JXDatePicker.class){
            return "date";
        } else if (field.getClass() == JComboBox.class){
            return "selectedItem";
        } else if (field.getClass() == JFormattedTextField.class){
            return "value";
        } else if (field.getClass() == JCheckBox.class){
            return "selected";
        } else if (field.getClass() == MultiSelect.class){
            return "model.selecteds";
        } else {
            return "text";
        }
    }
    
    
}
