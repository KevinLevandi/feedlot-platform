package com.whitesheep.ui.crud;

import com.whitesheep.beans.MyBean;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.Binding;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.SwingBindings;
import org.jdesktop.swingx.JXDatePicker;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

public abstract class Form<T extends MyBean> extends TopComponent implements ActionListener, LookupListener {
    protected Class<T> clazz;
    private BindingGroup bindingGroup;
    private JScrollPane scrollPane;
    private JPanel panel;
    private Map<String, JLabel> labelMap;
    private Map<String, JComponent> fieldMap;
    private Map<String, List> listMap;
    private JButton newButton;
    private JButton saveButton;
    private JButton deleteButton;
    
    private T selectedEntity;
    private PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
    private String topComponentProviderId;
    private InstanceContent dynamicContent = new InstanceContent();
    private Lookup.Result<T> result;
    
    public Form(Class<T> clazz, String topComponentProviderId){
        this.clazz = clazz;
        this.topComponentProviderId = topComponentProviderId;
        initComponents();
        associateLookup(new AbstractLookup(dynamicContent));
    }
    
    private void initComponents(){
        bindingGroup = new BindingGroup();
        panel = new JPanel();
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(panel);
        labelMap = new LinkedHashMap();
        fieldMap = new LinkedHashMap();
        listMap = new HashMap();
        newButton = new JButton("New");
        saveButton = new JButton("Save");
        deleteButton = new JButton("Delete");
        
        configureBinding();
        configureLayout();
        registerListener();
    }
    
    private void configureBinding(){
        Field fieldList[] = clazz.getDeclaredFields();
        for (Field f : fieldList) {
            boolean isTransient = Modifier.isTransient(f.getModifiers());
            Class attrClass = f.getType();
            String attrName = f.getName();
            
            if (!isTransient){
                labelMap.put(attrName, getLabel(attrName));
                fieldMap.put(attrName, getField(attrClass));
                if (MyBean.class.isAssignableFrom(attrClass)) listMap.put(attrName, getEntityList(attrClass));

                bindingGroup.addBinding(createAutoBinding(attrName));
                if (MyBean.class.isAssignableFrom(attrClass)) bindingGroup.addBinding(createJComboBoxBinding(attrName));
            }
        }
        bindingGroup.bind();
    }
    
