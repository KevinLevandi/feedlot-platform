/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.crud;

import com.whitesheep.entity.animal.Animal;
import com.whitesheep.entity.weight.Weight;
import com.whitesheep.entity.weight.WeightPK;
import com.whitesheep.rest.JerseyClient;
import java.awt.EventQueue;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.text.NumberFormatter;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.ELProperty;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;

/**
 *
 * @author timotius
 */
@ConvertAsProperties(
        dtd = "-//com.whitesheep.crud//WeightCRUD//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "WeightCRUD",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(
        mode = "editor", 
        openAtStartup = false)
@ActionID(
        category = "Window", 
        id = "com.whitesheep.crud.WeightCRUD")
@ActionReference(
        path = "Menu/Window/Entity Manager")
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_WeightCRUDAction",
        preferredID = "WeightCRUD"
)
@NbBundle.Messages({
    "CTL_WeightCRUDAction=Weight Manager",
    "CTL_WeightCRUDTopComponent=Weight Manager",
    "HINT_WeightCRUDTopComponent=This is a Weight Manager window"
})
public class WeightCRUD extends TopComponent implements Lookup.Provider, LookupListener {
    
    private final JerseyClient weightClient = new JerseyClient("weight");
    private final JerseyClient animalClient = new JerseyClient("animal");
    private final InstanceContent dynamicContent = new InstanceContent();
    private final NumberFormatter nf = new NumberFormatter(NumberFormat.getIntegerInstance()){
        @Override
        public String valueToString(Object iv) throws ParseException {
            return iv == null ? "" : ((Double)iv).toString();
        }
        @Override
        public Object stringToValue(String text) throws ParseException {
            return text.isEmpty() ? null : NumberFormat.getNumberInstance().parse(text).doubleValue();
        }
    };
    
    public WeightCRUD() {
        initComponents();
        setName(Bundle.CTL_WeightCRUDTopComponent());
        setToolTipText(Bundle.HINT_WeightCRUDTopComponent());
        associateLookup(new AbstractLookup(dynamicContent));
    }
    
    private List<Weight> findAll(){
        Response response = weightClient.findAll_JSON();
        GenericType<List<Weight>> genericType = new GenericType<List<Weight>>(){};
        return response.readEntity(genericType);
    }
    
    private List<Animal> findAllAnimal(){
        Response response = animalClient.findAll_JSON();
        GenericType<List<Animal>> genericType = new GenericType<List<Animal>>(){};
        List<Animal> ret = response.readEntity(genericType);
        ret.add(0, null);
        return ret;
    }
    
    private void save(){
        int selectedRowIdx = masterTable.getSelectedRow();
        int selectedModelIdx = masterTable.convertRowIndexToModel(selectedRowIdx);
        Weight selectedEntity = list.get(selectedModelIdx);
        if (selectedEntity.getWeightPK().getAnimalId() > 0){
            if (selectedEntity.getAnimal() == null || selectedEntity.getAnimal().getAnimalId() == null){
                selectedEntity.getWeightPK().setAnimalId(0);
            } else {
                selectedEntity.getWeightPK().setAnimalId(selectedEntity.getAnimal().getAnimalId());
            }
            String id = "animalId=" + selectedEntity.getWeightPK().getAnimalId() +";weighingDay=" + selectedEntity.getWeightPK().getWeighingDay();
            Response response = weightClient.edit_JSON(selectedEntity, id);
            //showDialog(response);
        } else {
            if (selectedEntity.getAnimal() == null || selectedEntity.getAnimal().getAnimalId() == null){
                selectedEntity.getWeightPK().setAnimalId(0);
            } else {
                selectedEntity.getWeightPK().setAnimalId(selectedEntity.getAnimal().getAnimalId());
            }
            Response response = weightClient.create_JSON(selectedEntity);
            //showDialog(response);
        }
    }

