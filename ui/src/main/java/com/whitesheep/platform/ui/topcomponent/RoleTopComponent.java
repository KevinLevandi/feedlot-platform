package com.whitesheep.platform.ui.topcomponent;

import com.whitesheep.platform.api.Controller;
import com.whitesheep.platform.api.Role;
import com.whitesheep.platform.api.annotation.Property;
import com.whitesheep.platform.ui.Resources;
import com.whitesheep.platform.ui.datatype.FieldPair;
import com.whitesheep.platform.ui.component.MultiSelect;
import com.whitesheep.platform.ui.virtualmodel.RoleVM;
import java.awt.EventQueue;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

public class RoleTopComponent extends AbstractTopComponent<RoleVM> {

    private static final Class[] windows = new Class[]
    {
        RoleTopComponent.class, 
        UserTopComponent.class
    };
    private static final String PERMISSION_VIEW = "Role.PERMISSION_VIEW"; 
    private static final String PERMISSION_CREATE = "Role.PERMISSION_CREATE"; 
    private static final String PERMISSION_UPDATE = "Role.PERMISSION_UPDATE"; 
    private static final String PERMISSION_DELETE = "Role.PERMISSION_DELETE"; 
    
    public RoleTopComponent() throws InstantiationException, IllegalAccessException {
        super(RoleVM.class);
        populateTable(mapToVM(new Controller(Role.class, "role").readEntities()));
        populatePermissions();
        draw();
    }
    
    private List<RoleVM> mapToVM(List<Role> roleList) {
        List<RoleVM> roleVMList = new ArrayList();
        for (Role r : roleList){
            roleVMList.add(mapToVM(r));
        }
        return roleVMList;
    }
    
    private RoleVM mapToVM(Role role) {
        RoleVM roleVM = new RoleVM();
        roleVM.setRoleId(role.getRoleId());
        roleVM.setName(role.getName());
        roleVM.setPermissions(transformPermission(role.getPermission()));
        return roleVM;
    }
    
    private List transformPermission(String binaryString) {
        char[] binaryChars = binaryString.toCharArray();
        List ret = new ArrayList();
        List options = generateOptions();
        for (int i = 0; i < options.size(); i++){
            if (i < binaryChars.length) {
                if (binaryChars[i] != '0') ret.add(options.get(i));
            } else {
                ret.add(options.get(i));
            }
        }
        return ret;
    }
    
    private void populatePermissions() {
        FieldPair fp = getCrudComponent("permissions");
        if (fp != null) {
            ((MultiSelect)fp.getField()).setOptions(generateOptions());
        }
    }
    
    private List generateOptions(){
        List ret = new ArrayList();
        for (Class window : windows){
            Class typeClass = (Class)((ParameterizedType)window.getGenericSuperclass()).getActualTypeArguments()[0];
            ret.add(window.getSimpleName() + " [" + NbBundle.getMessage(Resources.class, PERMISSION_VIEW) + "]");
            ret.add(window.getSimpleName() + " [" + NbBundle.getMessage(Resources.class, PERMISSION_CREATE) + "]");
            ret.add(window.getSimpleName() + " [" + NbBundle.getMessage(Resources.class, PERMISSION_UPDATE) + "]");
            ret.add(window.getSimpleName() + " [" + NbBundle.getMessage(Resources.class, PERMISSION_DELETE) + "]");
            Field[] fields = typeClass.getDeclaredFields();
            for (Field f : fields){
                if (f.isAnnotationPresent(Property.class)) {
                    ret.add(window.getSimpleName() + " [" + f.getName() + "]");
                }
            }
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
