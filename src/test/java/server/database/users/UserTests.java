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
    }

    /**
     * Tests getting a user from the database using ID.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetUserByID() throws Exception {
        // Create a testing user and upload it to the database
        User TestUser = new User("Username1", "pass", "salt");
        dataService.users.insert(TestUser);

        // Fetch the billboard and save the ID
        AtomicInteger TestUserID = new AtomicInteger();
        dataService.users.get("Username1").ifPresent(user -> TestUserID.set(user.id));

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
     * Tests getting a billboard from the database using user name.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetUserByUsername() throws Exception {
        // Create testing User and insert it into the database
        User UserTest = new User("Username1", "pass", "salt");
        dataService.users.insert(UserTest);

        // Retrieve the testing User
        Optional<User> GotUser = dataService.users.get("Username1");
        if (GotUser.isPresent()) {
            // Test the retrieved User username against the control username
            assertEquals(GotUser.get().username, UserTest.username);

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
        User TestUserOne = new User("Username1", "pass", "salt");
        User TestUserTwo = new User("Username2", "pass", "salt");
        User TestUserThree = new User("Username3", "pass", "salt");
        User TestUserFour = new User("Username4", "pass", "salt");

        // Insert the testing users into the database
        dataService.users.insert(TestUserOne);
        dataService.users.insert(TestUserTwo);
        dataService.users.insert(TestUserThree);
        dataService.users.insert(TestUserFour);

        // Create the array to test against based on the testing users
        List<User> ControlUsers = new ArrayList<User>();
        ControlUsers.add(TestUserOne);
        ControlUsers.add(TestUserTwo);
        ControlUsers.add(TestUserThree);
        ControlUsers.add(TestUserFour);

        // Retrieve the testing users
        List<User> ListUsers = dataService.users.getAll();
        assertEquals(
            ControlUsers.stream().map(user -> user.username).collect(Collectors.toList()),
            ListUsers.stream().map(user -> user.username).collect(Collectors.toList())
        );

        // Cleanup and delete all the users
        List<User> DeleteListUsers = dataService.users.getAll();
        List<User> UserDeleteList = new ArrayList<User>(DeleteListUsers);
        for (User user : UserDeleteList) {
            dataService.users.delete(user);
        }
    }

    /**
     * Tests adding a user to the database.
     *
     * @throws Exception: this exception returns when there is an issue inserting data into the database.
     */
    @Test
    public void AddUserTest() throws Exception {
        // Create a testing user and insert it into the database
        User TestUser = new User("Username1", "Password", "Salt");
        dataService.users.insert(TestUser);

        // Retrieve the testing user
        Optional<User> InsertedUser = dataService.users.get("Username1");
        if (InsertedUser.isPresent()) {
            assertEquals(InsertedUser.get().username, TestUser.username);
            dataService.users.delete(InsertedUser.get());
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
        User TestUser = new User("Username1", "Password", "Salt");
        dataService.users.insert(TestUser);

        // Retrieve the testing user then update its password
        Optional<User> EditUser = dataService.users.get("Username1");
        if (EditUser.isPresent()) {
            EditUser.get().password = "Password2";
            dataService.users.update(EditUser.get());
        } else {
            fail("Error fetching testing User.");
        }

        // Retrieve the testing user and test if the password was changed then delete it
        Optional<User> NewUser = dataService.users.get("Username1");
        if (NewUser.isPresent()) {
            assertEquals(NewUser.get().password, EditUser.get().password);
            dataService.users.delete(NewUser.get());
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
        User TestUser = new User("Username1", "Password", "Salt");

        // Insert the testing user into the database
        dataService.users.insert(TestUser);

        // Retrieve the testing user then delete it
        Optional<User> ToDeleteUser = dataService.users.get("Username1");
        if (ToDeleteUser.isPresent()) {
            dataService.users.delete(ToDeleteUser.get());

            // Attempt to retrieve the testing user and ensure nothing is returned
            Optional<User> DeletedUser = dataService.users.get("Username1");
            assertTrue(DeletedUser.isEmpty());
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
