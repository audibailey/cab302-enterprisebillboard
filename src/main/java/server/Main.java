package server;

import common.models.*;
import common.utils.ClientSocketFactory;
import common.utils.HashingFactory;
import common.utils.Props;
import common.utils.RandomFactory;
import server.controllers.*;
import server.middleware.*;
import server.router.*;
import server.services.DataService;
import server.services.RouterService;
import server.sql.CollectionFactory;
import server.sql.SchemaBuilder;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static common.utils.HashingFactory.encodeHex;

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

        // Insert admin user( u:admin-p:admin)
        boolean checkAdmin = false;
        List<User> userList = CollectionFactory.getInstance(User.class).get(user -> true);
        for (User checkUser : userList) {
            if (checkUser.username.equals("admin")) {
                checkAdmin = true;
                break;
            }
        }
        if (!checkAdmin) {
            HashMap<String, String> params = null;
            User testUser = User.Random();
            testUser.username = "admin";
            testUser.password = "admin";
            Permissions testPerm = Permissions.Random(testUser.id, testUser.username);
            testPerm.canEditUser = true;
            testPerm.canScheduleBillboard = true;
            testPerm.canCreateBillboard = true;
            testPerm.canViewBillboard = true;
            testPerm.canEditBillboard = true;
            // Hash the password supplied and set the respective user objects for database insertion.
            byte[] salt = RandomFactory.String().getBytes();
            byte[] password = HashingFactory.hashPassword(Integer.toString(testUser.password.hashCode()), salt, 64);
            testUser.salt = encodeHex(salt);
            testUser.password = encodeHex(password);

            // Attempt to insert the user into the database then return a success IActionResult.
            CollectionFactory.getInstance(User.class).insert(testUser);
            testPerm.username = testUser.username;
            CollectionFactory.getInstance(Permissions.class).insert(testPerm);
        }


        // ADD THE ROUTER
        RouterService.getInstance().SET_AUTH(Authentication.Authenticate.class)
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
            .ADD_AUTH("/permission/get/username", Permission.canViewPermission.class, PermissionController.GetByUsername.class)
            .ADD_AUTH("/permission/update", Permission.canEditUser.class, PermissionController.Update.class);

        // Fetch the port from the props file
        Properties props = Props.getProps("./network.props");
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
            new Thread(new SocketHandler(s)).start();
        }
    }
}
