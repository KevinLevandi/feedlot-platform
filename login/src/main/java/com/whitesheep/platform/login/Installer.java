/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.whitesheep.platform.login;

import com.whitesheep.platform.api.Controller;
import com.whitesheep.platform.api.Root;
import com.whitesheep.platform.api.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.NotifyDescriptor;
import org.openide.modules.ModuleInstall;
import org.openide.util.Exceptions;

public class Installer extends ModuleInstall {

    LoginForm form = new LoginForm();
    
    @Override
    public void restored() {
        NotifyDescriptor nd = new NotifyDescriptor.Confirmation(form,"Login");
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                LifecycleManager.getDefault().exit();
            }
        });
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String userName = form.getUserName();
                String password = getEncodedPassword(form.getPassword());
                Controller ctrl = new Controller(User.class, "user");
                List<User> result = ctrl.readEntities(userName, password);
                if (result.isEmpty()){
                    JOptionPane.showMessageDialog(null, "", "", JOptionPane.ERROR_MESSAGE);
                    LifecycleManager.getDefault().exit();
                } else {
                    Root.getInstance().setCurrentUser(result.get(0));
                }
            }
        });
        nd.setOptions(new Object[] {ok, cancel});
        
        DialogDisplayer.getDefault().notifyLater(nd);
    }
    
    private String getEncodedPassword(String originalPassword){
        String ret = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(originalPassword.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            ret = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Exceptions.printStackTrace(ex);
        } finally {
            return ret;
        }
    }
}
