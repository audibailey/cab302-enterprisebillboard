package server.services;

import common.Status;
import common.models.Response;
import common.models.User;
import common.utils.RandomFactory;
import server.router.Request;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * This class handles the how the server responds to the authenticated required requests.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 */
public class TokenService {
    // Hashing Iterations
    private final int ITERATIONS = 1000;

    private Map<String, AbstractMap.SimpleEntry<String, LocalDateTime>> sessionTokens = new HashMap<>();
    // TODO: a map if can have more than 1 logged in client
    private List<String> loggedInUsers = new ArrayList<>();

    private static class TokenServiceHolder {
        private final static TokenService INSTANCE = new TokenService();
    }

    public static TokenService getInstance() {
        return TokenService.TokenServiceHolder.INSTANCE;
    }

    /**
     * This is the function called when a client attempts to login.
     *
     * @param data: this is the input from the client, should contain username and password as `username:password`.
     * @param <T>:  this is the type parameter for the data, should be a String.
     * @return Response<?>: this response gets directly sent to client.
     */
    public String loginUser(User user) {
        // Ensure the data type is a String
//        if (data instanceof String) {
//
//            // Set some variables for use outside of try/catch argument
//            String[] parts;
//            User user = null;
//            boolean passwordMatch = false;
//
//            // Attempt to Base64 Decode the login data and split the username and password hash
//            try {
//                // Decode
//                byte[] decodedValue = Base64.getDecoder().decode((String) data);
//                String b64decoded = new String(decodedValue, StandardCharsets.UTF_8.toString());
//
//                // Split
//                parts = b64decoded.split(":");
//            } catch (Exception e) {
//                // TODO: Console Log this
//                return new Response<>(
//                    Status.INTERNAL_SERVER_ERROR,
//                    "An error occurred decoding the login data received."
//                );
//            }
//
//            // Attempt to fetch user
//            try {
//                // Fetch user from database based off username then save to a variable to reference later
//                Optional<User> fetchedUser = db.users.get(parts[0]);
//                if (fetchedUser.isPresent()) {
//                    user = fetchedUser.get();
//
//                    // If client is already logged in under that username, deny them
//                    if (loggedInUsers.contains(user.username)) {
//                        return new Response<>(
//                            Status.UNAUTHORIZED,
//                            "Already logged in!"
//                        );
//                    }
//                } else {
//                    // If user not found, tell the client
//                    return new Response<>(
//                        Status.UNAUTHORIZED,
//                        "Unknown Username."
//                    );
//                }
//            } catch (SQLException e) {
//                // TODO: Console Log this
//                return new Response<>(
//                    Status.INTERNAL_SERVER_ERROR,
//                    "An error occurred retrieving your username from the database."
//                );
//            }
//
//            // Ensure the password is correct
//            try {
//                // Convert the users saved password and salt as a hex to a byte array for the hashing spec
//                byte[] password = decodeHex(user.password);
//                byte[] salt = decodeHex(user.salt);
//
//                // Creating a hashing spec based on the supplied login password, the users saved salt, iterations and length
//                PBEKeySpec HashingSpec = new PBEKeySpec(parts[1].toCharArray(), salt, ITERATIONS, password.length * 8);
//
//                // Choose the cryptography standard for hashing
//                SecretKeyFactory HashingStandard = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//
//                // Attempt to hash the supplied password using the hashing standard and hashing spec
//                byte[] testHash = HashingStandard.generateSecret(HashingSpec).getEncoded();
//
//                // Ensure the hashes are the same
//                int diff = password.length ^ testHash.length;
//                for (int i = 0; i < password.length && i < testHash.length; i++) {
//                    diff |= password[i] ^ testHash[i];
//                }
//
//                // Save the result to reference later
//                passwordMatch = diff == 0;
//            } catch (Exception e) {
//                // TODO: Console Log this
//                return new Response<>(
//                    Status.INTERNAL_SERVER_ERROR,
//                    "An error occurred processing the hash of your password."
//                );
//            }
//
//            // Ensure the password was correct else return a incorrect password error
//            if (passwordMatch) {
//                // Generate the token and save it to memory along with the username and token expiry
//                String token = RandomFactory.token();
//                sessionTokens.put(token,
//                    new AbstractMap.SimpleEntry<>(
//                        user.username,
//                        LocalDateTime.now().plusHours(24)
//                    )
//                );
//
//                // Also set the user as logged in
//                loggedInUsers.add(user.username);
//                return new Response<>(
//                    Status.CREATED,
//                    token
//                );
//            } else {
//                return new Response<>(
//                    Status.UNAUTHORIZED,
//                    "Incorrect Password."
//                );
//            }
//        } else {
//            return new Response<>(
//                Status.UNSUPPORTED_TYPE,
//                "Unknown parameter received in data field."
//            );
//        }
        return "";
    }

    /**
     * This function logs the user out by removing the token from the session and removing them
     * from the logged in list.
     *
     * @param token: The supplied user token.
     */
    public void logoutUser(String token) {
        // Ensure they are in the session
//        if (sessionTokens.containsKey(token)) {
//            // Ensure they are logged in
//            if (loggedInUsers.contains(sessionTokens.get(token).getKey())) {
//                loggedInUsers.remove(sessionTokens.get(token).getKey());
//            }
//            sessionTokens.remove(token);
//        }
    }

    /**
     * This function ensures the token is valid.
     *
     * @param token: The supplied user token.
     * @return boolean: Token valid or invalid.
     */
    public boolean checkToken(String token) {
        // Ensure the token is even in this session
//        if (sessionTokens.containsKey(token)) {
//            // Ensure the token hasn't expired
//            int checkTime = sessionTokens.get(token).getValue().compareTo(LocalDateTime.now());
//            if (checkTime < 0 || checkTime == 0) {
//                // If expired remove it from token store and remove user from logged in list
//                loggedInUsers.remove(sessionTokens.get(token).getKey());
//                sessionTokens.remove(token);
//                return false;
//            } else {
//                return true;
//            }
//        } else {
//            return false;
//        }
        return false;
    }

    /**
     * This function is a helper function that converts a hex string to a byte array.
     *
     * @param hex: The hex string to be converted to a byte array.
     * @return byte[]: The byte array after converting the hex string.
     */
    private static byte[] decodeHex(String hex) {
        // Convert using BigInteger then turn into a byte array
        byte[] byteArray = new BigInteger(hex, 16).toByteArray();
        // If the byte array has a garbage first value, remove it
        if (byteArray[0] == 0) {
            return Arrays.copyOfRange(byteArray, 1, byteArray.length);
        }
        return byteArray;
    }
}