    private void delete(){
        int selectedRowIdx = masterTable.getSelectedRow();
        int selectedModelIdx = masterTable.convertRowIndexToModel(selectedRowIdx);
        Weight selectedEntity = list.get(selectedModelIdx);
        if (selectedEntity.getWeightPK().getAnimalId() > 0){
            String id = " ;animalId=" + selectedEntity.getWeightPK().getAnimalId() +";weighingDay=" + selectedEntity.getWeightPK().getWeighingDay();
            Response response = weightClient.remove(id);
            //showDialog(response);
        }
    }
    
    private void refresh(){
        list.clear();
        list.addAll(findAll());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        entityManager = null;
        query = null;
        list = java.beans.Beans.isDesignTime() ? new ArrayList() : org.jdesktop.observablecollections.ObservableCollections.observableList(findAll());
        animalQuery = null;
        animalList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllAnimal();
        tableScrollPane = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        JTableBinding jTableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ_WRITE, list, masterTable);
        JTableBinding.ColumnBinding columnBinding;
        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${weightPK.weighingDay}"));
        columnBinding.setColumnName("Weighing Day");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${animal}"));
        columnBinding.setColumnName("Animal Id");
        columnBinding.setColumnClass(com.whitesheep.entity.animal.Animal.class);
        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${weight}"));
        columnBinding.setColumnName("Weight");
        columnBinding.setColumnClass(Double.class);
        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${tstamp}"));
        columnBinding.setColumnName("Tstamp");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(ELProperty.create("${userName}"));
        columnBinding.setColumnName("User Name");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        refreshButton = new javax.swing.JButton();
        userNameLabel = new javax.swing.JLabel();
        weightLabel = new javax.swing.JLabel();
        tstampLabel = new javax.swing.JLabel();
        animalIdField = new javax.swing.JComboBox();
        animalIdLabel = new javax.swing.JLabel();
        weighingDayField = new org.jdesktop.swingx.JXDatePicker();
        weighingDayLabel = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        tstampField = new javax.swing.JFormattedTextField();
        userNameField = new javax.swing.JTextField();
        weightField = new javax.swing.JFormattedTextField(nf);

        FormListener formListener = new FormListener();

        masterTable.setAutoCreateRowSorter(true);
        tableScrollPane.setViewportView(masterTable);

        org.openide.awt.Mnemonics.setLocalizedText(refreshButton, org.openide.util.NbBundle.getMessage(WeightCRUD.class, "WeightCRUD.refreshButton.text")); // NOI18N
        refreshButton.addActionListener(formListener);

