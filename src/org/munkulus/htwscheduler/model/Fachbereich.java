package org.munkulus.htwscheduler.model;


import java.util.ArrayList;
import java.util.List;

public class Fachbereich {

    private String name;
    private String url;
    private List<Studiengang> studiengaenge;
    
    public Fachbereich() {

        this.studiengaenge = new ArrayList<Studiengang>();
        

    }

    /**
     * Liefert eine Liste mit den enthaltenen Studiengaengen.
     * @return Liste, mit Studiengaengen.
     */
    public List<Studiengang> getStudiengaenge() {
        return studiengaenge;
    }

    /**
     * Fuegt einen Studiengang zur Liste hinzu.
     * @param s Studiengang, der hinzugefuegt wird.
     */
    public void addElement(Studiengang s) {
        this.studiengaenge.add(s);
    }
    
    /**
     * Liefert ein Studiengang der sich an
     * der uebergebenen Position befindet.
     *
     * @param index Position des angeforderten Elements.
     * @return Studiengang, der sich an der uebergebenen Position befindet.
     */
    public Studiengang getElementAt(int index) {
        return studiengaenge.get(index);
    }
        
    public Studiengang getElementByName(String name) {
        Studiengang s = null;
        for (Studiengang sg : studiengaenge) {
            //System.out.println("Fachbereich -> getElementByName() -> input: " + name +
            //        " | " + sg.getName());
            if (sg.getName().equals(name)) {
                s = sg;
            }
        }
        return s;
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
