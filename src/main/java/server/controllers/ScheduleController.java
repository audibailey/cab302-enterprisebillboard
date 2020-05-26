package server.controllers;

import common.models.Billboard;
import common.models.DayOfWeek;
import common.models.Schedule;
import common.router.*;
import server.router.*;
import server.sql.CollectionFactory;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
        public Get() {
        }

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
     * This Action is the Insert Action for the schedules.
     */
    public static class Insert extends Action {
        // Generic Insert action constructor.
        public Insert() {
        }

        // Override the execute to run the insert function of the schedule collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            // Ensure the body is of type schedule.
            if (req.body instanceof Schedule) {
                // Check if billboard exist
                Schedule s = (Schedule) req.body;

                if (s.dayOfWeek > 7 || s.dayOfWeek < 0)
                    return new BadRequest("Start must be between 0-7 as in 0 for Every day or Sun - Sat");
                if (s.interval != 0 && s.duration > s.interval) return new BadRequest("Duration cannot be longer than the interval");
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

                Billboard temp = billboardList.get(0);
                temp.locked = true;
                CollectionFactory.getInstance(Billboard.class).update(temp);
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
        public Delete() {
        }

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
        public GetCurrent() {
        }

        // Override the execute to run the get function of the schedule collection
        @Override
        public IActionResult execute(Request req) throws Exception {
            // Get list of all schedules.
            String day = Instant.now().atZone(ZoneId.systemDefault()).getDayOfWeek().name();
            // Get the list of schedules which the day is the same.
            DayOfWeek today = DayOfWeek.valueOf(day);
            List<Schedule> todaySchedule = CollectionFactory.getInstance(Schedule.class).get(s -> s.dayOfWeek == 0 || today.ordinal() == s.dayOfWeek);
            todaySchedule.sort(Comparator.comparing(s -> s.createTime));

            // Get the list of schedules which should be shown now
            int totalMinutes = Instant.now().atZone(ZoneOffset.UTC).getHour() * 60 + Instant.now().atZone(ZoneOffset.UTC).getMinute();
            List<Schedule> scheduleList = new ArrayList<>();
            for (Schedule schedule : todaySchedule) {
                int scheduleInterval = schedule.dayOfWeek == 0 ? 24 * 60 : schedule.interval;
                int check = (totalMinutes - schedule.start) % scheduleInterval;

                if (check < schedule.duration && check >= 0) {
                    scheduleList.add(schedule);
                }
            }

            // If no billboard is scheduled, return an OK
            if (scheduleList.isEmpty()) return new Ok();

            // Get the newest inserted billboard.
            scheduleList.sort(Comparator.comparing(s -> s.createTime));

            Schedule resultSchedule = scheduleList.get(scheduleList.size() - 1);

            List<Billboard> billboardList = CollectionFactory.getInstance(Billboard.class).get(
                billboard -> resultSchedule.billboardName.equals(billboard.name)
            );
            //            Schedule[] minutesInDay = new Schedule[1440];

//            for (Schedule schedule: todaySchedule)
//            {
//                for (int i = schedule.start; i < minutesInDay.length; i = i + schedule.interval) {
//                    int diff = schedule.duration;
//
//                    if (i + diff >= 1440) {
//                        diff = 1440 - i;
//                    }
//
//                    for (int j = i; j < i + diff; j++) {
//                        minutesInDay[j] = schedule;
//                    }
//                    if (schedule.interval == 0) {
//                        break;
//                    }
//                }
//            }
//

//            Schedule resultSchedule = minutesInDay[totalMinutes];

//            if (resultSchedule == null) return new Ok();
//            List<Billboard> billboardList = CollectionFactory.getInstance(Billboard.class).get(
//                billboard -> scheduleList.billboardName.equals(billboard.name)


            // Return a success IActionResult with the list of billboard.
            return new Ok(billboardList.stream().findFirst().get());
        }
    }
}
