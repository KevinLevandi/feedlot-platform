/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whitesheep.entity.animal.Animal;
import com.whitesheep.entity.weight.Weight;
import com.whitesheep.entity.weight.WeightFilter;
import com.whitesheep.rest.JerseyClient;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.whitesheep.report//WeightReport//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "WeightReport",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "com.whitesheep.report.WeightReport")
@ActionReference(path = "Menu/Window/Report" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_WeightReportAction",
        preferredID = "WeightReport"
)
@Messages({
    "CTL_WeightReportAction=Weight Report",
    "CTL_WeightReportTopComponent=Weight Report",
    "HINT_WeightReportTopComponent=This is a Weight Report window"
})
public final class WeightReport extends TopComponent implements LookupListener {
    
    private final JerseyClient weightClient = new JerseyClient("weight");
    private final JerseyClient animalClient = new JerseyClient("animal");
    private WeightFilter filter = new WeightFilter();
    
    private Lookup.Result<List> result = null;
    
    public WeightReport() {
        init();
        initComponents();
        drawChart();
        setName(Bundle.CTL_WeightReportTopComponent());
        setToolTipText(Bundle.HINT_WeightReportTopComponent());

    }
    
    private void init(){
        filter.setWeighingDayFrom(new Date());
        filter.setWeighingDayTo(new Date());
    }

    public WeightFilter getFilter() {
        return filter;
    }

    public void setFilter(WeightFilter filter) {
        this.filter = filter;
    }
    