    private Binding createAutoBinding(String attrName){
        JComponent field = fieldMap.get(attrName);
        String boundProperty;
        if (field.getClass().isInstance(JXDatePicker.class)){
            boundProperty = "date";
        } else if (field.getClass().isInstance(JComboBox.class)){
            boundProperty = "selectedItem";
        } else {
            boundProperty = "text";
        }
        return Bindings.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, this, ELProperty.create("${selectedEntity." + attrName + "}"), field, BeanProperty.create(boundProperty));
    }
    
    private Binding createJComboBoxBinding(String attrName){
        JComboBox field = (JComboBox)fieldMap.get(attrName);
        List fieldValues = listMap.get(attrName);
        return SwingBindings.createJComboBoxBinding(AutoBinding.UpdateStrategy.READ_WRITE, fieldValues, field);
    }
    
    private JLabel getLabel(String attrName){
        return new JLabel(attrName);
    }
    
    private JComponent getField(Class<?> attrClass){
        if (attrClass.equals(java.util.Date.class)){
            return new JXDatePicker();
        } else if (MyBean.class.isAssignableFrom(attrClass)){
            return new JComboBox();
        } else {
            return new JTextField();
        }
    }
    
    private List<?> getEntityList(Class<?> entityClass){
        return null;
    }
    
    private void configureLayout(){
        configurePanel();
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup().addComponent(scrollPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup().addComponent(scrollPane)
        );
    }
    
    private void configurePanel(){
        GroupLayout panelLayout = new GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(getHorizontalGroup(panelLayout));
        panelLayout.setVerticalGroup(getVerticalGroup(panelLayout));
    }
    
    private Group getHorizontalGroup(GroupLayout layout){
        GroupLayout.SequentialGroup group = layout.createSequentialGroup();
        group.addContainerGap();
        group.addGroup(getParallelGroup(layout));
        group.addContainerGap();
        return group;
    }
    
    private Group getParallelGroup(GroupLayout layout){
        GroupLayout.ParallelGroup group = layout.createParallelGroup();
        group.addGroup(getFormGroup(layout));
        group.addGroup(GroupLayout.Alignment.TRAILING, getSequentialButtonGroup(layout));
        return group;
    }
    
    private Group getFormGroup(GroupLayout layout){
        GroupLayout.SequentialGroup group = layout.createSequentialGroup();
        group.addGroup(getLabelGroup(layout));
        group.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        group.addGroup(getFieldGroup(layout));
        return group;
    }
    
    private Group getLabelGroup(GroupLayout layout){
        GroupLayout.ParallelGroup group = layout.createParallelGroup();
        for (Entry<String, JLabel> entry : labelMap.entrySet()){
            group.addComponent(entry.getValue());
        }
        return group;
    }
    
    private Group getFieldGroup(GroupLayout layout){
        GroupLayout.ParallelGroup group = layout.createParallelGroup();
        for (Entry<String, JComponent> entry : fieldMap.entrySet()){
            group.addComponent(entry.getValue());
        }
        return group;
    }
    
    private Group getSequentialButtonGroup(GroupLayout layout){
        GroupLayout.SequentialGroup group = layout.createSequentialGroup();
        group.addComponent(newButton);
        group.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        group.addComponent(saveButton);
        group.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        group.addComponent(deleteButton);
        return group;
    }
    
    private Group getVerticalGroup(GroupLayout layout){
        GroupLayout.SequentialGroup group = layout.createSequentialGroup();
        group.addContainerGap();
        group.addGroup(getSequentialGroup(layout));
        group.addContainerGap();
        return group;
    }
    
    private Group getSequentialGroup(GroupLayout layout){
        GroupLayout.SequentialGroup group = layout.createSequentialGroup();
        Iterator<Entry<String, JLabel>> labelIterator = labelMap.entrySet().iterator();
        Iterator<Entry<String, JComponent>> fieldIterator = fieldMap.entrySet().iterator();
        while(labelIterator.hasNext() && fieldIterator.hasNext()){
            Entry<String, JLabel> labelEntry = labelIterator.next();
            Entry<String, JComponent> fieldEntry = fieldIterator.next();
            
            group.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
            GroupLayout.ParallelGroup pairGroup = layout.createParallelGroup();
            pairGroup.addComponent(labelEntry.getValue());
            pairGroup.addComponent(fieldEntry.getValue());
            group.addGroup(pairGroup);
        }
        group.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        group.addGroup(getParallelButtonGroup(layout));
        return group;
    }
    
    private Group getParallelButtonGroup(GroupLayout layout){
        GroupLayout.ParallelGroup group = layout.createParallelGroup();
        group.addComponent(deleteButton);
        group.addComponent(saveButton);
        group.addComponent(newButton);
        return group;
    }
    
    private void registerListener(){
        newButton.addActionListener(this);
        saveButton.addActionListener(this);
        deleteButton.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == saveButton) {
            update();
        } else if (e.getSource() == newButton) {
            create();
        } else if (e.getSource() == deleteButton) {
            delete();
        }
    }
    
    protected abstract void create();
    
    protected abstract void update();
    
    protected abstract void delete();
    
    @Override
    public void componentOpened() {
        TopComponent tc = WindowManager.getDefault().findTopComponent(topComponentProviderId);
        result = tc.getLookup().lookupResult(clazz);
        result.addLookupListener(this); 
    }

    @Override
    public void componentClosed() {
        result.removeLookupListener(this); 
    }

    @Override
    public void resultChanged(LookupEvent le){
        System.out.println("RESULT CHANGED!!");
        Collection<? extends T> allEntities = result.allInstances(); 
        if (!allEntities.isEmpty()) { 
            T entity = allEntities.iterator().next(); 
            setSelectedEntity(entity);
            System.out.println("NOT EMPTY " + entity);
        } else {
            setSelectedEntity(null);
            System.out.println("EMPTY");
        }
    }

    public T getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(T selectedEntity) {
        T oldSelectedEntity = this.selectedEntity;
        this.selectedEntity = selectedEntity;
        propertyChangeSupport.firePropertyChange("selectedEntity", oldSelectedEntity, selectedEntity);
    }
    
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}