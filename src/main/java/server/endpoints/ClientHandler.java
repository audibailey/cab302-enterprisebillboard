package server.endpoints;

import common.Method;
import common.models.*;
import server.database.DataService;
import server.endpoints.billboard.BillboardHandler;

import java.io.*;

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
        BillboardHandler billboardHandler = null;
        try {
            ois = new ObjectInputStream(this.clientIn);
            oos = new ObjectOutputStream(this.clientOut);
            dataService = new DataService(true);
            billboardHandler = new BillboardHandler(dataService);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Object o = ois.readObject();

            if (o instanceof Request) {
                Request r = (Request) o;

                if (r.data instanceof User) {
                    Request<User> ur = r;

                } else if (r.data instanceof Billboard) {
                    Request<Billboard> br = r;
                    billboardHandler.setRequest(br);

                    for (Method m : Method.values()) {
                        if (m == br.method) {
                            Response<Billboard> b = m.run(billboardHandler);
                        }
                    }

                } else if (r.data instanceof Schedule) {
                    Request<Schedule> sr = r;

                } else if (r.data instanceof Permissions) {
                    Request<Permissions> pr = r;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
