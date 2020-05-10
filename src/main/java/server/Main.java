package server;

import common.models.*;
import server.controllers.*;
import server.middleware.*;
import server.router.*;
import server.services.DataService;
import server.sql.CollectionFactory;
import server.sql.SchemaBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        Router router = new Router()
            .ADD("/login", Authentication.Login.class)
            .ADD("/logout", Authentication.Logout.class)
            // Add Billboard actions to router
            .ADD("/billboard/get", Authentication.Authenticate.class, BillboardController.Get.class)
            .ADD("/billboard/getbyid", Authentication.Authenticate.class, BillboardController.GetById.class)
            .ADD("/billboard/getbylock", Authentication.Authenticate.class, BillboardController.GetByLock.class)
            .ADD("/billboard/insert", Authentication.Authenticate.class, BillboardController.Insert.class)
            .ADD("/billboard/update", Authentication.Authenticate.class, BillboardController.Update.class)
            .ADD("/billboard/delete", Authentication.Authenticate.class, BillboardController.Delete.class)
            // Add User actions to router
            .ADD("/user/get", Authentication.Authenticate.class, UserController.Get.class)
            .ADD("/user/getbyid", Authentication.Authenticate.class, UserController.GetById.class)
            .ADD("/user/insert", Authentication.Authenticate.class, UserController.Insert.class)
            .ADD("/user/update", Authentication.Authenticate.class, UserController.Update.class)
            .ADD("/user/delete", Authentication.Authenticate.class, UserController.Delete.class)
            // Add Schedule actions to router
            .ADD("/schedule/get", Authentication.Authenticate.class, ScheduleController.Get.class)
            .ADD("/schedule/getbyid", Authentication.Authenticate.class, ScheduleController.GetById.class)
            .ADD("/schedule/insert", Authentication.Authenticate.class, ScheduleController.Insert.class)
            .ADD("/schedule/delete", Authentication.Authenticate.class, ScheduleController.Delete.class)
            //Add Permission actions to router
            .ADD("/permission/get", Authentication.Authenticate.class, PermissionController.Get.class)
            .ADD("/permission/getbyid", Authentication.Authenticate.class, PermissionController.GetById.class)
            .ADD("/permission/insert", Authentication.Authenticate.class, PermissionController.Insert.class)
            .ADD("/permission/update", Authentication.Authenticate.class, PermissionController.Update.class);

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
