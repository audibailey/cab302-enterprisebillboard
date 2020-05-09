package server.endpoints.user;

import java.util.List;
import java.util.Optional;

import common.models.Response;
import common.Status;
import common.models.User;

/**
 * This class handles the get function of user database handler and turns it into a response
 * for the client.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 */
public class GetUserHandler {
//    /**
//     * Fetch a user based on ID as a response.
//     *
//     * @param db:     This is used to call the database handler.
//     * @param userID: This is used to get the userID
//     * @return Response<?>: This is the response to send back to the client.
//     */
//
//    public static Response<?> getUserByID(DataService db, int userID) {
//        // Attempt get the user by ID from the database
//        try {
//            Optional<User> resultUser = db.users.get(userID);
//
//            if (resultUser.isPresent()) {
//                // Return a success with the user matched the ID
//                return new Response<>(
//                    Status.SUCCESS,
//                    resultUser
//                );
//            } else {
//                // Return a failed with the error message
//                return new Response<>(
//                    Status.BAD_REQUEST,
//                    "User not Found."
//                );
//            }
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.FAILED, "Failed to get user from the database.");
//        }
//    }
//
//    /**
//     * Fetch a user based on ID as a response.
//     *
//     * @param db:       This is used to call the database handler.
//     * @param username: This is used to get the username
//     * @return Response<?>: This is the response to send back to the client.
//     */
//
//    public static Response<?> getUserByUsername(DataService db, String username) {
//        // Attempt get the user by username from the database
//        try {
//            Optional<User> resultUser = db.users.get(username);
//
//            if (resultUser.isPresent()) {
//                // Return a success with the user matched the ID
//                return new Response<>(
//                    Status.SUCCESS,
//                    resultUser
//                );
//            } else {
//                // Return a failed with the error message
//                return new Response<>(
//                    Status.BAD_REQUEST,
//                    "User not Found."
//                );
//            }
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.FAILED, "Failed to get user from the database.");
//        }
//    }
//
//    /**
//     * Fetch a list of all users as a response.
//     *
//     * @param db: This is used to call the database handler.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//
//    public static Response<?> getAllUsers(DataService db) {
//        // Attempt to get the list of all users from the database
//        try {
//            List<User> allUsers = db.users.getAll();
//            // Return a success with the list of users
//            return new Response<>(
//                Status.SUCCESS,
//                allUsers
//            );
//
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to get all users from the database.");
//        }
//    }

}
