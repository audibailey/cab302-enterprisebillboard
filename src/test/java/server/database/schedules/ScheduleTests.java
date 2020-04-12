package server.database.schedules;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import common.models.Billboard;
import common.models.Schedule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import common.models.Permissions;
import common.models.User;
import server.database.DataService;

/**
 * Test class for the schedule database handler
 *
 * @author Jamie Martin
 * @author Kevin Huynh
 * @author Perdana Bailey
 */
public class ScheduleTests {

    // The DataService used by all the tests, essentially a database connection.
    private static DataService dataService;

    // Test users ID for use in billboard testing
    private static int UserID;

    /**
     * Connects to the database or a mock database.
     *
     * @throws Exception: this exception returns when there is an issue connecting to the database.
     */
    @BeforeAll
    public static void ConnectToDatabase() throws Exception {
        dataService = new DataService(true);

        // Create the test user and save its ID
        User MasterUser = new User("Username1", "Password", "Salt");
        dataService.users.insert(MasterUser);
        Optional<User> DatabasedUser = dataService.users.get("Username1");
        DatabasedUser.ifPresent(user -> UserID = user.id);
    }

    /**
     * Tests adding a billboard and schedule to the database.
     *
     * @throws Exception: this exception returns when there is an issue inserting data into the database.
     */
    @Test
    public void AddBillboardScheduleTest() throws Exception {
        Billboard BillboardOne = new Billboard(
            "Billboard1",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            UserID
        );
        dataService.billboards.insert(BillboardOne);

        Optional<Billboard> InsertedBillboard = dataService.billboards.get(BillboardOne.name);
        if (InsertedBillboard.isPresent()) {
            Instant TestDate = Instant.now();
            Schedule TestSchedule = new Schedule(
                InsertedBillboard.get().name,
                TestDate,
                1,
                120
            );
            dataService.schedules.insert(TestSchedule);
            Optional<Schedule> InsertedSchedule = dataService.schedules.get(dataService.schedules.getAll().get(0).id);
            if (InsertedSchedule.isPresent()) {
                assertEquals(TestDate.getEpochSecond(), InsertedSchedule.get().startTime.getEpochSecond());

                dataService.schedules.delete(InsertedSchedule.get());
                dataService.billboards.delete(InsertedBillboard.get());
            } else {
                fail("Error fetching schedule");
            }
        } else {
            fail("Error fetching billboard");
        }

    }

