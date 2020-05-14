package server;

import common.router.IActionResult;
import common.router.Request;
import common.router.Status;
import common.models.Billboard;
import common.utils.ClientSocketFactory;
import common.utils.RandomFactory;

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

            IActionResult result = new ClientSocketFactory("/login", null, test, null).Connect();

            if (result.status == Status.SUCCESS) {
                System.out.println("Successfully logged in!");
                System.out.println("Your token is: " + (String) result.body);
                token = (String) result.body;
            } else {
                System.exit(0);
            }
        }

        {
            HashMap<String, String> params = null;
            new ClientSocketFactory("/billboard/insert", token, params, Billboard.Random(1)).Connect();
        }

        Billboard updated = null;
        Billboard deleted = null;
        {
            HashMap<String, String> params = null;
            IActionResult result = new ClientSocketFactory("/billboard/get", token, params, null).Connect();
            if (result != null && result.body != null) {
                List<Billboard> billboards = (List<Billboard>)result.body;

                for (Billboard billboard : billboards) {
                    System.out.println(billboard.name);
                }
                updated = billboards.get(billboards.size()-1);
                //deleted = billboards.get(billboards.size()-2);

            }
        }

        {
            Scanner sc = new Scanner(System.in);
            sc.nextLine();
            HashMap<String, String> params = null;
            updated.name = "Something else1";
            updated.message = "Hello";
            IActionResult result= new ClientSocketFactory("/billboard/update",token, params, updated).Connect();

            System.out.println("test fuc kyou Audi \n");
        }

        {
            HashMap<String, String> params = null;
            IActionResult result = new ClientSocketFactory("/billboard/get", token, params, null).Connect();
            if (result != null && result.body != null) {
                List<Billboard> billboards = (List<Billboard>)result.body;

                for (Billboard billboard : billboards) {
                    System.out.println(billboard.name);
                }
            }
        }
    }

}
