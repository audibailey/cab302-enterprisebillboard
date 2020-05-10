package server.endpoints.schedule;


/**
 * This class handles the delete function of schedule database handler and turns it into a response
 * for the client.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 */
public class DeleteScheduleHandler {
//    /**
//     * Delete the schedule and return a response
//     *
//     * @param db:       This is used to call the database handler.
//     * @param schedule: This is used to store the data for schedule
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    public static Response<?> deleteSchedule(DataService db, Schedule schedule) {
//        try {
//            db.schedules.delete(schedule);
//            // Attempt to get the deleted schedule (should be empty)
//            Optional<Schedule> deletedSchedule = db.schedules.get(schedule.billboardName);
//            // Check if the schedule is still in the database or not
//            if (deletedSchedule.isEmpty()) {
//                // Return a success  message
//                return new Response<>(
//                    Status.SUCCESS,
//                    "Schedule successfully deleted."
//                );
//            } else {
//                // Return a failed message
//                return new Response<>(
//                    Status.INTERNAL_SERVER_ERROR,
//                    "There was an error deleting the schedule"
//                );
//            }
//        } catch (Exception e) {
//            // TODO: Console Log this
//            // If an issue occurs return a failed with the error message as the exception
//            return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to delete schedule from the database.");
//        }
//    }
}
