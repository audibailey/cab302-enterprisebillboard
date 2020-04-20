package server.endpoints;

import java.io.*;
import java.net.Socket;

import common.Methods;
import common.Status;
import common.models.*;

/**
 * This class handles the how the server responds to the clients request.
 *
 * @author Perdana Bailey
 * @author Jamie Martin
 */
public class ClientHandler implements Runnable {

    // Used for getting the relevant streams and closing the socket when finished.
    private Socket client;

    // Accessing the end points to allow for routing and data manipulation.
    private EndpointHandler endpointHandler;

    /**
     * The ClientHandler Constructor.
     *
     * @param client:          This is the socket connection from the client.
     * @param endpointHandler: This is the endpoint handler for the server to send its requests.
     */
    public ClientHandler(Socket client, EndpointHandler endpointHandler) {
        this.client = client;
        this.endpointHandler = endpointHandler;
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

            // Ensure the read object is a request,
            if (o instanceof Request) {
                Request<?> r = (Request<?>) o;

                // Route the request to the responsible endpoint sub handler dependant on the method
                if (r.method.equals(Methods.GET_BILLBOARDS) ||
                    r.method.equals(Methods.POST_BILLBOARD) ||
                    r.method.equals(Methods.DELETE_BILLBOARD) ||
                    r.method.equals(Methods.UPDATE_BILLBOARD)) {

                    // Route the request to the billboard sub handler and reply the response to the client
                    replyClient(this.endpointHandler.billboard.route(r));
                } else {
                    // Reply to the client that it was invalid request as the method was invalid
                    replyClient(new Response<>(Status.FAILED, "Invalid Request: Invalid method."));
                }
            } else {
                // Reply to the client that it was not a request
                replyClient(new Response<>(Status.FAILED, "Invalid Request: Unknown object received."));
            }
        } catch (Exception e) {
            // Print an error if reading the objects fail
            e.printStackTrace();
        }

        // Close the connection as it is no longer needed
        try {
            this.client.close();
            System.out.println("Closed the connection to the client.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is a helper function to make replying to client easier.
     *
     * @param resp: This is the response that will be sent to the client.
     */
    private void replyClient(Response<?> resp) {

        // Attempt to send the response
        try {
            // Open an object output stream based on the output stream of the client socket and write the response then flush it
            ObjectOutputStream socketOut = new ObjectOutputStream(this.client.getOutputStream());
            socketOut.writeObject(resp);
            socketOut.flush();

            System.out.println("Wrote a response to client.");
        } catch (Exception e) {
            System.out.println("Failed to reply to client.");
        }
    }
}
