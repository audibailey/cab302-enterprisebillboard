package common.utils;

import common.models.*;
import common.router.*;
import common.swing.Notification;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ClientSocketFactory {

    String path;
    String token;
    HashMap<String, String> params;
    Object body;

    public ClientSocketFactory(String path, String token, HashMap<String, String> params) {
        this.path = path;
        this.token = token;
        this.params = params;
    }

    public ClientSocketFactory(String path, String token, HashMap<String, String> params, Object body) {
        this.path = path;
        this.token = token;
        this.params = params;
        this.body = body;
    }

    public IActionResult Connect() throws IOException, ClassNotFoundException {
        Socket s = new Socket("127.0.0.1", 12345);

        OutputStream outputStream = s.getOutputStream();
        InputStream inputStream = s.getInputStream();

        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        ObjectInputStream ois = new ObjectInputStream(inputStream);

        Request req = new Request(path, token, params, body);

        oos.writeObject(req);
        oos.flush();
        oos.close();

        IActionResult res = null;

        Object o = ois.readObject();

        if (o instanceof IActionResult) {
            res = (IActionResult) o;

            if (res.error) {
                Notification.display("Error", res.message, JOptionPane.ERROR_MESSAGE);
            }
        }

        ois.close();

        s.close();

        return res;
    }

}
