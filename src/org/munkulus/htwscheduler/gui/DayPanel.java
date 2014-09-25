package org.munkulus.htwscheduler.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class DayPanel extends JPanel {
    
    private List<JLabel> labelList;
    
    private JScrollPane scrollPane;
    private JLabel zeit;
    
    private final Dimension startSize = new Dimension(1250, 25);
    private final Dimension labelStartSize = new Dimension(150, 25);
    
    public DayPanel() {
        this.setPreferredSize(startSize);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        labelList = new ArrayList<>();
        
        initComponents();
        initLabels();        
        addComponents();
        
    }
    
    private void initComponents() {
        scrollPane = new JScrollPane(this, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
    }
    
    private void initLabels() {
        zeit = new JLabel("Zeit");
        zeit.setPreferredSize(new Dimension(80, 25));
        zeit.setHorizontalAlignment(SwingConstants.LEFT);
        JLabel mo = new JLabel("Montag");
        mo.setPreferredSize(new Dimension(150, 25));
        JLabel di = new JLabel("Dienstag");
        di.setPreferredSize(new Dimension(150, 25));
        JLabel mi = new JLabel("Mittwoch");
        mi.setPreferredSize(new Dimension(150, 25));
        JLabel don = new JLabel("Donnerstag");
        don.setPreferredSize(new Dimension(150, 25));
        JLabel fr = new JLabel("Freitag");
        fr.setPreferredSize(new Dimension(150, 25));
        JLabel sa = new JLabel("Samstag");
        sa.setPreferredSize(new Dimension(150, 25));
        JLabel so = new JLabel("Sonntag");
        so.setPreferredSize(new Dimension(150, 25));
        
        
        labelList.add(mo);
        labelList.add(di);
        labelList.add(mi);
        labelList.add(don);
        labelList.add(fr);
        labelList.add(sa);
        labelList.add(so);
        
    }
    
    private void addComponents() {
        this.add(zeit);
        for (int i = 0; i < labelList.size(); i++) {
            this.add(labelList.get(i));
        }
    }
    
    public void setSizeForLabelByName(String name, int dx) {
        for (JLabel l : labelList) {
            if (l.getText().equals(name)) {
                int x = l.getPreferredSize().width;
                int y = l.getPreferredSize().height;
                l.setPreferredSize(new Dimension(x + dx, y));
                
                int xParent = this.getPreferredSize().width;
                int yParent = this.getPreferredSize().height;
                this.setPreferredSize(new Dimension(xParent + dx, yParent));
            }
        }
        this.updateUI();
    }
    
    public void setScrollBarValue(int val) {
        this.scrollPane.getHorizontalScrollBar().setValue(val);
    }

    public JScrollPane getScrollPane() {
        return this.scrollPane;
    }
    
    public void resetSizeOfLabelByName(String name) {
        
        for (JLabel l : labelList) {
            if (l.getText().equals(name)) {
                int x = l.getPreferredSize().width;
                l.setPreferredSize(labelStartSize);
                
                int xParent = this.getPreferredSize().width;
                int yParent = this.getPreferredSize().height;
                this.setPreferredSize(new Dimension(xParent - (x - 150), yParent));
            }
        }
        this.updateUI();
        
    }
    
}
