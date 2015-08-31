/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.platform.ui.topcomponent;

import com.whitesheep.platform.api.annotation.DataType;
import com.whitesheep.platform.api.annotation.Property;
import com.whitesheep.platform.ui.Resources;
import com.whitesheep.platform.ui.datatype.FieldPair;
import com.whitesheep.platform.ui.datatype.GroupPair;
import com.whitesheep.platform.ui.component.MultiSelect;
import java.awt.Component;
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
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.observablecollections.ObservableCollections;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.swingx.JXDatePicker;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

/**
 *
 * @author timotius
 */
public abstract class AbstractTopComponent<T> extends TopComponent {
    
    private JTable table = new JTable();
    private List<T> tableContent = ObservableCollections.observableList(new ArrayList());
    private JScrollPane scrollPane = new JScrollPane(table);
    private Map<String, FieldPair> crudComponentMap = new LinkedHashMap();
    private Map<String, FieldPair> filterComponentMap = new LinkedHashMap();
    private JButton newButton = new JButton(NbBundle.getMessage(Resources.class, "ActionButton.NEW"));
    private JButton editButton = new JButton(NbBundle.getMessage(Resources.class, "ActionButton.EDIT"));
    private JButton deleteButton = new JButton(NbBundle.getMessage(Resources.class, "ActionButton.DELETE"));
    private String permission = "..";
    private Class<T> classReference;
    private T filter;
    
    @Override
    public GroupLayout getLayout() {
        return (GroupLayout)super.getLayout();
    }
    
    public AbstractTopComponent() {
        initComponents();
    }
    
    public AbstractTopComponent(Class<T> classReference) throws InstantiationException, IllegalAccessException {
        //permission = Root.getInstance().getCurrentUser().getRole() == null ? "" : Root.getInstance().getCurrentUser().getRole().getPermission();
        initComponents();
        setClassReference(classReference);
    }

    private void initComponents(){
        draw();
    }
    
    protected void draw(){
        if (!permission.isEmpty()) {
            drawLayoutAccessGranted();
        } else {
            drawLayoutAccessDenied();
        }
    }
    
    private void drawLayoutAccessDenied(){
        initLayout();
        setAsLayout(new JLabel("You don't have permission to access this component"));
    }
    
    private void drawLayoutAccessGranted(){
        initLayout();
        if (classReference != null) {
            GroupPair mainGroup = new GroupPair(getLayout());
            mainGroup.addRight(GroupLayout.Alignment.LEADING, getViewComponentGroup(), getInputComponentGroup());
            setAsLayout(mainGroup);
        } 
    }
    
    private void initLayout(){
        this.removeAll();
        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        this.setLayout(layout);
    }
    
    private void setAsLayout(Component component) {
        getLayout().setHorizontalGroup(
            getLayout().createSequentialGroup().addComponent(component)
        );
        getLayout().setVerticalGroup(
            getLayout().createSequentialGroup().addComponent(component)
        );
    }
    
    private void setAsLayout(GroupPair group) {
        getLayout().setHorizontalGroup(
            getLayout().createSequentialGroup().addGroup(group.getHgroup())
        );
        getLayout().setVerticalGroup(
            getLayout().createSequentialGroup().addGroup(group.getVgroup())
        );
    }
    
    private GroupPair getViewComponentGroup() {
        GroupPair gp = new GroupPair(getLayout());
        gp.addRight(GroupLayout.Alignment.LEADING, true, true, scrollPane);
        return gp;
    }
    
    private GroupPair getInputComponentGroup() {
        GroupPair inputGroup = new GroupPair(getLayout());
        inputGroup.addBelow(GroupLayout.Alignment.LEADING, getCrudComponentGroup());
        inputGroup.addBelow(GroupLayout.Alignment.TRAILING, getActionComponentGroup());
        return inputGroup;
    }
    
    private GroupPair getActionComponentGroup() {
        GroupPair gp = new GroupPair(getLayout());
        if (isAbleToCreate())   gp.addRight(GroupLayout.Alignment.TRAILING, false, false, newButton);
        if (isAbleToEdit())     gp.addRight(GroupLayout.Alignment.TRAILING, false, false, editButton);
        if (isAbleToDelete())   gp.addRight(GroupLayout.Alignment.TRAILING, false, false, deleteButton);
        return gp;
    }
    
