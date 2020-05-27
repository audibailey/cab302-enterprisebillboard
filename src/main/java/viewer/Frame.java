package viewer;

import common.models.Billboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Frame extends JFrame {

    public Frame(JPanel panel, boolean hardExit) {
        setTitle("Billboard Viewer");

        // Get the screen dimensions
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Setting the frame dimensions
        final int screen_Width = dim.width; // Screen width
        final int screen_Height = dim.height; // Screen height
        setSize(screen_Width, screen_Height); // Setting frame size

        // Setting the frame event listeners
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (hardExit) {
                        System.exit(0);
                    } else {
                        dispose();
                    }
                }
            }
        }); // Adding key listener
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (hardExit) {
                    System.exit(0);
                } else {
                    dispose();
                }
            }
        }); // Adding mouse listener

        // Setting frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set frame to exit on close
        setExtendedState(Frame.MAXIMIZED_BOTH); // Setting frame size to maximise to full screen
        setUndecorated(true); // Removing the frame title bar including default buttons
        setLayout(new BorderLayout());
        add(panel);

        setVisible(true); // Show frame
    }
}
