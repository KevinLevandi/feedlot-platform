/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.platform.ui;

import com.whitesheep.platform.api.Controller;
import com.whitesheep.platform.api.annotation.DataType;
import com.whitesheep.platform.api.annotation.Property;
import java.awt.GridBagLayout;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
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
public abstract class Xxx extends TopComponent {
    
    private JTable table = new JTable();
    private JScrollPane scrollPane = new JScrollPane(table);
    private List tableContent = ObservableCollections.observableList(new ArrayList());
    private Map<String, JLabel> filterLabels = new LinkedHashMap();
    private Map<String, JComponent> filterFields = new LinkedHashMap();
    private Map<String, JLabel> inputLabels = new LinkedHashMap();
    private Map<String, JComponent> inputFields = new LinkedHashMap();
    private String permission = ":)";
    private Class classReference;
    private int arrangement = 2;
    public static final int FORM_TOP = 0;
    public static final int FORM_LEFT = 1;
    public static final int FORM_BOTTOM = 2;
    public static final int FORM_RIGHT = 3;
    
    public Xxx(String name) {
        //permission = Root.getInstance().getCurrentUser().getRole() == null ? "" : Root.getInstance().getCurrentUser().getRole().getPermission();
        initComponents();
        setName(name);
    }

    public Class getClassReference() {
        return classReference;
    }
    
    protected void setClassReference(Class classReference) {
        this.classReference = classReference;
        configureBinding();
    }

    protected void setArrangement(int arrangement) {
        this.arrangement = arrangement;
    }

    public List getTableContent() {
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
    
    protected void addInput(JComponent inputField, String boundAttribute){
        inputLabels.putIfAbsent(boundAttribute, new JLabel(boundAttribute));
        inputFields.putIfAbsent(boundAttribute, inputField);
    }
    
    protected void addFilter(JComponent filterField, String boundAttribute){
        inputLabels.putIfAbsent(boundAttribute, new JLabel(boundAttribute));
        inputFields.putIfAbsent(boundAttribute, filterField);
    }
    
    private void initComponents(){
        if (!permission.isEmpty()){
            drawLayout();
        } else {
            drawLayoutAccessDenied();
        }
    }
    
    protected void configureBinding(){
        BindingGroup bindingGroup = new BindingGroup();
        JTableBinding tableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ_WRITE, tableContent, table);
        Field[] fieldList = classReference.getDeclaredFields();
        for (Field f : fieldList) {
            JComponent inputField = getInputField(f);
            if (inputField != null){
                inputLabels.put(f.getName(), new JLabel(f.getName()));
                inputFields.put(f.getName(), inputField);
                bindWithTable(f, tableBinding);
                bindingGroup.addBinding(createAutoBinding(f.getName(), inputField));
            }
            JComponent filterField = getFilterField(f);
            if (filterField != null){
                filterLabels.put(f.getName(), new JLabel(f.getName()));
                filterFields.put(f.getName(), filterField);
            }
        }
        bindingGroup.addBinding(tableBinding);
        bindingGroup.bind();
    }
    
    private void drawLayoutAccessDenied(){
        this.setLayout(new GridBagLayout());
        this.add(new JLabel("You don't have permission to access this component"));
    }
    
