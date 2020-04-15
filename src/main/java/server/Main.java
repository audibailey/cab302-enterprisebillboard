package server;

import common.models.*;
import server.database.DataService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
	    // write your code here

        DataService dataService = new DataService(true);

        ServerSocket ss = new ServerSocket(12345);

        while (true) {
            Socket s = ss.accept();

            System.out.println(s.getInetAddress());

            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

            Object o = ois.readObject();

            if (o instanceof Request) {
                Request r = (Request) o;

                if (r.data instanceof User) {
                    Request<User> ur = r;

                } else if (r.data instanceof Billboard) {
                    Request<Billboard> br = r;

                } else if (r.data instanceof Schedule) {
                    Request<Schedule> sr = r;

                } else if (r.data instanceof Permissions) {
                    Request<Permissions> pr = r;

                }
            }

            oos.close();
            ois.close();

            s.close();
        }
    }
}
