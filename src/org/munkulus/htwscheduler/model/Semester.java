package org.munkulus.htwscheduler.model;


import java.util.ArrayList;
import java.util.List;

public class Semester {

    private String name;
    private String url;
    private List<Modul> module;

    public Semester() {

        this.module = new ArrayList<Modul>();

    }

    /**
     * Liefert eine Liste mit den enthaltenen Module.
     * @return Liste, mit Module.
     */
    public List<Modul> getModule() {
        return module;
    }

    /**
     * Fuegt einen Studiengang zur Liste hinzu.
     * @param m Studiengang, der hinzugefuegt wird.
     */
    public void addElement(Modul m) {
        this.module.add(m);
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
