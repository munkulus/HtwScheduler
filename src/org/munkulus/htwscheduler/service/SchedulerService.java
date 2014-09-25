package org.munkulus.htwscheduler.service;

import java.awt.Color;
import java.awt.Component;
import org.munkulus.htwscheduler.model.Fachbereich;
import org.munkulus.htwscheduler.model.Semester;
import org.munkulus.htwscheduler.model.Studiengang;
import org.munkulus.htwscheduler.model.Modul;
import org.munkulus.htwscheduler.model.Vorlesung;
import org.munkulus.htwscheduler.model.VorlesungsverzeichnisSingelton;
import org.munkulus.htwscheduler.utility.LsfParser;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.munkulus.htwscheduler.gui.Dialog;
import org.munkulus.htwscheduler.gui.HtwScheduler;
import org.munkulus.htwscheduler.gui.ScheduleDayPanel;
import org.munkulus.htwscheduler.gui.SchedulePanel;
import org.munkulus.htwscheduler.gui.VerzeichnisPanel;
import org.munkulus.htwscheduler.gui.VorlesungsGuiPanel;
import org.munkulus.htwscheduler.repository.ColorRepository;
import org.munkulus.htwscheduler.repository.SelectedVorlesungen;
import org.munkulus.htwscheduler.utility.Factory;

public class SchedulerService {

    private LsfParser parser;
    private Factory factory;
    private SelectedVorlesungen selectedVorlesungen;
    private SchedulePanel schedulePanel;
    private VerzeichnisPanel verzeichnisPanel;
    private HtwScheduler frame;
    private VorlesungsverzeichnisSingelton vv = VorlesungsverzeichnisSingelton.getInstance();
    private ColorRepository colorRepo;

    
    public SchedulerService() {
        this.parser = new LsfParser(this);
        factory = new Factory();
        selectedVorlesungen = new SelectedVorlesungen();
        colorRepo = new ColorRepository();
    }  

    public void addGuiVorlesungen(Modul m) throws NullPointerException {

        String[] split = m.getName().split(" ");
        String name = "";
        for (int i = 1; i < split.length -1; i++) {
            name = name.concat(split[i]);
            name = name.concat(" ");
        }
        name = name.replaceAll(" ", "");

        Color c = schedulePanel.getColorFromExistingModul(name);
        if (c == null) {
            c = colorRepo.getFreeColor();
        }

        for (Vorlesung v : m.getVorlesungen()) {            
            VorlesungsGuiPanel guiV = factory.createGuiVorlesung(v, m.getName(), c);
            schedulePanel.addVorlesung(guiV);            
        }
    }
    
    public void showErrorDialog(String message) {
        Dialog d = new Dialog(frame, message);
    }

    public void deleteGuiVorlesungen(Modul m) {

        Color c = null;
        for (Vorlesung v : m.getVorlesungen()) {
            VorlesungsGuiPanel guiV = factory.createGuiVorlesung(v, m.getName());
            c = schedulePanel.deleteVorlesung(guiV);
            colorRepo.removeColor(c);
        }
    }

    public void deleteNotSelectedGuiVorlesungen() {
        VorlesungsGuiPanel guiP = null;
        for (ScheduleDayPanel p : schedulePanel.getDayPanelList()) {
            Component[] comps = p.getComponents();
            if (comps.length > 0) {
                for (int i = 0; i < comps.length; i++) {
                    guiP = (VorlesungsGuiPanel) comps[i];
                    if (!guiP.isSelected()) {
                        p.deleteVorlesung(guiP);
                    }
                }

            }
        }
        colorRepo.removeAllColors();
        deselectGuiVorlesungen();        
        verzeichnisPanel.deselectAllSelectedLeafs();

    }

    private void deselectGuiVorlesungen() {
        VorlesungsGuiPanel guiP = null;
        for (ScheduleDayPanel p : schedulePanel.getDayPanelList()) {
            Component[] comps = p.getComponents();
            if (comps.length > 0) {
                for (int i = 0; i < comps.length; i++) {
                    guiP = (VorlesungsGuiPanel) comps[i];
                    guiP.deselect();
                    colorRepo.addUsedColor(guiP.getColor());
                }

            }
        }
    }

    public VorlesungsverzeichnisSingelton getVorlesungsverzeichnis() {
        VorlesungsverzeichnisSingelton vv = VorlesungsverzeichnisSingelton.getInstance();
        
        if (vv.getFachbereiche().isEmpty()) {
            try {
                parser.leseFachbereiche(vv.getUrl());
            } catch (SocketTimeoutException ste) {
                Dialog d = new Dialog(frame, "Socket Time Out :(");
                
            } catch (IOException ioe) {
                Dialog d = new Dialog(frame, "Ein Fehler ist Aufgetreten :(");
            }
        }
        return vv;
    }

    public Studiengang getStudiengangByName(String name) {
        VorlesungsverzeichnisSingelton vv = VorlesungsverzeichnisSingelton.getInstance();
        return vv.getStudienganByName(name);
    }

    public Semester getSemesterByName(String semName, String sgName) {
        Semester s = null;
        VorlesungsverzeichnisSingelton vv = VorlesungsverzeichnisSingelton.getInstance();
        Studiengang sg = getStudiengangByName(sgName);
        s = sg.getSemesterByName(semName);
        
        return s;
    }
    
    
    public void leseStudiengaenge(Fachbereich fb) {
        try {
            parser.leseStudiengaenge(fb);
        } catch (IOException e) {
            Dialog d = new Dialog(frame, "Ein Fehler ist Aufgetreten :(");
        }
    }
    
    public void leseSemester(Studiengang sg) {
        try {
            parser.leseSemester(sg);
        } catch (IOException e) {
            Dialog d = new Dialog(frame, "Ein Fehler ist Aufgetreten :(");
        }
    }
    
    public void leseModule(Semester sem) {
        try {
            parser.leseModule(sem);
        } catch (IOException e) {
            Dialog d = new Dialog(frame, "Ein Fehler ist Aufgetreten :(");
        }
    }
    
    public void leseAWEs(Studiengang sg) {
        try {
            parser.leseModule(sg);
        } catch (IOException e) {
            Dialog d = new Dialog(frame, "Ein Fehler ist Aufgetreten :(");
        }
    }

    public void leseVorlesungen(Modul m) {
        List<Vorlesung> list = new ArrayList<>();
        try {
            list = parser.vorlesungenAuslesen(m);
            if (!list.isEmpty()) {
                for (Vorlesung v : list) {
                    selectedVorlesungen.addElement(v);
                    m.addElement(v);
                }
            }
        } catch (IllegalArgumentException iae) {
            Dialog d = new Dialog(frame, "Ein Fehler ist Aufgetreten :(");
        } catch (IOException e) {
            Dialog d = new Dialog(frame, "Ein Fehler ist Aufgetreten :(");
        } catch (URISyntaxException ex) {
            Dialog d = new Dialog(frame, "Ein Fehler ist Aufgetreten :(");
        } catch (ClassNotFoundException ex) {
            Dialog d = new Dialog(frame, "Ein Fehler ist Aufgetreten :(");
        }
    }

    public void setSchedulePanel(SchedulePanel sp) {
        this.schedulePanel = sp;
    }

    public void setVerzeichnisPanel(VerzeichnisPanel vp) {
        this.verzeichnisPanel = vp;
    }
    
    public void setFrame(HtwScheduler f) {
        this.frame = f;
    }

}
