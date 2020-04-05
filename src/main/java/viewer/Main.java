package viewer;

import javax.swing.*;
import java.awt.*;

public class Main {
    // Class to create and display GUI
    private static void createAndShowGUI () {
        JFrame frame = new JFrame("Billboard Viewer"); // Constructing Billboard Viewer frame

        // Get the screen dimensions
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Setting the frame dimensions
        final int screen_Width = dim.width; // Screen width
        final int screen_Height = dim.height; // Screen height
        frame.setSize(screen_Width, screen_Height); // Setting frame size

        // Setting the frame event listeners
        frame.addKeyListener(new ExitEvents.KeyListener()); // Adding key listener
        frame.addMouseListener(new ExitEvents.MouseListener()); // Adding mouse listener

        // Setting frame properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set frame to exit on close
        frame.setExtendedState(Frame.MAXIMIZED_BOTH); // Setting frame size to maximise to full screen
        frame.setUndecorated(true); // Removing the frame title bar including default buttons
        frame.setContentPane (new ViewerPanel ()); // Assigning Viewer panel to Viewer frame
        frame.setVisible (true); // Show frame
    }

    // Main class to run GUI Application
    public static void main(String[] args) throws InterruptedException {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
