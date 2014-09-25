package org.munkulus.htwscheduler.gui;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.munkulus.htwscheduler.service.SchedulerService;

public class HtwScheduler extends JFrame {

    private final Dimension windowSize = new Dimension(800, 600);
    private VerzeichnisPanel verzeichnisPanel;
    private ScheduleContainer scheduleContainer;
    
    private SchedulerService service;

    public HtwScheduler() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.setTitle("HTWScheduler");
        this.setSize(windowSize);
        this.setLocation(100, 100);
        this.setLayout(new BorderLayout());
        
        service = new SchedulerService();
        service.setFrame(this);
        
        initComponents();
        addComponents();
        
        
        this.addWindowStateListener(new WindowAdapter() {

            @Override
            public void windowStateChanged(WindowEvent e) {
                super.windowStateChanged(e);
                JFrame f = (JFrame) e.getSource();  
                f.revalidate();
                //System.out.println("Frame size: " + f.getSize().height);
                if (e.getNewState() == MAXIMIZED_BOTH) {                    
                    verzeichnisPanel.setNewHeight(f.getHeight());
                } else if (e.getNewState() == MAXIMIZED_VERT) {
                    verzeichnisPanel.setNewHeight(f.getHeight());
                } else {
                    verzeichnisPanel.setNewHeight(f.getHeight());
                }
                
            }
            
        });
        
        
    }

    private void initComponents() {
        this.verzeichnisPanel = new VerzeichnisPanel(service);
        service.setVerzeichnisPanel(verzeichnisPanel);
        this.scheduleContainer = new ScheduleContainer(service);       
    }

    private void addComponents() {
        this.add(verzeichnisPanel, BorderLayout.WEST);
        this.add(scheduleContainer, BorderLayout.CENTER);
    }

}
