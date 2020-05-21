package client.panels;

import client.frames.CreateEditUserFrame;
import client.frames.ScheduleFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SchedulePanel extends JPanel {
    JButton scheduleButton;

    public SchedulePanel() {

        scheduleButton = new JButton("Schedule");
        scheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                try {
//                    common.system.Notification.display("Hello", "test", TrayIcon.MessageType.ERROR);
//                } catch (AWTException awtException) {
//                    awtException.printStackTrace();
//                }
                // Check if schedule button is pressed
                if(e.getSource() == scheduleButton){
                    new ScheduleFrame(); // Open create user frame
                }
            }
        });
        add(scheduleButton);

    }
}
