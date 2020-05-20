package common.utils;

import common.router.IActionResult;
import common.router.Request;
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

    public IActionResult Connect() {
        try {
            Socket s = new Socket("127.0.0.1", 12345);

            OutputStream outputStream = s.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            Request req = new Request(path, token, params, body);
            oos.writeObject(req);
            oos.flush();
            oos.reset();

            InputStream inputStream = s.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            Object o = ois.readObject();

            IActionResult res = null;

            if (o instanceof IActionResult) {
                res = (IActionResult) o;

                if (res.error) {
                    IActionResult finalRes = res;
                    SwingUtilities.invokeLater(() -> Notification.display(finalRes.message));
                    System.out.println(req.path);
                    System.out.println( res.status.toString());
                    System.out.println( res.message);
                }
            }

            ois.close();
            s.close();

            return res;
        } catch (IOException ex) {
            Notification.display(ex.getMessage());
            return null;
        } catch (ClassNotFoundException ex) {
            Notification.display(ex.getMessage());
            return null;
        }
    }

}
