package server;

import common.router.IActionResult;
import common.router.Request;
import common.router.Status;
import common.models.Billboard;
import common.utils.RandomFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClientTest {

    private String host;
    private int port;

    public static <Response> void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Username: ");
        String username = sc.nextLine();

        System.out.println("Password: ");
        String password = sc.nextLine();

        Socket s = new Socket("127.0.0.1", 12345);
        OutputStream outputStream = s.getOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(outputStream);

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

        Request req = new Request("/login", null, test, null);
        oos.writeObject(req);
        oos.flush();
        oos.reset();

        String token = null;
        InputStream inputStream = s.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(inputStream);
        Object o = ois.readObject();
        if (o instanceof IActionResult) {
            IActionResult resp = (IActionResult) o;
            if (resp.status == Status.SUCCESS) {
                System.out.println("Successfully logged in!");
                System.out.println("Your token is: " + (String) resp.body);
                token = (String) resp.body;
            } else {
                System.out.println("Failed to login in.");
                System.out.println((String) resp.status.toString());
                System.out.println((String) resp.message);
            }
        }
        s.close();

        Socket newS = new Socket("127.0.0.1", 12345);
        OutputStream newOStream = newS.getOutputStream();

        ObjectOutputStream newOos = new ObjectOutputStream(newOStream);
        Request newReq = new Request(
            "/billboard/insert",
            token,
            null,
            Billboard.Random(1)
        );

        newOos.writeObject(newReq);
        newOos.flush();
        newOos.reset();

        InputStream newIStream = newS.getInputStream();
        ObjectInputStream newOis = new ObjectInputStream(newIStream);
        Object newO = newOis.readObject();
        if (newO instanceof IActionResult) {
            IActionResult resp = (IActionResult) newO;
            if (resp.status == Status.SUCCESS) {
                System.out.println("Posted Billboard");;
            } else {
                System.out.println("Failed to post Billboard.");
                System.out.println((String) resp.status.toString());
                System.out.println((String) resp.message);
            }
        }
        newS.close();
        sc.close();
    }

}
