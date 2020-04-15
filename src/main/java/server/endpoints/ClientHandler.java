package server.endpoints;

import common.models.*;
import server.database.DataService;

import java.io.*;
import java.time.Instant;

public class ClientHandler implements Runnable {

    private InputStream clientIn;
    private OutputStream clientOut;

    public ClientHandler(InputStream clientIn, OutputStream clientOut) {
        this.clientIn = clientIn;
        this.clientOut = clientOut;
    }

    @Override
    public void run() {
        DataService dataService = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(this.clientIn);
            oos = new ObjectOutputStream(this.clientOut);
            dataService = new DataService(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            while (true) {
                // TODO: THIS DOESNT READ THEN EMPTY CAUSING A LOOP BOMB
                Object o = ois.readObject();

                if (o instanceof Request) {
                    Request r = (Request) o;

                    if (r.data instanceof User) {
                        Request<User> ur = r;

                    } else if (r.data instanceof Billboard) {
                        Request<Billboard> br = r;
                        System.out.println(br.method);
                        System.out.println(br.data.userId);
                        if (br.method.equals("Insert")) {
                            dataService.billboards.insert(br.data);
                        }

                    } else if (r.data instanceof Schedule) {
                        Request<Schedule> sr = r;

                    } else if (r.data instanceof Permissions) {
                        Request<Permissions> pr = r;

                    }
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
