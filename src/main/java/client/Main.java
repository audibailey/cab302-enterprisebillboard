package client;

import client.frames.*;
import common.models.Billboard;

import java.io.*;

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
    public static void createAndShowLogin() {
        new Login();
    }

    public static void createAndShowClient() {
        new Client();
    }

    public static void runAll() {
        new Login();
        new Client();
        new BillboardFrame(Billboard.Random(1));
    }

    /**
     * Main class to run GUI Application and socket interface
     */
    public static void main(String[] args) throws IOException {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.

        javax.swing.SwingUtilities.invokeLater(() -> runAll());
    }
}
