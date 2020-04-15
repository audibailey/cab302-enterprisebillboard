package client.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SchedulePanel extends JPanel {
    JButton button;

    public SchedulePanel() {

        button = new JButton("Schedule");
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                common.swing.Notification.display("Hello", "World!", JOptionPane.INFORMATION_MESSAGE);
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
