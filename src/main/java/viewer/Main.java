package viewer;

import common.models.Billboard;
import common.router.IActionResult;
import common.utils.ClientSocketFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * This class consists of the Billboard Viewer handler.
 * All methods that manage and create the GUI are present in this file.
 *
 * @author Trevor Waturuocha
 * @author Jamie Martin
 */
public class Main {
    /**
     * Create the Billboard Viewer GUI and show it.
     */
    public static void createAndShowGUI(Billboard billboard) throws IOException {
        JFrame frame = new JFrame("Billboard Viewer"); // Constructing Billboard Viewer frame

        if (billboard == null) {
            IActionResult res = new ClientSocketFactory("/schedule/get/current", null, null).Connect();
            if (res != null && res.body instanceof Billboard) {
                billboard = (Billboard)res.body;
            } else {
                billboard = new Billboard();
                billboard.message = "No billboard scheduled";
                billboard.information = "Schedule a billboard to see it appear";
            }
        }

        // Check if billboard has a background colour attribute to add background colour
        if(billboard.backgroundColor != null){
            frame.getContentPane().setBackground(Color.decode(billboard.backgroundColor)); // Setting background colour
        }

        // Get the screen dimensions
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Setting the frame dimensions
        final int screen_Width = dim.width; // Screen width
        final int screen_Height = dim.height; // Screen height
        frame.setSize(screen_Width, screen_Height); // Setting frame size

        // Setting the frame event listeners
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                }
            }
        }); // Adding key listener
        frame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        }); // Adding mouse listener

        // Setting frame properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set frame to exit on close
        frame.setExtendedState(Frame.MAXIMIZED_BOTH); // Setting frame size to maximise to full screen
        frame.setUndecorated(true); // Removing the frame title bar including default buttons
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        new ViewerPanel(frame, billboard); // Assigning ViewerPanel to Viewer frame
        frame.setVisible(true); // Show frame
    }

    /**
     * Main class to run GUI Application and socket interface
     */
    public static void main(String[] args) throws InterruptedException {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
