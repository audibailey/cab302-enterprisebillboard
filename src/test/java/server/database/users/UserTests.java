package server.database.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import common.models.User;
import server.database.DataService;

/**
 * Test class for the user database handler
 *
 * @author Jamie Martin
 * @author Kevin Huynh
 * @author Perdana Bailey
 */
public class UserTests {

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
        dataService.permissions.deleteAll();
        dataService.users.deleteAll();
    }

    /**
     * Tests getting a user from the database using ID.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetUserByID() throws Exception {
        // Create a testing user and upload it to the database
        User TestUser = User.Random();
        dataService.users.insert(TestUser);

        // Fetch the billboard and save the ID
        AtomicInteger TestUserID = new AtomicInteger();
        dataService.users.get(TestUser.username).ifPresent(user -> TestUserID.set(user.id));

        Optional<User> GotUser = dataService.users.get(TestUserID.get());
        if (GotUser.isPresent()) {
            // Test the retrieve user ID against the control ID
            assertEquals(GotUser.get().username, TestUser.username);

            // Cleanup and delete the user
            dataService.users.delete(GotUser.get());
        } else {
            fail("Error fetching user.");
        }
    }


    /**
     * Tests getting a user from the database using user name.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetUserByUsername() throws Exception {
        // Create testing User and insert it into the database
        User user = User.Random();
        dataService.users.insert(user);

        // Retrieve the testing User
        Optional<User> GotUser = dataService.users.get(user.username);
        if (GotUser.isPresent()) {
            // Test the retrieved User username against the control username
            assertEquals(GotUser.get().username, user.username);

            // Cleanup and delete the user
            dataService.users.delete(GotUser.get());
        } else {
            fail("Error fetching billboard.");
        }
    }

    /**
     * Tests getting all users from the database.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetAllUsers() throws Exception {
        // Create some testing users
        User user1 = User.Random();
        User user2 = User.Random();
        User user3 = User.Random();
        User user4 = User.Random();

        // Insert the testing users into the database
        dataService.users.insert(user1);
        dataService.users.insert(user2);
        dataService.users.insert(user3);
        dataService.users.insert(user4);

        // Create the array to test against based on the testing users
        List<User> controlUsers = new ArrayList<User>();
        controlUsers.add(user1);
        controlUsers.add(user2);
        controlUsers.add(user3);
        controlUsers.add(user4);

        // Retrieve the testing users
        List<User> dbUsers = dataService.users.getAll();
        assertEquals(
            controlUsers.stream().map(user -> user.username).collect(Collectors.toList()),
            dbUsers.stream().map(user -> user.username).collect(Collectors.toList())
        );

        // Cleanup and delete all the users
        dataService.users.deleteAll();
    }

    /**
     * Tests adding a user to the database.
     *
     * @throws Exception: this exception returns when there is an issue inserting data into the database.
     */
    @Test
    public void AddUserTest() throws Exception {
        // Create a testing user and insert it into the database
        User user = User.Random();
        dataService.users.insert(user);

        // Retrieve the testing user
        Optional<User> insertedUser = dataService.users.get(user.username);
        if (insertedUser.isPresent()) {
            assertEquals(insertedUser.get().username, user.username);
            dataService.users.delete(insertedUser.get());
        } else {
            fail("Error fetching user.");
        }
    }

    /**
     * Tests changing a users property in the database.
     *
     * @throws Exception: this exception returns when there is an issue changing data in the database.
     */
    @Test
    public void UpdateExistingUser() throws Exception {
        // Create a testing user and insert it into the database
        User user = User.Random();
        dataService.users.insert(user);

        // Retrieve the testing user then update its password
        Optional<User> editUser = dataService.users.get(user.username);
        if (editUser.isPresent()) {
            editUser.get().password = user.password;
            dataService.users.update(editUser.get());
        } else {
            fail("Error fetching testing User.");
        }

        // Retrieve the testing user and test if the password was changed then delete it
        Optional<User> newUser = dataService.users.get(user.username);
        if (newUser.isPresent()) {
            assertEquals(newUser.get().password, editUser.get().password);
            dataService.users.delete(newUser.get());
        } else {
            fail("Error fetching changed User.");
        }
    }

    /**
     * Tests deleting a user in the database.
     *
     * @throws Exception: this exception returns when there is an issue deleting data in the database.
     */
    @Test
    public void DeleteNewUser() throws Exception {
        // Create a testing User
        User user = User.Random();

        // Insert the testing user into the database
        dataService.users.insert(user);

        // Retrieve the testing user then delete it
        Optional<User> toDeleteUser = dataService.users.get(user.username);
        if (toDeleteUser.isPresent()) {
            dataService.users.delete(toDeleteUser.get());

            // Attempt to retrieve the testing user and ensure nothing is returned
            Optional<User> deletedUser = dataService.users.get(user.username);
            assertTrue(deletedUser.isEmpty());
        } else {
            fail("Error fetching to be deleted User.");
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
