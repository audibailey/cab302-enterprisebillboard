package client.services;

import common.models.Billboard;
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
