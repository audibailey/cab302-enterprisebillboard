package client.frames;

import client.Main;
import client.components.Menu;
import client.panels.PanelHandler;
import client.services.PermissionsService;
import client.services.SessionService;
import common.models.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client extends JFrame implements ActionListener {

    Menu menu = new Menu();

    public Client() {
        setTitle("Control Panel");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationAndSize();

        menu.getLogout().addActionListener(e -> {
            SessionService.setInstance(null);
            client.Main.createAndShowLogin();
            dispose();
        });

        menu.getUpdatePassword().addActionListener(e -> {
            Session session = SessionService.getInstance();

            String result = (String)JOptionPane.showInputDialog(
                this,
                "Input a new password for user: " + session.username,
                "Edit Password",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                ""
            );

            try {
                PermissionsService.getInstance().updatePassword(session.userId, session.username, result);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        setJMenuBar(menu);

        add(new PanelHandler(), BorderLayout.CENTER);

        setVisible(true);

    }

    public void setLocationAndSize() {
        // Get the screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        // Display the window.
        // Get the screen size
        setSize(width / 2, height / 2);
        // Get the frame size for centering
        int x = (width - getWidth()) / 2;
        int y = (height - getHeight()) / 2;
        // Set the new frame location and show GUI
        setLocation(x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
        Main.createAndShowLogin();
    }
}
