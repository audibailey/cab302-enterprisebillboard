package server;

import server.endpoints.ClientHandler;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(12345);

        while (true) {
            Socket s = ss.accept();
            new Thread(new ClientHandler(s.getInputStream(), s.getOutputStream())).start();
        }
    }
}
