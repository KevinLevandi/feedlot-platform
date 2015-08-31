/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.platform.ui.datatype;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author timotius
 */
public class FieldPair {
    private JLabel label;
    private JComponent field;

    public FieldPair() {
        this.label = new JLabel();
        this.field = new JTextField();
    }

    public FieldPair(JLabel label, JComponent field) {
        this.label = label;
        this.field = field;
    }

    public JLabel getLabel() {
        return label;
    }

    public JComponent getField() {
        return field;
    }
}
