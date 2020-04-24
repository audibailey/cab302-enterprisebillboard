package server.endpoints.billboard;

import java.util.Optional;

import common.models.Billboard;
import common.models.Response;
import common.Status;
import server.database.DataService;


/**
 * This class handles the delete function of billboard database handler and turns it into a response
 * for the client.
 *
 * @author audibailey
 * @author Kevin Huynh
 */
public class DeleteBillboardHandler {
    /**
     * Delete the billboard and return a response
     *
     * @param db: This is used to call the database handler.
     * @return Response<?>: This is the response to send back to the client.
     */
    public static Response<?> deleteBillboard(DataService db, Billboard bb) {
        // Attempt to the list of locked billboards from the database
        try {
            db.billboards.delete(bb);
            Optional<Billboard> deletedBillboard = db.billboards.get(bb.name);

            // Check if the billboard is still in the database or not
            if (deletedBillboard.isEmpty()) {
                // Return a success with the list of locked billboards
                return new Response<>(
                    Status.SUCCESS,
                    "Billboard successfully deleted."
                );
            } else {
                return new Response<>(
                    Status.INTERNAL_SERVER_ERROR,
                    "There was an error deleting the billboard"
                );
            }
        } catch (Exception e) {
            // TODO: Console Log this
            // If an issue occurs return a failed with the error message as the exception
            return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to delete billboard from the database.");
        }
    }
}
