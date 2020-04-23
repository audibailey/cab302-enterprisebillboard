package server.endpoints.billboard;

import java.util.Optional;

import common.models.Billboard;
import common.models.Response;
import common.Status;
import server.database.DataService;


/**
 * This class handles the update function of billboard database handler and turns it into a response
 * for the client.
 *
 * @author Kevin Huynh
 */
public class UpdateBillboardHandler {
    /**
     * Delete the billboard and return a response
     *
     * @param db: This is used to call the database handler.
     * @return Response<?>: This is the response to send back to the client.
     */
    public static Response<?> updateBillboard(DataService db, Billboard bb) {
        // Attempt to the list of locked billboards from the database
        try {
            db.billboards.update(bb);
            Optional<Billboard> updatedBillboard = db.billboards.get(bb.name);

            // Check if the billboard is still in the database or not
            if (updatedBillboard.isPresent()) {
                // Return a success with the list of locked billboards
                return new Response<>(
                    Status.SUCCESS,
                    "Billboard successfully updated."
                );
            } else {
                return new Response<>(
                    Status.FAILED,
                    "There was an error updating the billboard"
                );
            }
        } catch (Exception e) {

            // If an issue occurs return a failed with the error message as the exception
            return new Response<>(Status.FAILED, e.getMessage());
        }
    }
}
