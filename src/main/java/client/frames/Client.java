package client.frames;

import client.Main;
import client.components.Menu;
import client.panels.PanelHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Client extends JFrame implements ActionListener {

    Menu menu = new Menu();

    public Client() {
        setTitle("Billboard Control Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationAndSize();

        menu.getLogout().addActionListener(e -> {
            System.out.println("clicked!");
            client.Main.createAndShowLogin();
            dispose();
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
