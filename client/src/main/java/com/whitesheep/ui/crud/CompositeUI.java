/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.ui.crud;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.TemporalType;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.text.NumberFormatter;
import org.openide.windows.TopComponent;

/**
 *
 * @author timotius
 */
public class CompositeUI<T> extends TopComponent {

    private Class<T> clazz;
    private JScrollPane scrollPane;
    private JTable table;
    private List<JLabel> labelList;
    private List<JComponent> fieldList;
    
    public CompositeUI(Class<T> clazz) {
        this.clazz = clazz;
        initComponents();
    }
    
    protected void build(){
        this.setLayout(createLayout());
    }
    
    private void initComponents() {
        labelList = new ArrayList();
        fieldList = new ArrayList();
        for (Field f : clazz.getDeclaredFields()) {
            generateInputField(f);
        }
        
        table = new JTable();
        scrollPane = new JScrollPane(table);
    }
    
    private void generateInputField(Field field){
        Class attrClass = field.getType();
        String attrName = field.getName();
        Annotation[] attrAnnotations = field.getDeclaredAnnotations();
        if (isColumn(attrAnnotations)){
            labelList.add(new JLabel(attrName + ":"));
            fieldList.add(new JTextField());
        } else if (isEmbeddedId(attrAnnotations)){
            Field[] fields = attrClass.getDeclaredFields();
            for (Field f : fields){
                generateInputField(f);
            }
        }
    }
    
    private boolean isColumn(Annotation[] attrAnnotations){
        boolean isColumn = false;
        for (int i = 0; i < attrAnnotations.length && !isColumn; i++){
            Class<? extends Annotation> type = attrAnnotations[i].annotationType();
            if (type.equals(javax.persistence.Column.class)) isColumn = true;
        }
        return isColumn;
    }
    
    private boolean isEmbeddedId(Annotation[] attrAnnotations){
        boolean isEmbeddedId = false;
        for (int i = 0; i < attrAnnotations.length && !isEmbeddedId; i++){
            Class<? extends Annotation> type = attrAnnotations[i].annotationType();
            if (type.equals(javax.persistence.EmbeddedId.class)) isEmbeddedId = true;
        }
        return isEmbeddedId;
    }
    
    private boolean isTemporalDate(Annotation[] attrAnnotations) throws Exception {
        boolean isTemporal = false, isDate = false;
        for (int i = 0; i < attrAnnotations.length && !isTemporal; i++){
            Class<? extends Annotation> type = attrAnnotations[i].annotationType();
            Method[] methods = type.getDeclaredMethods();
            for (int j = 0; j < methods.length && !isDate; j++) {
                Object methodValue = methods[j].invoke(attrAnnotations[i], (Object[])null);
                String methodName = methods[j].getName();
                if ("value".equals(methodName) && methodValue == TemporalType.DATE) isDate = true;
            }
            if (type.equals(javax.persistence.Temporal.class)) isTemporal = true;
        }
        return isTemporal && isDate;
    }
    
    private boolean isTemporalTimestamp(Annotation[] attrAnnotations) throws Exception {
        boolean isTemporal = false, isTimestamp = false;
        for (int i = 0; i < attrAnnotations.length && !isTemporal; i++){
            Class<? extends Annotation> type = attrAnnotations[i].annotationType();
            Method[] methods = type.getDeclaredMethods();
            for (int j = 0; j < methods.length && !isTimestamp; j++) {
                Object methodValue = methods[j].invoke(attrAnnotations[i], (Object[])null);
                String methodName = methods[j].getName();
                if ("value".equals(methodName) && methodValue == TemporalType.TIMESTAMP) isTimestamp = true;
            }
            if (type.equals(javax.persistence.Temporal.class)) isTemporal = true;
        }
        return isTemporal && isTimestamp;
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
    
    private GroupLayout createLayout(){
        GroupLayout layout = new GroupLayout(this);
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(createHGroup(layout))
            .addContainerGap()
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(createVGroup(layout))
            .addContainerGap()
        );
        return layout;
    }
    
    private GroupLayout.Group createHGroup(GroupLayout layout){
        GroupLayout.SequentialGroup group = layout.createSequentialGroup();
        group.addComponent(scrollPane);
        group.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        group.addGroup(createHInputGroup(layout));
        return group;
    }

    private GroupLayout.Group createVGroup(GroupLayout layout){
        GroupLayout.ParallelGroup group = layout.createParallelGroup();
        group.addComponent(scrollPane);
        group.addGroup(createVInputGroup(layout));
        return group;
    }
    
    private GroupLayout.Group createHInputGroup(GroupLayout layout){
        GroupLayout.ParallelGroup labelGroup = layout.createParallelGroup();
        for (JLabel l : labelList){
            labelGroup.addComponent(l);
        }
        GroupLayout.ParallelGroup fieldGroup = layout.createParallelGroup();
        for (JComponent f : fieldList){
            fieldGroup.addComponent(f);
        }
        GroupLayout.SequentialGroup group = layout.createSequentialGroup();
        group.addGroup(labelGroup);
        group.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        group.addGroup(fieldGroup);
        return group;
    }
    
    private GroupLayout.Group createVInputGroup(GroupLayout layout){
        GroupLayout.SequentialGroup group = layout.createSequentialGroup();
        for (int i = 0; i < labelList.size() && i < fieldList.size(); i++){
            group.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
            GroupLayout.ParallelGroup pairGroup = layout.createParallelGroup(Alignment.LEADING, false);
            pairGroup.addComponent(labelList.get(i));
            pairGroup.addComponent(fieldList.get(i));
            group.addGroup(pairGroup);
        }
        return group;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public JTable getTable() {
        return table;
    }

}
