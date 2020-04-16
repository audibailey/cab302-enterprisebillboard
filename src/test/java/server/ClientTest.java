/*package server;

import common.models.Billboard;
import common.models.Request;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

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
            Billboard bb = Billboard.Random(20);
            Request<Billboard> req = new Request<Billboard>(
                "Insert",
                bb
            );
            socketOut.writeObject(req);

            // TODO: THIS DOESNT FLUSH
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
            DataInputStream clientBufferedIn = new DataInputStream(new BufferedInputStream(this.server));

            int len = clientBufferedIn.readInt();
            if (len > 0) {
                byte[] message = new byte[len];
                clientBufferedIn.readFully(message);
                System.out.println(new String(message));
            }

            Thread.sleep(100);

        } catch (Exception e) {
            e.getMessage();
        }

    }
}*/
