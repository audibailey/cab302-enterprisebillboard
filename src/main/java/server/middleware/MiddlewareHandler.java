package server.middleware;

import common.Status;
import common.models.*;
import server.database.DataService;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MiddlewareHandler {
    private final int ITERATIONS = 1000;

    private DataService db;
    private Map<String, AbstractMap.SimpleEntry<String, LocalDateTime>> sessionTokens = new HashMap<>();
    private List<String> loggedInUsers = new ArrayList<>();

    public MiddlewareHandler(DataService db) {
        this.db = db;
    }

    public <T> Response<?> loginUser(T data) {
        if (data instanceof String) {
            String[] parts;
            User user = null;
            boolean passwordMatch = false;

            try {
                byte[] decodedValue = Base64.getDecoder().decode((String) data);
                String b64decoded = new String(decodedValue, StandardCharsets.UTF_8.toString());
                parts = b64decoded.split(":");
            } catch (Exception e) {
                return new Response<>(
                    Status.FAILED,
                    "An error occurred processing your request."
                );
            }

            try {
                Optional<User> fetchedUser = db.users.get(parts[0]);
                if (fetchedUser.isPresent()) {
                    user = fetchedUser.get();
                    if (loggedInUsers.contains(user.username)) {
                        return new Response<>(
                            Status.FAILED,
                            "Invalid Request: Already logged in!"
                        );
                    }
                } else {
                    return new Response<>(
                        Status.FAILED,
                        "Invalid Request: Username not found."
                    );
                }
            } catch (SQLException e) {
                return new Response<>(
                    Status.FAILED,
                    "An error occurred processing your request."
                );
            }

            try {
                byte[] password = decodeUsingBigInteger(user.password);
                PBEKeySpec spec = new PBEKeySpec(parts[1].toCharArray(), decodeUsingBigInteger(user.salt), ITERATIONS, password.length * 8);
                SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                byte[] testHash = skf.generateSecret(spec).getEncoded();

                int diff = password.length ^ testHash.length;
                for (int i = 0; i < password.length && i < testHash.length; i++) {
                    diff |= password[i] ^ testHash[i];
                }

                passwordMatch = diff == 0;
            } catch (Exception e) {
                return new Response<>(
                    Status.FAILED,
                    "An error occurred processing your request."
                );
            }

            if (passwordMatch) {
                SecureRandom secureRandom = new SecureRandom();
                Base64.Encoder base64Encoder = Base64.getUrlEncoder();
                byte[] randomBytes = new byte[24];
                secureRandom.nextBytes(randomBytes);
                String token = base64Encoder.encodeToString(randomBytes);
                sessionTokens.put(token,
                    new AbstractMap.SimpleEntry<>(
                        user.username,
                        LocalDateTime.now().plusHours(24)
                    )
                );
                loggedInUsers.add(user.username);
                return new Response<>(
                    Status.SUCCESS,
                    token
                );
            } else {
                return new Response<>(
                    Status.FAILED,
                    "Invalid Request: Incorrect Password."
                );
            }
        } else {
            return new Response<>(
                Status.FAILED,
                "Invalid Request: Unknown parameter received."
            );
        }
    }

    public boolean checkToken(String token) {
        if (sessionTokens.containsKey(token)) {
            int checkTime = sessionTokens.get(token).getValue().compareTo(LocalDateTime.now());
            if (checkTime < 0 || checkTime == 0) {
                loggedInUsers.remove(sessionTokens.get(token).getKey());
                sessionTokens.remove(token);
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean checkCanCreateBillboard(String token) {
        try {
            Optional<Permissions> fetchedPerms = db.permissions.get(sessionTokens.get(token).getKey());
            AtomicBoolean canCreateBillboard = new AtomicBoolean(false);
            fetchedPerms.ifPresent(permissions -> canCreateBillboard.set(permissions.canCreateBillboard));
            return canCreateBillboard.get();
        } catch (Exception e) {
            return false;
        }
    }

    public void checkCanEditBillboard(String token) {
        // checkToken then using username check permission
    }

    public void checkCanScheduleBillboard(String token) {
        // checkToken then using username check permission
    }

    public void checkCanEditUser(String token) {
        // checkToken then using username check permission
    }

    public boolean checkCanViewBillboard(String token) {
        try {
            Optional<Permissions> fetchedPerms = db.permissions.get(sessionTokens.get(token).getKey());
            AtomicBoolean canViewBillboard = new AtomicBoolean(false);
            fetchedPerms.ifPresent(permissions -> canViewBillboard.set(permissions.canViewBillboard));
            return canViewBillboard.get();
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteToken(String token) {
        if (sessionTokens.containsKey(token)) {
            if (loggedInUsers.contains(sessionTokens.get(token).getKey())) {
                loggedInUsers.remove(sessionTokens.get(token).getKey());
            }
            sessionTokens.remove(token);
        }
    }

    public byte[] decodeUsingBigInteger(String hexString) {
        byte[] byteArray = new BigInteger(hexString, 16)
            .toByteArray();
        if (byteArray[0] == 0) {
            byte[] output = new byte[byteArray.length - 1];
            System.arraycopy(
                byteArray, 1, output,
                0, output.length);
            return output;
        }
        return byteArray;
    }
}
