package org.munkulus.htwscheduler.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class MenuButton {

    private static final Font   smallBold       = new Font("Helvetica", Font.BOLD, 10);
    private static final Color  activeColor     = Color.GREEN;
    private static final Color  nonActiveColor  = Color.LIGHT_GRAY;
    
    private final JButton       button;   
    private final String        text;
    private int                 leaf;

    public MenuButton(String t, int l) {
        text = t;
        leaf = l;
        button = new JButton(text);
        button.setFont(smallBold);
        button.setBackground(nonActiveColor);
        button.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        button.setFocusable(false);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.button);
        hash = 53 * hash + Objects.hashCode(this.text);
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
        final MenuButton other = (MenuButton) obj;
        if (!Objects.equals(this.button, other.button)) {
            return false;
        }
        if (!Objects.equals(this.text, other.text)) {
            return false;
        }
        return true;
    }
    
    public JButton getButton() {
        return this.button;
    }
    
    public Color getColor() {
        return button.getBackground();
    }
    
    public void setColor(Color c) {
        button.setBackground(c);
    }
    
    public String getButtonText() {
        return text;
    }
    
    public int isLeaf() {
        return leaf;
    }
    
    

}
