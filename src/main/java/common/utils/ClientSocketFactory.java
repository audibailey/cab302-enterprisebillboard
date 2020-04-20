/*package common.utils;

import common.models.Request;
import common.models.Response;
import common.swing.Notification;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ClientSocketFactory<T> {

    T data;
    Method method;

    public ClientSocketFactory(Method method) {
        this.method = method;
    }

    public ClientSocketFactory(Method method, T data) {
        this.method = method;
        this.data = data;
    }

    public Response<T> Connect() throws IOException, ClassNotFoundException {
        Socket s = new Socket("localhost", 12345);

        OutputStream outputStream = s.getOutputStream();
        InputStream inputStream = s.getInputStream();

        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        ObjectInputStream ois = new ObjectInputStream(inputStream);

        Request<T> req = new Request<>(method, data);

        oos.writeObject(req);
        oos.flush();
        oos.close();

        Response<T> res = new Response<>();

        while (ois.available() > 0) {
            Object o = ois.readObject();

            if (o instanceof Response) {
                res = (Response<T>) o;

                if (res.status.error) {
                    Notification.display("Error", res.status.message, JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        ois.close();

        s.close();

        return res;
    }

}*/
