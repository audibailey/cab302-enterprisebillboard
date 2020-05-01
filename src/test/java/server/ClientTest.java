package server;

import common.Status;
import common.Methods;
import common.models.Billboard;
import common.models.Request;
import common.models.Response;
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

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Username: ");
        String username = sc.nextLine();

        System.out.println("Password: ");
        String password = sc.nextLine();

        Socket s = new Socket("127.0.0.1", 12345);
        OutputStream outputStream = s.getOutputStream();

        ObjectOutputStream oos = new ObjectOutputStream(outputStream);

        int passHash = password.hashCode();
        String reqData = username + ":" + Integer.toString(passHash);

        /*Request<Boolean> req = new Request(
            Methods.GET_BILLBOARDS,
            sc.next(),
            true
        );*/

        // Use this to generate both the salt and password to store in the database manually
        int iterations = 1000;
        char[] chars = Integer.toString(passHash).toCharArray();
        byte[] salt = RandomFactory.String().getBytes();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        System.out.println("Salt: " + new BigInteger(1, salt).toString(16));
        System.out.println("Password Hash: " + new BigInteger(1, hash).toString(16));

        Request<String> req = new Request<String>(
            Methods.LOGIN,
            null,
            Base64.getEncoder()
                .encodeToString(reqData.getBytes(StandardCharsets.UTF_8.toString()))
        );
        oos.writeObject(req);
        oos.flush();
        oos.reset();

        String token = null;
        InputStream inputStream = s.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(inputStream);
        Object o = ois.readObject();
        if (o instanceof Response) {
            Response resp = (Response) o;
            if (resp.status == Status.CREATED) {
                System.out.println("Successfully logged in!");
                System.out.println("Your token is: " + (String) resp.data);
                token = (String) resp.data;
            } else {
                System.out.println("Failed to login in.");
                System.out.println((String) resp.data);
            }
        }
        s.close();

        Socket newS = new Socket("127.0.0.1", 12345);
        OutputStream newOStream = newS.getOutputStream();

        ObjectOutputStream newOos = new ObjectOutputStream(newOStream);
        Request<?> newReq = new Request<>(
            Methods.POST_BILLBOARD,
            token,
            true
        );
        newOos.writeObject(newReq);
        newOos.flush();
        newOos.reset();

        InputStream newIStream = newS.getInputStream();
        ObjectInputStream newOis = new ObjectInputStream(newIStream);
        Object newO = newOis.readObject();
        if (newO instanceof Response) {
            Response resp = (Response) newO;
            if (resp.status == Status.SUCCESS) {
                System.out.println("Billboard 1 name: ");
                System.out.println(((List<Billboard>) resp.data).get(0).name);
            } else {
                System.out.println("Failed to post Billboard.");
                System.out.println((String) resp.data);
            }
        }
        newS.close();
        sc.close();
    }

}
