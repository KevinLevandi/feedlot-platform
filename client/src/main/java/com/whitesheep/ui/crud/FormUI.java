/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.ui.crud;

import com.whitesheep.beans.MyBean;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.text.NumberFormatter;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingx.JXDatePicker;
import org.openide.util.Exceptions;

/**
 *
 * @author timotius
 */
public abstract class FormUI<T> extends JPanel implements ActionListener {

    private Class<T> clazz;
    private T entity;
    BindingGroup bindingGroup;
    private List<JLabel> labelList;
    private List<JComponent> fieldList;
    private JButton clearButton;
    private JButton saveButton;
    private JButton deleteButton;
    
    public FormUI(Class<T> clazz) {
        this.clazz = clazz;
        try {
            initComponents();
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    protected void build(){
        this.setLayout(createLayout());
    }
    
    private void initComponents() throws Exception {
        entity = clazz.newInstance();
        labelList = new ArrayList();
        fieldList = new ArrayList();
        for (Field f : clazz.getDeclaredFields()) {
            generateInputField(f);
        }
        clearButton = new JButton("Clear");
        saveButton = new JButton("Save");
        deleteButton = new JButton("Delete");
    }
    
    private void generateInputField(Field field){
        Class attrClass = field.getType();
        String attrName = field.getName();
        Annotation[] attrAnnotations = field.getDeclaredAnnotations();
        labelList.add(new JLabel(attrName + ":"));
        JComponent inputField = getField(attrClass); 
        fieldList.add(inputField);
        bindingGroup.addBinding(createAutoBinding(attrName, inputField));
    }
    
    private JComponent getField(Class<?> attrClass){
        if (attrClass.equals(java.util.Date.class)){
            return new JXDatePicker();
        } else if (MyBean.class.isAssignableFrom(attrClass)){
            return new JComboBox();
        } else if (Number.class.isAssignableFrom(attrClass)){
            return new JFormattedTextField(getNumberFormatter(attrClass.asSubclass(Number.class)));
        } else {
            return new JTextField();
        }
    }
    
    private Binding createAutoBinding(String attrName, JComponent field){
        String boundProperty;
        if (field.getClass().isInstance(JXDatePicker.class)){
            boundProperty = "date";
        } else if (field.getClass().isInstance(JComboBox.class)){
            boundProperty = "selectedItem";
        } else {
            boundProperty = "text";
        }
        return Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, this, ELProperty.create("${entity." + attrName + "}"), field, BeanProperty.create(boundProperty));
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
            .addGroup(createHInputGroup(layout))
            .addContainerGap()
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(createVInputGroup(layout))
            .addContainerGap()
        );
        return layout;
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
        
        GroupLayout.SequentialGroup buttonGroup = layout.createSequentialGroup();
        buttonGroup.addComponent(clearButton);
        buttonGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        buttonGroup.addComponent(saveButton);
        buttonGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        buttonGroup.addComponent(deleteButton);
        
        GroupLayout.SequentialGroup inputGroup = layout.createSequentialGroup();
        inputGroup.addGroup(labelGroup);
        inputGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        inputGroup.addGroup(fieldGroup);
        
        GroupLayout.ParallelGroup group = layout.createParallelGroup();
        group.addGroup(inputGroup);
        group.addGroup(Alignment.TRAILING, buttonGroup);
        
        return group;
    }
    
    private GroupLayout.Group createVInputGroup(GroupLayout layout){
        GroupLayout.ParallelGroup buttonGroup = layout.createParallelGroup();
        buttonGroup.addComponent(clearButton);
        buttonGroup.addComponent(saveButton);
        buttonGroup.addComponent(deleteButton);
        
        GroupLayout.SequentialGroup group = layout.createSequentialGroup();
        for (int i = 0; i < labelList.size() && i < fieldList.size(); i++){
            GroupLayout.ParallelGroup pairGroup = layout.createParallelGroup(Alignment.LEADING, false);
            pairGroup.addComponent(labelList.get(i));
            pairGroup.addComponent(fieldList.get(i));
            group.addGroup(pairGroup);
            group.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        }
        
        group.addGroup(buttonGroup);
        
        return group;
    }

    public abstract void clear();
    
    public abstract void save();
    
    public abstract void delete();
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearButton){
            clear();
        } else if (e.getSource() == saveButton) {
            save();
        } else if (e.getSource() == deleteButton) {
            delete();
        }
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

}