    protected void drawLayout(){
        if (!permission.isEmpty()){
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
                if (arrangement == 3){
                    hgroup.addGroup(viewParallelGroup);
                    hgroup.addGap(10);
                    hgroup.addGroup(inputLabelFieldSequentialGroup);
                } else {
                    hgroup.addGroup(inputLabelFieldSequentialGroup);
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
                GroupLayout.SequentialGroup inputPairSequentialGroup = layout.createSequentialGroup();
                itLabel = inputLabels.entrySet().iterator();
                itField = inputFields.entrySet().iterator();
                while (itLabel.hasNext() && itField.hasNext()) {
                    Map.Entry<String, JLabel> entryLabel = (Map.Entry)itLabel.next();
                    Map.Entry<String, JComponent> entryField = (Map.Entry)itField.next();
                    GroupLayout.ParallelGroup inputLabelFieldParallelGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING, false);
                    inputLabelFieldParallelGroup.addComponent(entryLabel.getValue());
                    inputLabelFieldParallelGroup.addComponent(entryField.getValue());
                    inputPairSequentialGroup.addGroup(inputLabelFieldParallelGroup);
                    if (itLabel.hasNext() && itField.hasNext()) inputPairSequentialGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
                }
                if (arrangement == 0){
                    vgroup.addGroup(inputPairSequentialGroup);
                    vgroup.addGap(10);
                    vgroup.addGroup(viewSequentialGroup);
                } else {
                    vgroup.addGroup(viewSequentialGroup);
                    vgroup.addGap(10);
                    vgroup.addGroup(inputPairSequentialGroup);
                }
                layout.setVerticalGroup(
                    layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(vgroup)
                        .addContainerGap()
                );
            }
        }
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
                JComponent fieldSwing = null;
                if (prop.type() == DataType.BOOLEAN){
                    fieldSwing = new JCheckBox();
                } else if (prop.type() == DataType.DATE){
                    fieldSwing = new JXDatePicker();
                } else if (prop.type() == DataType.DOUBLE) {
                    fieldSwing = new JFormattedTextField();
                } else if (prop.type() == DataType.INTEGER) {
                    fieldSwing = new JFormattedTextField();
                } else if (prop.type() == DataType.SELECTION){
                    fieldSwing = new JComboBox();
                } else if (prop.type() == DataType.LONG) {
                    fieldSwing = new JFormattedTextField();
                } else if (prop.type() == DataType.TEXT) {
                    fieldSwing = new JTextField();
                } else if (prop.type() == DataType.TIMESTAMP) {
                    fieldSwing = new JFormattedTextField();
                }
                if (prop.showInput()){
                    inputLabels.put(field.getName(), new JLabel(field.getName()));
                    inputFields.put(field.getName(), fieldSwing);
                    bindingGroup.addBinding(createAutoBinding(field.getName(), fieldSwing));
                }
                if (prop.showFilter()){
                    filterLabels.put(field.getName(), new JLabel(field.getName()));
                    filterFields.put(field.getName(), fieldSwing);
                }
                if (prop.showColumn()){
                    bindWithTable(field, tableBinding);
                }
            }
        }
        bindingGroup.addBinding(tableBinding);
        bindingGroup.bind();
    }
    
    private JComponent getInputField(Field field){
        if (field.isAnnotationPresent(Property.class)){
            Property prop = (Property)field.getDeclaredAnnotation(Property.class);
            if (prop.showInput()){
                
            } else {
                return null;
            }
        }
        
        Class attrClass = field.getType();
        Annotation[] attrAnnotations = field.getDeclaredAnnotations();
        if (hasAnnotation(attrAnnotations, MyInput.class, "type", DataType.TEXT)){
            return new JTextField();
        } else if (hasAnnotation(attrAnnotations, MyInput.class, "type", DataType.NUMBER)){
            return getNumberFormatter(attrClass) == null ? new JFormattedTextField() : new JFormattedTextField(getNumberFormatter(attrClass));
        } else if (hasAnnotation(attrAnnotations, MyInput.class, "type", DataType.DATE)){
            return new JXDatePicker();
        } else if (hasAnnotation(attrAnnotations, MyInput.class, "type", DataType.TIMESTAMP)){
            JFormattedTextField ftf = new JFormattedTextField();
            DateFormatter df = new DateFormatter(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            DefaultFormatterFactory factory = new DefaultFormatterFactory();
            factory.setDefaultFormatter(df);
            ftf.setFormatterFactory(factory);
            return ftf;
        } else if (hasAnnotation(attrAnnotations, MyInput.class, "type", DataType.SELECTION)){
            return new JComboBox();
        } else if (hasAnnotation(attrAnnotations, MyInput.class, "type", DataType.BOOLEAN)){
            return new JCheckBox();
        } else if (isColumn(attrAnnotations)){
            if (isTemporalDate(attrAnnotations)){
                return new JXDatePicker();
            } else if (isTemporalTimestamp(attrAnnotations)){
                JFormattedTextField ftf = new JFormattedTextField();
                DateFormatter df = new DateFormatter(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                DefaultFormatterFactory factory = new DefaultFormatterFactory();
                factory.setDefaultFormatter(df);
                ftf.setFormatterFactory(factory);
                return ftf;
            } else if (Number.class.isAssignableFrom(attrClass)){
                return new JFormattedTextField(getNumberFormatter(attrClass));
            } else {
                return new JTextField();
            }
        } else if (isJoinColumn(attrAnnotations)){
            Controller ctrl = new Controller(attrClass, attrClass.getSimpleName().toLowerCase());
            List entityList = ctrl.readEntities();
            return new JComboBox(entityList.toArray(new Object[entityList.size()]));
        } else {
            return null;
        }
    }
    
    private JComponent getFilterField(Field field){
        Class attrClass = field.getType();
        Annotation[] attrAnnotations = field.getDeclaredAnnotations();
        if (hasAnnotation(attrAnnotations, MyFilter.class, "type", DataType.TEXT)){
            return new JTextField();
        } else if (hasAnnotation(attrAnnotations, MyFilter.class, "type", DataType.NUMBER)){
            return getNumberFormatter(attrClass) == null ? new JFormattedTextField() : new JFormattedTextField(getNumberFormatter(attrClass));
        } else if (hasAnnotation(attrAnnotations, MyFilter.class, "type", DataType.DATE)){
            return new JXDatePicker();
        } else if (hasAnnotation(attrAnnotations, MyFilter.class, "type", DataType.TIMESTAMP)){
            JFormattedTextField ftf = new JFormattedTextField();
            DateFormatter df = new DateFormatter(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            DefaultFormatterFactory factory = new DefaultFormatterFactory();
            factory.setDefaultFormatter(df);
            ftf.setFormatterFactory(factory);
            return ftf;
        } else if (hasAnnotation(attrAnnotations, MyFilter.class, "type", DataType.SELECTION)){
            return new JComboBox();
        } else if (hasAnnotation(attrAnnotations, MyFilter.class, "type", DataType.BOOLEAN)){
            return new JCheckBox();
        } else {
            return null;
        }
    }
    
    private boolean isColumn(Annotation[] annotations){
        boolean isColumn = false;
        for (int i = 0; i < annotations.length && !isColumn; i++){
            Class<? extends Annotation> type = annotations[i].annotationType();
            isColumn = type.equals(Column.class);
        }
        return isColumn;
    }
    
    private boolean isJoinColumn(Annotation[] annotations){
        boolean isJoinColumn = false;
        for (int i = 0; i < annotations.length && !isJoinColumn; i++){
            Class<? extends Annotation> type = annotations[i].annotationType();
            isJoinColumn = type.equals(JoinColumn.class);
        }
        return isJoinColumn;
    }
    
    private boolean isTemporalDate(Annotation[] annotations){
        boolean isTemporal = false, isDate = false;
        for (int i = 0; i < annotations.length && !isTemporal; i++){
            Class<? extends Annotation> type = annotations[i].annotationType();
            Method[] methods = type.getDeclaredMethods();
            isTemporal = type.equals(Temporal.class);
            for (int j = 0; j < methods.length && isTemporal && !isDate; j++) {
                try {
                    Object value = methods[j].invoke(annotations[i], (Object[])null);
                    isDate = methods[j].getName().equals("value") && value.equals(TemporalType.DATE);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ignore) {}
            }
        }
        return isTemporal && isDate;
    }
    
    private boolean isTemporalTimestamp(Annotation[] annotations){
        boolean isTemporal = false, isTimestamp = false;
        for (int i = 0; i < annotations.length && !isTemporal; i++){
            Class<? extends Annotation> type = annotations[i].annotationType();
            Method[] methods = type.getDeclaredMethods();
            isTemporal = type.equals(Temporal.class);
            for (int j = 0; j < methods.length && isTemporal && !isTimestamp; j++) {
                try {
                    Object value = methods[j].invoke(annotations[i], (Object[])null);
                    isTimestamp = methods[j].getName().equals("value") && value.equals(TemporalType.TIMESTAMP);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ignore) {}
            }
        }
        return isTemporal && isTimestamp;
    }
    
    private boolean hasAnnotation(Annotation[] annotations, Class clazz){
        boolean found = false;
        for (int i = 0; i < annotations.length && !found; i++){
            Class<? extends Annotation> type = annotations[i].annotationType();
            found = type.equals(clazz);
        }
        return found;
    }
    
    private boolean hasAnnotation(Annotation[] annotations, Class clazz, String key, Object val){
        boolean found1 = false, found2 = false;
        for (int i = 0; i < annotations.length && !found1; i++){
            Class<? extends Annotation> type = annotations[i].annotationType();
            Method[] methods = type.getDeclaredMethods();
            found1 = type.equals(clazz);
            for (int j = 0; j < methods.length && found1 && !found2; j++) {
                try {
                    Object value = methods[j].invoke(annotations[i], (Object[])null);
                    found2 = methods[j].getName().equals(key) && value.equals(val);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ignore) {}
            }
        }
        return found1 && found2;
    }
    
    private NumberFormatter getNumberFormatter(final Class<? extends Number> numberClass){
        return new NumberFormatter(NumberFormat.getNumberInstance()){
            
            @Override
            public String valueToString(Object iv) throws ParseException {
                return iv == null ? "" : (numberClass.cast(iv)).toString();
            }
            
            @Override
            public Object stringToValue(String text) throws ParseException {
                Number n = NumberFormat.getNumberInstance().parse(text);
                Object ret = null;
                if (numberClass == Double.class || numberClass == double.class){
                    ret = n.doubleValue();
                } else if (numberClass == Float.class || numberClass == float.class){
                    ret = n.floatValue();
                } else if (numberClass == Long.class || numberClass == long.class){
                    ret = n.longValue();
                } else if (numberClass == Integer.class || numberClass == int.class){
                    ret = n.intValue();
                } else if (numberClass == Short.class || numberClass == short.class){
                    ret = n.shortValue();
                } else if (numberClass == Byte.class || numberClass == byte.class){
                    ret = n.byteValue();
                }
                return text.isEmpty() ? null : ret;
            }
        };
    }
    
    private Binding createAutoBinding(String attrName, JComponent field){
        String boundProperty;
        if (field.getClass() == JXDatePicker.class){
            boundProperty = "date";
        } else if (field.getClass() == JComboBox.class){
            boundProperty = "selectedItem";
        } else if (field.getClass() == JFormattedTextField.class){
            boundProperty = "value";
        } else {
            boundProperty = "text";
        }
        Binding binding = Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, table, ELProperty.create("${selectedElement." + attrName + "}"), field, BeanProperty.create(boundProperty));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        return binding;
    }
}
