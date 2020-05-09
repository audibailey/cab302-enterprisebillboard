package server;

import java.io.*;
import java.net.Socket;

import common.Methods;
import common.Status;
import server.controllers.billboard.Get;
import server.controllers.billboard.Insert;
import server.controllers.billboard.Update;
import server.endpoints.EndpointHandler;
import server.router.*;
import server.middleware.MiddlewareHandler;
import server.router.models.BadRequest;
import server.router.models.IActionResult;

/**
 * This class handles the how the server responds to the clients request.
 *
 * @author Perdana Bailey
 * @author Jamie Martin
 */
public class ClientHandler implements Runnable {

    // Used for getting the relevant streams and closing the socket when finished.
    private Socket client;
    private Router router;
    /**
     * The ClientHandler Constructor.
     *
     * @param client:          This is the socket connection from the client.
     */
    public ClientHandler(Socket client, Router router) {
        this.client = client;
        this.router = router;
    }

    /**
     * This is the function that runs on the server as the clients thread.
     */
    public void run() {
        // Initialise the Object Input stream
        ObjectInputStream ois = null;

        // Attempt to populate the Object Input Stream with the InputStream from the client
        try {
            ois = new ObjectInputStream(this.client.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Attempt to read the object input and reply with the correct information
        try {
            Object o = ois.readObject();
            // cast the object to a request
            Request req = (Request) o;

            // assign the IP of the client to the request
            req.ip = client.getLocalAddress().toString();

            // use the router to try and find a response
            IActionResult res = router.route(req);

            // send the response
            replyClient(res);

        } catch (Exception e) {
            // Print an error if reading the objects fail
            replyClient(new BadRequest("Not a valid request"));
        }

        // Close the connection as it is no longer needed
        try {
            this.client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is a helper function to make replying to client easier.
     *
     * @param resp: This is the response that will be sent to the client.
     */
    private void replyClient(IActionResult resp) {

        // Attempt to send the response
        try {
            // Open an object output stream based on the output stream of the client socket and write the response then flush it
            ObjectOutputStream socketOut = new ObjectOutputStream(this.client.getOutputStream());
            socketOut.writeObject(resp);
            socketOut.flush();
            System.out.println("Responded to " + this.client.getInetAddress());
        } catch (Exception e) {
            System.out.println("Failed to respond to " + this.client.getInetAddress());
        }
    }
}
