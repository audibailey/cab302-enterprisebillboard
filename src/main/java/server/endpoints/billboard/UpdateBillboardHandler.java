package server.endpoints.billboard;

import java.util.Optional;

import common.models.Billboard;
import common.models.Response;
import common.Status;


/**
 * This class handles the update function of billboard database handler and turns it into a response
 * for the client.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 */
public class UpdateBillboardHandler {
//    /**
//     * Delete the billboard and return a response
//     *
//     * @param db: This is used to call the database handler.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    public static Response<?> updateBillboard(DataService db, Billboard bb) {
//        try {
//            // Get the billboard before updated
//            Optional<Billboard> beforeUpdated = db.billboards.get(bb.name);
//            db.billboards.update(bb);
//            // Attempt to get the updated billboard
//            Optional<Billboard> updatedBillboard = db.billboards.get(bb.name);
//
//            // Check if the billboard is updated or not
//            if (!beforeUpdated.equals(updatedBillboard)) {
//                // Return a success
//                return new Response<>(
//                    Status.SUCCESS,
//                    "Billboard successfully updated."
//                );
//            } else {
//                return new Response<>(
//                    Status.BAD_REQUEST,
//                    "There was an error updating the billboard."
//                );
//            }
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to update billboard on the database.");
//        }
//    }
}
