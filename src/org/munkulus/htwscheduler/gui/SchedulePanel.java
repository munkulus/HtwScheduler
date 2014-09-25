package org.munkulus.htwscheduler.gui;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.util.ArrayList;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;
import org.munkulus.htwscheduler.service.SchedulerService;

public class SchedulePanel extends JPanel {

    private FlowLayout layout;
    private JScrollPane scrollPane;
    private List<ScheduleDayPanel> dayPanelList;

    private TimePanel timePanel;
    private DayPanel dayPanel;
    private SchedulerService service;
    
    private final Dimension startSize = new Dimension(1100, 960);
    
    public SchedulePanel(TimePanel tp, DayPanel dp) {
        timePanel = tp;
        dayPanel = dp;
        this.setPreferredSize(startSize);
        this.setBackground(Color.DARK_GRAY);
        initComponents();
        this.setLayout(layout);
        addComponents();
        
    }
    
    private void initComponents() {
        scrollPane = new JScrollPane(this, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int pos = getScrollPane().getVerticalScrollBar().getValue();
                timePanel.setScrollBarValue(pos);
            }
        });

        scrollPane.getVerticalScrollBar().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int pos = getScrollPane().getVerticalScrollBar().getValue();
                timePanel.setScrollBarValue(pos);

            }
        });
        
        scrollPane.getHorizontalScrollBar().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int pos = getScrollPane().getHorizontalScrollBar().getValue();
                dayPanel.setScrollBarValue(pos);

            }
        });

        layout = new FlowLayout(FlowLayout.LEFT);
        dayPanelList = new ArrayList<>();
        initDayPanels();
    }

    private void initDayPanels() {
        dayPanelList.add(new ScheduleDayPanel("Montag", this));
        dayPanelList.add(new ScheduleDayPanel("Dienstag", this));
        dayPanelList.add(new ScheduleDayPanel("Mittwoch", this));
        dayPanelList.add(new ScheduleDayPanel("Donnerstag", this));
        dayPanelList.add(new ScheduleDayPanel("Freitag", this));
        dayPanelList.add(new ScheduleDayPanel("Samstag", this));
        dayPanelList.add(new ScheduleDayPanel("Sonntag", this));
    }

    private void addComponents() {
        for (ScheduleDayPanel p : dayPanelList) {
            this.add(p);
        }
    }   
    
    public void addVorlesung(VorlesungsGuiPanel vorlesung) throws NullPointerException {
        if (vorlesung == null) {
            throw new NullPointerException();
        }
        ScheduleDayPanel scheduleDayPanel = getDayPanelFromListByDay(vorlesung.getDay());
        if (scheduleDayPanel == null) {
            throw new NullPointerException("Im LSF ist kein Tag angegeben.");
        }
        scheduleDayPanel.addVorlesung(vorlesung);

    }
    
    public Color deleteVorlesung(VorlesungsGuiPanel vorlesung) {
        ScheduleDayPanel scheduleDayPanel = getDayPanelFromListByDay(vorlesung.getDay());
        return scheduleDayPanel.deleteVorlesung(vorlesung);

    }

    private ScheduleDayPanel getDayPanelFromListByDay(String tag) {
        ScheduleDayPanel panel = null;
        for (ScheduleDayPanel p : dayPanelList) {
            if (p.getDay().equals(tag)) {
                panel = p;
            }
        }
        return panel;
    }
    
    public Color getColorFromExistingModul(String name) {
        Color c = null;
        for (ScheduleDayPanel panel : dayPanelList) {
            for (VorlesungsGuiPanel guiP : panel.getShownVorlesungen()) {
                if (guiP.getVeranstaltungsName().replaceAll(" ", "").contains(name)) {
                    c = guiP.getColor();
                }
            }
        }
        return c;
    }

    public void shrink(int dx) {
        int x = this.getPreferredSize().width;
        int y = this.getPreferredSize().height;
        this.setPreferredSize(new Dimension(x - dx, y));
    }
    
    public void setToStartSize() {
        this.setPreferredSize(startSize);
    }
    
    public List<ScheduleDayPanel> getDayPanelList() {
        return this.dayPanelList;
    }
    
    public void setSizeForLabelInDayPanel(String day, int dx) {
        dayPanel.setSizeForLabelByName(day, dx);
    }
    
    public void resetDayLabel(String day) {
        dayPanel.resetSizeOfLabelByName(day);
    }

    public JScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public void setService(SchedulerService s) {
        this.service = s;
    }

}
