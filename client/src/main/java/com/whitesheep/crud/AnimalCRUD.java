/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.crud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whitesheep.entity.Aim;
import com.whitesheep.entity.animal.Animal;
import com.whitesheep.entity.animal.AnimalFilter;
import com.whitesheep.entity.Bnature;
import com.whitesheep.entity.Confinement;
import com.whitesheep.entity.Customer;
import com.whitesheep.entity.Dnature;
import com.whitesheep.entity.Feed;
import com.whitesheep.entity.Flag;
import com.whitesheep.entity.Sex;
import com.whitesheep.entity.Supplier;
import com.whitesheep.entity.Variant;
import com.whitesheep.rest.JerseyClient;
import java.awt.EventQueue;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.text.NumberFormatter;
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
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.TopComponent;

/**
 *
 * @author timotius
 */
@ConvertAsProperties(
        dtd = "-//com.whitesheep.crud//AnimalCRUD//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "AnimalCRUD",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(
        mode = "editor", 
        openAtStartup = false)
@ActionID(
        category = "Window", 
        id = "com.whitesheep.crud.AnimalCRUD")
@ActionReference(
        path = "Menu/Window/Entity Manager")
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_AnimalCRUDAction",
        preferredID = "AnimalCRUD"
)
@NbBundle.Messages({
    "CTL_AnimalCRUDAction=Animal Manager",
    "CTL_AnimalCRUDTopComponent=Animal Manager",
    "HINT_AnimalCRUDTopComponent=This is a Animal Manager window"
})
public class AnimalCRUD extends TopComponent implements LookupListener, Lookup.Provider {
    
    private final JerseyClient animalClient;
    private final JerseyClient variantClient;
    private final JerseyClient sexClient;
    private final JerseyClient bnatureClient;
    private final JerseyClient dnatureClient;
    private final JerseyClient supplierClient;
    private final JerseyClient customerClient;
    private final JerseyClient feedClient;
    private final JerseyClient confinementClient;
    private final JerseyClient aimClient;
    private final JerseyClient flagClient;
    private AnimalFilter filter;
    private Lookup.Result<List> result = null;
    private final InstanceContent dynamicContent = new InstanceContent();
    private final NumberFormatter nf = new NumberFormatter(NumberFormat.getIntegerInstance()){
        @Override
        public String valueToString(Object iv) throws ParseException {
            return iv == null ? "" : ((Integer)iv).toString();
        }
        @Override
        public Object stringToValue(String text) throws ParseException {
            return text.isEmpty() ? null : NumberFormat.getNumberInstance().parse(text).intValue();
        }
    };

    public AnimalCRUD() {
        animalClient = new JerseyClient("animal");
        variantClient = new JerseyClient("variant");
        sexClient = new JerseyClient("sex");
        bnatureClient = new JerseyClient("bnature");
        dnatureClient = new JerseyClient("dnature");
        supplierClient = new JerseyClient("supplier");
        customerClient = new JerseyClient("customer");
        feedClient = new JerseyClient("feed");
        confinementClient = new JerseyClient("confinement");
        aimClient = new JerseyClient("aim");
        flagClient = new JerseyClient("flag");
        filter = new AnimalFilter();
        initComponents();
        setName(Bundle.CTL_AnimalCRUDTopComponent());
        setToolTipText(Bundle.HINT_AnimalCRUDTopComponent());
        associateLookup(new AbstractLookup(dynamicContent));
    }
    
    public AnimalFilter getFilter() {
        return filter;
    }

    public void setFilter(AnimalFilter filter) {
        this.filter = filter;
    }
    
    private List<Animal> findAll(){
        Response response = animalClient.findAll_JSON();
        GenericType<List<Animal>> genericType = new GenericType<List<Animal>>(){};
        return response.readEntity(genericType);
    }
    
    private List<Animal> findQueried(){
        ObjectMapper m = new ObjectMapper();
        Map<String,Object> props = m.convertValue(filter, Map.class);
        Response response = animalClient.findQueried_JSON(props);
        GenericType<List<Animal>> genericType = new GenericType<List<Animal>>(){};
        return response.readEntity(genericType);
    }

    private void save(){
        int selectedRowIdx = masterTable.getSelectedRow();
        int selectedModelIdx = masterTable.convertRowIndexToModel(selectedRowIdx);
        Animal selectedEntity = list.get(selectedModelIdx);
        if (selectedEntity.getAnimalId() != null){
            Response response = animalClient.edit_JSON(selectedEntity, selectedEntity.getAnimalId().toString());
            //showDialog(response);
        } else {
            List<Animal> entityList = new ArrayList();
            for (int i = 0; i < selectedEntity.getLoop(); i++){
                entityList.add(selectedEntity);
            }
            Response response = animalClient.create_JSON(entityList);
            //showDialog(response);
        }
    }
    
    private void delete(){
        int selectedRowIdx = masterTable.getSelectedRow();
        int selectedModelIdx = masterTable.convertRowIndexToModel(selectedRowIdx);
        Animal selectedEntity = list.get(selectedModelIdx);
        if (selectedEntity.getAnimalId() != null){
            Response response = animalClient.remove(selectedEntity.getAnimalId().toString());
            //showDialog(response);
        }
    }
    
