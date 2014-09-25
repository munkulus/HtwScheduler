package org.munkulus.htwscheduler.repository;

import java.util.ArrayList;
import java.util.List;
import org.munkulus.htwscheduler.model.Vorlesung;

public class SelectedVorlesungen {
    
    private List<Vorlesung> list;
    
    public SelectedVorlesungen() {
        list = new ArrayList<>();
    }
    
    public void addElement(Vorlesung v) {
        list.add(v);
    }
    
}
