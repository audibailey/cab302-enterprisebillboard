package server;

import common.models.*;
import server.controllers.*;
import server.middleware.*;
import server.router.*;
import server.services.DataService;
import server.sql.CollectionFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

/**
 * This class is the main class, used as the entry point for the server application.
 *
 * @author Perdana Bailey
 * @author Jamie Martin
 */
public class Main {

    /**
     * This is the main function.
     *
     * @param args: command line arguments.
     * @throws Exception: Automatically critical fail if an error is thrown.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Billboard Server");

        System.out.println("Attempting to connect to database...");
        // Connect and populate the database
        DataService.getConnection();
        CollectionFactory.getInstance(Billboard.class);
        CollectionFactory.getInstance(User.class);
        CollectionFactory.getInstance(Schedule.class);
        CollectionFactory.getInstance(Permissions.class);

        // ADD THE ROUTER
        Router router = new Router(Authentication.Authenticate.class)
            .ADD("/login", Authentication.Login.class)
            .ADD("/logout", Authentication.Logout.class)
            // Add Billboard actions to router
            .ADD_AUTH("/billboard/get", BillboardController.Get.class)
            .ADD_AUTH("/billboard/getbyid", BillboardController.GetById.class)
            .ADD_AUTH("/billboard/getbylock", BillboardController.GetByLock.class)
            .ADD_AUTH("/billboard/insert", BillboardController.Insert.class)
            .ADD_AUTH("/billboard/update", BillboardController.Update.class)
            .ADD_AUTH("/billboard/delete", BillboardController.Delete.class)
            // Add User actions to router
            .ADD_AUTH("/user/get", UserController.Get.class)
            .ADD_AUTH("/user/getbyid", UserController.GetById.class)
            .ADD_AUTH("/user/insert", UserController.Insert.class)
            .ADD_AUTH("/user/update", UserController.Update.class)
            .ADD_AUTH("/user/delete", UserController.Delete.class)
            // Add Schedule actions to router
            .ADD_AUTH("/schedule/get", ScheduleController.Get.class)
            .ADD_AUTH("/schedule/getbyid", ScheduleController.GetById.class)
            .ADD_AUTH("/schedule/insert", ScheduleController.Insert.class)
            .ADD_AUTH("/schedule/delete", ScheduleController.Delete.class)
             //Add Permission actions to router
            .ADD_AUTH("/permission/get", PermissionController.Get.class)
            .ADD_AUTH("/permission/getbyid", PermissionController.GetById.class)
            .ADD_AUTH("/permission/insert", PermissionController.Insert.class)
            .ADD_AUTH("/permission/update", PermissionController.Update.class);

        // Fetch the port from the props file
        Properties props = getProps();
        String port = props.getProperty("server.port");
        if (port == null) {
            throw new Exception("Server Port was not specified in the network.props file!");
        }

        // Open the socket
        System.out.println("Opening Server on port " + port + "...");
        int portNum = Integer.parseInt(port);
        ServerSocket ss = new ServerSocket(portNum);
        System.out.println("Sever available at " + ss.getLocalSocketAddress());

        // Loop through constantly looking for connections
        while (true) {
            // When a connection is found accept it and create a thread for it
            Socket s = ss.accept();
            System.out.println("A request attempt has been made from " + s.getInetAddress());
            new Thread(new SocketHandler(s, router)).start();
        }
    }

    /**
     * Gets the properties from network.props for the socket port.
     *
     * @return Properties: The port.
     * @throws IOException:          Thrown when props file not found or when unable to read props file or close prop file stream.
     * @throws NullPointerException: Thrown when props file stream is empty.
     */
    private static Properties getProps() throws IOException, NullPointerException {

        // Initialize variables
        Properties props = new Properties();
        FileInputStream in = null;

        // Read the props file into the properties object
        try {
            in = new FileInputStream("./network.props");
            props.load(in);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("network.props not in root of directory.");
        } catch (IOException e) {
            throw new IOException("Error reading network.props.", e);
        } catch (NullPointerException e) {
            throw new NullPointerException("No configuration information in network.props file.");
        } finally {
            // Close file stream
            try {
                in.close();
            } catch (IOException e) {
                throw new IOException("Error closing network.props.", e);
            } catch (NullPointerException e) {
                throw new NullPointerException("Error closing network.props. network.props doesn't exist anymore.");
            }
        }

        // Return the properties object
        return props;
    }
}
