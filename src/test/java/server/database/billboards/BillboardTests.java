package server.database.billboards;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import common.models.User;
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
     * Tests getting all billboards from the database that aren't locked.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetUnlockedBillboardsTest() throws Exception {
        // Create some testing billboards
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
        Billboard BillboardTwo = new Billboard(
            "Billboard2",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            true,
            UserID
        );
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
        Billboard BillboardFour = new Billboard(
            "Billboard4",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            UserID
        );

        // Create the array to test against based on the testing billboards
        List<Billboard> ControlArray = new ArrayList<Billboard>();
        ControlArray.add(BillboardOne);
        ControlArray.add(BillboardThree);
        ControlArray.add(BillboardFour);

        // Insert the testing billboards into the database
        dataService.billboards.insert(BillboardOne);
        dataService.billboards.insert(BillboardTwo);
        dataService.billboards.insert(BillboardThree);
        dataService.billboards.insert(BillboardFour);

        // Retrieve the testing billboards that aren't locked
        List<Billboard> ListBillboards = dataService.billboards.getAll(false);
        assertEquals(
            ControlArray.stream().map(billboard -> billboard.name).collect(Collectors.toList()),
            ListBillboards.stream().map(billboard -> billboard.name).collect(Collectors.toList())
        );

        // Cleanup and delete all the billboards
        List<Billboard> DeleteListBillboards = dataService.billboards.getAll();
        List<Billboard> BillboardDeleteList = new ArrayList<Billboard>(DeleteListBillboards);
        for (Billboard billboard : BillboardDeleteList) {
            dataService.billboards.delete(billboard);
        }

    }

    /**
     * Tests getting all billboards from the database that are locked.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetLockedBillboardsTest() throws Exception {
        // Create some testing billboards
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
        Billboard BillboardTwo = new Billboard(
            "Billboard2",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            true,
            UserID
        );
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
        Billboard BillboardFour = new Billboard(
            "Billboard4",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            UserID
        );

        // Create the array to test against based on the testing billboards
        List<Billboard> ControlArray = new ArrayList<Billboard>();
        ControlArray.add(BillboardTwo);

        // Insert the testing billboards into the database
        dataService.billboards.insert(BillboardOne);
        dataService.billboards.insert(BillboardTwo);
        dataService.billboards.insert(BillboardThree);
        dataService.billboards.insert(BillboardFour);

        // Retrieve the testing billboards that are locked
        List<Billboard> ListBillboards = dataService.billboards.getAll(true);

        // Test the retrieved billboards names against the control billboards names
        assertEquals(
            ControlArray.stream().map(billboard -> billboard.name).collect(Collectors.toList()),
            ListBillboards.stream().map(billboard -> billboard.name).collect(Collectors.toList())
        );

        // Cleanup and delete all the billboards
        List<Billboard> DeleteListBillboards = dataService.billboards.getAll();
        List<Billboard> BillboardDeleteList = new ArrayList<Billboard>(DeleteListBillboards);
        for (Billboard billboard : BillboardDeleteList) {
            dataService.billboards.delete(billboard);
        }
    }


    /**
     * Tests getting all billboards from the database.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetAllBillboardsTest() throws Exception {
        // Create some testing billboards
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
        Billboard BillboardTwo = new Billboard(
            "Billboard2",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            true,
            UserID
        );
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
        Billboard BillboardFour = new Billboard(
            "Billboard4",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            UserID
        );

        // Create the array to test against based on the testing billboards
        List<Billboard> ControlArray = new ArrayList<Billboard>();
        ControlArray.add(BillboardOne);
        ControlArray.add(BillboardTwo);
        ControlArray.add(BillboardThree);
        ControlArray.add(BillboardFour);

        // Insert the testing billboards into the database
        dataService.billboards.insert(BillboardOne);
        dataService.billboards.insert(BillboardTwo);
        dataService.billboards.insert(BillboardThree);
        dataService.billboards.insert(BillboardFour);

        // Retrieve the testing billboards
        List<Billboard> ListBillboards = dataService.billboards.getAll();

        // Test the retrieved billboards names against the control billboards names
        assertEquals(
            ControlArray.stream().map(billboard -> billboard.name).collect(Collectors.toList()),
            ListBillboards.stream().map(billboard -> billboard.name).collect(Collectors.toList())
        );

        // Cleanup and delete all the billboards
        List<Billboard> DeleteListBillboards = dataService.billboards.getAll();
        List<Billboard> BillboardDeleteList = new ArrayList<Billboard>(DeleteListBillboards);
        for (Billboard billboard : BillboardDeleteList) {
            dataService.billboards.delete(billboard);
        }
    }

    /**
     * Tests getting a billboard from the database using billboard name.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetBillboardNameTest() throws Exception {
        // Create a testing billboard
        Billboard TestBillboard = new Billboard(
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

        // Insert the testing billboard into the database
        dataService.billboards.insert(TestBillboard);

        // Retrieve the testing billboard
        Optional<Billboard> GotBillboard = dataService.billboards.get("Billboard1");
        if (GotBillboard.isPresent()) {
            // Test the retrieved billboard name against the control name
            assertEquals(GotBillboard.get().name, "Billboard1");

            // Cleanup and delete the billboard
            dataService.billboards.delete(GotBillboard.get());
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
        Billboard TestBillboard = new Billboard(
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
        dataService.billboards.insert(TestBillboard);

        // Fetch the billboard and save the ID
        AtomicInteger TestBillboardID = new AtomicInteger();
        dataService.billboards.get("Billboard1").ifPresent(billboard -> TestBillboardID.set(billboard.id));

        // Retrieve the testing billboard
        Optional<Billboard> GotBillboard = dataService.billboards.get(TestBillboardID.get());
        if (GotBillboard.isPresent()) {
            // Test the retrieved billboard name against the control name
            assertEquals(GotBillboard.get().name, "Billboard1");

            // Cleanup and delete the billboard
            dataService.billboards.delete(GotBillboard.get());
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
        Billboard TestBillboard = new Billboard(
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

        // Insert the testing billboard into the database
        dataService.billboards.insert(TestBillboard);

        // Retrieve the testing billboard
        Optional<Billboard> InsertedBillboard = dataService.billboards.get("Billboard1");
        if (InsertedBillboard.isPresent()) {
            assertEquals(InsertedBillboard.get().name, "Billboard1");
            dataService.billboards.delete(InsertedBillboard.get());
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
        Billboard TestBillboard = new Billboard(
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

        // Insert the testing billboard into the database
        dataService.billboards.insert(TestBillboard);

        // Retrieve the testing billboard then update its name
        Optional<Billboard> EditBillboard = dataService.billboards.get("Billboard1");
        if (EditBillboard.isPresent()) {
            EditBillboard.get().name = "Billboard2";
            dataService.billboards.update(EditBillboard.get());
        } else {
            fail("Error fetching testing billboard.");
        }

        // Retrieve the testing billboard and test if the name was changed then delete it
        Optional<Billboard> NewBillboard = dataService.billboards.get("Billboard2");
        if (NewBillboard.isPresent()) {
            assertEquals(NewBillboard.get().name, "Billboard2");
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
        Billboard TestBillboard = new Billboard(
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

        // Insert the testing billboard into the database
        dataService.billboards.insert(TestBillboard);

        // Retrieve the testing billboard then delete it
        Optional<Billboard> ToDeleteBillboard = dataService.billboards.get("Billboard1");
        if (ToDeleteBillboard.isPresent()) {
            dataService.billboards.delete(ToDeleteBillboard.get());

            // Attempt to retrieve the testing billboard and ensure nothing is returned
            Optional<Billboard> DeletedBillboard = dataService.billboards.get("Billboard1");
            assertTrue(DeletedBillboard.isEmpty());
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
        Optional<User> TestUser = dataService.users.get(UserID);
        if (TestUser.isPresent()) {
            dataService.users.delete(TestUser.get());
        }

        dataService.closeConnection();
    }
}
