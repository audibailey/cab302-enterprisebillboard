package server.database.billboards;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import common.models.User;
import common.utils.RandomFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import server.database.DataService;
import common.models.Billboard;

/**
 * Test class for the billboard database handler
 *
 * @author Perdana Bailey
 */
public class BillboardTests {

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
        dataService.billboards.deleteAll();
        dataService.users.deleteAll();

        // Create the test user and save its ID
        User masterUser = User.Random();
        dataService.users.insert(masterUser);
        Optional<User> dbUser = dataService.users.get(masterUser.username);
        dbUser.ifPresent(user -> userId = user.id);
    }

    /**
     * Tests getting all billboards from the database that aren't locked.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetUnlockedBillboardsTest() throws Exception {
        // Create some testing billboards
        Billboard billboard1 = Billboard.Random(userId);
        billboard1.locked = false;
        Billboard billboard2 = Billboard.Random(userId);
        billboard2.locked = true;
        Billboard billboard3 = Billboard.Random(userId);
        billboard3.locked = false;
        Billboard billboard4 = Billboard.Random(userId);
        billboard4.locked = false;

        // Create the array to test against based on the testing billboards
        List<Billboard> control = new ArrayList<Billboard>();
        control.add(billboard1);
        control.add(billboard3);
        control.add(billboard4);

        // Insert the testing billboards into the database
        dataService.billboards.insert(billboard1);
        dataService.billboards.insert(billboard2);
        dataService.billboards.insert(billboard3);
        dataService.billboards.insert(billboard4);

        // Retrieve the testing billboards that aren't locked
        List<Billboard> billboards = dataService.billboards.getAll(false);
        assertEquals(
            control.stream().map(billboard -> billboard.name).collect(Collectors.toList()),
            billboards.stream().map(billboard -> billboard.name).collect(Collectors.toList())
        );

        // Cleanup and delete all the billboards
        dataService.billboards.deleteAll();
    }

    /**
     * Tests getting all billboards from the database that are locked.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetLockedBillboardsTest() throws Exception {
        // Create some testing billboards
        Billboard billboard1 = Billboard.Random(userId);
        billboard1.locked = false;
        Billboard billboard2 = Billboard.Random(userId);
        billboard2.locked = true;
        Billboard billboard3 = Billboard.Random(userId);
        billboard3.locked = false;
        Billboard billboard4 = Billboard.Random(userId);
        billboard4.locked = false;

        // Create the array to test against based on the testing billboards
        List<Billboard> control = new ArrayList<Billboard>();
        control.add(billboard2);

        // Insert the testing billboards into the database
        dataService.billboards.insert(billboard1);
        dataService.billboards.insert(billboard2);
        dataService.billboards.insert(billboard3);
        dataService.billboards.insert(billboard4);

        // Retrieve the testing billboards that are locked
        List<Billboard> billboards = dataService.billboards.getAll(true);

        // Test the retrieved billboards names against the control billboards names
        assertEquals(
            control.stream().map(billboard -> billboard.name).collect(Collectors.toList()),
            billboards.stream().map(billboard -> billboard.name).collect(Collectors.toList())
        );

        // Cleanup and delete all the billboards
        dataService.billboards.deleteAll();
    }


    /**
     * Tests getting all billboards from the database.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetAllBillboardsTest() throws Exception {
        // Create some testing billboards
        Billboard billboard1 = Billboard.Random(userId);
        Billboard billboard2 = Billboard.Random(userId);
        Billboard billboard3 = Billboard.Random(userId);
        Billboard billboard4 = Billboard.Random(userId);

        // Create the array to test against based on the testing billboards
        List<Billboard> control = new ArrayList<Billboard>();
        control.add(billboard1);
        control.add(billboard2);
        control.add(billboard3);
        control.add(billboard4);

        // Insert the testing billboards into the database
        dataService.billboards.insert(billboard1);
        dataService.billboards.insert(billboard2);
        dataService.billboards.insert(billboard3);
        dataService.billboards.insert(billboard4);

        // Retrieve the testing billboards
        List<Billboard> billboards = dataService.billboards.getAll();

        // Test the retrieved billboards names against the control billboards names
        assertEquals(
            control.stream().map(billboard -> billboard.name).collect(Collectors.toList()),
            billboards.stream().map(billboard -> billboard.name).collect(Collectors.toList())
        );

        // Cleanup and delete all the billboards
        dataService.billboards.deleteAll();
    }

    /**
     * Tests getting a billboard from the database using billboard name.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetBillboardNameTest() throws Exception {
        // Create a testing billboard
        Billboard billboard = Billboard.Random(userId);

        // Insert the testing billboard into the database
        dataService.billboards.insert(billboard);

        // Retrieve the testing billboard
        Optional<Billboard> gotBillboard = dataService.billboards.get(billboard.name);
        if (gotBillboard.isPresent()) {
            // Test the retrieved billboard name against the control name
            assertEquals(gotBillboard.get().name, billboard.name);

            // Cleanup and delete the billboard
            dataService.billboards.delete(gotBillboard.get());
        } else {
            fail("Error fetching billboard.");
        }

    }

    /**
     * Tests getting a billboard from the database using ID.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetBillboardIDTest() throws Exception {
        // Create a testing billboard and insert it into the database
        Billboard billboard = Billboard.Random(userId);
        dataService.billboards.insert(billboard);

        // Fetch the billboard and save the ID
        AtomicInteger billboardId = new AtomicInteger();
        dataService.billboards.get(billboard.name).ifPresent(b -> billboardId.set(b.id));

        // Retrieve the testing billboard
        Optional<Billboard> gotBillboard = dataService.billboards.get(billboardId.get());
        if (gotBillboard.isPresent()) {
            // Test the retrieved billboard name against the control name
            assertEquals(gotBillboard.get().name, billboard.name);

            // Cleanup and delete the billboard
            dataService.billboards.delete(gotBillboard.get());
        } else {
            fail("Error fetching billboard.");
        }
    }

    /**
     * Tests adding a billboard to the database.
     *
     * @throws Exception: this exception returns when there is an issue inserting data into the database.
     */
    @Test
    public void AddBillboardTest() throws Exception {
        // Create a testing billboard
        Billboard billboard = Billboard.Random(userId);

        // Insert the testing billboard into the database
        dataService.billboards.insert(billboard);

        // Retrieve the testing billboard
        Optional<Billboard> insertedBillboard = dataService.billboards.get(billboard.name);
        if (insertedBillboard.isPresent()) {
            assertEquals(insertedBillboard.get().name, billboard.name);
            dataService.billboards.delete(insertedBillboard.get());
        } else {
            fail("Error fetching billboard.");
        }
    }

    /**
     * Tests changing a billboards property in the database.
     *
     * @throws Exception: this exception returns when there is an issue changing data in the database.
     */
    @Test
    public void UpdateBillboardTest() throws Exception {
        // Create a testing billboard
        Billboard billboard = Billboard.Random(userId);
        String newName = RandomFactory.String();

        // Insert the testing billboard into the database
        dataService.billboards.insert(billboard);

        // Retrieve the testing billboard then update its name
        Optional<Billboard> editBillboard = dataService.billboards.get(billboard.name);
        if (editBillboard.isPresent()) {
            editBillboard.get().name = newName;

            dataService.billboards.update(editBillboard.get());
        } else {
            fail("Error fetching testing billboard.");
        }

        // Retrieve the testing billboard and test if the name was changed then delete it
        Optional<Billboard> NewBillboard = dataService.billboards.get(newName);
        if (NewBillboard.isPresent()) {
            assertEquals(NewBillboard.get().name, newName);
            dataService.billboards.delete(NewBillboard.get());
        } else {
            fail("Error fetching changed billboard.");
        }
    }

    /**
     * Tests deleting a billboard in the database.
     *
     * @throws Exception: this exception returns when there is an issue deleting data in the database.
     */
    @Test
    public void DeleteBillboardTest() throws Exception {
        // Create a testing billboard
        Billboard billboard = Billboard.Random(userId);

        // Insert the testing billboard into the database
        dataService.billboards.insert(billboard);

        // Retrieve the testing billboard then delete it
        Optional<Billboard> toDeleteBillboard = dataService.billboards.get(billboard.name);
        if (toDeleteBillboard.isPresent()) {
            dataService.billboards.delete(toDeleteBillboard.get());

            // Attempt to retrieve the testing billboard and ensure nothing is returned
            Optional<Billboard> deletedBillboard = dataService.billboards.get(billboard.name);
            assertTrue(deletedBillboard.isEmpty());
        } else {
            fail("Error fetching to be deleted billboard.");
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
