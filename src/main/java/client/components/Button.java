package client.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import common.system.Notification;

public class Button extends JButton {
    public Button() {
        setBounds(50,100,80,30);

        addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                common.swing.Notification.display("Hello", "World!", JOptionPane.INFORMATION_MESSAGE);
                try {
                    Notification.display("Hello", "test", TrayIcon.MessageType.ERROR);
                } catch (AWTException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


}
