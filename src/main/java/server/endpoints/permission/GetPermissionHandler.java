package server.endpoints.permission;

/**
 * This class handles the get function of permission database handler and turns it into a response
 * for the client.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 */
public class GetPermissionHandler {
//    /**
//     * Fetch a permission based on username as a response.
//     *
//     * @param db: This is used to call the database handler.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    public static Response<?> getPermissionByUsername(DataService db, String username) {
//        // Attempt get the permission by username from the database
//        try {
//            Optional<Permissions> resultPermission = db.permissions.get(username);
//            if (resultPermission.isPresent()) {
//                // Return a success with the permission matched the name
//                return new Response<>(
//                    Status.SUCCESS,
//                    resultPermission
//                );
//            } else {
//                // Return a failed with the error message
//                return new Response<>(
//                    Status.BAD_REQUEST,
//                    "Permission not found."
//                );
//            }
//
//
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to get permission from the database.");
//        }
//    }

}
