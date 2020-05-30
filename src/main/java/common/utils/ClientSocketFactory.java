package common.utils;

import common.models.Billboard;
import common.router.Response;
import common.router.Request;
import common.router.response.Status;
import common.swing.Notification;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.time.Instant;
import java.util.HashMap;
import java.util.Properties;

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
    boolean messageOnError = true;

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

    public ClientSocketFactory setMessageOnError(boolean messageOnError) {
        this.messageOnError = messageOnError;
        return this;
    }

    // Connect to the server and send the request with then wait and receive a response.
    public Response Connect() {
        try {
            // Initialise socket.
            Properties props = Props.getProps("./network.props");

            if (props.containsKey("server.address") && props.containsKey("server.port")) {
                String host = props.getProperty("server.address");
                int port = Integer.parseInt(props.getProperty("server.port"));

                Socket s = new Socket(host, port);

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
                        if (messageOnError) SwingUtilities.invokeLater(() -> Notification.display(finalRes.message));
                    }
                }

                // Clean up.
                ois.close();
                oos.close();
                s.close();

                return res;
            } else {
                Notification.display("Please configure network.props file");
                return null;
            }
        } catch (IOException ex) { // Handle errors.
            if (messageOnError) {
                Notification.display(ex.getMessage());
                return null;
            }
            Response res = new Response(Status.SUCCESS, "Cannot connect to server");
            res.body = new Billboard("Cannot connect to server", "Cannot connect to server", "#000000", null, "#FFFFFF", "Please ensure the server is running", "#000000", false, 0);
            return res;
        } catch ( ClassNotFoundException ex) {
            if (messageOnError) Notification.display(ex.getMessage());
            return null;
        }
    }

}
