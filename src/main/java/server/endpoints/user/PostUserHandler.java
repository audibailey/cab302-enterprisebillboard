package server.endpoints.user;

import java.util.Optional;

import common.models.Response;
import common.Status;
import common.models.User;

/**
 * This class handles the post function of user database handler and turns it into a response
 * for the client.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 */
public class PostUserHandler {
//    /**
//     * Insert the user and return a response
//     *
//     * @param db:   This is used to call the database handler.
//     * @param user: This is used to pass the user data
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    public static Response<?> insertUser(DataService db, User user) {
//        try {
//            db.users.insert(user);
//            // Attempt to get the inserted user
//            Optional<User> insertedUser = db.users.get(user.username);
//
//            if (insertedUser.isPresent()) {
//                // Return success code
//                return new Response<>(
//                    Status.SUCCESS,
//                    "User successfully inserted."
//                );
//            } else {
//                return new Response<>(
//                    Status.INTERNAL_SERVER_ERROR,
//                    "User did not insert."
//                );
//            }
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to get insert user into the database.");
//        }
//    }
}
