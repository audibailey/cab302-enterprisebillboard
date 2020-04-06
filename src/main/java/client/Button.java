package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import common.*;

public class Button extends JButton {
    public Button() {
        setBounds(50,100,80,30);

        addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                AppNotification.display("Hello", "World!", JOptionPane.INFORMATION_MESSAGE);
                try {
                    SystemNotification.display("Hello", "test", TrayIcon.MessageType.ERROR);
                } catch (AWTException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


}
