package client.services;

import common.models.DayOfWeek;
import common.models.Schedule;
import common.models.Session;
import common.router.IActionResult;
import common.router.Status;
import common.utils.ClientSocketFactory;

import java.util.*;
import java.util.stream.Collectors;

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
            IActionResult result = new ClientSocketFactory("/schedule/get", session.token, null).Connect();

            if (result != null && result.status == Status.SUCCESS && result.body instanceof List) {
                ScheduleServiceHolder.INSTANCE.schedules = (List<Schedule>) result.body;
            }
        }

        return ScheduleServiceHolder.INSTANCE.schedules;
    }

    public static HashMap<DayOfWeek, String[]> getSchedule(List<Schedule> scheduleList) {
        HashMap<DayOfWeek, String[]> daysOfWeek = new HashMap<>();

        for (var day: DayOfWeek.values()) {
            if (day != DayOfWeek.EVERY) {
                String[] minutesInDay = new String[1440];
                List<Schedule> todaysList = scheduleList.stream().filter(s -> s.dayOfWeek == 0 || day.ordinal() == s.dayOfWeek).collect(Collectors.toList());
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

                daysOfWeek.put(day, minutesInDay);
            }
        }

        return daysOfWeek;
    }

    public List<Schedule> insert(Schedule s) {
        Session session = SessionService.getInstance();
        IActionResult res = new ClientSocketFactory("/schedule/insert", session.token, null, s).Connect();
        return refresh();
    }

    public List<Schedule> delete(Schedule s) {
        Session session = SessionService.getInstance();
        IActionResult res = new ClientSocketFactory("/schedule/delete", session.token, null, s).Connect();
        return refresh();
    }
}