    private GroupPair getCrudComponentGroup() {
        GroupPair gp = new GroupPair(getLayout());
        Iterator it = crudComponentMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, FieldPair> entry = (Map.Entry)it.next();
            FieldPair fp = entry.getValue();
            gp.addBelow(GroupLayout.Alignment.LEADING, true, false, fp.getLabel(), fp.getField());
        }
        return gp;
    }
    
    protected final void setClassReference(Class<T> classReference) throws InstantiationException, IllegalAccessException {
        this.classReference = classReference;
        this.filter = classReference.newInstance();
        materialize(classReference);
    }
    
    private void materialize(Class<T> classReference){
        BindingGroup bindingGroup = new BindingGroup();
        JTableBinding tableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ_WRITE, tableContent, table);
        for (Field field : classReference.getDeclaredFields()) {
            if (field.isAnnotationPresent(Property.class)){
                Property prop = (Property)field.getDeclaredAnnotation(Property.class);
                JLabel crudLabel = new JLabel(NbBundle.getMessage(Resources.class, prop.name()));
                JComponent crudField = new JTextField();
                JLabel filterLabel = new JLabel(NbBundle.getMessage(Resources.class, prop.name()));
                JComponent filterField = new JTextField();
                if (prop.inputType() == DataType.CHECK_BOX){
                    crudField = new JCheckBox();
                    filterField = new JCheckBox();
                } else if (prop.inputType() == DataType.DATE_PICKER){
                    crudField = new JXDatePicker();
                    filterField = new JXDatePicker();
                } else if (prop.inputType() == DataType.DOUBLE_FIELD) {
                    crudField = new JFormattedTextField();
                    filterField = new JFormattedTextField();
                } else if (prop.inputType() == DataType.INTEGER_FIELD) {
                    crudField = new JFormattedTextField();
                    filterField = new JFormattedTextField();
                } else if (prop.inputType() == DataType.LONG_FIELD) {
                    crudField = new JFormattedTextField();
                    filterField = new JFormattedTextField();
                } else if (prop.inputType() == DataType.MULTI_SELECTION){
                    crudField = new MultiSelect();
                    filterField = new MultiSelect();
                } else if (prop.inputType() == DataType.SINGLE_SELECTION){
                    crudField = new JComboBox();
                    filterField = new JComboBox();
                } else if (prop.inputType() == DataType.TEXT_FIELD) {
                    crudField = new JTextField();
                    filterField = new JTextField();
                } else if (prop.inputType() == DataType.TIMESTAMP_FIELD) {
                    crudField = new JFormattedTextField();
                    filterField = new JFormattedTextField();
                }
                if (prop.hasInputCrud()){
                    crudComponentMap.put(field.getName(), new FieldPair(crudLabel, crudField));
                    bindingGroup.addBinding(Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, table, ELProperty.create("${selectedElement." + field.getName() + "}"), crudField, BeanProperty.create(getBoundProperty(crudField))));
                    bindingGroup.addBinding(Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ, table, ELProperty.create("${selectedElement != null}"), crudField, BeanProperty.create("enabled")));
                }
                if (prop.hasInputFilter()){
                    filterComponentMap.put(field.getName(), new FieldPair(filterLabel, filterField));
                    bindingGroup.addBinding(Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, this, ELProperty.create("${filter." + field.getName() + "}"), filterField, BeanProperty.create(getBoundProperty(filterField))));
                }
                if (prop.hasView()){
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
    
    private void bindWithTable(Field field, JTableBinding tableBinding){
        Property prop = (Property)field.getDeclaredAnnotation(Property.class);
        Class attrClass = field.getType();
        String attrName = field.getName();
        JTableBinding.ColumnBinding columnBinding = tableBinding.addColumnBinding(ELProperty.create("${" + attrName + "}"));
        columnBinding.setColumnName(NbBundle.getMessage(Resources.class, prop.name()));
        columnBinding.setColumnClass(attrClass);
        columnBinding.setEditable(false);
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
    
    public void populateTable(List<T> content) {
        tableContent.clear();
        tableContent.addAll(content);
    }
    
    public FieldPair getCrudComponent(String key) {
        return crudComponentMap.get(key);
    }
}
