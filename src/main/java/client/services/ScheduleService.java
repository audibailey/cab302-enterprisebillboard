package client.services;

import common.models.Schedule;
import common.models.Session;
import common.router.IActionResult;
import common.router.Status;
import common.utils.ClientSocketFactory;

import java.util.ArrayList;
import java.util.List;

public class ScheduleService {

    private List<Schedule> schedules;

    protected ScheduleService() {
        this.schedules = new ArrayList<>();
    }

    private static class ScheduleServiceHolder {
        private final static ScheduleService INSTANCE = new ScheduleService();
    }

    public static List<Schedule> getInstance() { return ScheduleServiceHolder.INSTANCE.schedules; }

    public static void refresh() {
        Session session = SessionService.getInstance();

        IActionResult result = new ClientSocketFactory("/schedule/get", session.token, null).Connect();

        if (result != null && result.status == Status.SUCCESS && result.body instanceof List) {
            ScheduleServiceHolder.INSTANCE.schedules = (List<Schedule>) result.body;
        }
    }

    public static void insert(Schedule s) {
        Session session = SessionService.getInstance();
        new ClientSocketFactory("/schedule/insert", session.token, null, s).Connect();
        refresh();
    }

    public static void delete(Schedule s) {
        Session session = SessionService.getInstance();
        new ClientSocketFactory("/schedule/delete", session.token, null, s).Connect();
        refresh();
    }
}
