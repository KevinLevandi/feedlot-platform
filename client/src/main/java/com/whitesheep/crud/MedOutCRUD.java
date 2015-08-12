/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.crud;

import com.whitesheep.entity.Actor;
import com.whitesheep.entity.Ailment;
import com.whitesheep.entity.MedOut;
import com.whitesheep.entity.Medicine;
import com.whitesheep.entity.Supplier;
import com.whitesheep.entity.animal.Animal;
import com.whitesheep.rest.JerseyClient;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import java.util.List;
import javax.persistence.RollbackException;
import javax.swing.JFrame;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 *
 * @author timotius
 */
@ConvertAsProperties(
        dtd = "-//com.whitesheep.crud//MedOutCRUD//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "MedOutCRUD",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(
        mode = "editor", 
        openAtStartup = false)
@ActionID(
        category = "Window", 
        id = "com.whitesheep.crud.MedOutCRUD")
@ActionReference(
        path = "Menu/Window/Entity Manager")
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_MedOutCRUDAction",
        preferredID = "MedOutCRUD"
)
@NbBundle.Messages({
    "CTL_MedOutCRUDAction=MedOut Manager",
    "CTL_MedOutCRUDTopComponent=MedOut Manager",
    "HINT_MedOutCRUDTopComponent=This is a MedOut Manager window"
})
public class MedOutCRUD extends TopComponent implements LookupListener {
    
    private final JerseyClient medoutClient = new JerseyClient("medout");
    private final JerseyClient animalClient = new JerseyClient("animal");
    private final JerseyClient medicineClient = new JerseyClient("medicine");
    private final JerseyClient ailmentClient = new JerseyClient("ailment");
    private final JerseyClient actorClient = new JerseyClient("actor");
    private Lookup.Result<List> result = null;
    
    public MedOutCRUD() {
        initComponents();
        setName(Bundle.CTL_MedOutCRUDTopComponent());
        setToolTipText(Bundle.HINT_MedOutCRUDTopComponent());
    }
    
    private List<MedOut> findAll(){
        Response response = medoutClient.findAll_JSON();
        GenericType<List<MedOut>> genericType = new GenericType<List<MedOut>>(){};
        return response.readEntity(genericType);
    }
    
    private void save(){
        int selectedRowIdx = masterTable.getSelectedRow();
        int selectedModelIdx = masterTable.convertRowIndexToModel(selectedRowIdx);
        MedOut selectedEntity = list.get(selectedModelIdx);
        if (selectedEntity.getEventId() != null){
            Response response = medoutClient.edit_JSON(selectedEntity, selectedEntity.getEventId().toString());
            //showDialog(response);
        } else {
            Response response = medoutClient.create_JSON(selectedEntity);
            //showDialog(response);
        }
    }
    
    private void delete(){
        int selectedRowIdx = masterTable.getSelectedRow();
        int selectedModelIdx = masterTable.convertRowIndexToModel(selectedRowIdx);
        MedOut selectedEntity = list.get(selectedModelIdx);
        if (selectedEntity.getEventId() != null){
            Response response = medoutClient.remove(selectedEntity.getEventId().toString());
            //showDialog(response);
        }
    }
    
    private void refresh(){
        list.clear();
        list.addAll(findAll());
    }
    
    private List<Medicine> findAllMedicine(){
        Response response = medicineClient.findAll_JSON();
        GenericType<List<Medicine>> genericMedicine = new GenericType<List<Medicine>>(){};
        List<Medicine> ret = response.readEntity(genericMedicine);
        ret.add(0, null);
        return ret;
    }
    
    private List<Ailment> findAllAilment(){
        Response response = ailmentClient.findAll_JSON();
        GenericType<List<Ailment>> genericAilment = new GenericType<List<Ailment>>(){};
        List<Ailment> ret = response.readEntity(genericAilment);
        ret.add(0, null);
        return ret;
    }
    
    private List<Animal> findAllAnimal(){
        Response response = animalClient.findAll_JSON();
        GenericType<List<Animal>> genericAnimal = new GenericType<List<Animal>>(){};
        List<Animal> ret = response.readEntity(genericAnimal);
        ret.add(0, null);
        return ret;
    }
    
