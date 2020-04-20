package server;

import java.net.ServerSocket;
import java.net.Socket;

import server.database.DataService;
import server.endpoints.ClientHandler;
import server.endpoints.EndpointHandler;

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
        // Connect and populate the database
        DataService dataService = new DataService(false);

        // Connect the endpoint routes to the database handler
        EndpointHandler endpointHandler = new EndpointHandler(dataService);

        // Open the socket
        ServerSocket ss = new ServerSocket(12345);

        // Loop through constantly looking for connections
        while (true) {
            // When a connection is found accept it and create a thread for it
            Socket s = ss.accept();
            new Thread(new ClientHandler(s, endpointHandler)).start();
        }
    }
}