    private List<Weight> findQueried(){
        ObjectMapper m = new ObjectMapper();
        Map<String,Object> props = m.convertValue(filter, Map.class);
        Response response = weightClient.findQueried_JSON(props);
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
    
    private void drawChart(){
        XYDataset dataset = createDataset(metricField.getSelectedIndex());
        JFreeChart chart = createChart(dataset);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        chartContainerPanel.removeAll();
        chartContainerPanel.add(new ChartPanel(chart), gbc);
        chartContainerPanel.revalidate();
    }
    
    private XYDataset createDataset(int mode){
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        Map<String, List> weightMap = transformWeightListToMap(weightList);
        for (Map.Entry<String, List> entry : weightMap.entrySet()){
            TimeSeries series = new TimeSeries(entry.getKey());
            List<Weight> weightHistoryList = entry.getValue();
            Collections.sort(weightHistoryList, new Comparator<Weight>() {
                @Override
                public int compare(Weight o1, Weight o2) {
                    return o1.getWeightPK().getWeighingDay().compareTo(o2.getWeightPK().getWeighingDay());
                }
            });
            if (mode == 0) {    //weight
                for (Weight weightHistory : weightHistoryList){
                    Double yData = weightHistory.getWeight();
                    series.add(new Day(weightHistory.getWeightPK().getWeighingDay()), yData);
                }
            } else if (mode == 1) { //weight growth
                Double weightBefore = null;
                for (Weight weightHistory : weightHistoryList){
                    Double yData = weightBefore == null ? 0 : weightHistory.getWeight()-weightBefore; 
                    series.add(new Day(weightHistory.getWeightPK().getWeighingDay()), yData);
                    weightBefore = weightHistory.getWeight();
                }
            } else if (mode == 2) { //ADG
                Double weightBefore = null;
                Date weighingDayBefore = null;
                for (Weight weightHistory : weightHistoryList){
                    Double weightData = weightBefore == null ? 0 : weightHistory.getWeight()-weightBefore; 
                    weighingDayBefore = weighingDayBefore == null ? weightHistory.getWeightPK().getWeighingDay() : weighingDayBefore;
                    int days = daysBetween(weighingDayBefore, weightHistory.getWeightPK().getWeighingDay());
                    Double yData = days == 0 ? 0 : weightData / days; 
                    series.add(new Day(weightHistory.getWeightPK().getWeighingDay()), yData);
                    weightBefore = weightHistory.getWeight();
                    weighingDayBefore = weightHistory.getWeightPK().getWeighingDay();
                }
            }
            dataset.addSeries(series);
        }
        return dataset;
    }
    
    private int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
    
    private Map<String, List> transformWeightListToMap(List<Weight> weightList){
        Map<String, List> weightMap = new HashMap();
        for (Weight weight : weightList){
            String key = weight.getAnimal().toString();
            if (weightMap.containsKey(key)){
                weightMap.get(key).add(weight);
            } else {
                List<Weight> weightHistoryList = new ArrayList();
                weightHistoryList.add(weight);
                weightMap.put(key, weightHistoryList);
            }
        }
        return weightMap;
    }
    
    private JFreeChart createChart(XYDataset dataset) {
        String yAxisTitle = "";
        switch (metricField.getSelectedIndex()){
            case 0: yAxisTitle = NbBundle.getMessage(WeightReport.class, "WeightReport.WEIGHT"); break;
            case 1: yAxisTitle = NbBundle.getMessage(WeightReport.class, "WeightReport.WEIGHT_GROWTH"); break;
            case 2: yAxisTitle = NbBundle.getMessage(WeightReport.class, "WeightReport.ADG"); break;    
        }
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            null,       // chart title
            NbBundle.getMessage(WeightReport.class, "WeightReport.TIME"),     // x axis label
            yAxisTitle, // y axis label
            dataset,    // data
            true,       // include legend
            true,       // tooltips
            false       // urls
        );
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        XYItemRenderer r = plot.getRenderer();
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(true);
        }
        DateAxis dateAxis = (DateAxis) plot.getDomainAxis();
        dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1));
        dateAxis.setDateFormatOverride(new SimpleDateFormat("dd-MMM"));
        dateAxis.setVerticalTickLabels(true);
        return chart;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        entityManager = null;
        animalQuery = null;
        animalList = java.beans.Beans.isDesignTime() ? new ArrayList() : findAllAnimal();
        weightQuery = null;
        weightList = java.beans.Beans.isDesignTime() ? new ArrayList() : findQueried();
        metricLabel = new javax.swing.JLabel();
        animalIdField = new javax.swing.JComboBox();
        animalIdLabel = new javax.swing.JLabel();
        weighingDayFromField = new org.jdesktop.swingx.JXDatePicker();
        weighingDayLabel = new javax.swing.JLabel();
        weighingDayToField = new org.jdesktop.swingx.JXDatePicker();
        filterButton = new javax.swing.JButton();
        chartContainerPanel = new javax.swing.JPanel();
        metricField = new javax.swing.JComboBox();

        org.openide.awt.Mnemonics.setLocalizedText(metricLabel, org.openide.util.NbBundle.getMessage(WeightReport.class, "WeightReport.metricLabel.text")); // NOI18N

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, animalList, animalIdField);
        jComboBoxBinding.setSourceNullValue(null);
        jComboBoxBinding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(jComboBoxBinding);
        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.animal}"), animalIdField, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        org.openide.awt.Mnemonics.setLocalizedText(animalIdLabel, org.openide.util.NbBundle.getMessage(WeightReport.class, "WeightReport.animalIdLabel.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.weighingDayFrom}"), weighingDayFromField, org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        org.openide.awt.Mnemonics.setLocalizedText(weighingDayLabel, org.openide.util.NbBundle.getMessage(WeightReport.class, "WeightReport.weighingDayLabel.text")); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${filter.weighingDayTo}"), weighingDayToField, org.jdesktop.beansbinding.BeanProperty.create("date"));
        binding.setSourceNullValue(null);
        binding.setSourceUnreadableValue(null);
        bindingGroup.addBinding(binding);

        org.openide.awt.Mnemonics.setLocalizedText(filterButton, org.openide.util.NbBundle.getMessage(WeightReport.class, "WeightReport.filterButton.text")); // NOI18N
        filterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterButtonActionPerformed(evt);
            }
        });

        chartContainerPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        chartContainerPanel.setLayout(new java.awt.GridBagLayout());

        metricField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Weight", "Weight Growth", "Average Daily Gain" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chartContainerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(metricLabel)
                            .addComponent(animalIdLabel)
                            .addComponent(weighingDayLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(animalIdField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(weighingDayFromField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(weighingDayToField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(metricField, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(filterButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chartContainerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weighingDayFromField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weighingDayLabel)
                    .addComponent(weighingDayToField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(animalIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(animalIdLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(metricLabel)
                    .addComponent(metricField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(filterButton)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void filterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterButtonActionPerformed
        weightList.clear();
        weightList.addAll(findQueried());
        drawChart();
    }//GEN-LAST:event_filterButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox animalIdField;
    private javax.swing.JLabel animalIdLabel;
    private java.util.List<com.whitesheep.entity.animal.Animal> animalList;
    private javax.persistence.Query animalQuery;
    private javax.swing.JPanel chartContainerPanel;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JButton filterButton;
    private javax.swing.JComboBox metricField;
    private javax.swing.JLabel metricLabel;
    private org.jdesktop.swingx.JXDatePicker weighingDayFromField;
    private javax.swing.JLabel weighingDayLabel;
    private org.jdesktop.swingx.JXDatePicker weighingDayToField;
    private java.util.List<com.whitesheep.entity.weight.Weight> weightList;
    private javax.persistence.Query weightQuery;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setContentPane(new WeightReport());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    protected void componentClosed() {
        result.removeLookupListener(this); 
    }

    @Override
    protected void componentOpened() {
        result = Utilities.actionsGlobalContext().lookupResult(List.class); 
        result.addLookupListener (this); 
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
                        } else if (list.get(0) instanceof Weight){
                            weightList.clear();
                            weightList.addAll(findQueried());
                            drawChart();
                        }
                    }
                }
            }
        }
    }
}
