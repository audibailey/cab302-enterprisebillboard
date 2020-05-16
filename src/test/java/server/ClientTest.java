package server;

import common.models.Permissions;
import common.models.User;
import common.models.UserPermissions;
import common.router.IActionResult;
import common.router.Request;
import common.router.Status;
import common.models.Billboard;
import common.utils.ClientSocketFactory;
import common.utils.RandomFactory;
import server.sql.CollectionFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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

            int passHash = password.hashCode();

            // Use this to generate both the salt and password to store in the database manually
            int iterations = 1000;
            char[] chars = Integer.toString(passHash).toCharArray();
            byte[] salt = RandomFactory.String().getBytes();

            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            System.out.println("Salt: " + new BigInteger(1, salt).toString(16));
            System.out.println("Password Hash: " + new BigInteger(1, hash).toString(16));

            HashMap<String, String> test = new HashMap<String, String>();
            test.put("username", username);
            test.put("password", Integer.toString(passHash));

            System.out.println("Username from test: " + test.get("username"));
            IActionResult result = new ClientSocketFactory("/login", null, test, null).Connect();

            if (result.status == Status.SUCCESS) {
                System.out.println("Successfully logged in!");
                System.out.println("Your token is: " + (String) result.body);
                token = (String) result.body;
            } else {
                System.exit(0);
            }
        }

        // test insert billboard -- Worked
//        {
//            HashMap<String, String> params = null;
//            Billboard bb = Billboard.Random(1);
//
//            new ClientSocketFactory("/billboard/insert", token, params, bb).Connect();
//        }

        Billboard updated = null;
        Billboard deleted = null;
        // Test get all -- Worked
//        {
//            HashMap<String, String> params = null;
//            IActionResult result = new ClientSocketFactory("/billboard/get", token, params, null).Connect();
//            if (result != null && result.body != null) {
//                List<Billboard> billboards = (List<Billboard>) result.body;
//
//                for (Billboard billboard : billboards) {
//                    System.out.println(billboard.name);
//                }
//                updated = billboards.get(billboards.size() - 1);
//
//            }
//        }
        // Test update billboard -- Worked
//        {
//            Scanner sc = new Scanner(System.in);
//            sc.nextLine();
//            HashMap<String, String> params = null;
//            updated.name = "Something else1";
//            updated.message = "Hello";
//            IActionResult result= new ClientSocketFactory("/billboard/update",token, params, updated).Connect();
//
//            System.out.println("test fuc kyou Audi \n");
//        }

        // Test delete billboard -- Worked
//        {
//            Scanner sc = new Scanner(System.in);
//            sc.nextLine();
//            HashMap<String, String> params = null;
//            IActionResult result = new ClientSocketFactory("/billboard/get", token, params, null).Connect();
//            if (result != null && result.body != null) {
//                List<Billboard> billboards = (List<Billboard>) result.body;
//
//                for (Billboard billboard : billboards) {
//                    if (billboard.userId == 2) {
//                        deleted = billboard;
//                        break;
//                    }
//                }
//                result = new Clie ntSocketFactory("/billboard/delete", token, params, deleted).Connect();
//                System.out.println("Deleted billboard");
//            }

        // Test insert user -- Worked
//        {
//            Scanner sc = new Scanner(System.in);
//            sc.nextLine();
//            HashMap<String, String> params = null;
//            User testUser = User.Random();
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

        // Test list users
        {

        }

    }

}

