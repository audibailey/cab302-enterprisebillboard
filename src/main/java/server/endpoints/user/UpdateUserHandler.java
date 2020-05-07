package server.endpoints.user;

import java.util.Optional;

import common.models.Response;
import common.Status;
import common.models.User;
import server.database.DataService;

/**
 * This class handles the delete function of user database handler and turns it into a response
 * for the client.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 */
public class UpdateUserHandler {
    /**
     * Delete the user and return a response
     *
     * @param db: This is used to call the database handler.
     * @return Response<?>: This is the response to send back to the client.
     */
    public static Response<?> updateUser(DataService db, User user) {
        try {
            // Get the user before update
            Optional<User> beforeUpdate = db.users.get(user.username);
            db.users.update(user);
            // Attempt to get the updated user
            Optional<User> updatedUser = db.users.get(user.username);
            // Check if the user has been update or not
            if (!beforeUpdate.equals(updatedUser)) {
                // Return a success  message
                return new Response<>(
                    Status.SUCCESS,
                    "User successfully updated."
                );
            } else {
                // Return a failed message
                return new Response<>(
                    Status.INTERNAL_SERVER_ERROR,
                    "There was an error updating the user."
                );
            }
        } catch (Exception e) {
            // TODO: Console Log this
            // If an issue occurs return a failed with the error message as the exception
            return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to delete user from the database.");
        }
    }
}
