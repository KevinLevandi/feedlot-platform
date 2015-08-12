package com.whitesheep.ui.crud;

import com.whitesheep.beans.Animal;
import java.awt.EventQueue;
import javax.swing.JFrame;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

@TopComponent.Description(
        preferredID = "AnimalViewer",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(
        mode = "editor", 
        openAtStartup = false)
@ActionID(
        category = "Window", 
        id = "com.whitesheep.ui.crud.AnimalViewer")
@ActionReference(
        path = "Menu/Window/Viewer")
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_AnimalViewerAction",
        preferredID = "AnimalViewer"
)
@NbBundle.Messages({
    "CTL_AnimalViewerAction=Animal Viewer",
    "CTL_AnimalViewerTopComponent=Animal Viewer",
    "HINT_AnimalViewerTopComponent=This is a Animal Viewer window"
})
public class AnimalViewer extends TopComponent{

    public AnimalViewer() {
        setName(Bundle.CTL_AnimalViewerTopComponent());
        setToolTipText(Bundle.HINT_AnimalViewerTopComponent());
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                frame.setContentPane(new AnimalViewer());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
