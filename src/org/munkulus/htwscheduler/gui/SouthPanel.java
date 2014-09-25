package org.munkulus.htwscheduler.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.munkulus.htwscheduler.service.SchedulerService;

public class SouthPanel extends JPanel {

    private SchedulerService service;
    private JButton deleteNoneSelected;

    private final Font smallPlain = new Font("Helvetica", Font.PLAIN, 11);

    public SouthPanel(SchedulerService s) {
        service = s;
        this.setBackground(Color.LIGHT_GRAY);

        initComponents();
        addComponentes();
    }

    private void initComponents() {
        deleteNoneSelected = new JButton("Delete None Selected");
        deleteNoneSelected.setFont(smallPlain);
        deleteNoneSelected.setFocusable(false);
        deleteNoneSelected.setBackground(Color.GRAY);
        deleteNoneSelected.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
        deleteNoneSelected.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                service.deleteNotSelectedGuiVorlesungen();
            }
        });
    }

    private void addComponentes() {
        addComponent(this, deleteNoneSelected, 20, 5, 140, 20);
    }

    private void addComponent(JPanel panel, Component c, int x, int y, int w, int h) {
        c.setBounds(x, y, w, h);
        panel.add(c);
    }

}
