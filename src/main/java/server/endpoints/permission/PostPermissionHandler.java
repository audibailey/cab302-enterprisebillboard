package server.endpoints.permission;

import java.util.Optional;

import common.models.Permissions;
import common.models.Response;
import common.Status;
import common.models.Schedule;

/**
 * This class handles the post function of permission database handler and turns it into a response
 * for the client.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 */
public class PostPermissionHandler {
//    /**
//     * Insert a permission into the database and return a response.
//     *
//     * @param db: This is used to call the database handler.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//
//    public static Response<?> insertPermission(DataService db, Permissions permission) {
//        try {
//            db.permissions.insert(permission);
//            // Attempt to get the inserted schedule
//            Optional<Permissions> insertedPermission = db.permissions.get(permission.username);
//
//            if (insertedPermission.isPresent()) {
//                // Return success code
//                return new Response<>(
//                    Status.SUCCESS,
//                    "Permission successfully inserted."
//                );
//            } else {
//                return new Response<>(
//                    Status.INTERNAL_SERVER_ERROR,
//                    "Permission did not insert."
//                );
//            }
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to get insert permission into the database.");
//        }
//    }
}
