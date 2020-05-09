package server.endpoints.billboard;

import java.util.List;
import java.util.Optional;

import common.models.Billboard;
import common.models.Response;
import common.Status;

/**
 * This class handles the get function of billboard database handler and turns it into a response
 * for the client.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 */
public class GetBillboardHandler {
//
//    /**
//     * Fetch a list of locked billboards as a response.
//     *
//     * @param db: This is used to call the database handler.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    public static Response<?> getLockedBillboards(DataService db) {
//        // Attempt to the list of locked billboards from the database
//        try {
//            List<Billboard> lockedBillboards = db.billboards.getAll(true);
//
//            // Return a success with the list of locked billboards
//            return new Response<>(
//                Status.SUCCESS,
//                lockedBillboards
//            );
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to get all locked billboards from the database.");
//        }
//    }
//
//    /**
//     * Fetch a list of unlocked billboards as a response.
//     *
//     * @param db: This is used to call the database handler.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    public static Response<?> getUnlockedBillboards(DataService db) {
//        // Attempt to the list of unlocked billboards from the database
//        try {
//            List<Billboard> unlockedBillboards = db.billboards.getAll(false);
//
//            // Return a success with the list of unlocked billboards
//            return new Response<>(
//                Status.SUCCESS,
//                unlockedBillboards
//            );
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to get all unlocked billboards from the database.");
//        }
//    }
//
//    /**
//     * Fetch a billboard based on ID as a response.
//     *
//     * @param db: This is used to call the database handler.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//
//    public static Response<?> getBillboardsByID(DataService db, int billboardID) {
//        // Attempt get the billboard by ID from the database
//        try {
//            Optional<Billboard> resultBillboard = db.billboards.get(billboardID);
//
//            if (resultBillboard.isPresent()) {
//                // Return a success with the billboard matched the ID
//                return new Response<>(
//                    Status.SUCCESS,
//                    resultBillboard
//                );
//            } else {
//                // Return a failed with the error message
//                return new Response<>(
//                    Status.BAD_REQUEST,
//                    "Billboard not Found."
//                );
//            }
//
//
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.FAILED, "Failed to get billboard from the database.");
//        }
//    }
//
//    /**
//     * Fetch a billboard based on name as a response.
//     *
//     * @param db: This is used to call the database handler.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    public static Response<?> getBillboardsByName(DataService db, String billboardName) {
//        // Attempt get the billboard by name from the database
//        try {
//            Optional<Billboard> resultBillboard = db.billboards.get(billboardName);
//
//            if (resultBillboard.isPresent()) {
//                // Return a success with the billboard matched the name
//                return new Response<>(
//                    Status.SUCCESS,
//                    resultBillboard
//                );
//            } else {
//                // Return a failed with the error message
//                return new Response<>(
//                    Status.BAD_REQUEST,
//                    "Billboard not found."
//                );
//            }
//
//
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to get billboard from the database.");
//        }
//    }
//
//    /**
//     * Fetch a list of all billboards as a response.
//     *
//     * @param db: This is used to call the database handler.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//
//    public static Response<?> getAllBillboards(DataService db) {
//        // Attempt to get the list of all billboards from the database
//        try {
//            List<Billboard> allBillboards = db.billboards.getAll();
//            // Return a success with the billboard matched the name
//            return new Response<>(
//                Status.SUCCESS,
//                allBillboards
//            );
//
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to get all billboards from the database.");
//        }
//    }
}
