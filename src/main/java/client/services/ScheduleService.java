package client.services;

import common.utils.scheduling.Day;
import common.utils.scheduling.DayOfWeek;
import common.models.Schedule;
import common.utils.session.Session;
import common.router.Response;
import common.router.response.Status;
import common.utils.ClientSocketFactory;
import common.utils.scheduling.Time;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is responsible for the backend schedule service for the client/viewer.
 *
 * @author Jamie Martin
 */
public class ScheduleService {

    public List<Schedule> schedules;

    protected ScheduleService() {
        this.schedules = new ArrayList<>();
    }

    private static class ScheduleServiceHolder {
        private final static ScheduleService INSTANCE = new ScheduleService();
    }

    public static ScheduleService getInstance() { return ScheduleServiceHolder.INSTANCE; }

    public List<Schedule> refresh() {
        Session session = SessionService.getInstance();

        if (session != null) {
            Response result = new ClientSocketFactory("/schedule/get", session.token, null).Connect();

            if (result != null && result.status == Status.SUCCESS && result.body instanceof List) {
                ScheduleServiceHolder.INSTANCE.schedules = (List<Schedule>) result.body;
            }
        }

        return ScheduleServiceHolder.INSTANCE.schedules;
    }

    public List<Day> getSchedule() {
        List<Day> schedulesList = new ArrayList<>();

        for (var day: DayOfWeek.values()) {
            if (day != DayOfWeek.EVERY) {
                String[] minutesInDay = new String[1440];
                List<Schedule> todaysList = schedules.stream().filter(s -> s.dayOfWeek == 0 || day.ordinal() == s.dayOfWeek).collect(Collectors.toList());
                todaysList.sort(Comparator.comparing(s -> s.createTime));

                for (var schedule: todaysList) {
                    for (int i = schedule.start; i < minutesInDay.length; i = i + schedule.interval) {
                        int diff = schedule.duration;

                        if (i + diff >= 1440) {
                            diff = 1440 - i;
                        }

                        for (int j = i; j < i + diff; j++) {
                            minutesInDay[j] = schedule.billboardName;
                        }
                        if (schedule.interval == 0) {
                            break;
                        }
                    }
                }

                List<String> listOfTimes = new ArrayList<>();

                // iterate over minutes
                for (int i = 0; i < minutesInDay.length - 1; i++) {
                    if (minutesInDay[i] == null) continue;

                    // store the name
                    String name = minutesInDay[i];

                    int j = i;

                    // go until the last index
                    while(j + 1 < 1440 && minutesInDay[j + 1] == name) j++;

                    // add formatted string to listOfTimes
                    listOfTimes.add(Time.minutesToTime(i) + " - " + Time.minutesToTime(j) + " " + name);

                    i = j;
                }

                schedulesList.add(new Day(day, listOfTimes));
            }
        }

        return schedulesList;
    }

    public List<Schedule> insert(Schedule s) {
        Session session = SessionService.getInstance();
        Response res = new ClientSocketFactory("/schedule/insert", session.token, null, s).Connect();
        return refresh();
    }

    public List<Schedule> delete(Schedule s) {
        Session session = SessionService.getInstance();
        Response res = new ClientSocketFactory("/schedule/delete", session.token, null, s).Connect();
        return refresh();
    }
}
