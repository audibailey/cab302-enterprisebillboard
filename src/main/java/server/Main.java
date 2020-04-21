package server;

import java.net.ServerSocket;
import java.net.Socket;

import server.database.DataService;
import server.endpoints.ClientHandler;
import server.endpoints.EndpointHandler;
import server.middleware.MiddlewareHandler;

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
        DataService dataService = new DataService(false);

        // Connect middleware with the database
        System.out.println("Attempting to connect middleware to database...");
        MiddlewareHandler middlewareHandler = new MiddlewareHandler(dataService);

        // Connect the endpoint routes to the database handler and middleware
        System.out.println("Attempting to sync endpoints with the database and middleware...");
        EndpointHandler endpointHandler = new EndpointHandler(dataService, middlewareHandler);
        System.out.println("All required services are online and linked.");

        // Open the socket
        System.out.println("Opening Server on port 12345...");
        ServerSocket ss = new ServerSocket(12345);
        System.out.println("Sever available at " + ss.getLocalSocketAddress());

        // Loop through constantly looking for connections
        while (true) {
            // When a connection is found accept it and create a thread for it
            Socket s = ss.accept();
            System.out.println("A request attempt has been made from " + s.getInetAddress());
            new Thread(new ClientHandler(s, endpointHandler)).start();
        }
    }
}
