package server.database.users;


import common.models.Billboard;
import common.models.Permissions;
import common.models.User;
import server.database.DataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the user database handler
 *
 * @author Jamie Martin
 * @author Kevin Huynh
 * @author Perdana Bailey
 */

public class PermissionsTests {

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

    //Permission testing
    @Test
    public void insertPermissions() throws Exception {
        //Create new permissions list
        User user = new User(69, "Kevin", "Password", "Salt");
        Permissions permissions = new Permissions(69, "Kevin",
            true,
            true,
            false,
            false,
            true);

        dataService.users.insert(user);
        dataService.permissions.insert(permissions);

        Optional<Permissions> insertedPerm = dataService.permissions.get(69);
        if (insertedPerm.isPresent()) {
            assertEquals(insertedPerm.get().username, "Kevin");
            dataService.permissions.delete(insertedPerm.get());
        } else {
            fail("Error fetching permissions");
        }
    }

    @Test
    public void getUserPermissions() throws Exception {
        User user = new User(69, "Kevin", "Password", "Salt");
        Permissions permissions = new Permissions(69, "Kevin",
            true,
            true,
            false,
            false,
            true);
        User user1 = new User(70, "Kevin1", "Password", "Salt");
        Permissions permissions1 = new Permissions(70, "Kevin1",
            true,
            true,
            false,
            false,
            true);
        User user2 = new User(71, "Kevin2", "Password", "Salt");
        Permissions permissions2 = new Permissions(71, "Kevin2",
            true,
            true,
            false,
            false,
            true);

        //Insert user and their permissions into database
        dataService.permissions.insert(permissions);
        dataService.users.insert(user);
        dataService.permissions.insert(permissions1);
        dataService.users.insert(user1);
        dataService.permissions.insert(permissions2);
        dataService.users.insert(user2);

        // Create the array to test against based on the testing billboards
        Permissions result = permissions1;

        AtomicInteger TestPermID = new AtomicInteger();
        dataService.permissions.get("Kevin1").ifPresent(perm -> TestPermID.set(perm.id));

        // Retrieve the testing billboard
        Optional<Permissions> GotPermissions = dataService.permissions.get(TestPermID.get());
        if (GotPermissions.isPresent()) {
            // Test the retrieved billboard name against the control name
            assertEquals(GotPermissions.get(), result);

            // Cleanup and delete the billboard
            dataService.permissions.delete(GotPermissions.get());
        } else {
            fail("Error fetching permission.");
        }
    }

    /**
     * Disconnects from the database.
     *
     * @throws Exception: this exception returns when there is an issue disconnecting from the database.
     */
    @AfterAll
    public static void DisconnectDatabase() throws Exception {
        dataService.closeConnection();
    }
}
