package server.endpoints.permission;

import java.util.Optional;

import common.models.Permissions;
import common.models.Response;
import common.Status;

/**
 * This class handles the update function of permission database handler and turns it into a response
 * for the client.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 */
public class UpdatePermissionHandler {
//    /**
//     * Update the user and return a response
//     *
//     * @param db: This is used to call the database handler.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    public static Response<?> updatePermission(DataService db, Permissions permissions) {
//        try {
//            // Get the user before update
//            Optional<Permissions> beforeUpdate = db.permissions.get(permissions.username);
//            db.permissions.update(permissions);
//            // Attempt to get the updated user
//            Optional<Permissions> updatedPerm = db.permissions.get(permissions.username);
//            // Check if the user has been update or not
//            if (!beforeUpdate.equals(updatedPerm)) {
//                // Return a success  message
//                return new Response<>(
//                    Status.SUCCESS,
//                    "Permission successfully updated."
//                );
//            } else {
//                // Return a failed message
//                return new Response<>(
//                    Status.INTERNAL_SERVER_ERROR,
//                    "There was an error updating the permission."
//                );
//            }
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to delete permission from the database.");
//        }
//    }
}
