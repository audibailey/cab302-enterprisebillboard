package server.endpoints.billboard;

import java.util.Optional;

import common.models.Billboard;
import common.models.Response;
import common.Status;

/**
 * This class handles the post function of billboard database handler and turns it into a response
 * for the client.
 *
 * @author // Attempt to the list of locked billboards from the database
 * @author Kevin Huynh
 */
public class PostBillboardHandler {
//    /**
//     * Insert the billboard and return a response
//     *
//     * @param db: This is used to call the database handler.
//     * @param bb: This is used to pass the billboard data
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    public static Response<?> insertBillboard(DataService db, Billboard bb) {
//        try {
//            db.billboards.insert(bb);
//            // Attempt to get the inserted billboard
//            Optional<Billboard> insertedBillboard = db.billboards.get(bb.name);
//
//            if (insertedBillboard.isPresent()) {
//                // Return success code
//                return new Response<>(
//                    Status.SUCCESS,
//                    "Billboard successfully inserted."
//                );
//            } else {
//                return new Response<>(
//                    Status.INTERNAL_SERVER_ERROR,
//                    "Billboard did not insert."
//                );
//            }
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to get insert billboard into the database.");
//        }
//    }
}
