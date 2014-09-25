package org.munkulus.htwscheduler.gui;

import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDayPanel extends JPanel {

    private String day;
    private final Color LIGHTYELLOW = new Color(255, 255, 204);
    private final Color LIGHTBLUE = new Color(204, 204, 255);

    private SchedulePanel parent;

    private final Dimension singleLineSize = new Dimension(150, 950);

    private List<VorlesungsGuiPanel> vorlesungen;

    public ScheduleDayPanel(String day, SchedulePanel p) {
        this.setPreferredSize(singleLineSize);
        this.setBackground(Color.GREEN);
        this.setLayout(null);
        this.day = day;
        parent = p;
        vorlesungen = new ArrayList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        paintBackground(g);
    }

    private void paintBackground(Graphics g) {
        int yOffset = 0;
        g.setColor(LIGHTYELLOW);
        for (int i = 0; i < 31; i++) {
            g.fillRect(0, 0 + yOffset, this.getWidth(), 15);
            yOffset += 30;
        }
        yOffset = 0;
        g.setColor(LIGHTBLUE);
        for (int i = 0; i < 31; i++) {
            g.fillRect(0, 15 + yOffset, this.getWidth(), 15);
            yOffset += 30;
        }
    }

    private void expandPanelPlusDependencys(int multiplikator) {

        Dimension d = this.getPreferredSize();
        int x = d.width;
        if (x <= (multiplikator - 1) * 150) {
            this.setPreferredSize(new Dimension((int) d.getWidth() + 150, (int) d.getHeight()));
            Dimension dParent = parent.getPreferredSize();
            parent.setPreferredSize(new Dimension((int) dParent.getWidth() + 150, (int) dParent.getHeight()));
            parent.setSizeForLabelInDayPanel(this.getDay(), 150);
        }
    }

    private void setPanelPlusDependencysToOriginalSize() {
        int x = this.getPreferredSize().width;
        int dx = x - singleLineSize.width;
        this.setPreferredSize(singleLineSize);
        parent.shrink(dx);
        parent.resetDayLabel(this.getDay());
        this.updateUI();
        parent.updateUI();
    }

    public Color deleteVorlesung(VorlesungsGuiPanel vorlesung) {
        if (vorlesungen.contains(vorlesung)) {
            vorlesungen.remove(vorlesung);
            this.removeAll();

            setPanelPlusDependencysToOriginalSize();

            for (VorlesungsGuiPanel v : vorlesungen) {
                v = berechnePosition(v);
                addComponent(this, v, v.getPosX(), v.getPosY(), 148, v.getHight());
                int collision = checkCollision(v);
                int expandCount = 1;
                while (collision != 0) {

                    this.remove(v);
                    v.setPosX(v.getPosX() + 150);
                    addComponent(this, v, v.getPosX(), v.getPosY(), 148, v.getHight());
                    expandCount++;
                    collision = checkCollision(v);

                }
                expandPanelPlusDependencys(expandCount);
            }

        }

        this.updateUI();
        parent.updateUI();
        return vorlesung.getColor();
    }

    public void addVorlesung(VorlesungsGuiPanel vorlesung) {

        vorlesung = berechnePosition(vorlesung);

        if (!vorlesungen.contains(vorlesung)) {
            vorlesungen.add(vorlesung);
            List<VorlesungsGuiPanel> shown = getShownVorlesungen();

            for (VorlesungsGuiPanel v : vorlesungen) {
                if (!shown.contains(v)) {
                    addComponent(this, v, v.getPosX(), v.getPosY(), 148, v.getHight());
                    int collision = checkCollision(v);
                    int expandCount = 1;
                    while (collision != 0) {

                        this.remove(v);
                        v.setPosX(v.getPosX() + 150);
                        addComponent(this, v, v.getPosX(), v.getPosY(), 148, v.getHight());
                        expandCount++;
                        collision = checkCollision(v);

                    }
                    expandPanelPlusDependencys(expandCount);
                }
            }
        }

        this.updateUI();
        parent.updateUI();
    }

    private int checkCollision(VorlesungsGuiPanel v) {
        int col = 0;
        Component[] comps = this.getComponents();
        Component c = null;
        if (comps != null) {

            for (int i = 0; i < comps.length; i++) {
                c = comps[i];
                VorlesungsGuiPanel guiP = (VorlesungsGuiPanel) comps[i];
                if (guiP != v) {
                    int x = v.getPosX() + 10;              
                    //System.out.println("===Pruefe Kollision=========================");
                    for (int j = v.getPosY(); j < (v.getPosY() + v.getHight()); j++) {
                        if (c.getBounds().contains(x, j)) {
                            col = 1;
                            //System.out.println("ColX: " + x + "ColY: " + j);
                        }                        
                    }
                    if (col == 1) {
                        //System.out.println("Component: " + c.toString());
                        //System.out.println("Vorlesung: " + v.toString() + " | Height: " + v.getHight() + 
                        //        " Rect-Height: " + v.getBounds().height + " PosY: " + v.getPosY());
                        //System.out.println("========================================");
                        break;
                    }
                }
            }

        }
        return col;
    }

    public List<VorlesungsGuiPanel> getShownVorlesungen() {
        Component[] comps = this.getComponents();
        List<VorlesungsGuiPanel> list = new ArrayList<>();
        for (Component comp : comps) {
            list.add((VorlesungsGuiPanel) comp);
        }
        return list;
    }

    private VorlesungsGuiPanel berechnePosition(VorlesungsGuiPanel vorlesung) {
        int yPosition = berechneYPosition(vorlesung.getStartZeit());
        int height = berechneHoeheFuerVorlesungsGuiObjekt(vorlesung.getStartZeit(), vorlesung.getEndZeit());
        vorlesung.setPosY(yPosition);
        vorlesung.setHeight(height);
        vorlesung.setPosX(1);
        return vorlesung;
    }

    private int berechneHoeheFuerVorlesungsGuiObjekt(String startZeit, String endZeit) {
        String[] startZeitSplit = startZeit.split(":");
        int hoursStartZeit = new Integer(startZeitSplit[0]);
        int minutesStartZeit = new Integer(startZeitSplit[1]);
        String[] endZeitSplit = endZeit.split(":");
        int hoursEndZeit = new Integer(endZeitSplit[0]);
        int minutesEndZeit = new Integer(endZeitSplit[1]);

        int height = ((hoursEndZeit - hoursStartZeit) * 4) * 15;
        if (minutesStartZeit == 15) {
            height -= (1 * 15);
        }
        if (minutesStartZeit == 30) {
            height -= (2 * 15);
        }
        if (minutesStartZeit == 45) {
            height -= (3 * 15);
        }
        if (minutesEndZeit == 15) {
            height += (1 * 15);
        }
        if (minutesEndZeit == 30) {
            height += (2 * 15);
        }
        if (minutesEndZeit == 45) {
            height += (3 * 15);
        }
        return height;
    }

    private int berechneYPosition(String startZeit) {
        String[] startZeitSplit = startZeit.split(":");
        int hoursStartZeit = new Integer(startZeitSplit[0]);
        int minutesStartZeit = new Integer(startZeitSplit[1]);
        int yPosition = ((hoursStartZeit - 8) * 4) * 15;
        if (minutesStartZeit == 15) {
            yPosition += (1 * 15);
        }
        if (minutesStartZeit == 30) {
            yPosition += (2 * 15);
        }
        if (minutesStartZeit == 45) {
            yPosition += (3 * 15);
        }
        return yPosition;
    }

    private void addComponent(JPanel panel, Component c, int x, int y, int w, int h) {
        c.setBounds(x, y, w, h);
        panel.add(c);
    }

    public String getDay() {
        return this.day;
    }

}
