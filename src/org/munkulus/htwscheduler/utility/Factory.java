package org.munkulus.htwscheduler.utility;

import java.awt.Color;
import org.munkulus.htwscheduler.gui.VorlesungsGuiPanel;
import org.munkulus.htwscheduler.model.Fachbereich;
import org.munkulus.htwscheduler.model.Modul;
import org.munkulus.htwscheduler.model.Semester;
import org.munkulus.htwscheduler.model.Studiengang;
import org.munkulus.htwscheduler.model.Vorlesung;

public class Factory {

    public Factory() {

    }

    public Fachbereich createFachbereich(String csvValues) {
        Fachbereich fb = new Fachbereich();
        String[] valSplit = csvValues.split("#");
        fb.setName(valSplit[0]);
        fb.setUrl(valSplit[1]);
        return fb;
    }

    public Studiengang createStudiengang(String csvValues, Fachbereich parent) {
        Studiengang s = new Studiengang();
        String[] valSplit = csvValues.split("#");
        s.setName(valSplit[0]);
        s.setUrl(valSplit[1]);
        s.setParent(parent);
        return s;
    }

    public Semester createSemester(String csvValues) {
        Semester s = new Semester();
        String[] valSplit = csvValues.split("#");
        s.setName(valSplit[0]);
        s.setUrl(valSplit[1]);
        return s;
    }

    public Modul createModul(String csvValues) {
        Modul m = new Modul();
        String[] valSplit = csvValues.split("#");
        m.setName(valSplit[0]);
        m.setUrl(valSplit[1]);
        return m;
    }

    public Vorlesung createVorlesung(String csvValues, String modulName) {
        Vorlesung v = new Vorlesung();
        //System.out.println(csvValues);
        String regEx = "[^\\w\\däöüÄÖÜ]";

        //ZugGruppe#Freitag  #  14:00  -  19:00 #  Einzelt. #  Beginn: 25.04.2014 #  Ende: 25.04.2014#Höppner-Zierow  #WH C 403
        String[] valSplit = csvValues.split("#");
        v.setName(modulName);
        v.setZugGruppe(valSplit[0]);

        if (valSplit.length <= 3) {
            v.setTag(null);
            v.setBeginn(null);
            v.setEnde(null);
            v.setWoche(null);
            v.setBeginnDatum(null);
            v.setEndeDatum(null);
            v.setDozent(null);
            v.setRaum(null);
            
        } else {
            v.setTag(valSplit[1].replaceAll(regEx, ""));

            try {
                v.setBeginn(valSplit[2].trim().substring(2, 7));
                v.setEnde(valSplit[2].substring(12, 17));
            } catch (StringIndexOutOfBoundsException soob) {
                //throw new StringIndexOutOfBoundsException("Im LSF ist keine Zeit angegeben");
                v.setBeginn(null);
                v.setEnde(null);
            }

            v.setWoche(valSplit[3].replaceAll(regEx, ""));
            String[] datumSplit = valSplit[4].split(":");
            v.setBeginnDatum(datumSplit[1].replaceAll(" ", ""));
            datumSplit = valSplit[5].split(":");
            v.setEndeDatum(datumSplit[1].replaceAll(" ", ""));
            v.setDozent(valSplit[6].replaceAll(" ", ""));
            v.setRaum(valSplit[7]);
        }
        System.out.println("Create Vorlesung: " + v.toString());

        return v;
    }

    public VorlesungsGuiPanel createGuiVorlesung(Vorlesung v, String modulName, Color c) {
        VorlesungsGuiPanel guiV = new VorlesungsGuiPanel(
                modulName,
                v.getZugGruppe(),
                v.getTag(),
                v.getBeginn(),
                v.getEnde(),
                v.getBeginnDatum(),
                v.getEndeDatum(),
                v.getWoche(),
                v.getDozent(),
                v.getRaum());
        guiV.setColor(c);

        return guiV;
    }

    public VorlesungsGuiPanel createGuiVorlesung(Vorlesung v, String modulName) {
        VorlesungsGuiPanel guiV = new VorlesungsGuiPanel(
                modulName,
                v.getZugGruppe(),
                v.getTag(),
                v.getBeginn(),
                v.getEnde(),
                v.getBeginnDatum(),
                v.getEndeDatum(),
                v.getWoche(),
                v.getDozent(),
                v.getRaum());

        return guiV;
    }
}
