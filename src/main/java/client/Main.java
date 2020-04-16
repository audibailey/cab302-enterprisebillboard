package client;

import client.frames.*;
import common.models.Billboard;
import common.models.Request;
import common.models.Response;
import common.models.User;

import java.io.*;
import java.net.Socket;

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

    /**
     * Main class to run GUI Application and socket interface
     */
    public static void main(String[] args) throws IOException {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.

        Socket s = new Socket("localhost", 12345);

        OutputStream o = s.getOutputStream();
        InputStream i = s.getInputStream();

        ObjectOutputStream oos = new ObjectOutputStream(o);
        ObjectInputStream ois = new ObjectInputStream(i);

        User u = User.Random();

        Request<User> ur = new Request<>("PUT", u);

        oos.writeObject(ur);
        oos.flush();

        ois.close();
        oos.close();

        s.close();

        javax.swing.SwingUtilities.invokeLater(() -> createAndShowLogin());
    }
}