    private void refresh(){
        list.clear();
        list.addAll(findQueried());
    }
    
    private List<Variant> findAllVariant(){
        Response response = variantClient.findAll_JSON();
        GenericType<List<Variant>> genericType = new GenericType<List<Variant>>(){};
        List<Variant> ret = response.readEntity(genericType);
        ret.add(0, null);
        return ret;
    }
    
    private List<Sex> findAllSex(){
        Response response = sexClient.findAll_JSON();
        GenericType<List<Sex>> genericType = new GenericType<List<Sex>>(){};
        List<Sex> ret = response.readEntity(genericType);
        ret.add(0, null);
        return ret;
    }
    
    private List<Bnature> findAllBnature(){
        Response response = bnatureClient.findAll_JSON();
        GenericType<List<Bnature>> genericType = new GenericType<List<Bnature>>(){};
        List<Bnature> ret = response.readEntity(genericType);
        ret.add(0, null);
        return ret;
    }
    
    private List<Dnature> findAllDnature(){
        Response response = dnatureClient.findAll_JSON();
        GenericType<List<Dnature>> genericType = new GenericType<List<Dnature>>(){};
        List<Dnature> ret = response.readEntity(genericType);
        ret.add(0, null);
        return ret;
    }
    
    private List<Supplier> findAllSupplier(){
        Response response = supplierClient.findAll_JSON();
        GenericType<List<Supplier>> genericType = new GenericType<List<Supplier>>(){};
        List<Supplier> ret = response.readEntity(genericType);
        ret.add(0, null);
        return ret;
    }
    
    private List<Customer> findAllCustomer(){
        Response response = customerClient.findAll_JSON();
        GenericType<List<Customer>> genericType = new GenericType<List<Customer>>(){};
        List<Customer> ret = response.readEntity(genericType);
        ret.add(0, null);
        return ret;
    }
    
    private List<Feed> findAllFeed(){
        Response response = feedClient.findAll_JSON();
        GenericType<List<Feed>> genericType = new GenericType<List<Feed>>(){};
        List<Feed> ret = response.readEntity(genericType);
        ret.add(0, null);
        return ret;
    }
    
    private List<Confinement> findAllConfinement(){
        Response response = confinementClient.findAll_JSON();
        GenericType<List<Confinement>> genericType = new GenericType<List<Confinement>>(){};
        List<Confinement> ret = response.readEntity(genericType);
        ret.add(0, null);
        return ret;
    }
    
    private List<Aim> findAllAim(){
        Response response = aimClient.findAll_JSON();
        GenericType<List<Aim>> genericType = new GenericType<List<Aim>>(){};
        List<Aim> ret = response.readEntity(genericType);
        ret.add(0, null);
        return ret;
    }
    
    private List<Flag> findAllFlag(){
        Response response = flagClient.findAll_JSON();
        GenericType<List<Flag>> genericType = new GenericType<List<Flag>>(){};
        List<Flag> ret = response.readEntity(genericType);
        ret.add(0, null);
        return ret;
    }
    
    private List<Animal> findAllMother(){
        return findAll();
    }
    
