package server.controllers;

import common.models.Billboard;
import common.models.DayOfWeek;
import common.models.Schedule;
import common.router.*;
import common.utils.Time;
import server.router.*;
import server.sql.CollectionFactory;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
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
                Schedule s = (Schedule) req.body;

                if (s.start > 7 || s.start < 0) return new BadRequest("Start must be between 0-7 as in 0 for Every day or Sun - Sat");
                if (s.duration > s.interval) return new BadRequest("Duration cannot be longer than the interval");
                if (s.duration > 1440 || s.duration < 1) return new BadRequest("Duration must be between 1 - 1440");
                if (s.interval > 60 || s.interval < 0) return new BadRequest("Interval must be between 0 - 60");

                String sName = s.billboardName;

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
            String day = Instant.now().atZone(ZoneId.systemDefault()).getDayOfWeek().name();
            DayOfWeek today = DayOfWeek.valueOf(day);

            List<Schedule> todaysSchedule = CollectionFactory.getInstance(Schedule.class).get(s -> s.dayOfWeek == 0 || today.ordinal() == s.dayOfWeek);
            todaysSchedule.sort(Comparator.comparing(s -> s.createTime));

            Schedule[] minutesInDay = new Schedule[1440];

            for (Schedule schedule: todaysSchedule)
            {
                for (int i = schedule.start; i < minutesInDay.length; i = i + schedule.interval) {
                    int diff = schedule.duration;

                    if (i + diff >= 1440) {
                        diff = 1440 - i;
                    }

                    for (int j = i; j < i + diff; j++) {
                        minutesInDay[j] = schedule;
                    }
                    if (schedule.interval == 0) {
                        break;
                    }
                }
            }

            int totalMinutes = Time.timeToMinute(Date.from(Instant.now()));

            Schedule resultSchedule = minutesInDay[totalMinutes];

            if (resultSchedule == null) return new Ok();

            List<Billboard> billboardList = CollectionFactory.getInstance(Billboard.class).get(
                billboard -> resultSchedule.billboardName.equals(billboard.name)
            );

            // Return a success IActionResult with the list of schedules.
            return new Ok(billboardList.stream().findFirst().get());
        }
    }
}
