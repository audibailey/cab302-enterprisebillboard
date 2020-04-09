package client;

import client.frames.LoginFrame;
import client.panels.MainPanel;

import javax.swing.*;
import java.awt.*;

/**
 * This class consists of the Billboard Viewer handler.
 * All methods that manage and create the GUI are present in this file.
 *
 * @author Jamie Martin
 * @author Trevor Waturuocha
 */
public class Main {
    /**
     * Create the Billboard Control Panel GUI and show it.
     */
    private static void createAndShowLoginGUI() {
        LoginFrame frame = new LoginFrame();
    }

    public static void createAndShowMainGUI() {
        JFrame frame = new JFrame("Billboard Control Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Get the screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        // Display the window.
        // Get the screen size
        frame.setSize(width / 2, height / 2);
        // Get the frame size for centering
        int x = (width - frame.getWidth()) / 2;
        int y = (height - frame.getHeight()) / 2;
        // Set the new frame location and show GUI
        frame.setLocation(x, y);

        client.MenuBar menuBar = new client.MenuBar();
        menuBar.getLogout().addActionListener(e -> {
            frame.dispose();
        });

        frame.setJMenuBar(new MenuBar());

        frame.add(new MainPanel(menuBar), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    /**
     * Main class to run GUI Application and socket interface
     */
    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowLoginGUI();
            }
        });
    }
}
