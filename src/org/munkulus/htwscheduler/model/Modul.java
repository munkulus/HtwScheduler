package org.munkulus.htwscheduler.model;


import java.util.ArrayList;
import java.util.List;

public class Modul {

    private String name;
    private String url;
    private List<Vorlesung> vorlesungen;

    public Modul() {

        this.vorlesungen = new ArrayList<Vorlesung>();

    }

    /**
     * Liefert eine Liste mit den enthaltenen Vorlesungen.
     * @return Liste, mit Vorlesungen.
     */
    public List<Vorlesung> getVorlesungen() {
        return vorlesungen;
    }

    /**
     * Fuegt eine Vorlesung zur Liste hinzu.
     * @param v Vorlesung, der hinzugefuegt wird.
     */
    public void addElement(Vorlesung v) {
        this.vorlesungen.add(v);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
