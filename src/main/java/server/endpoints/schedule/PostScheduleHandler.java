package server.endpoints.schedule;


/**
 * This class handles the post function of schedule database handler and turns it into a response
 * for the client.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 */
public class PostScheduleHandler {
//    /**
//     * Insert the schedule and return a response
//     *
//     * @param db:       This is used to call the database handler.
//     * @param schedule: This is used to pass the schedule data
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    public static Response<?> insertSchedule(DataService db, Schedule schedule) {
//        try {
//            db.schedules.insert(schedule);
//            // Attempt to get the inserted schedule
//            Optional<Schedule> insertedSchedule = db.schedules.get(schedule.billboardName);
//
//            if (insertedSchedule.isPresent()) {
//                // Return success code
//                return new Response<>(
//                    Status.SUCCESS,
//                    "Schedule successfully inserted."
//                );
//            } else {
//                return new Response<>(
//                    Status.INTERNAL_SERVER_ERROR,
//                    "Schedule did not insert."
//                );
//            }
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to get insert schedule into the database.");
//        }
//    }
}
