package com.whitesheep.platform.ui.topcomponent;


import com.whitesheep.platform.ui.virtualmodel.UserVM;
import java.awt.EventQueue;
import javax.swing.JFrame;
import org.openide.util.Exceptions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author timotius
 */
public class UserTopComponent extends AbstractTopComponent<UserVM> {

    public UserTopComponent() throws InstantiationException, IllegalAccessException {
        super(UserVM.class);
        draw();
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                try {
                    frame.setContentPane(new UserTopComponent());
                } catch (InstantiationException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (IllegalAccessException ex) {
                    Exceptions.printStackTrace(ex);
                }
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
