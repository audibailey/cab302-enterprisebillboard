package server.ControllerTest;

import common.models.*;
import common.router.IActionResult;
import common.router.Request;
import common.router.Status;
import common.utils.HashingFactory;
import org.junit.jupiter.api.Test;
import server.controllers.BillboardController;
import server.controllers.ScheduleController;
import server.sql.CollectionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScheduleControllerTest {

    @Test
    public void InsertSchedule() throws Exception
    {
        // Generate the billboard data to insert
        Billboard bb = Billboard.Random(1);
        bb.locked = true;

        Schedule schedule = Schedule.Random(bb.name);
        // Create new request.
        Request req = new Request(null, "blah", null, bb);
        // Generate a session
        req.session = new Session(
            1, "kevin", null
        );
        // Insert the billboard to the database
        IActionResult test = new BillboardController.Insert().execute(req);

        // Create new request and insert schedule
        req = new Request(null, "blah", null, schedule);
        IActionResult result = new ScheduleController.Insert().execute(req);

        assertEquals(Status.SUCCESS, result.status);
    }

    @Test
    public void InsertExistedSchedule() throws Exception
    {
        // Generate the billboard data to insert
        Billboard bb = Billboard.Random(1);
        bb.name = "ExistedScheduled";
        bb.locked = true;

        Schedule schedule = Schedule.Random(bb.name);
        // Create new request.
        Request req = new Request(null, "blah", null, bb);
        // Generate a session
        req.session = new Session(
            1, "kevin", null
        );
        // Insert the billboard to the database
        IActionResult test = new BillboardController.Insert().execute(req);

        // Create new request and insert schedule
        req = new Request(null, "blah", null, schedule);
        test = new ScheduleController.Insert().execute(req);

        Schedule schedule2 = Schedule.Random(bb.name);
        req = new Request(null,"blah",null,schedule2);
        IActionResult result = new ScheduleController.Insert().execute(req);
        assertEquals(Status.BAD_REQUEST, result.status);
    }

    @Test
    public void DeleteSchedule() throws  Exception
    {
        // Generate the billboard data to insert
        Billboard bb = Billboard.Random(1);
        bb.name = "DeleteSchedule";
        bb.locked = true;

        // Generate schedule data to insert
        Schedule schedule = Schedule.Random(bb.name);
        // Create new request to insert billboard
        Request req = new Request(null, "blah", null, bb);
        // Generate a session
        req.session = new Session(
            1, "kevin", null
        );
        // Insert the billboard to the database
        IActionResult test = new BillboardController.Insert().execute(req);

        // Create new request and insert schedule
        req = new Request(null, "blah", null, schedule);
        test = new ScheduleController.Insert().execute(req);

        //Create new request and get all schedules
        req = new  Request(null,"blah",null,null);
        test = new ScheduleController.Get().execute(req);
        List<Schedule> scheduleList = (List<Schedule>) test.body;

        // Get the deleted schedule data
        Schedule deleted = null;
        for (Schedule sche: scheduleList)
        {
            if (sche.billboardName.equals("DeleteSchedule"))
            {
                deleted = sche;
                break;
            }
        }
        req = new Request(null,"blah",null,deleted);
        IActionResult result = new ScheduleController.Delete().execute(req);
        assertEquals(Status.SUCCESS,result.status);
    }

    @Test
    public void DeleteNonExistedSchedule() throws  Exception
    {
        // Generate a random schedule
        Schedule deleted = Schedule.Random("aRandomName");

        // Create new request and try to delete the schedule
        Request req = new Request(null,"blah",null,deleted);
        IActionResult result = new ScheduleController.Delete().execute(req);

        assertEquals(Status.BAD_REQUEST,result.status);
    }

    @Test
    public void GetAllSchedule() throws  Exception
    {
        // Create new request and try to get all schedule
        Request req = new Request(null,"blah",null,null);
        IActionResult result = new ScheduleController.Get().execute(req);

        assertEquals(Status.SUCCESS,result.status);
    }

}
