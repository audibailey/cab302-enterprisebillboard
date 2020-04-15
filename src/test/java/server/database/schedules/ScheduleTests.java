package server.database.schedules;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import common.models.Billboard;
import common.models.Schedule;
import common.utils.RandomFactory;
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
    private static int userId;

    /**
     * Connects to the database or a mock database.
     *
     * @throws Exception: this exception returns when there is an issue connecting to the database.
     */
    @BeforeAll
    public static void ConnectToDatabase() throws Exception {
        dataService = new DataService(true);

        // Create the test user and save its ID
        User masterUser = User.Random();
        dataService.users.insert(masterUser);
        Optional<User> dbUser = dataService.users.get(masterUser.username);
        dbUser.ifPresent(user -> userId = user.id);
    }

    /**
     * Tests adding a billboard and schedule to the database.
     *
     * @throws Exception: this exception returns when there is an issue inserting data into the database.
     */
    @Test
    public void AddBillboardScheduleTest() throws Exception {
       // Create a testing billboard and insert it to the database
        Billboard billboard = Billboard.Random(userId);

        dataService.billboards.insert(billboard);

        // Retrieve the testing Billboard
        Optional<Billboard> insertedBillboard = dataService.billboards.get(billboard.name);
        if (insertedBillboard.isPresent()) {
            // Create a new schedule
            Schedule schedule = Schedule.Random(billboard.name);
            // Insert the new schedule to database
            dataService.schedules.insert(schedule);

            // Retrieve the testing billboard's name
            Optional<Schedule> insertedSchedule = dataService.schedules.get(billboard.name);
            if (insertedSchedule.isPresent()) {
                // Test the retrieve start time against the control start time
                assertEquals(schedule.startTime, insertedSchedule.get().startTime);

                // Cleanup and delete the schedule + billboard
                dataService.schedules.delete(insertedSchedule.get());
                dataService.billboards.delete(insertedBillboard.get());
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
        List<Billboard> controlBillboards = new ArrayList<Billboard>();
        List<Schedule> controlSchedules = new ArrayList<Schedule>();

        // Create a testing billboard and insert it to the database
        Billboard billboard1 = Billboard.Random(userId);

        dataService.billboards.insert(billboard1);
        // Add the first billboard to control list
        controlBillboards.add(billboard1);

        // Retrieve the first testing billboard
        Optional<Billboard> insertedBillboard = dataService.billboards.get(billboard1.name);
        if (insertedBillboard.isPresent()) {
            // Create a new schedule for the new billboard
            Schedule schedule = Schedule.Random(insertedBillboard.get().name);
            // Insert the schedule to the database and add the schedule to the control list
            dataService.schedules.insert(schedule);
            controlSchedules.add(schedule);
        } else {
            fail("Error fetching billboard");
        }

        // Create the second testing billboard and insert it to the database
        Billboard billboard2 = Billboard.Random(userId);

        dataService.billboards.insert(billboard2);
        // Add the second billboard to control lists
        controlBillboards.add(billboard2);

        // Retrieve the second testing billboard
        Optional<Billboard> insertedBillboard2 = dataService.billboards.get(billboard2.name);
        if (insertedBillboard2.isPresent()) {
            // Create the second schedule
            Schedule schedule = Schedule.Random(billboard2.name);
            // Insert the second schedule to the database and add the second schedule to control list
            dataService.schedules.insert(schedule);
            controlSchedules.add(schedule);
        } else {
            fail("Error fetching billboard");
        }

        // Create the third testing billboard and insert it to the database
        Billboard billboard3 = Billboard.Random(userId);

        dataService.billboards.insert(billboard3);
        // Add the third billboard the control list
        controlBillboards.add(billboard3);

        // Retrieve the third testing billboard
        Optional<Billboard> insertedBillboard3 = dataService.billboards.get(billboard3.name);
        if (insertedBillboard3.isPresent()) {
            // Create the third schedule
            Schedule schedule = Schedule.Random(billboard3.name);
            // Insert the third schedule and add the third schedule to control list
            dataService.schedules.insert(schedule);
            controlSchedules.add(schedule);
        } else {
            fail("Error fetching billboard");
        }
        // Get all the schedules and save it to ListSchedules
        List<Schedule> schedules = dataService.schedules.getAll();

        // Retrieve the testing schedules' start times and compare with control lists' results.
        assertEquals(
            controlSchedules.stream().map(schedule -> schedule.startTime.getEpochSecond()).collect(Collectors.toList()),
            schedules.stream().map(schedule -> schedule.startTime.getEpochSecond()).collect(Collectors.toList())
        );

        // Clean up and delete all schedules
        dataService.schedules.deleteAll();
        // Clean up and delete all billboards
        dataService.billboards.deleteAll();
    }

    /**
     * Tests getting a schedule by ID.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetScheduleByID() throws Exception {
        // Create a billboard and insert to the database
        Billboard billboard = Billboard.Random(userId);

        dataService.billboards.insert(billboard);

        // // Retrieve the testing Billboard
        Optional<Billboard> insertedBillboard = dataService.billboards.get(billboard.name);
        if (insertedBillboard.isPresent()) {
            // Create a new schedule
            Schedule schedule = Schedule.Random(insertedBillboard.get().name);
            // Insert the first schedule to the database
            dataService.schedules.insert(schedule);
            // Retrieve the schedule ID
            Optional<Schedule> InsertedSchedule = dataService.schedules.get(dataService.schedules.getAll().get(0).id);
            if (InsertedSchedule.isPresent()) {
                // Test the retrieved time against control time
                assertEquals(schedule.startTime, InsertedSchedule.get().startTime);

                // Clean up and delete the billboard + schedule
                dataService.schedules.delete(InsertedSchedule.get());
                dataService.billboards.delete(insertedBillboard.get());
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
        Billboard billboard = Billboard.Random(userId);
        dataService.billboards.insert(billboard);

        // Retrieve the testing Billboard
        Optional<Billboard> insertedBillboard = dataService.billboards.get(billboard.name);
        if (insertedBillboard.isPresent()) {
            // Create the new schedule
            Schedule schedule = Schedule.Random(insertedBillboard.get().name);
            // Insert the schedule into the database
            dataService.schedules.insert(schedule);

            // Retrieve the testing schedule
            Optional<Schedule> toChangeSchedule = dataService.schedules.get(schedule.billboardName);
            if (toChangeSchedule.isPresent()) {
                // Test the retrieved time against the control time
                assertEquals(toChangeSchedule.get().startTime, schedule.startTime);
                // Change the duration of the schedule
                int randomInt = RandomFactory.Int(60);
                toChangeSchedule.get().duration = randomInt;
                dataService.schedules.update(toChangeSchedule.get());
                // Retrieve the testing schedule after updated
                Optional<Schedule> changedSchedule = dataService.schedules.get(toChangeSchedule.get().id);
                if (changedSchedule.isPresent()) {
                    // Test if the new schedule's duration is the same as our change
                    assertEquals(changedSchedule.get().duration, randomInt);

                    // Clean up and delete schedule + billboard
                    Optional<Schedule> DeletingSchedule = dataService.schedules.get(changedSchedule.get().id);
                    if (DeletingSchedule.isPresent()) {
                        dataService.schedules.delete(DeletingSchedule.get());
                        dataService.billboards.delete(insertedBillboard.get());
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
        Billboard billboard = Billboard.Random(userId);
        dataService.billboards.insert(billboard);

        // Retrieve the testing billboard
        Optional<Billboard> insertedBillboard = dataService.billboards.get(billboard.name);
        if (insertedBillboard.isPresent()) {
            // Create a new schedule
            Schedule schedule = Schedule.Random(insertedBillboard.get().name);
            //Insert the new schedule into the database
            dataService.schedules.insert(schedule);

            // Retrieve the testing schedule
            Optional<Schedule> toDeleteSchedule = dataService.schedules.get(schedule.billboardName);
            if (toDeleteSchedule.isPresent()) {
                // Delete the schedule from the database
                dataService.schedules.delete(toDeleteSchedule.get());
                // Check if the schedule is deleted
                Optional<Schedule> deletedSchedule = dataService.schedules.get(toDeleteSchedule.get().id);
                assertTrue(deletedSchedule.isEmpty());

                // Cleanup and delete the billboard
                Optional<Billboard> toDeleteBillboard = dataService.billboards.get(insertedBillboard.get().id);
                if (toDeleteBillboard.isPresent()) {
                    dataService.billboards.delete(toDeleteBillboard.get());
                } else {
                    fail("Error fetching billboard");
                }
            } else {
                fail("Error fetching schedule");
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
        Optional<User> user = dataService.users.get(userId);
        if (user.isPresent()) {
            dataService.users.delete(user.get());
        }

        dataService.closeConnection();
    }
}
