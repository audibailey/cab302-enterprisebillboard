package server.endpoints.billboard;

import java.util.Optional;

import common.models.Billboard;
import common.models.Response;
import common.Status;
import server.database.DataService;

/**
 * This class handles the post function of billboard database handler and turns it into a response
 * for the client.
 *
 * @author Kevin Huynh
 */
public class PostBillboardHandler {
    /**
     * Insert the billboard and return a response
     *
     * @param db: This is used to call the database handler.
     * @return Response<?>: This is the response to send back to the client.
     */
    public static Response<?> insertBillboard(DataService db, Billboard bb) {
        // Attempt to the list of locked billboards from the database
        try {
            db.billboards.insert(bb);
            Optional<Billboard> insertedBillboard = db.billboards.get(bb.name);

            if (insertedBillboard.isPresent()) {
                // Return success code
                return new Response<>(
                    Status.SUCCESS,
                    "Billboard successfully inserted."
                );
            } else {
                return new Response<>(
                    Status.FAILED,
                    "There was an error inserting the billboard"
                );
            }
        } catch (Exception e) {

            // If an issue occurs return a failed with the error message as the exception
            return new Response<>(Status.FAILED, e.getMessage());
        }
    }
}
