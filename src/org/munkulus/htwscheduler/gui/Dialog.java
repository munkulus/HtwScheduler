package org.munkulus.htwscheduler.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Dialog extends JDialog {
    
    
    private JPanel  content;
    private String  text;
    //private JLabel  message;
    private JTextArea message;
    private JButton button;
    
    private GroupLayout layout;
    
    private final Dimension size = new Dimension(300, 150);
    
    public Dialog(JFrame owner, String m) {
        this.setTitle("Fehler :(");
        this.setSize(size);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocation(owner.getLocationOnScreen().x + (owner.getWidth() / 2) - (size.width / 2),
                owner.getLocationOnScreen().y + (owner.getHeight() / 2) - (size.height / 2));
        this.setResizable(false);
        
        text = m;
        initComponents();
         
        this.setContentPane(content);
        this.setVisible(true);
    }

    private void initComponents() {
        content = new JPanel(null);
        content.setPreferredSize(size);
        content.setBackground(Color.LIGHT_GRAY);
        
        layout = new GroupLayout(content);
        content.setLayout(layout);
        
        message = new JTextArea(text, 3, 40);
        message.setLineWrap(true);
        message.setFocusable(false);
        message.setEditable(false);
        message.setBackground(Color.LIGHT_GRAY);
        
        button = new JButton("Ok");
        button.setFocusable(false);
        button.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        button.setPreferredSize(new Dimension(80, 25));
        button.setMinimumSize(new Dimension(80, 25));
        button.setBackground(Color.LIGHT_GRAY);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                getMe().dispose();         
            }
        });
        layoutSetup();
    }
    
    private void layoutSetup() {
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(message))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(button))
        );
        
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(message))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(button))
      
        );
    }
    
    private JDialog getMe() {
        return this;
    }
    
}
