package server.controllers;

import common.models.Billboard;
import common.models.Schedule;
import common.router.*;
import server.router.*;
import server.sql.CollectionFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
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
    public static class Get extends Action {
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
    public static class GetById extends Action {
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
    public static class Insert extends Action {
        // Generic Insert action constructor.
        public Insert() { }

        // Override the execute to run the insert function of the schedule collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            // Ensure the body is of type schedule.
            if (req.body instanceof Schedule) {
                // Check if billboard exist
                String sName = ((Schedule) req.body).billboardName;

                List<Billboard> billboardList = CollectionFactory.getInstance(Billboard.class).get(
                    billboard -> sName.equals(String.valueOf(billboard.name)));
                if (billboardList.isEmpty()) return new BadRequest("Billboard doesn't exists.");
                // Check if the schedule already existed

                List<Schedule> scheduleList = CollectionFactory.getInstance(Schedule.class).get(
                    schedule -> sName.equals(String.valueOf(schedule.billboardName)));
                if (!scheduleList.isEmpty()) return new BadRequest("Schedule already exists.");


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
    public static class Delete extends Action {
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

    /**
     * This Action is the GetCurrent Action for the schedules.
     */
    public static class GetCurrent extends Action {
        // Generic GetById action constructor.
        public GetCurrent() { }

        // Override the execute to run the get function of the schedule collection
        @Override
        public IActionResult execute(Request req) throws Exception {
            // Get list of all schedules.
            List<Schedule> allSchedule = CollectionFactory.getInstance(Schedule.class).get(x -> true);
            List<Schedule> scheduleList = new ArrayList<>();
            for ( Schedule temp: allSchedule)
            {
                Instant startTime = temp.startTime;
                Instant endTime = startTime.plus(temp.duration, ChronoUnit.MINUTES);
                Instant rightNow = Instant.now() ;
                int x= startTime.compareTo(rightNow);
                int y = rightNow.compareTo(endTime);
                if ( x*y>= 0) // start < now < end
                {
                    scheduleList.add(temp);
                }
            }

            if (scheduleList.isEmpty()) return new Ok();

            scheduleList.sort(Comparator.comparing(s -> s.createTime));

            Schedule resultSchedule = scheduleList.get(scheduleList.size() - 1);

            List<Billboard> billboardList = CollectionFactory.getInstance(Billboard.class).get(
                billboard -> resultSchedule.billboardName.equals(billboard.name)
            );

            // Return a success IActionResult with the list of schedules.
            return new Ok(billboardList.stream().findFirst().get());
        }
    }
}
