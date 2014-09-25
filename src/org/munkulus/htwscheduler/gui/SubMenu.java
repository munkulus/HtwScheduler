package org.munkulus.htwscheduler.gui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import org.munkulus.htwscheduler.service.SchedulerService;


public class SubMenu {
    
    private SchedulerService service;
    private MenuButton parent;
    private List<MenuButton> items;
    private int level;
    
    public SubMenu(SchedulerService s, MenuButton p, int l) {
        service = s;
        parent = p;
        level = l;
        items = new ArrayList<>();
    }
    
    public void addItem(MenuButton b) {
        items.add(b);
    }
    
    public List<MenuButton> getItems() {
        return this.items;
    }
    
    public MenuButton getParent() {
        return this.parent;
    }
    
    public void setParent(MenuButton p) {
        this.parent = p;
    }
    
    public int getLevel() {
        return level;
    }
    
}
