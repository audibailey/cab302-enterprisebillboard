package client.panels;

import common.models.Schedule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SchedulePanel extends JPanel {
    JButton button = new JButton("Schedule");
    private List<Schedule> scheduleList = new ArrayList<>();

    public SchedulePanel() {

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    common.system.Notification.display("Hello", "test", TrayIcon.MessageType.ERROR);
                } catch (AWTException awtException) {
                    awtException.printStackTrace();
                }
            }
        });
        add(button);
    }
}