    private List<Actor> findAllActor(){
        Response response = actorClient.findAll_JSON();
        GenericType<List<Actor>> genericActor = new GenericType<List<Actor>>(){};
        List<Actor> ret = response.readEntity(genericActor);
        ret.add(0, null);
        return ret;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        entityManager = null;
        query = null;
        list = java.beans.Beans.isDesignTime() ? new ArrayList() : org.jdesktop.observablecollections.ObservableCollections.observableList(findAll());
        medicineQuery = null;
        medicineList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllMedicine();
        animalQuery = null;
        animalList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllAnimal();
        ailmentQuery = null;
        ailmentList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllAilment();
        actorQuery = null;
        actorList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllActor();
        masterScrollPane = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        eventIdLabel = new javax.swing.JLabel();
        eventDayLabel = new javax.swing.JLabel();
        medicineIdLabel = new javax.swing.JLabel();
        amountLabel = new javax.swing.JLabel();
        animalIdLabel = new javax.swing.JLabel();
        ailmentIdLabel = new javax.swing.JLabel();
        actorIdLabel = new javax.swing.JLabel();
        tstampLabel = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();
        eventIdField = new javax.swing.JTextField();
        userNameField = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        medicineIdField = new javax.swing.JComboBox();
        amountField = new javax.swing.JFormattedTextField();
        animalIdField = new javax.swing.JComboBox();
        ailmentIdField = new javax.swing.JComboBox();
        actorIdField = new javax.swing.JComboBox();
        tstampField = new javax.swing.JFormattedTextField();
        eventDayField = new org.jdesktop.swingx.JXDatePicker();

        FormListener formListener = new FormListener();

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, masterTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${eventId}"));
        columnBinding.setColumnName("Event Id");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${eventDay}"));
        columnBinding.setColumnName("Event Day");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${medicineId}"));
        columnBinding.setColumnName("Medicine Id");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${amount}"));
        columnBinding.setColumnName("Amount");
        columnBinding.setColumnClass(Double.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${animalId}"));
        columnBinding.setColumnName("Animal Id");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${ailmentId}"));
        columnBinding.setColumnName("Ailment Id");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${actorId}"));
        columnBinding.setColumnName("Actor Id");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${tstamp}"));
        columnBinding.setColumnName("Tstamp");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${userName}"));
        columnBinding.setColumnName("User Name");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);

        masterScrollPane.setViewportView(masterTable);

        org.openide.awt.Mnemonics.setLocalizedText(eventIdLabel, org.openide.util.NbBundle.getMessage(MedOutCRUD.class, "MedOutCRUD.eventIdLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(eventDayLabel, org.openide.util.NbBundle.getMessage(MedOutCRUD.class, "MedOutCRUD.eventDayLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(medicineIdLabel, org.openide.util.NbBundle.getMessage(MedOutCRUD.class, "MedOutCRUD.medicineIdLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(amountLabel, org.openide.util.NbBundle.getMessage(MedOutCRUD.class, "MedOutCRUD.amountLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(animalIdLabel, org.openide.util.NbBundle.getMessage(MedOutCRUD.class, "MedOutCRUD.animalIdLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(ailmentIdLabel, org.openide.util.NbBundle.getMessage(MedOutCRUD.class, "MedOutCRUD.ailmentIdLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(actorIdLabel, org.openide.util.NbBundle.getMessage(MedOutCRUD.class, "MedOutCRUD.actorIdLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(tstampLabel, org.openide.util.NbBundle.getMessage(MedOutCRUD.class, "MedOutCRUD.tstampLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(userNameLabel, org.openide.util.NbBundle.getMessage(MedOutCRUD.class, "MedOutCRUD.userNameLabel.text")); // NOI18N

        eventIdField.setEditable(false);
        eventIdField.setEnabled(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.eventId}"), eventIdField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        userNameField.setEditable(false);
        userNameField.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.userName}"), userNameField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceUnreadableValue("null");
        bindingGroup.addBinding(binding);

        org.openide.awt.Mnemonics.setLocalizedText(saveButton, org.openide.util.NbBundle.getMessage(MedOutCRUD.class, "MedOutCRUD.saveButton.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), saveButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        saveButton.addActionListener(formListener);

        org.openide.awt.Mnemonics.setLocalizedText(refreshButton, org.openide.util.NbBundle.getMessage(MedOutCRUD.class, "MedOutCRUD.refreshButton.text")); // NOI18N
        refreshButton.addActionListener(formListener);

        org.openide.awt.Mnemonics.setLocalizedText(newButton, org.openide.util.NbBundle.getMessage(MedOutCRUD.class, "MedOutCRUD.newButton.text")); // NOI18N
        newButton.addActionListener(formListener);

        org.openide.awt.Mnemonics.setLocalizedText(deleteButton, org.openide.util.NbBundle.getMessage(MedOutCRUD.class, "MedOutCRUD.deleteButton.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null && selectedElement.eventId != null}"), deleteButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        deleteButton.addActionListener(formListener);

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, medicineList, medicineIdField);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), medicineIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), amountField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, animalList, animalIdField);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), animalIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, ailmentList, ailmentIdField);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), ailmentIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, actorList, actorIdField);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), actorIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        tstampField.setEditable(false);
        tstampField.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), eventDayField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(newButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(eventIdLabel)
                                    .addComponent(eventDayLabel)
                                    .addComponent(medicineIdLabel)
                                    .addComponent(amountLabel)
                                    .addComponent(animalIdLabel)
                                    .addComponent(ailmentIdLabel)
                                    .addComponent(actorIdLabel)
                                    .addComponent(tstampLabel)
                                    .addComponent(userNameLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(eventIdField, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                                    .addComponent(userNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                                    .addComponent(medicineIdField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(amountField)
                                    .addComponent(animalIdField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ailmentIdField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(actorIdField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tstampField)
                                    .addComponent(eventDayField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(masterScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteButton, newButton, refreshButton, saveButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(masterScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eventIdLabel)
                    .addComponent(eventIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eventDayLabel)
                    .addComponent(eventDayField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(medicineIdLabel)
                    .addComponent(medicineIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(amountLabel)
                    .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(animalIdLabel)
                    .addComponent(animalIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ailmentIdLabel)
                    .addComponent(ailmentIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(actorIdLabel)
                    .addComponent(actorIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(refreshButton)
                    .addComponent(deleteButton)
                    .addComponent(newButton))
                .addContainerGap())
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == saveButton) {
                MedOutCRUD.this.saveButtonActionPerformed(evt);
            }
            else if (evt.getSource() == refreshButton) {
                MedOutCRUD.this.refreshButtonActionPerformed(evt);
            }
            else if (evt.getSource() == newButton) {
                MedOutCRUD.this.newButtonActionPerformed(evt);
            }
            else if (evt.getSource() == deleteButton) {
                MedOutCRUD.this.deleteButtonActionPerformed(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    

    @SuppressWarnings("unchecked")
    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        refresh();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        delete();refresh();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        MedOut m = new MedOut();
        list.add(m);
        int row = list.size() - 1;
        masterTable.setRowSelectionInterval(row, row);
        masterTable.scrollRectToVisible(masterTable.getCellRect(row, 0, true));
    }//GEN-LAST:event_newButtonActionPerformed
    
    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        save();refresh();
    }//GEN-LAST:event_saveButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox actorIdField;
    private javax.swing.JLabel actorIdLabel;
    private java.util.List<com.whitesheep.entity.Actor> actorList;
    private javax.persistence.Query actorQuery;
    private javax.swing.JComboBox ailmentIdField;
    private javax.swing.JLabel ailmentIdLabel;
    private java.util.List<com.whitesheep.entity.Ailment> ailmentList;
    private javax.persistence.Query ailmentQuery;
    private javax.swing.JFormattedTextField amountField;
    private javax.swing.JLabel amountLabel;
    private javax.swing.JComboBox animalIdField;
    private javax.swing.JLabel animalIdLabel;
    private java.util.List<com.whitesheep.entity.animal.Animal> animalList;
    private javax.persistence.Query animalQuery;
    private javax.swing.JButton deleteButton;
    private javax.persistence.EntityManager entityManager;
    private org.jdesktop.swingx.JXDatePicker eventDayField;
    private javax.swing.JLabel eventDayLabel;
    private javax.swing.JTextField eventIdField;
    private javax.swing.JLabel eventIdLabel;
    private java.util.List<com.whitesheep.entity.MedOut> list;
    private javax.swing.JScrollPane masterScrollPane;
    private javax.swing.JTable masterTable;
    private javax.swing.JComboBox medicineIdField;
    private javax.swing.JLabel medicineIdLabel;
    private java.util.List<com.whitesheep.entity.Medicine> medicineList;
    private javax.persistence.Query medicineQuery;
    private javax.swing.JButton newButton;
    private javax.persistence.Query query;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JFormattedTextField tstampField;
    private javax.swing.JLabel tstampLabel;
    private javax.swing.JTextField userNameField;
    private javax.swing.JLabel userNameLabel;
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
            java.util.logging.Logger.getLogger(MedOutCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MedOutCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MedOutCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MedOutCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setContentPane(new MedOutCRUD());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    @Override
    public void componentOpened() {
        result = Utilities.actionsGlobalContext().lookupResult(List.class); 
        result.addLookupListener (this); 
    }

    @Override
    public void componentClosed() {
        result.removeLookupListener(this); 
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
                        if (list.get(0) instanceof Medicine){
                            medicineList.clear();
                            medicineList.addAll(list);
                            medicineList.add(0, null);
                        } else if (list.get(0) instanceof Animal){
                            animalList.clear();
                            animalList.addAll(list);
                            animalList.add(0, null);
                        } else if (list.get(0) instanceof Actor){
                            actorList.clear();
                            actorList.addAll(list);
                            actorList.add(0, null);
                        } else if (list.get(0) instanceof Ailment){
                            ailmentList.clear();
                            ailmentList.addAll(list);
                            ailmentList.add(0, null);
                        }
                    }
                }
            }
        }
    }
}
