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
        new SchemaBuilder(DataService.getConnection(), true, User.class, Billboard.class, Schedule.class, Permissions.class).build();
        CollectionFactory.getInstance(Billboard.class);
        CollectionFactory.getInstance(User.class);
        CollectionFactory.getInstance(Schedule.class);
        CollectionFactory.getInstance(Permissions.class);

        // ADD THE ROUTER
        Router router = new Router(Authentication.Authenticate.class)
            .ADD("/login", UserController.Login.class)
            .ADD("/logout", UserController.Logout.class)
            // Add Billboard actions to router
            .ADD_AUTH("/billboard/get", Permission.canViewBillboard.class, BillboardController.Get.class)
            .ADD_AUTH("/billboard/get/lock", Permission.canViewBillboard.class, BillboardController.GetByLock.class)
            .ADD_AUTH("/billboard/insert", Permission.canCreateBillboard.class, BillboardController.Insert.class)
            .ADD_AUTH("/billboard/update", Permission.canEditBillboard.class, BillboardController.Update.class)
            .ADD_AUTH("/billboard/delete", Permission.canEditBillboard.class, BillboardController.Delete.class)
            // Add UserPermissions actions to router
            .ADD_AUTH("/userpermissions/insert", Permission.canEditUser.class, UserPermissionsController.Insert.class)
            // Add User actions to router
            .ADD_AUTH("/user/update/password", Permission.isSelf.class, UserController.UpdatePassword.class)
            .ADD_AUTH("/user/delete", Permission.canDeleteUser.class, UserController.Delete.class)
            // Add Schedule actions to router
            .ADD_AUTH("/schedule/get", Permission.canScheduleBillboard.class, ScheduleController.Get.class)
            .ADD_AUTH("/schedule/get/current", Permission.canScheduleBillboard.class, ScheduleController.GetCurrent.class)
            .ADD_AUTH("/schedule/insert", Permission.canScheduleBillboard.class, ScheduleController.Insert.class)
            .ADD_AUTH("/schedule/delete", Permission.canScheduleBillboard.class, ScheduleController.Delete.class)
            //Add Permission actions to router
            .ADD_AUTH("/permission/get", Permission.canEditUser.class, PermissionController.Get.class)
            .ADD_AUTH("/permission/get/username", Permission.canEditUser.class, PermissionController.GetByUsername.class)
            .ADD_AUTH("/permission/update", Permission.canEditUser.class, PermissionController.Update.class);

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
