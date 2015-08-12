package com.whitesheep.platform.ui.tc;

import com.whitesheep.platform.api.Controller;
import com.whitesheep.platform.api.Role;
import com.whitesheep.platform.api.annotation.Property;
import com.whitesheep.platform.ui.AbstractTopComponent;
import com.whitesheep.platform.ui.MultiSelect;
import com.whitesheep.platform.ui.vm.RoleVM;
import java.awt.EventQueue;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.openide.util.Exceptions;

public class RoleTopComponent extends AbstractTopComponent<RoleVM> {

    public RoleTopComponent() throws InstantiationException, IllegalAccessException {
        super(RoleVM.class);
        getTableContent().addAll(transformRoles(new Controller(Role.class, "role").readEntities()));
        ((MultiSelect)getInputFields().get("accesses")).getModel().setOptions(RoleVM.generateOptions());
        draw();
    }
    
    private List<RoleVM> transformRoles(List<Role> roleList){
        List<RoleVM> roleVMList = new ArrayList();
        for (Role r : roleList){
            roleVMList.add(new RoleVM(r));
        }
        return roleVMList;
    }
    
    private List generateOptions(){
        List ret = new ArrayList();
        Class[] classes = new Class[]{RoleVM.class};
        for (Class c : classes){
            Field[] fields = c.getDeclaredFields();
            for (Field f : fields){
                if (f.isAnnotationPresent(Property.class)){
                    ret.add(c.getSimpleName() + " [" + f.getName() + "]");
                }
            }
        }
        return ret;
    }
    
    private List transformPermission(String binaryCode){
        char[] binaries = binaryCode.toCharArray();
        List ret = new ArrayList();
        List options = generateOptions();
        for (int i = 0; i < options.size() && i < binaries.length; i++){
            ret.add(options.get(i));
        }
        return ret;
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                try {
                    frame.setContentPane(new RoleTopComponent());
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
