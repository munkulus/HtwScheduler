package org.munkulus.htwscheduler.model;


import java.util.ArrayList;
import java.util.List;

public class VorlesungsverzeichnisSingelton {

    /**
     * Instanz des Vorlesungsverzeichnisses.
     */
    private static VorlesungsverzeichnisSingelton vorlesungsverzeichnis = null;

    /**
     * Url fuer die Startseite des Vorlesungsverzeichnisses.
     */
    private final String url = "https://lsf.htw-berlin.de/qisserver/rds?state=wtree&search=1";    

    /**
     * Liste, in der die einzelnen Fachbereiche abgelegt werden.
     */
    private List<Fachbereich> fachbereiche;

    /**
     * Privater Konstruktor der ein neues Objekt instanziert,
     * dessen Fachbereichs Liste leer ist.
     */
    private VorlesungsverzeichnisSingelton() {
        this.fachbereiche = new ArrayList<Fachbereich>();
    }

    /**
     * Liefert eine Instanz des Vorlesungsverzeichnisses.
     *
     * @return Singelton Instanz des Vorlesungsverzeichnisses.
     */
    public static VorlesungsverzeichnisSingelton getInstance() {
        if (vorlesungsverzeichnis == null) {
            vorlesungsverzeichnis = new VorlesungsverzeichnisSingelton();
        }
        return vorlesungsverzeichnis;
    }

    /**
     * Liefert eine Liste mit den enthaltenen Fachbereichen.
     * @return Liste, mit Fachbereichen.
     */
    public List<Fachbereich> getFachbereiche() {
        return fachbereiche;
    }

    /**
     * Liefert die Anzahl an Fachbereichen.
     *
     * @return Anzahl von Fachbereichen.
     */
    public int anzahlFachbereiche() {
        return fachbereiche.size();
    }

    /**
     * Fuegt einen Fachbereich zur Liste hinzu.
     * @param fb Fachbereich, der hinzugefuegt wird.
     */
    public void addElement(Fachbereich fb) {
        this.fachbereiche.add(fb);
    }

    /**
     * Liefert ein Fachbereich der sich an
     * der uebergebenen Position befindet.
     *
     * @param index Position des angeforderten Elements.
     * @return Fachbereich, der sich an der uebergebenen Position befindet.
     */
    public Fachbereich getElementAt(int index) {
        return fachbereiche.get(index);
    }

    /**
     * Liefert die Url.
     *
     * @return Url, in form eines Strings.
     */
    public String getUrl() {
        return this.url;
    }
    
    public Fachbereich getFachbereichByName(String name) {
        Fachbereich fb = null;
        for (Fachbereich f : fachbereiche) {
            //System.out.println("vorlesungsverzeichnis -> getFachbereichByName -> " + f.getName());
            if (f.getName().equals(name)) {
                fb = f;
            }
        }
        return fb;
    }
    
    public Studiengang getStudienganByName(String name) {
        Studiengang s = null;
        for (Fachbereich fb : fachbereiche) {
            if (!fb.getStudiengaenge().isEmpty()) {
                s = fb.getElementByName(name);
            }
        }
        return s;
    }
}
