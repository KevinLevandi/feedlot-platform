/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.ui.crud;

import com.whitesheep.entity.weight.Weight;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author timotius
 */
public class ExWeightComposite extends CompositeUI {

    public ExWeightComposite() {
        super(Weight.class);
        build();
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setContentPane(new ExWeightComposite());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
