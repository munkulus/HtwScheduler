package org.munkulus.htwscheduler.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import org.munkulus.htwscheduler.service.SchedulerService;

public class ScheduleContainer extends JPanel {

    private SchedulerService service;
    private SchedulePanel schedulePanel;
    private TimePanel timePanel;
    private DayPanel dayPanel;
    private SouthPanel southPanel;

    public ScheduleContainer(SchedulerService s) {

        service = s;
        this.setPreferredSize(new Dimension(1100, 960));
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new BorderLayout());

        initComponents();
        addComponents();
    }

    private void initComponents() {
        timePanel = new TimePanel();
        dayPanel = new DayPanel();
        this.schedulePanel = new SchedulePanel(timePanel, dayPanel);
        this.schedulePanel.setService(service);
        service.setSchedulePanel(schedulePanel);
        southPanel = new SouthPanel(service);

        

    }

    private void addComponents() {
        this.add(dayPanel.getScrollPane(), BorderLayout.NORTH);
        this.add(timePanel.getScrollPane(), BorderLayout.WEST);
        this.add(schedulePanel.getScrollPane(), BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }
}
