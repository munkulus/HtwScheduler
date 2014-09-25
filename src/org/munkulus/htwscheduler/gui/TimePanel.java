package org.munkulus.htwscheduler.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class TimePanel extends JPanel {

    private InnerTimePanel panel;
    private JScrollPane scrollPane;
    

    public TimePanel() {
        this.setPreferredSize(new Dimension(40, 1000));
        this.setLayout(new FlowLayout());
        initComponents();
        
        this.add(panel);

    }

    private void initComponents() {
        panel = new InnerTimePanel();
        panel.setBounds(2, 0, 40, 800);
        scrollPane = new JScrollPane(this, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        
        
    }

    public void setScrollBarValue(int val) {
        this.scrollPane.getVerticalScrollBar().setValue(val);
        this.repaint();
    }

    public JScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public class InnerTimePanel extends JPanel {

        private final Color LIGHTYELLOW = new Color(255, 255, 204);
        private final Color LIGHTBLUE = new Color(204, 204, 255);

        public InnerTimePanel() {
            this.setPreferredSize(new Dimension(40, 950));
            this.setLayout(null);
            this.setBackground(Color.DARK_GRAY);
            this.setDoubleBuffered(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            paintBackground(g);
        }

        private void paintBackground(Graphics g) {
            int yOffset = 0;
            g.setColor(LIGHTYELLOW);
            for (int i = 0; i < 31; i++) {
                g.fillRect(0, 0 + yOffset, 100, 15);
                yOffset += 30;
            }
            yOffset = 0;
            g.setColor(LIGHTBLUE);
            for (int i = 0; i < 31; i++) {
                g.fillRect(0, 15 + yOffset, 100, 15);
                yOffset += 30;
            }
            yOffset = 0;
            int stunde = 8;
            g.setColor(Color.BLACK);
            for (int i = 0; i < 16; i++) {
                g.drawString(stunde + ".00", 2, 12 + yOffset);
                stunde++;
                yOffset += 60;
            }
        }

    }
}
