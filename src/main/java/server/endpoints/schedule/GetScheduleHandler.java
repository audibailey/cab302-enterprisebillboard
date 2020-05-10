package server.endpoints.schedule;


/**
 * This class handles the get function of schedule database handler and turns it into a response
 * for the client.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 */
public class GetScheduleHandler {
//    /**
//     * Fetch a schedule based on ID as a response.
//     *
//     * @param db:         This is used to call the database handler.
//     * @param scheduleID: This is used to get the scheduleID
//     * @return Response<?>: This is the response to send back to the client.
//     */
//
//    public static Response<?> getScheduleByID(DataService db, int scheduleID) {
//        // Attempt get the schedule by ID from the database
//        try {
//            Optional<Schedule> resultSchedule = db.schedules.get(scheduleID);
//
//            if (resultSchedule.isPresent()) {
//                // Return a success with the schedule matched the ID
//                return new Response<>(
//                    Status.SUCCESS,
//                    resultSchedule
//                );
//            } else {
//                // Return a failed with the error message
//                return new Response<>(
//                    Status.BAD_REQUEST,
//                    "Schedule not Found."
//                );
//            }
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.FAILED, "Failed to get schedule from the database.");
//        }
//    }
//
//    /**
//     * Fetch a schedule based on billboardName as a response.
//     *
//     * @param db:            This is used to call the database handler.
//     * @param billboardName: This is used to get the scheduleID
//     * @return Response<?>: This is the response to send back to the client.
//     */
//
//    public static Response<?> getScheduleByBillboardName(DataService db, String billboardName) {
//        // Attempt get the schedule by billboardName from the database
//        try {
//            Optional<Schedule> resultSchedule = db.schedules.get(billboardName);
//
//            if (resultSchedule.isPresent()) {
//                // Return a success with the schedule matched the ID
//                return new Response<>(
//                    Status.SUCCESS,
//                    resultSchedule
//                );
//            } else {
//                // Return a failed with the error message
//                return new Response<>(
//                    Status.BAD_REQUEST,
//                    "Schedule not Found."
//                );
//            }
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.FAILED, "Failed to get schedule from the database.");
//        }
//    }
//
//    /**
//     * Fetch a list of all scchedules as a response.
//     *
//     * @param db: This is used to call the database handler.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//
//    public static Response<?> getAllSchedule(DataService db) {
//        // Attempt get the schedule by billboardName from the database
//        try {
//            List<Schedule> allSchedules = db.schedules.getAll();
//            // Return a success with the list of schedules
//            return new Response<>(
//                Status.SUCCESS,
//                allSchedules
//            );
//
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.FAILED, "Failed to get schedule from the database.");
//        }
//    }
}
