package server.database.billboards;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
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

    /**
     * Connects to the database or a mock database.
     *
     * @throws Exception: this exception returns when there is an issue connecting to the database.
     */
    @BeforeAll
    public static void ConnectToDatabase() throws Exception {
        dataService = new DataService(true);
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
            1
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
            1
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
            1
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
            1
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
        Optional<List<Billboard>> ListBillboards = dataService.billboards.getAll(false);
        ListBillboards.ifPresentOrElse(billboards -> {
                // Test the retrieved billboards names against the control billboards names
                assertEquals(
                    ControlArray.stream().map(billboard -> billboard.name).collect(Collectors.toList()),
                    ListBillboards.get().stream().map(billboard -> billboard.name).collect(Collectors.toList())
                );
            }, Assertions::fail
        );

        // Cleanup and delete all the billboards
        Optional<List<Billboard>> DeleteListBillboards = dataService.billboards.getAll();
        List<Billboard> ListBillboardsDeleted;
        if (DeleteListBillboards.isPresent()) {
            ListBillboardsDeleted = new ArrayList<>(DeleteListBillboards.get());
            for (Billboard billboard : ListBillboardsDeleted) {
                dataService.billboards.delete(billboard);
            }
        } else {
            fail("Error cleaning up.");
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
            1
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
            1
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
            1
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
            1
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
        Optional<List<Billboard>> ListBillboards = dataService.billboards.getAll(true);
        ListBillboards.ifPresentOrElse(billboards -> {
                // Test the retrieved billboards names against the control billboards names
                assertEquals(
                    ControlArray.stream().map(billboard -> billboard.name).collect(Collectors.toList()),
                    ListBillboards.get().stream().map(billboard -> billboard.name).collect(Collectors.toList())
                );
            }, Assertions::fail
        );

        // Cleanup and delete all the billboards
        Optional<List<Billboard>> DeleteListBillboards = dataService.billboards.getAll();
        List<Billboard> ListBillboardsDeleted;
        if (DeleteListBillboards.isPresent()) {
            ListBillboardsDeleted = new ArrayList<>(DeleteListBillboards.get());
            for (Billboard billboard : ListBillboardsDeleted) {
                dataService.billboards.delete(billboard);
            }
        } else {
            fail("Error cleaning up.");
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
            1,
            "Billboard1",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            1
        );
        Billboard BillboardTwo = new Billboard(
            2,
            "Billboard2",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            true,
            1
        );
        Billboard BillboardThree = new Billboard(
            3,
            "Billboard3",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            1
        );
        Billboard BillboardFour = new Billboard(
            4,
            "Billboard4",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            1
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
        Optional<List<Billboard>> ListBillboards = dataService.billboards.getAll();
        ListBillboards.ifPresentOrElse(billboards -> {
                // Test the retrieved billboards names against the control billboards names
                assertEquals(
                    ControlArray.stream().map(billboard -> billboard.name).collect(Collectors.toList()),
                    ListBillboards.get().stream().map(billboard -> billboard.name).collect(Collectors.toList())
                );
            }, Assertions::fail
        );

        // Cleanup and delete all the billboards
        Optional<List<Billboard>> DeleteListBillboards = dataService.billboards.getAll();
        List<Billboard> ListBillboardsDeleted;
        if (DeleteListBillboards.isPresent()) {
            ListBillboardsDeleted = new ArrayList<>(DeleteListBillboards.get());
            for (Billboard billboard : ListBillboardsDeleted) {
                dataService.billboards.delete(billboard);
            }
        } else {
            fail("Error cleaning up.");
        }
    }

    /**
     * Tests getting a billboard from the database.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetBillboardNameTest() throws Exception {
        // Create a testing billboard
        Billboard TestBillboard = new Billboard(
            "BillboardOne",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            1
        );

        // Insert the testing billboard into the database
        dataService.billboards.insert(TestBillboard);

        // Retrieve the testing billboard
        Optional<Billboard> GotBillboard = dataService.billboards.get("BillboardOne");
        if (GotBillboard.isPresent()) {
            // Test the retrieved billboard name against the control name
            assertEquals(GotBillboard.get().name, "BillboardOne");

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
            "BillboardOne",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            1
        );

        // Insert the testing billboard into the database
        dataService.billboards.insert(TestBillboard);

        // Retrieve the testing billboard
        Optional<Billboard> InsertedBillboard = dataService.billboards.get("BillboardOne");
        if (InsertedBillboard.isPresent()) {
            assertEquals(InsertedBillboard.get().name, "BillboardOne");
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
            "BillboardOne",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            1
        );

        // Insert the testing billboard into the database
        dataService.billboards.insert(TestBillboard);

        // Retrieve the testing billboard then update its name
        Optional<Billboard> EditBillboard = dataService.billboards.get("BillboardOne");
        if (EditBillboard.isPresent()) {
            EditBillboard.get().name = "BillboardTwo";
            dataService.billboards.update(EditBillboard.get());
        } else {
            fail("Error fetching testing billboard.");
        }

        // Retrieve the testing billboard and test if the name was changed then delete it
        Optional<Billboard> NewBillboard = dataService.billboards.get("BillboardTwo");
        if (NewBillboard.isPresent()) {
            assertEquals(NewBillboard.get().name, "BillboardTwo");
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
            "BillboardOne",
            "Test Message",
            "blue",
            "test".getBytes(),
            "green",
            "Test Information",
            "red",
            false,
            1
        );

        // Insert the testing billboard into the database
        dataService.billboards.insert(TestBillboard);

        // Retrieve the testing billboard then delete it
        Optional<Billboard> ToDeleteBillboard = dataService.billboards.get("BillboardOne");
        if (ToDeleteBillboard.isPresent()) {
            dataService.billboards.delete(ToDeleteBillboard.get());

            // Attempt to retrieve the testing billboard and ensure nothing is returned
            Optional<Billboard> DeletedBillboard = dataService.billboards.get("BillboardOne");
            assertTrue(DeletedBillboard.isEmpty());
        } else {
            fail("Error fetching to be deleted billboard.");
        }
    }
}
