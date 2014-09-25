package org.munkulus.htwscheduler.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class DropDownMenu extends JPanel {
    
    private static final Color activeColor = Color.GREEN;
    private static final Color nonActiveColor = Color.LIGHT_GRAY;
    private static final int BUTTON_WIDTH = 250;
    
    private final JScrollPane scrollPane;    
    private final Dimension panelSize = new Dimension(320, 545);
    
    private final List<MenuButton> roots; 
    private final List<SubMenu> subMenus;
    
    public DropDownMenu() {
        roots = new ArrayList<>();
        subMenus = new ArrayList<>();
        
        this.setPreferredSize(panelSize);
        //this.setMaximumSize(panelSize);
        this.setLayout(null);
        this.setBackground(Color.LIGHT_GRAY);
        
        scrollPane = new JScrollPane(this, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.setPreferredSize(panelSize);
        scrollPane.setMaximumSize(panelSize);
    }
    
    //===DRAWING THE MENU======================================================
    public void drawRootMenu() {
        int yOffset = 0;
        for (MenuButton mb : roots) {
            addComponent(this, mb.getButton(), -1, yOffset, 321, 25);
            yOffset += 24;
        }

    }

    public void drawMenuLevel(int level, MenuButton parent) {
        SubMenu sm = getSubMenuByParent(parent);
        int yOffset = 0;
        int startY = parent.getButton().getLocation().y + 30;
        int locX = getLocXByLevel(level);
        rearrangeOffsetAffectedComponentsOpen(parent.getButton().getLocation().y, sm.getItems().size());
        for (MenuButton mb : sm.getItems()) {
            addComponent(this, mb.getButton(), locX, startY + yOffset, BUTTON_WIDTH, 25);
            yOffset += 24;
        }
        this.updateUI();
        scrollPane.updateUI();
    }

    private int getLocXByLevel(int level) {
        int x = 0;
        if (level == 1) {
            x = 10;
        } else if (level == 2) {
            x = 30;
        } else if (level == 3) {
            x = 50;
        }
        return x;
    }

    public void removeMenuLevel(MenuButton parent) {
        SubMenu sm = getSubMenuByParent(parent);

        for (MenuButton mb : sm.getItems()) {
            if (mb.getColor() == activeColor && mb.isLeaf() != 1) {
                removeMenuLevel(mb);
            }
            if (mb.getColor() == activeColor && mb.isLeaf() == 0) {
                mb.setColor(nonActiveColor);
            }
            this.remove(mb.getButton());
        }
        rearrangeOffsetAffectedComponentsClose(parent.getButton().getLocation().y, sm.getItems().size());
        this.updateUI();
        scrollPane.updateUI();
    }

    //=========================================================================
    public void rearrangeOffsetAffectedComponentsClose(int y, int anzahl) {
        Component[] comps = this.getComponents();
        Component c = null;
        int oldY;
        int oldX;

        for (int i = 0; i < comps.length; i++) {
            c = comps[i];
            oldY = c.getLocation().y;
            oldX = c.getLocation().x;
            if (oldY > y) {
                c.setLocation(oldX, oldY - ((anzahl * 24) + 10));
            }

        }
        int shownComponentsHeight = countHeightOfAllComponents();
        int height = this.getPreferredSize().height;
        if (height < shownComponentsHeight) {
            this.setPreferredSize(new Dimension(panelSize.width, (int) shownComponentsHeight));
        } else {
            this.setPreferredSize(panelSize);
        }
    }

    public void rearrangeOffsetAffectedComponentsOpen(int y, int anzahl) {
        Component[] comps = this.getComponents();
        Component c = null;
        int oldY;
        int oldX;

        for (int i = 0; i < comps.length; i++) {
            c = comps[i];
            oldY = c.getLocation().y;
            oldX = c.getLocation().x;
            if (oldY > y) {
                c.setLocation(oldX, oldY + (anzahl * 24) + 10);
            }

        }
        int shownComponentsHeight = countHeightOfAllComponents() + ((anzahl + 1) * 25);
        int height = this.getPreferredSize().height;
        if (height < shownComponentsHeight) {
            this.setPreferredSize(new Dimension(panelSize.width, (int) shownComponentsHeight));
        }        
        
    }
    
    public SubMenu getSubMenuByParent(MenuButton parent) {
        SubMenu sm = null;
        for (SubMenu s : subMenus) {
            if (s.getParent().equals(parent)) {
                sm = s;
            }
        }
        return sm;
    }
    
    private void addComponent(JPanel panel, Component c, int x, int y, int w, int h) {
        c.setBounds(x, y, w, h);
        panel.add(c);
    } 
    
    private int countHeightOfAllComponents() {
        Component[] comps = this.getComponents();
        int h = 0;
        for (Component c : comps) {
            h += 24;
        }
        h += 24;
        return h;
    }
    
    public void addRoot(MenuButton b) {
        roots.add(b);
    } 
    
    public int getRootIndex(JButton b) {
        return roots.indexOf(b);
    }
    
    public List<MenuButton> getRoots() {
        return this.roots;
    }
    
    public void addSubMenu(SubMenu sm) {
        subMenus.add(sm);
    }
    
    public List<SubMenu> getSubMenus() {
        return subMenus;
    }
    
    public Color getActiveColor() {
        return activeColor;
    }
    
    public Color getNoneActiveColor() {
        return nonActiveColor;
    }
    
    public JScrollPane getScrollPane() {
        return scrollPane;
    }
    
    public int getPanelHeight() {
        return panelSize.height;
    }
    
    public int getPanelWidth() {
        return panelSize.width;
    }
    
    public void setNewHeight(int h) {
        Dimension d = new Dimension(panelSize.width, h - 50);
        scrollPane.setPreferredSize(d);
        scrollPane.setMaximumSize(d);
        scrollPane.setSize(d);
        scrollPane.updateUI();
    }
    
}
