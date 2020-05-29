package common.utils;

// TODO: USE PROPS FOR NETWORK ADDY AND PORT

import common.router.Response;
import common.router.Request;
import common.swing.Notification;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;

/**
 * This class deals with client side socket stateless requests and receiving of the response.
 *
 * @author Perdana Bailey
 * @author Jamie Martin
 */
public class ClientSocketFactory {

    String path;
    String token;
    HashMap<String, String> params;
    Object body;

    // Initialise the request information with no body.
    public ClientSocketFactory(String path, String token, HashMap<String, String> params) {
        this.path = path;
        this.token = token;
        this.params = params;
    }

    // Initialise the request information with a body.
    public ClientSocketFactory(String path, String token, HashMap<String, String> params, Object body) {
        this.path = path;
        this.token = token;
        this.params = params;
        this.body = body;
    }

    // Connect to the server and send the request with then wait and receive a response.
    public Response Connect() {
        try {
            // Initialise socket.
            Socket s = new Socket("127.0.0.1", 12345);

            // Open up an object output stream and write the request to it.
            OutputStream outputStream = s.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            Request req = new Request(path, token, params, body);
            oos.writeObject(req);
            oos.flush();

            // Open up the input stream and wait for a response.
            InputStream inputStream = s.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            Object o = ois.readObject();

            // Ensure its the right response type.
            Response res = null;
            if (o instanceof Response) {
                res = (Response) o;

                // Handle errors.
                if (res.error) {
                    Response finalRes = res;
                    SwingUtilities.invokeLater(() -> Notification.display(finalRes.message));
                }
            }

            // Clean up.
            ois.close();
            oos.close();
            s.close();

            return res;
        } catch (IOException | ClassNotFoundException ex) { // Handle errors.
            Notification.display(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

}
