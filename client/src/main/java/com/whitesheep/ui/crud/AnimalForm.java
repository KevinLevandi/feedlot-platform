package com.whitesheep.ui.crud;

import com.whitesheep.beans.Animal;
import java.awt.EventQueue;
import javax.swing.JFrame;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

@TopComponent.Description(
        preferredID = "AnimalForm",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(
        mode = "output", 
        openAtStartup = false)
@ActionID(
        category = "Window", 
        id = "com.whitesheep.ui.crud.AnimalCrud")
@ActionReference(
        path = "Menu/Window/Form")
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_AnimalFormAction",
        preferredID = "AnimalForm"
)
@NbBundle.Messages({
    "CTL_AnimalFormAction=Animal Form",
    "CTL_AnimalFormTopComponent=Animal Form",
    "HINT_AnimalFormTopComponent=This is a Animal Form window"
})
public class AnimalForm extends TopComponent {

    public AnimalForm(){
        setName(Bundle.CTL_AnimalFormTopComponent());
        setToolTipText(Bundle.HINT_AnimalFormTopComponent());
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setContentPane(new AnimalForm());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
