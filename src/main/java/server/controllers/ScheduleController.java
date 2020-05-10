package server.controllers;

import common.models.Billboard;
import common.models.Schedule;
import common.models.User;
import common.router.*;
import server.router.*;
import server.sql.CollectionFactory;

import java.util.List;

/**
 * This class acts as the controller with all the Actions related to the schedule request path.
 *
 * @author Jamie Martin
 * @author Kevin Huynh
 * @author Perdana Bailey
 */
public class ScheduleController {

    /**
     * This Action is the get all Action for the schedule.
     */
    public class Get extends Action {
        // Generic Get action constructor.
        public Get() { }

        // Override the execute to run the get function of the schedule collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            // Get list of all schedules.
            List<Schedule> scheduleList = CollectionFactory.getInstance(Schedule.class).get(x -> true);

            // Return a success IActionResult with the list of schedules.
            return new Ok(scheduleList);
        }
    }

    /**
     * This Action is the GetByID Action for the schedules.
     */
    public class GetById extends Action {
        // Generic GetById action constructor.
        public GetById() { }

        // Override the execute to run the get function of the schedule collection
        @Override
        public IActionResult execute(Request req) throws Exception {
            String id = req.params.get("id");

            // Ensure id field is not null.
            if (id == null) {
                return new BadRequest("Must specify a schedule ID.");
            }

            // Get list of schedules with the ID as specified. This should only return 1 schedule.
            List<Schedule> scheduleList = CollectionFactory.getInstance(Schedule.class).get(
                schedule -> id.equals(String.valueOf(schedule.id))
            );

            // Return a success IActionResult with the list of schedules.
            return new Ok(scheduleList);
        }
    }

    /**
     * This Action is the Insert Action for the schedules.
     */
    public class Insert extends Action {
        // Generic Insert action constructor.
        public Insert() { }

        // Override the execute to run the insert function of the schedule collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            // Ensure the body is of type schedule.
            if (req.body instanceof Schedule) {
                // Attempt to insert the schedule into the database then return a success IActionResult.
                CollectionFactory.getInstance(Schedule.class).insert((Schedule) req.body);
                return new Ok();
            }

            // Return an error on incorrect body type.
            return new UnsupportedType(Schedule.class);
        }
    }

    /**
     * This Action is the Delete Action for the schedules.
     */
    public class Delete extends Action {
        // Generic Delete action constructor.
        public Delete() { }

        // Override the execute to run the delete function of the schedule collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            // Ensure the body is of type schedule.
            if (req.body instanceof Schedule) {
                // Attempt to delete the schedule in the database then return a success IActionResult.
                CollectionFactory.getInstance(Schedule.class).delete((Schedule) req.body);
                return new Ok();
            }

            // Return an error on incorrect body type.
            return new UnsupportedType(Schedule.class);
        }
    }
}
