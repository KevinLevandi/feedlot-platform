/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.ui.crud;

import com.whitesheep.beans.Weight;
import com.whitesheep.beans.WeightPK;
import com.whitesheep.rest.ClientController;
import com.whitesheep.vm.WeightVM;
import java.awt.EventQueue;
import javax.swing.JFrame;
import org.openide.windows.TopComponent;

/**
 *
 * @author timotius
 */
public class WeightComposite extends TopComponent {

    private FormUI<WeightVM> form = new FormUI(WeightVM.class) {
        
        ClientController ctrl = new ClientController(Weight.class, "weight");
        
        private Weight mapVM(WeightVM vm){
            WeightPK weightPK = new WeightPK();
            weightPK.setWeighingDay(vm.getWeighingDay());
            Weight weight = new Weight();
            weight.setWeightPK(weightPK);
            weight.setWeight(vm.getWeight());
            return weight;
        }
        
        @Override
        public void clear() {
            setEntity(new WeightVM());
        }
        
        @Override
        public void save() {
            Weight weight  = mapVM((WeightVM)getEntity());
            if (weight.getId() == "null"){
                ctrl.createEntity(weight);
            } else {
                ctrl.updateEntity(weight, weight.getId());
            }
        }
        
        @Override
        public void delete() {
            Weight weight  = mapVM((WeightVM)getEntity());
            ctrl.deleteEntity(weight.getId());
        }
    };
    
    public WeightComposite() {
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setContentPane(new WeightComposite());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
