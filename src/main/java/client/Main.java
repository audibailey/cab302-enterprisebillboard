package client;

import java.awt.*;
import javax.swing.*;

public class Main {

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new BackgroundMenuBar();
        menuBar.add(new JMenu("Billboards"));
        menuBar.add(new JMenu("Schedules"));
        menuBar.add(new JMenu("Users"));
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(new JMenu("Logout"));
        return menuBar;
    }

    public class BackgroundMenuBar extends JMenuBar {
        Color bgColor=Color.WHITE;

        public void setColor(Color color) {
            bgColor=color;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(bgColor);
            g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }

    public Container createContentPane() {
        // Create the content-pane
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);
        return contentPane;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("Billboard Control Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        Main demo = new Main();
        frame.setJMenuBar(demo.createMenuBar());
        frame.setContentPane(demo.createContentPane());

        // Display the window.
        frame.setSize(300, 100);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
