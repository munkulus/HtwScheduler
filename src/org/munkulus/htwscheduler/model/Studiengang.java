package org.munkulus.htwscheduler.model;

import java.util.ArrayList;
import java.util.List;

public class Studiengang {

    private String name;
    private String url;
    private Fachbereich parent;
    private List<Semester> semester;
    private List<Modul> awe;

    public Studiengang() {

        this.semester = new ArrayList<Semester>();
        this.awe = new ArrayList<>();

    }

    /**
     * Liefert eine Liste mit den enthaltenen Semestern.
     *
     * @return Liste, mit Semestern.
     */
    public List<Semester> getSemester() {
        return semester;
    }
    /**
     * Liefert eine Liste mit den enthaltenen AWEs.
     *
     * @return Liste, mit AWEs.
     */
    public List<Modul> getAWEs() {
        return awe;
    }

    /**
     * Fuegt einen Studiengang zur Liste hinzu.
     *
     * @param s Studiengang, der hinzugefuegt wird.
     */
    public void addElement(Semester s) {
        this.semester.add(s);
    }

    /**
     * Fuegt ein Modul zur Liste hinzu.
     *
     * @param s Studiengang, der hinzugefuegt wird.
     */
    public void addElement(Modul m) {
        this.awe.add(m);
    }

    /**
     * Liefert ein Semester der sich an der uebergebenen Position befindet.
     *
     * @param index Position des angeforderten Elements.
     * @return Semester, der sich an der uebergebenen Position befindet.
     */
    public Semester getElementAt(int index) {
        return semester.get(index);
    }

    /**
     * Liefert ein Awe das sich an der uebergebenen Position befindet.
     *
     * @param index Position des angeforderten Elements.
     * @return Modul, das sich an der uebergebenen Position befindet.
     */
    public Modul getAweAt(int index) {
        return awe.get(index);
    }

    public Modul getAweByName(String name) {
        Modul m = null;
        for (Modul modul : awe) {
            if (modul.getName().equals(name)) {
                m = modul;
            }
        }
        return m;
    }

    public Semester getSemesterByName(String name) {
        Semester sem = null;
        for (Semester s : semester) {
            if (s.getName().equals(name)) {
                sem = s;
            }
        }
        return sem;
    }
    
    public void setParent(Fachbereich fb) {
        this.parent = fb;
    }
    
    public Fachbereich getParent() {
        return this.parent;
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