    /**
     * Tests getting all schedules from the database.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetAllBillboardSchedules() throws Exception {
        List<Billboard> ControlBillboards = new ArrayList<Billboard>();
        List<Schedule> ControlSchedules = new ArrayList<Schedule>();

        Billboard BillboardOne = new Billboard(
            "Billboard1",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            UserID
        );
        dataService.billboards.insert(BillboardOne);
        ControlBillboards.add(BillboardOne);

        Optional<Billboard> InsertedBillboardOne = dataService.billboards.get(BillboardOne.name);
        if (InsertedBillboardOne.isPresent()) {
            Instant TestDate = Instant.now();
            Schedule TestSchedule = new Schedule(
                InsertedBillboardOne.get().name,
                TestDate,
                1,
                120
            );
            dataService.schedules.insert(TestSchedule);
            ControlSchedules.add(TestSchedule);
        } else {
            fail("Error fetching billboards");
        }

        Billboard BillboardTwo = new Billboard(
            "Billboard2",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            UserID
        );
        dataService.billboards.insert(BillboardTwo);
        ControlBillboards.add(BillboardTwo);

        Optional<Billboard> InsertedBillboardTwo = dataService.billboards.get(BillboardTwo.name);
        if (InsertedBillboardTwo.isPresent()) {
            Instant TestDate = Instant.now();
            Schedule TestSchedule = new Schedule(
                InsertedBillboardTwo.get().name,
                TestDate,
                1,
                120
            );
            dataService.schedules.insert(TestSchedule);
            ControlSchedules.add(TestSchedule);
        } else {
            fail("Error fetching billboards");
        }

        Billboard BillboardThree = new Billboard(
            "Billboard3",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            UserID
        );
        dataService.billboards.insert(BillboardThree);
        ControlBillboards.add(BillboardThree);

        Optional<Billboard> InsertedBillboardThree = dataService.billboards.get(BillboardThree.name);
        if (InsertedBillboardThree.isPresent()) {
            Instant TestDate = Instant.now();
            Schedule TestSchedule = new Schedule(
                InsertedBillboardThree.get().name,
                TestDate,
                1,
                120
            );
            dataService.schedules.insert(TestSchedule);
            ControlSchedules.add(TestSchedule);
        } else {
            fail("Error fetching billboards");
        }

        List<Schedule> ListSchedules = dataService.schedules.getAll();
        assertEquals(
            ControlSchedules.stream().map(schedule -> schedule.startTime.getEpochSecond()).collect(Collectors.toList()),
            ListSchedules.stream().map(schedule -> schedule.startTime.getEpochSecond()).collect(Collectors.toList())
        );

        List<Schedule> DeleteListSchedule = dataService.schedules.getAll();
        List<Schedule> ScheduleDeleteList = new ArrayList<Schedule>(DeleteListSchedule);
        for (Schedule schedule : ScheduleDeleteList) {
            dataService.schedules.delete(schedule);
        }

        List<Billboard> DeleteListBillboard = dataService.billboards.getAll();
        List<Billboard> BillboardDeleteList = new ArrayList<Billboard>(DeleteListBillboard);
        for (Billboard billboard : BillboardDeleteList) {
            dataService.billboards.delete(billboard);
        }
    }

    /**
     * Tests getting a schedule by ID.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetScheduleByID() throws Exception {
        Billboard BillboardOne = new Billboard(
            "Billboard1",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            UserID
        );
        dataService.billboards.insert(BillboardOne);

        Optional<Billboard> InsertedBillboard = dataService.billboards.get(BillboardOne.name);
        if (InsertedBillboard.isPresent()) {
            Instant TestDate = Instant.now();
            Schedule TestSchedule = new Schedule(
                InsertedBillboard.get().name,
                TestDate,
                1,
                120
            );
            dataService.schedules.insert(TestSchedule);
            Optional<Schedule> InsertedSchedule = dataService.schedules.get(dataService.schedules.getAll().get(0).id);
            if (InsertedSchedule.isPresent()) {
                assertEquals(TestDate.getEpochSecond(), InsertedSchedule.get().startTime.getEpochSecond());

                dataService.schedules.delete(InsertedSchedule.get());
                dataService.billboards.delete(InsertedBillboard.get());
            } else {
                fail("Error fetching schedule");
            }
        } else {
            fail("Error fetching billboard");
        }

    }

    /**
     * Tests changing a schedule property in the database.
     *
     * @throws Exception: this exception returns when there is an issue changing data in the database.
     */
    @Test
    public void UpdateExistingSchedule() throws Exception {
        Billboard BillboardOne = new Billboard(
            "Billboard1",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            UserID
        );
        dataService.billboards.insert(BillboardOne);

        Optional<Billboard> InsertedBillboard = dataService.billboards.get(BillboardOne.name);
        if (InsertedBillboard.isPresent()) {
            Instant TestDate = Instant.now();
            Schedule TestSchedule = new Schedule(
                InsertedBillboard.get().name,
                TestDate,
                1,
                120
            );
            dataService.schedules.insert(TestSchedule);

            Optional<Schedule> ChangeSchedule = dataService.schedules.get(dataService.schedules.getAll().get(0).id);
            if (ChangeSchedule.isPresent()) {
                assertEquals(ChangeSchedule.get().startTime.getEpochSecond(), TestDate.getEpochSecond());
                ChangeSchedule.get().duration = 60;
                dataService.schedules.update(ChangeSchedule.get());

                Optional<Schedule> ChangedSchedule = dataService.schedules.get(dataService.schedules.getAll().get(0).id);
                if (ChangedSchedule.isPresent()) {
                    assertEquals(ChangedSchedule.get().duration, 60);

                    Optional<Schedule> DeletingSchedule = dataService.schedules.get(dataService.schedules.getAll().get(0).id);
                    if (DeletingSchedule.isPresent()) {
                        dataService.schedules.delete(DeletingSchedule.get());
                        dataService.billboards.delete(InsertedBillboard.get());
                    } else {
                        fail("Error fetching schedule");
                    }

                } else {
                    fail("Error fetching schedule");
                }
            } else {
                fail("Error fetching schedule");
            }
        } else {
            fail("Error fetching billboard");
        }
    }

    /**
     * Tests deleting a schedule in the database.
     *
     * @throws Exception: this exception returns when there is an issue deleting data in the database.
     */
    @Test
    public void DeleteNewBillboardSchedules() throws Exception {
        Billboard BillboardOne = new Billboard(
            "Billboard1",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            UserID
        );
        dataService.billboards.insert(BillboardOne);

        Optional<Billboard> InsertedBillboard = dataService.billboards.get("Billboard1");
        if (InsertedBillboard.isPresent()) {
            Instant TestDate = Instant.now();
            Schedule TestSchedule = new Schedule(
                InsertedBillboard.get().name,
                TestDate,
                1,
                120
            );
            dataService.schedules.insert(TestSchedule);

            Optional<Schedule> DeleteSchedule = dataService.schedules.get(dataService.schedules.getAll().get(0).id);
            if (DeleteSchedule.isPresent()) {
                dataService.schedules.delete(DeleteSchedule.get());
                Optional<Permissions> DeletedPermission = dataService.permissions.get(DeleteSchedule.get().id);
                assertTrue(DeletedPermission.isEmpty());

                // Cleanup and delete the user
                Optional<Billboard> DeletingBillboard = dataService.billboards.get(InsertedBillboard.get().id);
                if (DeletingBillboard.isPresent()) {
                    dataService.billboards.delete(DeletingBillboard.get());
                } else {
                    fail("Error fetching permissions");
                }

            } else {
                fail("Error fetching permissions");
            }
        } else {
            fail("Error fetching billboard");
        }

    }

    /**
     * Disconnects from the database.
     *
     * @throws Exception: this exception returns when there is an issue disconnecting from the database.
     */
    @AfterAll
    public static void DisconnectDatabase() throws Exception {
        Optional<User> TestUser = dataService.users.get(UserID);
        if (TestUser.isPresent()) {
            dataService.users.delete(TestUser.get());
        }

        dataService.closeConnection();
    }
}
