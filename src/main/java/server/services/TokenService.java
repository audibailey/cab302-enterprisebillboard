package server.services;

import common.models.User;
import common.utils.RandomFactory;
import server.sql.CollectionFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.text.html.Option;
import java.math.BigInteger;
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

    // Better to just make a session object?
    class Session {
        public String token;
        public String username;
        public LocalDateTime expireTime;

        public Session(String username) {
            this.token = RandomFactory.token();
            this.username = username;
            this.expireTime = LocalDateTime.now().plusHours(24);
        }
    }

    private Set<Session> sessions = new HashSet<>();

    private static class TokenServiceHolder {
        private final static TokenService INSTANCE = new TokenService();
    }

    public static TokenService getInstance() {
        return TokenService.TokenServiceHolder.INSTANCE;
    }

    /**
     * This is the function called when a client attempts to login.
     *
     * @param username: this is the username of the user trying to login
     * @param password: the attempted password
     * @return String token: null if failed, token if valid Session exists or new Session created.
     */
    public String loginUser(String username, String password) throws Exception {
        // both are required
        if (username == null || password == null) return null;

        // Ensure user exists
        Optional<User> optionalUser = CollectionFactory.getInstance(User.class).get(u -> u.username == username).stream().findFirst();
        if (optionalUser.isEmpty()) return null;

        User user = optionalUser.get();

        // TODO: ENSURE PASSWORDY STUFF HERE IS CORRECT. Perhaps push salted to a method ie salted(password, salt) != user.password return null; continue otherwise

        // Convert the users saved password and salt as a hex to a byte array for the hashing spec
        byte[] storedPassword = decodeHex(user.password);
        byte[] userSalt = decodeHex(user.salt);

        // Creating a hashing spec based on the supplied login password, the users saved salt, iterations and length
        PBEKeySpec HashingSpec = new PBEKeySpec(password.toCharArray(), userSalt, ITERATIONS, storedPassword.length * 8);
          // Choose the cryptography standard for hashing
        SecretKeyFactory HashingStandard = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        // Attempt to hash the supplied password using the hashing standard and hashing spec
        byte[] testHash = HashingStandard.generateSecret(HashingSpec).getEncoded();

//        // Ensure the hashes are the same
//        int diff = storedPassword.length ^ testHash.length;
//        for (int i = 0; i < storedPassword.length && i < testHash.length; i++) {
//            diff |= storedPassword[i] ^ testHash[i];
//        }
//
//        // if passwords aren't the same return null
//        if (diff != 0) return null;
        if (!Arrays.equals(storedPassword, testHash)) return null;
        // TODO: isn't Array.equals() essentially the same thing as above?

        // Checks if there is a valid session already and returns token if so
        Optional<Session> existingSession = getSessionByUsername(username);
        if (existingSession.isPresent()) return existingSession.get().token;

        // Generate new session and save it to sessions set
        Session newSession = new Session(user.username);
        sessions.add(newSession);

        return newSession.token;
        // TODO: I THINK COMMENTED BELOW CAN BE REMOVED

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
    }

    public byte[] salted(String password, byte[] salt) throws Exception {
        // TODO: IDEALLY LIKE THIS?
//        // Creating a hashing spec based on the supplied login password, the users saved salt, iterations and length
//        PBEKeySpec HashingSpec = new PBEKeySpec(password.toCharArray(), userSalt, ITERATIONS, storedPassword.length * 8);
//        // Choose the cryptography standard for hashing
//        SecretKeyFactory HashingStandard = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//
//        // Attempt to hash the supplied password using the hashing standard and hashing spec
//        byte[] testHash = HashingStandard.generateSecret(HashingSpec).getEncoded();

        return new byte[0];
    }

    /**
     * This function logs the user out by removing the token from the session and removing them
     * from the logged in list.
     *
     * @param token: The supplied user token.
     */
    public void logoutUser(String token) {
        sessions.remove(token);
    }

    /**
     * This function ensures the token is valid.
     *
     * @param token: The supplied user token.
     * @return boolean: Token valid or invalid.
     */
    public boolean verify(String token) {
        Optional<Session> session = sessions.stream().filter(x -> x.token == token).findFirst();

        // verify the session isn't empty or expired
        if (session.isEmpty() || expired(token)) return false;

        return true;
    }

    public Optional<Session> getSessionByUsername(String username) {
        return sessions.stream().filter(x -> x.username == username).findFirst();
    }

    public boolean expired(String token) {
        Optional<Session> session = sessions.stream().filter(x -> x.token == token).findFirst();

        // session is empty so logical equivalent of being expired
        if (session.isEmpty()) return true;

        // If session is expired remove session and return null
        if (session.get().expireTime.compareTo(LocalDateTime.now()) <= 0) {
            sessions.remove(session);
            return true;
        }

        // If the session is present and valid
        return true;
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
