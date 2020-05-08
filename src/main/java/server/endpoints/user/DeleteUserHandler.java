package server.endpoints.user;

import java.util.Optional;

import common.models.Permissions;
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
public class DeleteUserHandler {
    /**
     * Delete the user and return a response
     *
     * @param db: This is used to call the database handler.
     * @return Response<?>: This is the response to send back to the client.
     */
    public static Response<?> deleteUser(DataService db, User user) {
        try {
            Optional<Permissions> deletePerm = db.permissions.get(user.username);
            db.permissions.delete(deletePerm.get());
            db.users.delete(user);
            // Attempt to get the deleted user (should be empty)
            Optional<User> deletedUser = db.users.get(user.username);
            Optional<Permissions> deletedPermission = db.permissions.get(user.username);
            // Check if the user is still in the database or not
            if (deletedUser.isEmpty() && deletedPermission.isEmpty()) {
                // Return a success  message
                return new Response<>(
                    Status.SUCCESS,
                    "User successfully deleted."
                );
            } else {
                // Return a failed message
                return new Response<>(
                    Status.INTERNAL_SERVER_ERROR,
                    "There was an error deleting the user"
                );
            }
        } catch (Exception e) {
            // TODO: Console Log this
            // If an issue occurs return a failed with the error message as the exception
            return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to delete user from the database.");
        }
    }
}
