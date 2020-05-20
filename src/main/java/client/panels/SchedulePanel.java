package client.panels;

import client.services.SessionService;
import common.models.Billboard;
import common.models.Schedule;
import common.router.IActionResult;
import common.utils.ClientSocketFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class SchedulePanel extends JPanel {
    JButton button = new JButton("Schedule");
    private List<Schedule> scheduleList = new ArrayList<>();

    public SchedulePanel() {
        IActionResult res = new ClientSocketFactory("/schedule/get", SessionService.getInstance().token, null).Connect();

        if (res != null && res.body instanceof java.util.List) {
            scheduleList = (List<Schedule>) res.body;
        }

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