    private List<Animal> findAllFather(){
        return findAll();
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
        variantList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllVariant();
        sexList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllSex();
        bnatureList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllBnature();
        dnatureList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllDnature();
        supplierList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllSupplier();
        customerList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllCustomer();
        feedList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllFeed();
        confinementList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllConfinement();
        aimList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllAim();
        flagList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllFlag();
        animalList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllMother();
        animalList1 = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllFather();
        masterScrollPane = new javax.swing.JScrollPane();
        masterTable = new javax.swing.JTable();
        detailTabbedPane = new javax.swing.JTabbedPane();
        crudScrollPane = new javax.swing.JScrollPane();
        crudPanel = new javax.swing.JPanel();
        nameField = new javax.swing.JTextField();
        flagIdLabel = new javax.swing.JLabel();
        tstampLabel = new javax.swing.JLabel();
        genomeLabel = new javax.swing.JLabel();
        aimIdLabel = new javax.swing.JLabel();
        animalIdLabel = new javax.swing.JLabel();
        sellDayLabel = new javax.swing.JLabel();
        sexIdLabel = new javax.swing.JLabel();
        priceOutLabel = new javax.swing.JLabel();
        fatherIdLabel = new javax.swing.JLabel();
        supplierIdLabel = new javax.swing.JLabel();
        priceInLabel = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();
        bnatureIdLabel = new javax.swing.JLabel();
        deathDayLabel = new javax.swing.JLabel();
        buyDayLabel = new javax.swing.JLabel();
        motherIdLabel = new javax.swing.JLabel();
        birthDayLabel = new javax.swing.JLabel();
        customerIdLabel = new javax.swing.JLabel();
        tstampField = new javax.swing.JFormattedTextField();
        dnatureIdLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        variantIdLabel = new javax.swing.JLabel();
        feedIdLabel = new javax.swing.JLabel();
        confinementIdLabel = new javax.swing.JLabel();
        variantIdField = new javax.swing.JComboBox();
        animalIdField = new javax.swing.JTextField();
        sexIdField = new javax.swing.JComboBox();
        bnatureIdField = new javax.swing.JComboBox();
        deathDayField = new org.jdesktop.swingx.JXDatePicker();
        birthDayField = new org.jdesktop.swingx.JXDatePicker();
        buyDayField = new org.jdesktop.swingx.JXDatePicker();
        sellDayField = new org.jdesktop.swingx.JXDatePicker();
        priceInField = new javax.swing.JFormattedTextField(nf);
        priceOutField = new javax.swing.JFormattedTextField(nf);
        dnatureIdField = new javax.swing.JComboBox();
        supplierIdField = new javax.swing.JComboBox();
        customerIdField = new javax.swing.JComboBox();
        feedIdField = new javax.swing.JComboBox();
        confinementIdField = new javax.swing.JComboBox();
        aimIdField = new javax.swing.JComboBox();
        flagIdField = new javax.swing.JComboBox();
        genomeField = new javax.swing.JTextField();
        userNameField = new javax.swing.JTextField();
        motherIdField = new javax.swing.JComboBox();
        fatherIdField = new javax.swing.JComboBox();
        loopField = new javax.swing.JSpinner();
        loopLabel = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        newButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        filterScrollPane = new javax.swing.JScrollPane();
        crudPanel1 = new javax.swing.JPanel();
        nameField1 = new javax.swing.JTextField();
        flagIdLabel1 = new javax.swing.JLabel();
        aimIdLabel1 = new javax.swing.JLabel();
        sellDayLabel1 = new javax.swing.JLabel();
        sexIdLabel1 = new javax.swing.JLabel();
        priceOutLabel1 = new javax.swing.JLabel();
        fatherIdLabel1 = new javax.swing.JLabel();
        supplierIdLabel1 = new javax.swing.JLabel();
        priceInLabel1 = new javax.swing.JLabel();
        bnatureIdLabel1 = new javax.swing.JLabel();
        deathDayLabel1 = new javax.swing.JLabel();
        buyDayLabel1 = new javax.swing.JLabel();
        motherIdLabel1 = new javax.swing.JLabel();
        birthDayLabel1 = new javax.swing.JLabel();
        customerIdLabel1 = new javax.swing.JLabel();
        dnatureIdLabel1 = new javax.swing.JLabel();
        nameLabel1 = new javax.swing.JLabel();
        variantIdLabel1 = new javax.swing.JLabel();
        feedIdLabel1 = new javax.swing.JLabel();
        confinementIdLabel1 = new javax.swing.JLabel();
        variantIdField1 = new javax.swing.JComboBox();
        sexIdField1 = new javax.swing.JComboBox();
        bnatureIdField1 = new javax.swing.JComboBox();
        deathDayField1 = new org.jdesktop.swingx.JXDatePicker();
        birthDayField1 = new org.jdesktop.swingx.JXDatePicker();
        buyDayField1 = new org.jdesktop.swingx.JXDatePicker();
        sellDayField1 = new org.jdesktop.swingx.JXDatePicker();
        priceInField1 = new javax.swing.JFormattedTextField(nf);
        priceOutField1 = new javax.swing.JFormattedTextField(nf);
        dnatureIdField1 = new javax.swing.JComboBox();
        supplierIdField1 = new javax.swing.JComboBox();
        customerIdField1 = new javax.swing.JComboBox();
        feedIdField1 = new javax.swing.JComboBox();
        confinementIdField1 = new javax.swing.JComboBox();
        aimIdField1 = new javax.swing.JComboBox();
        flagIdField1 = new javax.swing.JComboBox();
        motherIdField1 = new javax.swing.JComboBox();
        fatherIdField1 = new javax.swing.JComboBox();
        birthDayField2 = new org.jdesktop.swingx.JXDatePicker();
        deathDayField2 = new org.jdesktop.swingx.JXDatePicker();
        buyDayField2 = new org.jdesktop.swingx.JXDatePicker();
        priceInField2 = new javax.swing.JFormattedTextField(nf);
        priceOutField2 = new javax.swing.JFormattedTextField(nf);
        jXDatePicker1 = new org.jdesktop.swingx.JXDatePicker();
        refreshButton = new javax.swing.JButton();

        FormListener formListener = new FormListener();

        masterTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list, masterTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${animalId}"));
        columnBinding.setColumnName("Animal Id");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${name}"));
        columnBinding.setColumnName("Name");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${variantId}"));
        columnBinding.setColumnName("Variant Id");
        columnBinding.setColumnClass(com.whitesheep.entity.Variant.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${sexId}"));
        columnBinding.setColumnName("Sex Id");
        columnBinding.setColumnClass(com.whitesheep.entity.Sex.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${birthDay}"));
        columnBinding.setColumnName("Birth Day");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${bnatureId}"));
        columnBinding.setColumnName("Bnature Id");
        columnBinding.setColumnClass(com.whitesheep.entity.Bnature.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${deathDay}"));
        columnBinding.setColumnName("Death Day");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${dnatureId}"));
        columnBinding.setColumnName("Dnature Id");
        columnBinding.setColumnClass(com.whitesheep.entity.Dnature.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${buyDay}"));
        columnBinding.setColumnName("Buy Day");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${supplierId}"));
        columnBinding.setColumnName("Supplier Id");
        columnBinding.setColumnClass(com.whitesheep.entity.Supplier.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${priceIn}"));
        columnBinding.setColumnName("Price In");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${sellDay}"));
        columnBinding.setColumnName("Sell Day");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${customerId}"));
        columnBinding.setColumnName("Customer Id");
        columnBinding.setColumnClass(com.whitesheep.entity.Customer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${priceOut}"));
        columnBinding.setColumnName("Price Out");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${feedId}"));
        columnBinding.setColumnName("Feed Id");
        columnBinding.setColumnClass(com.whitesheep.entity.Feed.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${confinementId}"));
        columnBinding.setColumnName("Confinement Id");
        columnBinding.setColumnClass(com.whitesheep.entity.Confinement.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${aimId}"));
        columnBinding.setColumnName("Aim Id");
        columnBinding.setColumnClass(com.whitesheep.entity.Aim.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${flagId}"));
        columnBinding.setColumnName("Flag Id");
        columnBinding.setColumnClass(com.whitesheep.entity.Flag.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${motherId}"));
        columnBinding.setColumnName("Mother Id");
        columnBinding.setColumnClass(com.whitesheep.entity.animal.Animal.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fatherId}"));
        columnBinding.setColumnName("Father Id");
        columnBinding.setColumnClass(com.whitesheep.entity.animal.Animal.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${genome}"));
        columnBinding.setColumnName("Genome");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${tstamp}"));
        columnBinding.setColumnName("Tstamp");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${userName}"));
        columnBinding.setColumnName("User Name");
        columnBinding.setColumnClass(com.whitesheep.entity.User.class);
        bindingGroup.addBinding(jTableBinding);

        masterScrollPane.setViewportView(masterTable);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.name}"), nameField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("");
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), nameField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        org.openide.awt.Mnemonics.setLocalizedText(flagIdLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.flagIdLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(tstampLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.tstampLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(genomeLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.genomeLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(aimIdLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.aimIdLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(animalIdLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.animalIdLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(sellDayLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.sellDayLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(sexIdLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.sexIdLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(priceOutLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.priceOutLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(fatherIdLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.fatherIdLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(supplierIdLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.supplierIdLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(priceInLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.priceInLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(userNameLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.userNameLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(bnatureIdLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.bnatureIdLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(deathDayLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.deathDayLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(buyDayLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.buyDayLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(motherIdLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.motherIdLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(birthDayLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.birthDayLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(customerIdLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.customerIdLabel.text_1")); // NOI18N

        tstampField.setEditable(false);
        tstampField.setText(org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.tstampField.text")); // NOI18N
        tstampField.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.tstamp}"), tstampField, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        org.openide.awt.Mnemonics.setLocalizedText(dnatureIdLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.dnatureIdLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(nameLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.nameLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(variantIdLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.variantIdLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(feedIdLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.feedIdLabel.text_1")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(confinementIdLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.confinementIdLabel.text_1")); // NOI18N

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, variantList, variantIdField);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.variantId}"), variantIdField, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), variantIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        animalIdField.setEditable(false);
        animalIdField.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.animalId}"), animalIdField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sexList, sexIdField);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.sexId}"), sexIdField, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), sexIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, bnatureList, bnatureIdField);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.bnatureId}"), bnatureIdField, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), bnatureIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.deathDay}"), deathDayField, org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), deathDayField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.birthDay}"), birthDayField, org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), birthDayField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.buyDay}"), buyDayField, org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), buyDayField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.sellDay}"), sellDayField, org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), sellDayField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        priceInField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        priceInField.setText(org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.priceInField.text")); // NOI18N
        priceInField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.priceIn}"), priceInField, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), priceInField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        priceOutField.setText(org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.priceOutField.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.priceOut}"), priceOutField, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), priceOutField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dnatureList, dnatureIdField);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.dnatureId}"), dnatureIdField, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), dnatureIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, supplierList, supplierIdField);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.supplierId}"), supplierIdField, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), supplierIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, customerList, customerIdField);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.customerId}"), customerIdField, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), customerIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, feedList, feedIdField);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.feedId}"), feedIdField, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), feedIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, confinementList, confinementIdField);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.confinementId}"), confinementIdField, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), confinementIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, aimList, aimIdField);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.aimId}"), aimIdField, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), aimIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, flagList, flagIdField);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.flagId}"), flagIdField, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), flagIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        genomeField.setEditable(false);
        genomeField.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.genome}"), genomeField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("");
        bindingGroup.addBinding(binding);

        userNameField.setEditable(false);
        userNameField.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.userName.userName}"), userNameField, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, animalList, motherIdField);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.motherId}"), motherIdField, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), motherIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, animalList1, fatherIdField);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.fatherId}"), fatherIdField, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), fatherIdField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        loopField.setModel(new javax.swing.SpinnerNumberModel(1, 1, 10, 1));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.loop}"), loopField, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null && selectedElement.animalId == null}"), loopField, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        org.openide.awt.Mnemonics.setLocalizedText(loopLabel, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.loopLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(saveButton, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.saveButton.text_1")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null}"), saveButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        saveButton.addActionListener(formListener);

        org.openide.awt.Mnemonics.setLocalizedText(newButton, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.newButton.text_1")); // NOI18N
        newButton.addActionListener(formListener);

        org.openide.awt.Mnemonics.setLocalizedText(deleteButton, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.deleteButton.text_1")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, masterTable, org.jdesktop.beansbinding.ELProperty.create("${selectedElement != null && selectedElement.animalId != null}"), deleteButton, org.jdesktop.beansbinding.BeanProperty.create("enabled"));
        bindingGroup.addBinding(binding);

        deleteButton.addActionListener(formListener);

        javax.swing.GroupLayout crudPanelLayout = new javax.swing.GroupLayout(crudPanel);
        crudPanel.setLayout(crudPanelLayout);
        crudPanelLayout.setHorizontalGroup(
            crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crudPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crudPanelLayout.createSequentialGroup()
                        .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(animalIdLabel)
                            .addComponent(nameLabel)
                            .addComponent(variantIdLabel)
                            .addComponent(sexIdLabel)
                            .addComponent(birthDayLabel)
                            .addComponent(bnatureIdLabel)
                            .addComponent(deathDayLabel)
                            .addComponent(dnatureIdLabel)
                            .addComponent(buyDayLabel)
                            .addComponent(supplierIdLabel)
                            .addComponent(priceInLabel)
                            .addComponent(sellDayLabel)
                            .addComponent(customerIdLabel)
                            .addComponent(priceOutLabel)
                            .addComponent(feedIdLabel)
                            .addComponent(confinementIdLabel)
                            .addComponent(aimIdLabel)
                            .addComponent(flagIdLabel)
                            .addComponent(motherIdLabel)
                            .addComponent(fatherIdLabel)
                            .addComponent(genomeLabel)
                            .addComponent(tstampLabel)
                            .addComponent(userNameLabel)
                            .addComponent(loopLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameField)
                            .addComponent(tstampField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(variantIdField, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(animalIdField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sexIdField, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bnatureIdField, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deathDayField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                            .addComponent(birthDayField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buyDayField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sellDayField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(priceInField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(priceOutField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(customerIdField, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(supplierIdField, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dnatureIdField, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(feedIdField, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(confinementIdField, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(aimIdField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(flagIdField, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(genomeField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(userNameField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(motherIdField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fatherIdField, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(loopField)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crudPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(newButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)))
                .addContainerGap())
        );

        crudPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {deleteButton, newButton, saveButton});

        crudPanelLayout.setVerticalGroup(
            crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crudPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(animalIdLabel)
                    .addComponent(animalIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(variantIdLabel)
                    .addComponent(variantIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sexIdLabel)
                    .addComponent(sexIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(birthDayLabel)
                    .addComponent(birthDayField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bnatureIdLabel)
                    .addComponent(bnatureIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deathDayLabel)
                    .addComponent(deathDayField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dnatureIdLabel)
                    .addComponent(dnatureIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buyDayLabel)
                    .addComponent(buyDayField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(supplierIdLabel)
                    .addComponent(supplierIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(priceInLabel)
                    .addComponent(priceInField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sellDayLabel)
                    .addComponent(sellDayField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerIdLabel)
                    .addComponent(customerIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(priceOutLabel)
                    .addComponent(priceOutField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(feedIdLabel)
                    .addComponent(feedIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confinementIdLabel)
                    .addComponent(confinementIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aimIdLabel)
                    .addComponent(aimIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(flagIdLabel)
                    .addComponent(flagIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(motherIdLabel)
                    .addComponent(motherIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fatherIdLabel)
                    .addComponent(fatherIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(genomeLabel)
                    .addComponent(genomeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tstampLabel)
                    .addComponent(tstampField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userNameLabel)
                    .addComponent(userNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loopField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(loopLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(crudPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(deleteButton)
                    .addComponent(newButton))
                .addContainerGap())
        );

        crudScrollPane.setViewportView(crudPanel);

        detailTabbedPane.addTab(org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.crudScrollPane.TabConstraints.tabTitle"), crudScrollPane); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.name}"), nameField1, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("");
        bindingGroup.addBinding(binding);

        org.openide.awt.Mnemonics.setLocalizedText(flagIdLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.flagIdLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(aimIdLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.aimIdLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(sellDayLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.sellDayLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(sexIdLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.sexIdLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(priceOutLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.priceOutLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(fatherIdLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.fatherIdLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(supplierIdLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.supplierIdLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(priceInLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.priceInLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(bnatureIdLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.bnatureIdLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(deathDayLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.deathDayLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(buyDayLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.buyDayLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(motherIdLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.motherIdLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(birthDayLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.birthDayLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(customerIdLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.customerIdLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(dnatureIdLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.dnatureIdLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(nameLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.nameLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(variantIdLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.variantIdLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(feedIdLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.feedIdLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(confinementIdLabel1, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.confinementIdLabel1.text")); // NOI18N

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, variantList, variantIdField1);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.variant}"), variantIdField1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, sexList, sexIdField1);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.sex}"), sexIdField1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, bnatureList, bnatureIdField1);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.bnature}"), bnatureIdField1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.deathDayFrom}"), deathDayField1, org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.birthDayFrom}"), birthDayField1, org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.buyDayFrom}"), buyDayField1, org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.sellDayFrom}"), sellDayField1, org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        priceInField1.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        priceInField1.setText(org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.priceInField1.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.priceInFrom}"), priceInField1, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        priceOutField1.setText(org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.priceOutField1.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.priceOutFrom}"), priceOutField1, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, dnatureList, dnatureIdField1);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.dnature}"), dnatureIdField1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, supplierList, supplierIdField1);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.supplier}"), supplierIdField1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, customerList, customerIdField1);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.customer}"), customerIdField1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, feedList, feedIdField1);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.feed}"), feedIdField1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, confinementList, confinementIdField1);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.confinement}"), confinementIdField1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, aimList, aimIdField1);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.aim}"), aimIdField1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, flagList, flagIdField1);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.flag}"), flagIdField1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, animalList, motherIdField1);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.mother}"), motherIdField1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, animalList1, fatherIdField1);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.father}"), fatherIdField1, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.birthDayTo}"), birthDayField2, org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.deathDayTo}"), deathDayField2, org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.buyDayTo}"), buyDayField2, org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        priceInField2.setText(org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.priceInField2.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.priceInTo}"), priceInField2, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        priceOutField2.setText(org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.priceOutField2.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.priceOutTo}"), priceOutField2, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.sellDayTo}"), jXDatePicker1, org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        org.openide.awt.Mnemonics.setLocalizedText(refreshButton, org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.refreshButton.text_1")); // NOI18N
        refreshButton.addActionListener(formListener);

        javax.swing.GroupLayout crudPanel1Layout = new javax.swing.GroupLayout(crudPanel1);
        crudPanel1.setLayout(crudPanel1Layout);
        crudPanel1Layout.setHorizontalGroup(
            crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crudPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crudPanel1Layout.createSequentialGroup()
                        .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel1)
                            .addComponent(variantIdLabel1)
                            .addComponent(sexIdLabel1)
                            .addComponent(birthDayLabel1)
                            .addComponent(bnatureIdLabel1)
                            .addComponent(deathDayLabel1)
                            .addComponent(dnatureIdLabel1)
                            .addComponent(buyDayLabel1)
                            .addComponent(supplierIdLabel1)
                            .addComponent(priceInLabel1)
                            .addComponent(sellDayLabel1)
                            .addComponent(customerIdLabel1)
                            .addComponent(priceOutLabel1)
                            .addComponent(feedIdLabel1)
                            .addComponent(confinementIdLabel1)
                            .addComponent(aimIdLabel1)
                            .addComponent(flagIdLabel1)
                            .addComponent(motherIdLabel1)
                            .addComponent(fatherIdLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameField1)
                            .addComponent(variantIdField1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sexIdField1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bnatureIdField1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(customerIdField1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(supplierIdField1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dnatureIdField1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(feedIdField1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(confinementIdField1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(aimIdField1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(flagIdField1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(motherIdField1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fatherIdField1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(crudPanel1Layout.createSequentialGroup()
                                .addComponent(birthDayField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(birthDayField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(crudPanel1Layout.createSequentialGroup()
                                .addComponent(deathDayField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deathDayField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(crudPanel1Layout.createSequentialGroup()
                                .addComponent(buyDayField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buyDayField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crudPanel1Layout.createSequentialGroup()
                                .addComponent(priceInField1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(priceInField2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crudPanel1Layout.createSequentialGroup()
                                .addComponent(priceOutField1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(priceOutField2))
                            .addGroup(crudPanel1Layout.createSequentialGroup()
                                .addComponent(sellDayField1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jXDatePicker1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crudPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(refreshButton)))
                .addContainerGap())
        );
        crudPanel1Layout.setVerticalGroup(
            crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crudPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel1)
                    .addComponent(nameField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(variantIdLabel1)
                    .addComponent(variantIdField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sexIdLabel1)
                    .addComponent(sexIdField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(birthDayLabel1)
                    .addComponent(birthDayField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(birthDayField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bnatureIdLabel1)
                    .addComponent(bnatureIdField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deathDayLabel1)
                    .addComponent(deathDayField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deathDayField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dnatureIdLabel1)
                    .addComponent(dnatureIdField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buyDayLabel1)
                    .addComponent(buyDayField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buyDayField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(supplierIdLabel1)
                    .addComponent(supplierIdField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(priceInLabel1)
                    .addComponent(priceInField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priceInField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sellDayLabel1)
                    .addComponent(sellDayField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jXDatePicker1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerIdLabel1)
                    .addComponent(customerIdField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(priceOutLabel1)
                    .addComponent(priceOutField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(priceOutField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(feedIdLabel1)
                    .addComponent(feedIdField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confinementIdLabel1)
                    .addComponent(confinementIdField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aimIdLabel1)
                    .addComponent(aimIdField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(flagIdLabel1)
                    .addComponent(flagIdField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(motherIdLabel1)
                    .addComponent(motherIdField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(crudPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fatherIdLabel1)
                    .addComponent(fatherIdField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(refreshButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        filterScrollPane.setViewportView(crudPanel1);

        detailTabbedPane.addTab(org.openide.util.NbBundle.getMessage(AnimalCRUD.class, "AnimalCRUD.filterScrollPane.TabConstraints.tabTitle"), filterScrollPane); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(masterScrollPane)
                    .addComponent(detailTabbedPane))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(masterScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(detailTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        bindingGroup.bind();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == saveButton) {
                AnimalCRUD.this.saveButtonActionPerformed(evt);
            }
            else if (evt.getSource() == refreshButton) {
                AnimalCRUD.this.refreshButtonActionPerformed(evt);
            }
            else if (evt.getSource() == newButton) {
                AnimalCRUD.this.newButtonActionPerformed(evt);
            }
            else if (evt.getSource() == deleteButton) {
                AnimalCRUD.this.deleteButtonActionPerformed(evt);
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
        com.whitesheep.entity.animal.Animal a = new com.whitesheep.entity.animal.Animal();
        list.add(a);
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
    private javax.swing.JComboBox aimIdField;
    private javax.swing.JComboBox aimIdField1;
    private javax.swing.JLabel aimIdLabel;
    private javax.swing.JLabel aimIdLabel1;
    private java.util.List<com.whitesheep.entity.Aim> aimList;
    private javax.swing.JTextField animalIdField;
    private javax.swing.JLabel animalIdLabel;
    private java.util.List<com.whitesheep.entity.animal.Animal> animalList;
    private java.util.List<com.whitesheep.entity.animal.Animal> animalList1;
    private org.jdesktop.swingx.JXDatePicker birthDayField;
    private org.jdesktop.swingx.JXDatePicker birthDayField1;
    private org.jdesktop.swingx.JXDatePicker birthDayField2;
    private javax.swing.JLabel birthDayLabel;
    private javax.swing.JLabel birthDayLabel1;
    private javax.swing.JComboBox bnatureIdField;
    private javax.swing.JComboBox bnatureIdField1;
    private javax.swing.JLabel bnatureIdLabel;
    private javax.swing.JLabel bnatureIdLabel1;
    private java.util.List<com.whitesheep.entity.Bnature> bnatureList;
    private org.jdesktop.swingx.JXDatePicker buyDayField;
    private org.jdesktop.swingx.JXDatePicker buyDayField1;
    private org.jdesktop.swingx.JXDatePicker buyDayField2;
    private javax.swing.JLabel buyDayLabel;
    private javax.swing.JLabel buyDayLabel1;
    private javax.swing.JComboBox confinementIdField;
    private javax.swing.JComboBox confinementIdField1;
    private javax.swing.JLabel confinementIdLabel;
    private javax.swing.JLabel confinementIdLabel1;
    private java.util.List<com.whitesheep.entity.Confinement> confinementList;
    private javax.swing.JPanel crudPanel;
    private javax.swing.JPanel crudPanel1;
    private javax.swing.JScrollPane crudScrollPane;
    private javax.swing.JComboBox customerIdField;
    private javax.swing.JComboBox customerIdField1;
    private javax.swing.JLabel customerIdLabel;
    private javax.swing.JLabel customerIdLabel1;
    private java.util.List<com.whitesheep.entity.Customer> customerList;
    private org.jdesktop.swingx.JXDatePicker deathDayField;
    private org.jdesktop.swingx.JXDatePicker deathDayField1;
    private org.jdesktop.swingx.JXDatePicker deathDayField2;
    private javax.swing.JLabel deathDayLabel;
    private javax.swing.JLabel deathDayLabel1;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTabbedPane detailTabbedPane;
    private javax.swing.JComboBox dnatureIdField;
    private javax.swing.JComboBox dnatureIdField1;
    private javax.swing.JLabel dnatureIdLabel;
    private javax.swing.JLabel dnatureIdLabel1;
    private java.util.List<com.whitesheep.entity.Dnature> dnatureList;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JComboBox fatherIdField;
    private javax.swing.JComboBox fatherIdField1;
    private javax.swing.JLabel fatherIdLabel;
    private javax.swing.JLabel fatherIdLabel1;
    private javax.swing.JComboBox feedIdField;
    private javax.swing.JComboBox feedIdField1;
    private javax.swing.JLabel feedIdLabel;
    private javax.swing.JLabel feedIdLabel1;
    private java.util.List<com.whitesheep.entity.Feed> feedList;
    private javax.swing.JScrollPane filterScrollPane;
    private javax.swing.JComboBox flagIdField;
    private javax.swing.JComboBox flagIdField1;
    private javax.swing.JLabel flagIdLabel;
    private javax.swing.JLabel flagIdLabel1;
    private java.util.List<com.whitesheep.entity.Flag> flagList;
    private javax.swing.JTextField genomeField;
    private javax.swing.JLabel genomeLabel;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1;
    private java.util.List<com.whitesheep.entity.animal.Animal> list;
    private javax.swing.JSpinner loopField;
    private javax.swing.JLabel loopLabel;
    private javax.swing.JScrollPane masterScrollPane;
    private javax.swing.JTable masterTable;
    private javax.swing.JComboBox motherIdField;
    private javax.swing.JComboBox motherIdField1;
    private javax.swing.JLabel motherIdLabel;
    private javax.swing.JLabel motherIdLabel1;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField nameField1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel nameLabel1;
    private javax.swing.JButton newButton;
    private javax.swing.JFormattedTextField priceInField;
    private javax.swing.JFormattedTextField priceInField1;
    private javax.swing.JFormattedTextField priceInField2;
    private javax.swing.JLabel priceInLabel;
    private javax.swing.JLabel priceInLabel1;
    private javax.swing.JFormattedTextField priceOutField;
    private javax.swing.JFormattedTextField priceOutField1;
    private javax.swing.JFormattedTextField priceOutField2;
    private javax.swing.JLabel priceOutLabel;
    private javax.swing.JLabel priceOutLabel1;
    private javax.persistence.Query query;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton saveButton;
    private org.jdesktop.swingx.JXDatePicker sellDayField;
    private org.jdesktop.swingx.JXDatePicker sellDayField1;
    private javax.swing.JLabel sellDayLabel;
    private javax.swing.JLabel sellDayLabel1;
    private javax.swing.JComboBox sexIdField;
    private javax.swing.JComboBox sexIdField1;
    private javax.swing.JLabel sexIdLabel;
    private javax.swing.JLabel sexIdLabel1;
    private java.util.List<com.whitesheep.entity.Sex> sexList;
    private javax.swing.JComboBox supplierIdField;
    private javax.swing.JComboBox supplierIdField1;
    private javax.swing.JLabel supplierIdLabel;
    private javax.swing.JLabel supplierIdLabel1;
    private java.util.List<com.whitesheep.entity.Supplier> supplierList;
    private javax.swing.JFormattedTextField tstampField;
    private javax.swing.JLabel tstampLabel;
    private javax.swing.JTextField userNameField;
    private javax.swing.JLabel userNameLabel;
    private javax.swing.JComboBox variantIdField;
    private javax.swing.JComboBox variantIdField1;
    private javax.swing.JLabel variantIdLabel;
    private javax.swing.JLabel variantIdLabel1;
    private java.util.List<com.whitesheep.entity.Variant> variantList;
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
            java.util.logging.Logger.getLogger(AnimalCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AnimalCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AnimalCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AnimalCRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                frame.setContentPane(new AnimalCRUD());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    @Override public void componentOpened() { 
        result = Utilities.actionsGlobalContext().lookupResult(List.class); 
        result.addLookupListener (this); 
    }
    
    @Override public void componentClosed() { 
        result.removeLookupListener(this); 
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
                        if (list.get(0) instanceof Variant){
                            variantList.clear();
                            variantList.addAll(list);
                            variantList.add(0, null);
                        } else if (list.get(0) instanceof Bnature){
                            bnatureList.clear();
                            bnatureList.addAll(list);
                            bnatureList.add(0, null);
                        } else if (list.get(0) instanceof Dnature){
                            dnatureList.clear();
                            dnatureList.addAll(list);
                            dnatureList.add(0, null);
                        } else if (list.get(0) instanceof Customer){
                            customerList.clear();
                            customerList.addAll(list);
                            customerList.add(0, null);
                        } else if (list.get(0) instanceof Supplier){
                            supplierList.clear();
                            supplierList.addAll(list);
                            supplierList.add(0, null);
                        } else if (list.get(0) instanceof Feed){
                            feedList.clear();
                            feedList.addAll(list);
                            feedList.add(0, null);
                        } else if (list.get(0) instanceof Confinement){
                            confinementList.clear();
                            confinementList.addAll(list);
                            confinementList.add(0, null);
                        } else if (list.get(0) instanceof Aim){
                            aimList.clear();
                            aimList.addAll(list);
                            aimList.add(0, null);
                        } else if (list.get(0) instanceof Flag){
                            flagList.clear();
                            flagList.addAll(list);
                            flagList.add(0, null);
                        } else if (list.get(0) instanceof Animal){
                            animalList.clear(); animalList1.clear();
                            animalList.addAll(list); animalList1.addAll(list);
                            animalList.add(0, null); animalList1.add(0, null);
                        }
                    }
                }
            }
        }
    }
    
    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }
    
}
