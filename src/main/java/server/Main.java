package server;

import server.controllers.billboard.Get;
import server.controllers.billboard.Insert;
import server.controllers.billboard.Update;
import server.router.*;
import server.services.DataService;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is the main class, used as the entry point for the server application.
 *
 * @author Perdana Bailey
 * @author Jamie Martin
 */
public class Main {

    /**
     * This is the main function.
     *
     * @param args: command line arguments.
     * @throws Exception: Automatically critical fail if an error is thrown.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Billboard Server");

        System.out.println("Attempting to connect to database...");
        // Connect and populate the database
        DataService.getConnection();

        // ADD THE ROUTER
        Router router = new Router().ADD("/billboard/get", Get.class)
            .ADD("/billboard/getbyid", Get.class)
            .ADD("billboard/insert", Insert.class)
            .ADD("/billboard/update", Update.class);

        // Open the socket
        System.out.println("Opening Server on port 12345...");
        ServerSocket ss = new ServerSocket(12345);
        System.out.println("Sever available at " + ss.getLocalSocketAddress());

        // Loop through constantly looking for connections
        while (true) {
            // When a connection is found accept it and create a thread for it
            Socket s = ss.accept();
            System.out.println("A request attempt has been made from " + s.getInetAddress());
            new Thread(new ClientHandler(s, router)).start();
        }
    }
}
