package org.munkulus.htwscheduler.model;


public class Vorlesung {
    
    private String name;
    private String zugGruppe;
    private String tag;
    private String beginn;
    private String ende;
    private String beginnDatum;
    private String endeDatum;
    private String dozent;
    private String raum;
    private String woche;

    public Vorlesung() {

    }
    
    @Override
    public String toString() {
        String s = zugGruppe + ", " + woche + ", " +
                tag + ", " + beginn + " - " + ende + ", " +
                beginnDatum + " - " + endeDatum + ", " +
                dozent + ", " + raum;
        return s;
        
    }
    
    public void setName(String n) {
        this.name = n;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getZugGruppe() {
        return zugGruppe;
    }

    public void setZugGruppe(String zg) {
        this.zugGruppe = zg;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBeginn() {
        return beginn;
    }

    public void setBeginn(String beginn) {
        this.beginn = beginn;
    }

    public String getEnde() {
        return ende;
    }

    public void setEnde(String ende) {
        this.ende = ende;
    }
    
    public void setBeginnDatum(String d) {
        this.beginnDatum = d;
    }
    
    public String getBeginnDatum() {
        return this.beginnDatum;
    }
    
    public void setEndeDatum(String d) {
        this.endeDatum = d;
    }
    
    public String getEndeDatum() {
        return this.endeDatum;
    }

    public String getDozent() {
        return dozent;
    }

    public void setDozent(String Dozent) {
        this.dozent = Dozent;
    }

    public String getRaum() {
        return raum;
    }

    public void setRaum(String raum) {
        this.raum = raum;
    }
    
    public String getWoche() {
        return woche;
    }

    public void setWoche(String woche) {
        this.woche = woche;
    }
    
    
}
