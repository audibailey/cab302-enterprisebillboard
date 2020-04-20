package server.endpoints.billboard;

import java.util.List;

import common.models.Billboard;
import common.models.Response;
import common.Status;
import server.database.DataService;

/**
 * This class handles the get function of billboard database handler and turns it into a response
 * for the client.
 *
 * @author Perdana Bailey
 */
public class GetBillboardHandler {

    /**
     * Fetch a list of locked billboards as a response.
     *
     * @param db: This is used to call the database handler.
     * @return Response<?>: This is the response to send back to the client.
     */
    public static Response<?> getLockedBillboards(DataService db) {
        // Attempt to the list of locked billboards from the database
        try {
            List<Billboard> lockedBillboards = db.billboards.getAll(true);

            // Return a success with the list of locked billboards
            return new Response<>(
                Status.SUCCESS,
                lockedBillboards
            );
        } catch (Exception e) {

            // If an issue occurs return a failed with the error message as the exception
            return new Response<>(Status.FAILED, e.getMessage());
        }
    }

    /**
     * Fetch a list of unlocked billboards as a response.
     *
     * @param db: This is used to call the database handler.
     * @return Response<?>: This is the response to send back to the client.
     */
    public static Response<?> getUnlockedBillboards(DataService db) {
        // Attempt to the list of unlocked billboards from the database
        try {
            List<Billboard> unlockedBillboards = db.billboards.getAll(false);

            // Return a success with the list of unlocked billboards
            return new Response<>(
                Status.SUCCESS,
                unlockedBillboards
            );
        } catch (Exception e) {

            // If an issue occurs return a failed with the error message as the exception
            return new Response<>(Status.FAILED, e.getMessage());
        }
    }

}
