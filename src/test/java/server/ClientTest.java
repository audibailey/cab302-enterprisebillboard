package server;

import common.models.*;
import common.router.IActionResult;
import common.router.Request;
import common.router.Status;
import common.utils.ClientSocketFactory;
import common.utils.HashingFactory;
import common.utils.RandomFactory;
import server.controllers.UserPermissionsController;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.util.*;

public class ClientTest {

    private String host;
    private int port;

    public static <Response> void main(String[] args) throws Exception {
        String token = null;

        {
            Scanner sc = new Scanner(System.in);

            System.out.println("Username: ");
            String username = sc.nextLine();

            System.out.println("Password: ");
            String password = sc.nextLine();

            HashMap<String, String> test = new HashMap<>();
            test.put("username", username);
            test.put("password", HashingFactory.hashPassword(password));

            System.out.println("Username from test: " + test.get("username"));
            IActionResult result = new ClientSocketFactory("/login", null, test, null).Connect();

            if (result.status == Status.SUCCESS) {
                System.out.println("Successfully logged in!");
                System.out.println("Your token is: " + ((Session) result.body).token);
                token = ((Session) result.body).token;
            } else {
                System.exit(0);
            }
        }

        {
            User u = new User("jamie", HashingFactory.hashPassword("1234"), null);
            Permissions p = new Permissions(u.username, true, true, true, false, true);
            UserPermissions up = new UserPermissions(u, p);

            new ClientSocketFactory("/userpermissions/insert", token, null, up).Connect();
            System.out.println("INSERTING USER jamie with pass 1234");
        }

        {

            HashMap<String, String> test = new HashMap<>();
            test.put("username", "jamie");
            test.put("password", HashingFactory.hashPassword("1234"));

            IActionResult result = new ClientSocketFactory("/login", null, test, null).Connect();

            if (result.status == Status.SUCCESS) {
                System.out.println("Successfully logged in!");
                System.out.println("Your token is: " + ((Session) result.body).token);
            } else {
                System.exit(0);
            }
        }

        // test insert billboard -- Worked
        {
            HashMap<String, String> params = null;
            Billboard bb = Billboard.Random(1);

            new ClientSocketFactory("/billboard/insert", token, params, bb).Connect();
        }


//         Test get all -- Worked
        {
            HashMap<String, String> params = null;
            IActionResult result = new ClientSocketFactory("/billboard/get", token, params, null).Connect();
            if (result != null && result.body != null) {
                List<Billboard> billboards = (List<Billboard>) result.body;

                for (Billboard billboard : billboards) {
                    System.out.println(billboard.name);
                }

            }
        }
        // Test update billboard -- Worked
//        {
//            Scanner sc = new Scanner(System.in);
//            sc.nextLine();
//            HashMap<String, String> params = null;
//            IActionResult result = new ClientSocketFactory("/billboard/get", token, params, null).Connect();
//            Billboard updated = null;
//            if (result != null && result.body != null) {
//                List<Billboard> billboards = (List<Billboard>) result.body;
//
//                for (Billboard billboard : billboards) {
//                    if (billboard.userId == 1) {
//                        updated = billboard;
//                        break;
//                    }
//                }
//                updated.name = "Something else1";
//                updated.message = "Hello 12345";
//                new ClientSocketFactory("/billboard/update", token, params, updated).Connect();
//
//                System.out.println("test fuck you Audi \n");
//            }
//        }

        // Test delete billboard -- Worked
//        {
//            Scanner sc = new Scanner(System.in);
//            sc.nextLine();
//            HashMap<String, String> params = null;
//            IActionResult result = new ClientSocketFactory("/billboard/get", token, params, null).Connect();
//            Billboard deleted = null;
//            if (result != null && result.body != null) {
//                List<Billboard> billboards = (List<Billboard>) result.body;
//
//                for (Billboard billboard : billboards) {
//                    if (billboard.userId == 2) {
//                        deleted = billboard;
//                        break;
//                    }
//                }
//                result = new ClientSocketFactory("/billboard/delete", token, params, deleted).Connect();
//                System.out.println("Deleted billboard");
//            }
//        }

        // Test insert user -- Worked
//        {
//            Scanner sc = new Scanner(System.in);
//            sc.nextLine();
//            HashMap<String, String> params = null;
//            User testUser = User.Random();
//            testUser.password = "1234";
//            Permissions testPerm = Permissions.Random(testUser.id, testUser.username);
//            UserPermissions temp = new UserPermissions(testUser, testPerm);
//
//            new ClientSocketFactory("/userpermissions/insert", token, params, temp).Connect();
//
//            System.out.println("Inserted user");
//        }
//
//        // Test delete user -- Worked
//        {
//            Scanner sc = new Scanner(System.in);
//            sc.nextLine();
//            HashMap<String, String> params = null;
//            List<User> userList = CollectionFactory.getInstance(User.class).get(user -> true);
//            User deleteUser = null;
//            for (User temp: userList)
//            {
//                if (temp.username.equals("testDelete"))
//                {
//                    deleteUser = temp;
//                    break;
//                }
//            }
//            IActionResult result = new ClientSocketFactory("/user/delete", token, params, deleteUser).Connect();
//            System.out.println("Deleted");
//        }

        // Test list users -- Worked
//        {
//            Scanner sc = new Scanner(System.in);
//            sc.nextLine();
//            HashMap<String, String> params = null;
//            IActionResult result = new ClientSocketFactory("/permission/get", token, params, null).Connect();
//            if (result != null && result.body != null) {
//                List<Permissions> userList = (List<Permissions>) result.body;
//
//                for (Permissions perm : userList) {
//                    System.out.print(perm.username + " has permission of: ");
//                    if (perm.canEditBillboard) {
//                        System.out.print(" canEditBillboard, ");
//                    }
//                    if (perm.canScheduleBillboard) {
//                        System.out.print(" canScheduleBillboard, ");
//                    }
//                    if (perm.canViewBillboard) {
//                        System.out.print(" canViewBillboard, ");
//                    }
//                    if (perm.canCreateBillboard) {
//                        System.out.print(" canCreateBillboard, ");
//                    }
//                    if (perm.canEditUser) {
//                        System.out.print(" canEditUser.");
//                    }
//                    System.out.println("");
//                }
//            }
//        }

        // Test get permission by username -- Worked
//        {
//            Scanner sc = new Scanner(System.in);
//            sc.nextLine();
//            HashMap<String, String> params = new HashMap<>();
//
//            String permUser = "kevin1";
//            params.put("username", permUser);
//            IActionResult result = new ClientSocketFactory("/permission/get/username", token, params, null).Connect();
//
//            if (result != null && result.body != null) {
//                List<Permissions> userList = (List<Permissions>) result.body;
//                Permissions perm = userList.get(0);
//                System.out.print(perm.username + " has permission of: ");
//                if (perm.canEditBillboard) {
//                    System.out.print(" canEditBillboard, ");
//                }
//                if (perm.canScheduleBillboard) {
//                    System.out.print(" canScheduleBillboard, ");
//                }
//                if (perm.canViewBillboard) {
//                    System.out.print(" canViewBillboard, ");
//                }
//                if (perm.canCreateBillboard) {
//                    System.out.print(" canCreateBillboard, ");
//                }
//                if (perm.canEditUser) {
//                    System.out.println(" canEditUser.");
//                }
//            }
//            System.out.println("Get perm by username");
//        }
        // Test update permission
//        {
//            Scanner sc = new Scanner(System.in);
//            sc.nextLine();
//            HashMap<String, String> params = new HashMap<>();
//            IActionResult result = new ClientSocketFactory("/permission/get", token, params, null).Connect();
//            Permissions updatePerm = null;
//            if (result != null && result.body != null) {
//                List<Permissions> permList = (List<Permissions>) result.body;
//                for (Permissions perm: permList)
//                {
//                    if (perm.username.equals("kevin1"))
//                    {
//                        updatePerm = perm;
//                        break;
//                    }
//                }
//            }
//            updatePerm.canEditUser = false;
//            new ClientSocketFactory("/permission/update", token, params, updatePerm).Connect();
//            System.out.println("Updated perm");
//        }

    }

}

