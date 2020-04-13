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
       // Create a testing billboard and insert it to the database
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

        // Retrieve the testing Billboard
        Optional<Billboard> InsertedBillboard = dataService.billboards.get(BillboardOne.name);

        if (InsertedBillboard.isPresent()) {
            // Get the current time
            Instant TestDate = Instant.now();
            // Create a new schedule
            Schedule TestSchedule = new Schedule(
                InsertedBillboard.get().name,
                TestDate,
                1,
                120
            );
            // Insert the new schedule to database
            dataService.schedules.insert(TestSchedule);

            // Retrieve the testing billboard's name
            Optional<Schedule> InsertedSchedule = dataService.schedules.get(dataService.schedules.getAll().get(0).id);
            if (InsertedSchedule.isPresent()) {
                // Test the retrieve start time against the control start time
                assertEquals(TestDate.getEpochSecond(), InsertedSchedule.get().startTime.getEpochSecond());

                // Cleanup and delete the schedule + billboard
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
        // Create 2 control lists to compare with the test functions.
        List<Billboard> ControlBillboards = new ArrayList<Billboard>();
        List<Schedule> ControlSchedules = new ArrayList<Schedule>();

        // Create a testing billboard and insert it to the database
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
        // Add the first billboard to control list
        ControlBillboards.add(BillboardOne);

        // Retrieve the first testing billboard
        Optional<Billboard> InsertedBillboardOne = dataService.billboards.get(BillboardOne.name);
        if (InsertedBillboardOne.isPresent()) {
            // Get the current time when create the first schedule
            Instant TestDate = Instant.now();
            // Create a new schedule for the new billboard
            Schedule TestSchedule = new Schedule(
                InsertedBillboardOne.get().name,
                TestDate,
                1,
                120
            );
            // Insert the schedule to the database and add the schedule to the control list
            dataService.schedules.insert(TestSchedule);
            ControlSchedules.add(TestSchedule);
        } else {
            fail("Error fetching billboards");
        }

        // Create the second testing billboard and insert it to the database
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
        // Add the second billboard to control lists
        ControlBillboards.add(BillboardTwo);

        // Retrieve the second testing billboard
        Optional<Billboard> InsertedBillboardTwo = dataService.billboards.get(BillboardTwo.name);
        if (InsertedBillboardTwo.isPresent()) {
            // Get the current time when creating the second schedule
            Instant TestDate = Instant.now();
            // Create the second schedule
            Schedule TestSchedule = new Schedule(
                InsertedBillboardTwo.get().name,
                TestDate,
                1,
                120
            );
            // Insert the second schedule to the database and add the second schedule to control list
            dataService.schedules.insert(TestSchedule);
            ControlSchedules.add(TestSchedule);
        } else {
            fail("Error fetching billboards");
        }

        // Create the third testing billboard and insert it to the database
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
        // Add the third billboard the control list
        ControlBillboards.add(BillboardThree);

        // Retrieve the third testing billboard
        Optional<Billboard> InsertedBillboardThree = dataService.billboards.get(BillboardThree.name);
        if (InsertedBillboardThree.isPresent()) {
            // Get the current time when creating the third schedule
            Instant TestDate = Instant.now();
            // Create the third schedule
            Schedule TestSchedule = new Schedule(
                InsertedBillboardThree.get().name,
                TestDate,
                1,
                120
            );
            // Insert the third schedule and add the third schedule to control list
            dataService.schedules.insert(TestSchedule);
            ControlSchedules.add(TestSchedule);
        } else {
            fail("Error fetching billboards");
        }
        // Get all the schedules and save it to ListSchedules
        List<Schedule> ListSchedules = dataService.schedules.getAll();

        // Retrieve the testing schedules' start times and compare with control lists' results.
        assertEquals(
            ControlSchedules.stream().map(schedule -> schedule.startTime.getEpochSecond()).collect(Collectors.toList()),
            ListSchedules.stream().map(schedule -> schedule.startTime.getEpochSecond()).collect(Collectors.toList())
        );

        // Clean up and delete all schedules
        List<Schedule> DeleteListSchedule = dataService.schedules.getAll();
        List<Schedule> ScheduleDeleteList = new ArrayList<Schedule>(DeleteListSchedule);
        for (Schedule schedule : ScheduleDeleteList) {
            dataService.schedules.delete(schedule);
        }

        // Clean up and delete all billboards
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
        // Create a billboard and insert to the database
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

        // // Retrieve the testing Billboard
        Optional<Billboard> InsertedBillboard = dataService.billboards.get(BillboardOne.name);

        if (InsertedBillboard.isPresent()) {
            // Get the current time when creating the schedule
            Instant TestDate = Instant.now();
            // Create a new schedule
            Schedule TestSchedule = new Schedule(
                InsertedBillboard.get().name,
                TestDate,
                1,
                120
            );
            // Insert the first schedule to the database
            dataService.schedules.insert(TestSchedule);
            // Retrieve the schedule ID
            Optional<Schedule> InsertedSchedule = dataService.schedules.get(dataService.schedules.getAll().get(0).id);
            if (InsertedSchedule.isPresent()) {
                // Test the retrieved ID against control ID
                assertEquals(TestDate.getEpochSecond(), InsertedSchedule.get().startTime.getEpochSecond());

                // Clean up and delete the billboard + schedule
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
        // Create testing Billboard and insert it into the database
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

        // Retrieve the testing Billboard
        Optional<Billboard> InsertedBillboard = dataService.billboards.get(BillboardOne.name);
        if (InsertedBillboard.isPresent()) {
            // Get the current time when creating schedule
            Instant TestDate = Instant.now();
            // Create the new schedule
            Schedule TestSchedule = new Schedule(
                InsertedBillboard.get().name,
                TestDate,
                1,
                120
            );
            // Insert the schedule into the database
            dataService.schedules.insert(TestSchedule);

            // Retrieve the testing schedule
            Optional<Schedule> ChangeSchedule = dataService.schedules.get(dataService.schedules.getAll().get(0).id);
            if (ChangeSchedule.isPresent()) {
                // Test the retrieved schedule against the control schedule
                assertEquals(ChangeSchedule.get().startTime.getEpochSecond(), TestDate.getEpochSecond());
                // Change the duration of the schedule
                ChangeSchedule.get().duration = 60;
                dataService.schedules.update(ChangeSchedule.get());
                // Retrieve the testing schedule after updated
                Optional<Schedule> ChangedSchedule = dataService.schedules.get(dataService.schedules.getAll().get(0).id);
                if (ChangedSchedule.isPresent()) {
                    // Test if the new schedule's duration is the same as our change
                    assertEquals(ChangedSchedule.get().duration, 60);

                    // Clean up and delete schedule + billboard
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
        // Create a new billboard and insert into the database
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

        // Retrieve the testing billboard
        Optional<Billboard> InsertedBillboard = dataService.billboards.get("Billboard1");
        if (InsertedBillboard.isPresent()) {
            // Get the current time when creating schedule
            Instant TestDate = Instant.now();
            // Create a new schedule
            Schedule TestSchedule = new Schedule(
                InsertedBillboard.get().name,
                TestDate,
                1,
                120
            );
            //Insert the new schedule into the database
            dataService.schedules.insert(TestSchedule);

            // Retrieve the testing schedule
            Optional<Schedule> DeleteSchedule = dataService.schedules.get(dataService.schedules.getAll().get(0).id);
            if (DeleteSchedule.isPresent()) {
                // Delete the schedule from the database
                dataService.schedules.delete(DeleteSchedule.get());
                // Check if the schedule is deleted
                Optional<Permissions> DeletedPermission = dataService.permissions.get(DeleteSchedule.get().id);
                assertTrue(DeletedPermission.isEmpty());

                // Cleanup and delete the billboard
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
