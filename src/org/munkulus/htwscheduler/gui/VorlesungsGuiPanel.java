package org.munkulus.htwscheduler.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import javax.swing.JButton;

public class VorlesungsGuiPanel extends JPanel {

    private String veranstaltungsName;
    private final String zugGruppe;
    private final String tag;
    private final String startZeit;
    private final String endZeit;
    private final String beginnDatum;
    private final String endeDatum;
    private final String woche;
    private final String dozent;
    private final String raum;

    private JLabel nrLabel;
    private JLabel nameLabel;
    private JLabel zugGruppeLabel;
    private JLabel wocheLabel;
    private JLabel dozentLabel;
    private JLabel zeitLabel;
    private JLabel datumLabel;
    private JLabel raumLabel;

    private JPanel topLabel;
    
    private int posX;
    private int posY;
    private int height;

    private final Font monoPlain10 = new Font("Helvetica", Font.PLAIN, 10);
    private final Font monoBold10 = new Font("Helvetica", Font.BOLD, 10);

    private Color headColor;

    private static final Color activeColor = Color.GREEN;
    private static final Color nonActiveColor = Color.LIGHT_GRAY;
    private boolean selected;
    
    

    private RollOverPanel overPanel;

    public VorlesungsGuiPanel(String name,
            String zugGruppe,
            String tag,
            String startZeit,
            String endZeit,
            String beginnDatum,
            String endeDatum,
            String woche,
            String dozent,
            String raum) {
        this.veranstaltungsName = name;
        this.zugGruppe = zugGruppe;
        this.tag = tag;
        this.startZeit = startZeit;
        this.endZeit = endZeit;
        this.beginnDatum = beginnDatum;
        this.endeDatum = endeDatum;
        this.woche = woche;
        this.dozent = dozent;
        this.raum = raum;
        this.selected = false;
        this.posX = 0;
        this.posY = 0;
        this.height = 0;

        this.setPreferredSize(new Dimension(148, 150));
        this.setLayout(null);
        this.setBackground(nonActiveColor);
        this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
        
        String toolTip = "<html><head><body>" +
                "<p>" + this.veranstaltungsName + "</p>" +
                "<p>" + this.zugGruppe + "</p>" +
                "<p>" + "Dozent: " + this.dozent + "</p>" +
                "<p>" + this.woche + "</p>" +
                "<p>" + this.startZeit + " - " + this.endZeit + "</p>" +
                "<p>" + this.beginnDatum + " - " + this.endeDatum + "</p>" +
                "<p>" + this.raum + "</p>";
        this.setToolTipText(toolTip);

        initComponents();
        addComponents();
        addListeners();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.veranstaltungsName);
        hash = 53 * hash + Objects.hashCode(this.zugGruppe);
        hash = 53 * hash + Objects.hashCode(this.tag);
        hash = 53 * hash + Objects.hashCode(this.startZeit);
        hash = 53 * hash + Objects.hashCode(this.endZeit);
        hash = 53 * hash + Objects.hashCode(this.beginnDatum);
        hash = 53 * hash + Objects.hashCode(this.endeDatum);
        hash = 53 * hash + Objects.hashCode(this.woche);
        hash = 53 * hash + Objects.hashCode(this.dozent);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VorlesungsGuiPanel other = (VorlesungsGuiPanel) obj;
        if (!Objects.equals(this.veranstaltungsName, other.veranstaltungsName)) {
            return false;
        }
        if (!Objects.equals(this.zugGruppe, other.zugGruppe)) {
            return false;
        }
        if (!Objects.equals(this.tag, other.tag)) {
            return false;
        }
        if (!Objects.equals(this.startZeit, other.startZeit)) {
            return false;
        }
        if (!Objects.equals(this.endZeit, other.endZeit)) {
            return false;
        }
        if (!Objects.equals(this.beginnDatum, other.beginnDatum)) {
            return false;
        }
        if (!Objects.equals(this.endeDatum, other.endeDatum)) {
            return false;
        }
        if (!Objects.equals(this.woche, other.woche)) {
            return false;
        }
        if (!Objects.equals(this.dozent, other.dozent)) {
            return false;
        }
        return true;
    }
    
    private void addListeners() {
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                overPanel.setVisibility(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                overPanel.setVisibility(false);
            }

        });
    }

    private void initComponents() {

        this.headColor = Color.GRAY;

        this.overPanel = new RollOverPanel(this);

        String[] split = veranstaltungsName.split(" ");
        String nr = split[0];
        String name = "";
        for (int i = 1; i < split.length; i++) {
            name = name.concat(split[i]);
        }
        this.nameLabel = new JLabel(name);
        this.nameLabel.setFont(this.monoBold10);
        this.nrLabel = new JLabel(nr);
        this.nrLabel.setFont(this.monoBold10);
        this.topLabel = new JPanel(null);
        this.topLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
        //======================================================================
        this.zugGruppeLabel = new JLabel(this.zugGruppe);
        this.zugGruppeLabel.setFont(this.monoPlain10);
        this.wocheLabel = new JLabel(this.woche);
        this.wocheLabel.setFont(this.monoPlain10);
        this.dozentLabel = new JLabel("Dozent: " + this.dozent);
        this.dozentLabel.setFont(this.monoPlain10);
        this.zeitLabel = new JLabel(this.startZeit + " - " + this.endZeit);
        this.zeitLabel.setFont(this.monoPlain10);
        this.datumLabel = new JLabel(this.beginnDatum + " - " + this.endeDatum);
        this.datumLabel.setFont(monoPlain10);
        this.raumLabel = new JLabel("Raum: " + this.raum);
        this.raumLabel.setFont(monoPlain10);
    }

    private void addComponents() {
        addComponent(this, overPanel, 0, 0, 150, 120);
        addComponent(topLabel, nrLabel, 0, 0, 150, 15);
        addComponent(topLabel, nameLabel, 0, 15, 150, 15);
        addComponent(this, topLabel, 0, 0, 150, 30);
        addComponent(this, zugGruppeLabel, 3, 30, 150, 15);
        addComponent(this, dozentLabel, 3, 45, 150, 15);
        addComponent(this, wocheLabel, 3, 60, 150, 15);
        addComponent(this, zeitLabel, 3, 75, 150, 15);
        addComponent(this, datumLabel, 3, 90, 150, 15);
        addComponent(this, raumLabel, 3, 105, 150, 15);

    }

    private void addComponent(JPanel panel, Component c, int x, int y, int w, int h) {
        c.setBounds(x, y, w, h);
        panel.add(c);
    }
    
    @Override
    public String toString() {
        String s = veranstaltungsName + ", " + zugGruppe + ", " +
                tag + ", " + startZeit + " - " + endZeit + ", " + woche + ", " +
                dozent + ", " + raum;

        return s;
    }
    
    
    public Color getColor() {
        return this.headColor;
    }

    public void setColor(Color c) {
        this.headColor = c;
        this.topLabel.setBackground(c);
    }
    
    public String getVeranstaltungsName() {
        return this.veranstaltungsName;
    }
    
    public void setVeranstaltungsName(String name) {
        this.veranstaltungsName = name;
    }

    public String getDay() {
        return this.tag;
    }

    public String getStartZeit() {
        return this.startZeit;
    }

    public String getEndZeit() {
        return this.endZeit;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void select() {
        this.setBackground(activeColor);
        this.selected = true;
    }

    public void deselect() {
        this.setBackground(nonActiveColor);
        this.selected = false;
    }
    
    public void setPosX(int val) {
        this.posX = val;
    }
    
    public int getPosX() {
        return this.posX;
    }
    
    public void setPosY(int val) {
        this.posY = val;
    }
    
    public int getPosY() {
        return this.posY;
    }
    
    public void setHeight(int val) {
        this.height = val;
    }
    
    public int getHight() {
        return this.height;
    }

    //=====================================================================
    public class RollOverPanel extends JPanel {

        private JButton select;
        private JButton deselect;

        private VorlesungsGuiPanel parent;

        private final Font smallBold = new Font("Monospaced", Font.PLAIN, 10);

        public RollOverPanel(VorlesungsGuiPanel p) {
            parent = p;
            this.setPreferredSize(new Dimension(150, 120));
            this.setOpaque(false);
            this.setBackground(Color.LIGHT_GRAY);
            this.setVisible(false);

            initComponents();
            addComponents();

        }

        private void initComponents() {
            select = new JButton("Select");
            select.setFont(smallBold);
            select.setFocusable(false);
            select.setBackground(Color.LIGHT_GRAY);
            select.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
            select.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    setVisibility(true);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    parent.select();
                }
            });

            deselect = new JButton("Deselect");
            deselect.setFont(smallBold);
            deselect.setFocusable(false);
            deselect.setBackground(Color.LIGHT_GRAY);
            deselect.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
            deselect.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    setVisibility(true);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    parent.deselect();
                }
            });
        }

        private void addComponents() {
            addComponent(this, select, 5, 5, 100, 20);
            addComponent(this, deselect, 5, 35, 100, 20);
        }

        private void addComponent(JPanel panel, Component c, int x, int y, int w, int h) {
            c.setBounds(x, y, w, h);
            panel.add(c);
        }

        public void setVisibility(boolean b) {
            this.setVisible(b);
        }
    }

}
