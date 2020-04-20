package server;

import common.Status;
import common.Methods;
import common.models.Billboard;
import common.models.Request;
import common.models.Response;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientTest {

    private String host;
    private int port;

    public static void main(String[] args) throws Exception {
        new ClientTest("127.0.0.1", 12345).run();
    }

    public ClientTest(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        // connect client to server
        Socket client = new Socket(host, port);

        // create a new thread for server messages handling
        new Thread(new ReceivedMessagesHandler(client.getInputStream())).start();

        ObjectOutputStream socketOut = new ObjectOutputStream(client.getOutputStream());

        System.out.println("Send messages: ");
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            Request<Boolean> req = new Request(
                Methods.GET_BILLBOARDS,
                sc.next(),
                true
            );
            socketOut.writeObject(req);
            socketOut.flush();
            socketOut.reset();
        }
        sc.close();
        client.close();
    }
}

class ReceivedMessagesHandler implements Runnable {

    private InputStream server;

    public ReceivedMessagesHandler(InputStream server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream clientBufferedIn = new ObjectInputStream(this.server);
            ;

            Object o = clientBufferedIn.readObject();
            if (o instanceof Response) {
                Response resp = (Response) o;
                if (resp.status == Status.SUCCESS) {
                    System.out.println(((List<Billboard>) resp.data).get(0).name);
                }
            }

            Thread.sleep(100);

        } catch (Exception e) {
            e.getMessage();
        }

    }
}
