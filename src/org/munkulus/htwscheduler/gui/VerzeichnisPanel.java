package org.munkulus.htwscheduler.gui;

import org.munkulus.htwscheduler.model.*;
import org.munkulus.htwscheduler.service.SchedulerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VerzeichnisPanel extends JPanel {

    //===ATTRIBUTE=============================================================
    private JScrollPane scrollPane;
    private SchedulerService service;
    private VorlesungsverzeichnisSingelton vv;
    private DropDownMenu menu;
    
    private final Dimension panelSize = new Dimension(320, 550);

    //=========================================================================
    public VerzeichnisPanel(SchedulerService s) {
        this.setPreferredSize(panelSize);
        this.setLayout(null);
        this.setBackground(Color.GRAY);
        service = s;
        
        initComponents();
    }

    private void initComponents() {

        vv = service.getVorlesungsverzeichnis();        
        JLabel vorlVerzTop = new JLabel("Vorlesungsverzeichnis");
        addComponent(this, vorlVerzTop, 80, 2, 200, 20);
        menu = new DropDownMenu();
        addComponent(this, menu.getScrollPane(), 0, 25, menu.getPanelWidth(), menu.getPanelHeight());
        initMenu();
        menu.drawRootMenu();

    }

    public void initMenu() {
        for (final Fachbereich fb : vv.getFachbereiche()) {

            final MenuButton mb = new MenuButton(fb.getName(), 0);

            mb.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (mb.getColor() == menu.getNoneActiveColor()) {
                        mb.setColor(menu.getActiveColor());

                        if (fb.getStudiengaenge().isEmpty()) {

                            service.leseStudiengaenge(vv.getFachbereichByName(mb.getButtonText()));
                            addStudiengaenge(fb, mb);
                            menu.drawMenuLevel(1, mb);

                        } else {
                            //addStudiengaenge(fb, mb);
                            menu.drawMenuLevel(1, mb);
                        }
                    } else {
                        mb.setColor(menu.getNoneActiveColor());
                        menu.removeMenuLevel(mb);
                    }

                }
            });
            menu.addRoot(mb);
        }
    }

    

    //===ADD MENU COMPONENTS===================================================
    public void addStudiengaenge(final Fachbereich fb, final MenuButton parent) {

        SubMenu sm = new SubMenu(service, parent, 1);
        for (final Studiengang sg : fb.getStudiengaenge()) {

            final MenuButton mb = new MenuButton(sg.getName(), 0);
            mb.getButton().setToolTipText(sg.getName());

            mb.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton b = (JButton) e.getSource();
                    String text = b.getText();
                    if (mb.getColor() == menu.getNoneActiveColor()) {
                        mb.setColor(menu.getActiveColor());

                        if (text.contains("AWE")) {
                            if (sg.getAWEs().isEmpty()) {
                                service.leseAWEs(sg);
                                addAWEs(sg, mb);
                                menu.drawMenuLevel(2, mb);

                            } else {
                                //addAWEs(sg, mb);
                                menu.drawMenuLevel(2, mb);
                            }
                        } else {
                            if (sg.getSemester().isEmpty()) {

                                service.leseSemester(sg);
                                addSemester(sg, mb);
                                menu.drawMenuLevel(2, mb);

                            } else {
                                //addSemester(sg, mb);
                                menu.drawMenuLevel(2, mb);
                            }
                        }

                    } else {
                        mb.setColor(menu.getNoneActiveColor());
                        if (text.contains("AWE")) {
                            menu.removeMenuLevel(mb);
                        } else {
                            menu.removeMenuLevel(mb);
                        }

                    }

                }
            });
            sm.addItem(mb);
        }
        menu.addSubMenu(sm);
    }

    public void addAWEs(final Studiengang sg, MenuButton parent) {
        SubMenu sm = new SubMenu(service, parent, 2);
        for (final Modul m : sg.getAWEs()) {

            final MenuButton mb = new MenuButton(m.getName(), 1);
            mb.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (mb.getColor() == menu.getNoneActiveColor()) {
                        mb.setColor(menu.getActiveColor());

                        if (m.getVorlesungen().isEmpty()) {
                            service.leseVorlesungen(m);
                            try {
                                service.addGuiVorlesungen(m);
                            } catch (NullPointerException npe) {
                                mb.setColor(menu.getNoneActiveColor());
                                service.showErrorDialog("Vorlesung konnte nicht korrekt eingelesen werden :( " +
                                        npe.getMessage());
                            }

                        } else {
                            try {
                                service.addGuiVorlesungen(m);
                            } catch (NullPointerException npe) {
                                mb.setColor(menu.getNoneActiveColor());
                                service.showErrorDialog("Vorlesung konnte nicht korrekt eingelesen werden :( " + 
                                        npe.getMessage());
                            }
                        }
                    } else {
                        mb.setColor(menu.getNoneActiveColor());
                        service.deleteGuiVorlesungen(m);
                    }

                }
            });
            sm.addItem(mb);
        }
        menu.addSubMenu(sm);

    }

    public void addSemester(final Studiengang sg, final MenuButton parent) {
        SubMenu sm = new SubMenu(service, parent, 2);
        for (final Semester sem : sg.getSemester()) {

            final MenuButton mb = new MenuButton(sem.getName(), 0);

            mb.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (mb.getColor() == menu.getNoneActiveColor()) {
                        mb.setColor(menu.getActiveColor());

                        if (sem.getModule().isEmpty()) {

                            service.leseModule(sem);
                            addModul(sem, mb);
                            menu.drawMenuLevel(3, mb);

                        } else {
                            //addModul(sem, mb);
                            menu.drawMenuLevel(3, mb);
                        }
                    } else {
                        mb.setColor(menu.getNoneActiveColor());
                        menu.removeMenuLevel(mb);
                    }

                }
            });
            sm.addItem(mb);
        }
        menu.addSubMenu(sm);

    }

    public void addModul(final Semester sem, MenuButton parent) {
        SubMenu sm = new SubMenu(service, parent, 3);
        for (final Modul m : sem.getModule()) {
            final MenuButton mb = new MenuButton(m.getName(), 1);
            mb.getButton().setToolTipText(m.getName());

            mb.getButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (mb.getColor() == menu.getNoneActiveColor()) {
                        mb.setColor(menu.getActiveColor());

                        if (m.getVorlesungen().isEmpty()) {
                            service.leseVorlesungen(m);
                            try {
                                service.addGuiVorlesungen(m);
                            } catch (NullPointerException npe) {
                                mb.setColor(menu.getNoneActiveColor());
                                service.showErrorDialog("Vorlesung konnte nicht korrekt eingelesen werden :( " +
                                        npe.getMessage());
                            }
                        } else {
                            try {
                                service.addGuiVorlesungen(m);
                            } catch (NullPointerException npe) {
                                mb.setColor(menu.getNoneActiveColor());
                                service.showErrorDialog("Vorlesung konnte nicht korrekt eingelesen werden :( " +
                                        npe.getMessage());
                            }
                        }
                    } else {
                        mb.setColor(menu.getNoneActiveColor());
                        service.deleteGuiVorlesungen(m);
                    }

                }
            });
            sm.addItem(mb);
        }
        menu.addSubMenu(sm);
    }
    //=========================================================================

    public void deselectAllSelectedLeafs() {
        for (SubMenu sm : menu.getSubMenus()) {
            if (sm.getParent().getButtonText().contains("AWE")) {
                for (MenuButton b : sm.getItems()) {
                    b.setColor(menu.getNoneActiveColor());
                }
            } else if (sm.getLevel() == 3) {
                for (MenuButton b : sm.getItems()) {
                    b.setColor(menu.getNoneActiveColor());
                }
            }
        }
    }
    
    public void setNewHeight(int h) {
        Dimension d = new Dimension(panelSize.width, h - 50);
        this.setPreferredSize(d);
        menu.setNewHeight(h);
        this.updateUI();
    }
    
    private void addComponent(JPanel panel, Component c, int x, int y, int w, int h) {
        c.setBounds(x, y, w, h);
        panel.add(c);
    }

    public JScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public void setService(SchedulerService s) {
        this.service = s;
    }

    public VerzeichnisPanel getMe() {
        return this;
    }

}
