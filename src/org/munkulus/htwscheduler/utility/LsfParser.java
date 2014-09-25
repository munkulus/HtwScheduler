package org.munkulus.htwscheduler.utility;

import org.munkulus.htwscheduler.model.*;
import org.munkulus.htwscheduler.utility.Factory;
import org.munkulus.htwscheduler.gui.Dialog;
import org.munkulus.htwscheduler.service.SchedulerService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class LsfParser {

    private Factory lsfFactory;
    private SchedulerService service;

    public LsfParser(SchedulerService s) {
        service = s;
        this.lsfFactory = new Factory();
    }

    public void leseFachbereiche(String url) throws IOException, SocketTimeoutException {

        VorlesungsverzeichnisSingelton vv = VorlesungsverzeichnisSingelton.getInstance();
        List<String> list = getLinksByAttributAndValue(url, "class", "ueb");
        for (String s : list) {
            if (!s.contains("Vorlesungsverzeichnis") && !s.contains("Fremdsprachen") &&
                    !s.contains("AWE") && !s.contains("Fernstudium und Weiterbildung (BIfAW)")) {
                vv.addElement(lsfFactory.createFachbereich(s));
            }
        }
    }

    public void leseStudiengaenge(Fachbereich fb) throws IOException {

        List<String> listStudiengaenge = getLinksByAttributAndValue(fb.getUrl(), "class", "ueb");
        for (String s : listStudiengaenge) {
            if (!s.contains("Kolloquien") && !s.contains("Vorlesungsverzeichnis")
                    && !s.contains(fb.getName())
                    && !s.contains("Arbeitsverzeichnis - Veranstaltungen ohne Dozenten / ohne Termine / wechselnde Titel")
                    && !s.contains("Erstsemesterveranstaltungen HTW & FB4")
                    && !s.contains("Arbeitsverzeichnis - Veranstaltungen ohne Dozenten/ohne Termine")
                    && !s.contains("Sonstige Belegungen")) {

                fb.addElement(lsfFactory.createStudiengang(s, fb));
            }
        }
    }

    public void leseSemester(Studiengang sg) throws IOException {

        List<String> listSemester = getLinksByAttributAndValue(sg.getUrl(), "class", "ueb");
        for (String s : listSemester) {
            if (!s.contains("Vorlesungsverzeichnis")
                    && !s.contains(sg.getName()) && !s.contains(sg.getParent().getName())) {
                sg.addElement(lsfFactory.createSemester(s));
            }
        }

    }

    public void leseModule(Studiengang sg) throws IOException {

        //System.out.println("---------" + sem.getName());
        List<String> listModul = getLinksByAttributAndValue(sg.getUrl(), "class", "ver");
        for (String s : listModul) {
            if (!s.contains("Vorlesungsverzeichnis")) {
                //&& !s.contains(sg.getName())) {
                //System.out.println("-------------" + s);
                sg.addElement(lsfFactory.createModul(s));
            }
        }

    }

    public void leseModule(Semester sem) throws IOException {

        //System.out.println("---------" + sem.getName());
        List<String> listModul = getLinksByAttributAndValue(sem.getUrl(), "class", "ver");
        for (String s : listModul) {
            if (!s.contains("Vorlesungsverzeichnis")
                    && !s.contains(sem.getName())) {
                //System.out.println("-------------" + s);
                sem.addElement(lsfFactory.createModul(s));
            }
        }

    }

    public List<Vorlesung> vorlesungenAuslesen(Modul m) throws IOException, URISyntaxException,
            ClassNotFoundException, IllegalArgumentException {
        Document doc = Jsoup.connect(m.getUrl()).get();
        /*
         System.out.println("Vorlesung Auslesen -> Document");
         System.out.println("==============================================");
         System.out.println(doc.toString());
         System.out.println("==============================================");
         */

        Elements tables = doc.select("table.inside");
        Element tableOutside = tables.get(1);

        Elements innerTables = tableOutside.getElementsByTag("tbody");

        List<Element> vorlesungsListe = new ArrayList<Element>();

        Elements eList = null;
        Elements e2List = null;

        for (int i = 8; i < innerTables.size(); i++) {
            Element table = innerTables.get(i);
            Elements allInnerTr = table.getElementsByTag("tr");

            for (Element e : allInnerTr) {
                eList = e.getAllElements();

                for (Element e2 : eList) {
                    if (e2.tagName().equals("table")) {
                        e2List = e2.getAllElements();

                        for (Element e3 : e2List) {
                            if (e3.text().equals("Gruppe")) {
                                vorlesungsListe.add(e2);
                            }
                        }
                    }
                }
            }
        }
        /*
         System.out.println("Vorlesung Auslesen -> vorlesungsListe");
         System.out.println("==============================================");
         for (Element e : vorlesungsListe) {
         System.out.println(e.toString());
         }
         */

        Elements vlElemente = null;

        String vlString = "";
        int index = 0;
        int indexOfvlElemente = 0;
        Element e2 = null;

        List<String> unformatierteDaten = new ArrayList<>();

        int gruppe_gesetzt = 0;
        int termin_gesetzt = 0;
        int dozent_gesetzt = 0;
        int raum_gesetzt = 0;
        String gruppe = "";
        for (Element e : vorlesungsListe) {

            vlElemente = e.getAllElements();
            indexOfvlElemente = vlElemente.size();
            for (int i = 2; i < indexOfvlElemente; i++) {

                e2 = vlElemente.get(i);
                //System.out.println(e2.toString());
                //System.out.println("==============================================");
                if (e2.text().equals("Gruppe")) {
                    index = vlElemente.indexOf(e2);
                    vlString = vlElemente.get(index + 2).text();
                    System.out.println("GRUPPE: " + vlString);
                    gruppe_gesetzt = 1;
                    gruppe = vlString;
                } else if (e2.text().equals("Termin")) {
                    index = vlElemente.indexOf(e2);
                    vlString = vlString.concat("#" + vlElemente.get(index + 2).text());
                    System.out.println("TERMIN: " + vlString);
                    termin_gesetzt = 1;

                } else if (e2.text().equals("Dozent")) {
                    index = vlElemente.indexOf(e2);
                    vlString = vlString.concat("#" + vlElemente.get(index + 2).text());
                    System.out.println("DOZENT: " + vlString);
                    dozent_gesetzt = 1;

                } else if (e2.text().equals("Dozenten")) {
                    index = vlElemente.indexOf(e2);
                    vlString = vlString.concat("#" + vlElemente.get(index + 2).text());
                    System.out.println("DOZENTEN: " + vlString);
                    e2 = vlElemente.get(index + 14);
                    index = vlElemente.indexOf(e2);
                    vlString = vlString.concat(", " + vlElemente.get(index + 2).text());
                    dozent_gesetzt = 1;
                } else if (e2.text().contains("Raum")) {
                    index = vlElemente.indexOf(e2);
                    vlString = vlString.concat("#" + vlElemente.get(index + 8).text());
                    System.out.println("RAUM: " + vlString);
                    raum_gesetzt = 1;
                    i += 8;

                }
                if (gruppe_gesetzt == 1 && termin_gesetzt == 1 && dozent_gesetzt == 1 && raum_gesetzt == 1) {
                    termin_gesetzt = 0;
                    dozent_gesetzt = 0;
                    raum_gesetzt = 0;
                    if (vlString.startsWith("#")) {
                        vlString = gruppe + vlString;
                    }
                    unformatierteDaten.add(vlString);
                    //System.out.println(vlString);
                    //System.out.println("===================================");
                    vlString = "";
                }

            }
            gruppe_gesetzt = 0;
            termin_gesetzt = 0;
            dozent_gesetzt = 0;
            raum_gesetzt = 0;
        }

        //1. Zug, nur 1. Gruppe#
        //Mittwoch  |  15:45  -  17:15 |  ger. W. |  Beginn: 16.10.2013 |  Ende: 11.02.2014#
        //Becker  #
        //WH C 639 - Wilhelminenhof Gebäude C#
        String formatiert = "";
        String[] split;
        String[] split2;

        List<String> list = new ArrayList<String>();
        int dataOk = 0;

        for (String s : unformatierteDaten) {

            dataOk = analysiereUnformatierteDaten(s);

            if (dataOk == 1) {

                split = s.split("#");

                formatiert = split[0]; //Gruppe
                split2 = split[1].split("\\|"); ////Tag, Woche, Zeit, Begin, Ende
                formatiert = formatiert.concat("#" + split2[0]); // Tag
                if (!split[1].contains("keine Angabe")) {
                    formatiert = formatiert.concat("#" + split2[1]); // Zeit
                    formatiert = formatiert.concat("#" + split2[2]); // woche
                    formatiert = formatiert.concat("#" + split2[3]); // Beginn
                    formatiert = formatiert.concat("#" + split2[4]); // Ende
                    formatiert = formatiert.concat("#" + split[2]);  //Dozent
                    split2 = split[3].split("-"); //Raum
                    formatiert = formatiert.concat("#" + split2[0]); //Raum

                }
                list.add(formatiert);

            }

        }

        return generateVorlesungen(list, m.getName());

    }

    private int analysiereUnformatierteDaten(String data) {

        String[] split = data.split("#");
        //String[] termin_split = split[1].split("\\|");
        /*
         for (String s : split) {
         System.out.println("-----" + s + "-------------");
         }
         for (String s : termin_split) {
         System.out.println("------------" + s + "-------------");
         }
         */
        //String regEx = "[^\\w\\däöüÄÖÜ]";        

        if (split[1].contains("keine Angabe")) {
            return 0;
        } else {
            return 1;
        }
    }

    private List<Vorlesung> generateVorlesungen(List<String> list, String modulName) {
        List<Vorlesung> vorlesungen = new ArrayList<>();
        for (String s : list) {
            System.out.println("erzeugen der Vorlesung: " + s);
            Vorlesung v = lsfFactory.createVorlesung(s, modulName);
            if (v.getTag() == null && v.getBeginn() == null && v.getEnde() == null) {
                service.showErrorDialog("Ein Fehler ist Aufgetreten :( Bei der Vorlesung " + v.getName()
                        + " Existieren keine Angaben");
            } else if (v.getBeginn() == null) {
                service.showErrorDialog("Ein Fehler ist Aufgetreten :( Bei der Vorlesung " + v.getName()
                        + ", Dozent: " + v.getDozent() + " ist keine Zeit Angegeben");
            } else {
                vorlesungen.add(v);
            }

            /*
             System.out.println("Vorlesung Auslesen -> Formatiert");
             System.out.println("==============================================");
             System.out.println(s);
             */
        }
        return vorlesungen;
    }

    /**
     * Liefert alle Links der uebergebenen URL als CSV Zeichenketten dessen Link
     * Tags das uebergebene Attribut und das Attribut den uebergenen Wert
     * enthaelt.
     *
     * ACHTUNG: Liest ALLE Tags ein die attrValue enthalten!!!!!!!
     *
     * aufbau einer Zeichenkette:
     *
     * name;url
     *
     * name = Bezeichnung des Links url = Seite auf die der Link verweist
     *
     *
     * @param url URL, die ausgelesen wird.
     * @param attrName Attribut, die jeder Link enthaelt.
     * @param attrValue Wert, des Attributs.
     * @return Liste, von Zeichenketten die Name und URL enthalten.
     * @throws java.io.IOException
     */
    public List<String> getLinksByAttributAndValue(String url, String attrName, String attrValue) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element body = doc.body();

        Elements links = body.getElementsByAttributeValueContaining(attrName, attrValue);
        int size = links.size();
        Element link = null;
        List<String> linkList = new ArrayList<String>();
        String s = "";
        for (int i = 0; i < size; i++) {
            link = links.get(i);
            s = link.text();
            if (!link.attr("href").equals("")) {
                s = s.concat("#" + link.attr("href"));
                //System.out.println("LSFREPOSITORY->getLinksByAttributeAndValue()-> concatString: " + s);
                linkList.add(s);
            }
        }

        return linkList;
    }

    private String readFileAsString(String fileName) throws IOException {
        //HTW Berlin - Vorlesungsverzeichnis.html
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream is = cl.getResourceAsStream(fileName);
        String ret = "";
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            ret = writer.toString();
        }
        return ret;
    }

}
