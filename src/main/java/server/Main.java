package server;

import common.models.*;
import server.controllers.*;
import server.middleware.*;
import server.router.*;
import server.services.DataService;
import server.sql.CollectionFactory;

import java.net.ServerSocket;
import java.net.Socket;

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
            .ADD_AUTH("/billboard/insert", Permission.canCreateBillboard.class, BillboardController.Insert.class)
            .ADD_AUTH("/billboard/update", BillboardController.Update.class)
            .ADD_AUTH("/billboard/delete", BillboardController.Delete.class)
            // Add User actions to router
            .ADD_AUTH("/user/get", Permission.canEditUser.class, UserController.Get.class)
            .ADD_AUTH("/user/getbyid", Permission.canEditUser.class, UserController.GetById.class)
            .ADD_AUTH("/user/insert", Permission.canEditUser.class, Permission.canEditUser.class, UserController.Insert.class)
            .ADD_AUTH("/user/update", UserController.Update.class)
            .ADD_AUTH("/user/delete", Permission.canEditUser.class, UserController.Delete.class)
            // Add Schedule actions to router
            .ADD_AUTH("/schedule/get", Permission.canScheduleBillboard.class, ScheduleController.Get.class)
            .ADD_AUTH("/schedule/getbyid", Permission.canScheduleBillboard.class, ScheduleController.GetById.class)
            .ADD_AUTH("/schedule/insert", Permission.canScheduleBillboard.class, ScheduleController.Insert.class)
            .ADD_AUTH("/schedule/delete", Permission.canScheduleBillboard.class, ScheduleController.Delete.class)
            //Add Permission actions to router
            .ADD_AUTH("/permission/get", PermissionController.Get.class)
            .ADD_AUTH("/permission/getbyid", PermissionController.GetById.class)
            .ADD_AUTH("/permission/insert", Permission.canEditUser.class, PermissionController.Insert.class)
            .ADD_AUTH("/permission/update", PermissionController.Update.class);

        // Open the socket
        System.out.println("Opening Server on port 12345...");
        ServerSocket ss = new ServerSocket(12345);
        System.out.println("Sever available at " + ss.getLocalSocketAddress());

        // Loop through constantly looking for connections
        while (true) {
            // When a connection is found accept it and create a thread for it
            Socket s = ss.accept();
            System.out.println("A request attempt has been made from " + s.getInetAddress());
            new Thread(new SocketHandler(s, router)).start();
        }
    }
}