        org.openide.awt.Mnemonics.setLocalizedText(userNameLabel, org.openide.util.NbBundle.getMessage(WeightCRUD.class, "WeightCRUD.userNameLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(weightLabel, org.openide.util.NbBundle.getMessage(WeightCRUD.class, "WeightCRUD.weightLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(tstampLabel, org.openide.util.NbBundle.getMessage(WeightCRUD.class, "WeightCRUD.tstampLabel.text")); // NOI18N

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, animalList, animalIdField);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.animal}"), animalIdField, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), animalIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        org.openide.awt.Mnemonics.setLocalizedText(animalIdLabel, org.openide.util.NbBundle.getMessage(WeightCRUD.class, "WeightCRUD.animalIdLabel.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.weightPK.weighingDay}"), weighingDayField, org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), weighingDayField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        org.openide.awt.Mnemonics.setLocalizedText(weighingDayLabel, org.openide.util.NbBundle.getMessage(WeightCRUD.class, "WeightCRUD.weighingDayLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(saveButton, org.openide.util.NbBundle.getMessage(WeightCRUD.class, "WeightCRUD.saveButton.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), saveButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        saveButton.addActionListener(formListener);

        org.openide.awt.Mnemonics.setLocalizedText(newButton, org.openide.util.NbBundle.getMessage(WeightCRUD.class, "WeightCRUD.newButton.text")); // NOI18N
        newButton.addActionListener(formListener);

        org.openide.awt.Mnemonics.setLocalizedText(deleteButton, org.openide.util.NbBundle.getMessage(WeightCRUD.class, "WeightCRUD.deleteButton.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), deleteButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        deleteButton.addActionListener(formListener);

        tstampField.setEditable(false);
        tstampField.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.tstamp}"), tstampField, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        userNameField.setEditable(false);
        userNameField.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.userName}"), userNameField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("");
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.weight}"), weightField, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), weightField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tableScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(weightLabel)
                            .addComponent(tstampLabel)
                            .addComponent(userNameLabel)
                            .addComponent(animalIdLabel)
                            .addComponent(weighingDayLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(animalIdField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(weighingDayField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tstampField)
                            .addComponent(userNameField)
                            .addComponent(weightField, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(newButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteButton, newButton, saveButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weighingDayField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weighingDayLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(animalIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(animalIdLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weightLabel)
                    .addComponent(weightField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tstampLabel)
                    .addComponent(tstampField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userNameLabel)
                    .addComponent(userNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(deleteButton)
                    .addComponent(newButton)
                    .addComponent(refreshButton))
                .addContainerGap())
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == saveButton) {
                WeightCRUD.this.saveButtonActionPerformed(evt);
            }
            else if (evt.getSource() == newButton) {
                WeightCRUD.this.newButtonActionPerformed(evt);
            }
            else if (evt.getSource() == deleteButton) {
                WeightCRUD.this.deleteButtonActionPerformed(evt);
            }
            else if (evt.getSource() == refreshButton) {
                WeightCRUD.this.refreshButtonActionPerformed(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    

    @SuppressWarnings("unchecked")
    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        refresh();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        delete();
        refresh();
        dynamicContent.set(Collections.singleton(list), null);
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        com.whitesheep.entity.weight.Weight w = new com.whitesheep.entity.weight.Weight(new WeightPK());
        list.add(w);
        int row = list.size() - 1;
        masterTable.setRowSelectionInterval(row, row);
        masterTable.scrollRectToVisible(masterTable.getCellRect(row, 0, true));
    }//GEN-LAST:event_newButtonActionPerformed
    
    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        save();
        refresh();
        dynamicContent.set(Collections.singleton(list), null);
    }//GEN-LAST:event_saveButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox animalIdField;
    private javax.swing.JLabel animalIdLabel;
    private java.util.List<com.whitesheep.entity.animal.Animal> animalList;
    private javax.persistence.Query animalQuery;
    private javax.swing.JButton deleteButton;
    private javax.persistence.EntityManager entityManager;
    private java.util.List<com.whitesheep.entity.weight.Weight> list;
    private javax.swing.JTable masterTable;
    private javax.swing.JButton newButton;
    private javax.persistence.Query query;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JScrollPane tableScrollPane;
    private javax.swing.JFormattedTextField tstampField;
    private javax.swing.JLabel tstampLabel;
    private javax.swing.JTextField userNameField;
    private javax.swing.JLabel userNameLabel;
    private org.jdesktop.swingx.JXDatePicker weighingDayField;
    private javax.swing.JLabel weighingDayLabel;
    private javax.swing.JFormattedTextField weightField;
    private javax.swing.JLabel weightLabel;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WeightCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WeightCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WeightCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WeightCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setContentPane(new WeightCRUD());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
    
    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }

    @Override
    public void resultChanged(LookupEvent le) {
        Lookup.Result res = (Lookup.Result) le.getSource();
        Collection instances = res.allInstances();
        if (!instances.isEmpty()) {
            Iterator it = instances.iterator();
            while (it.hasNext()) {
                Object o = it.next();
                if (o instanceof List){
                    List list = (List)o;
                    if (list != null && !list.isEmpty()){
                        if (list.get(0) instanceof Animal){
                            animalList.clear();
                            animalList.addAll(list);
                            animalList.add(0, null);
                        }
                    }
                }
            }
        }
    }
    
}
