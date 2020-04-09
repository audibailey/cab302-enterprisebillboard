package server.database.users;

import common.models.Billboard;
import common.models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.database.DataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

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

//    @Test
//    public void GetAllUsers() throws Exception {
//        List<User> controlUsers = new ArrayList<User>();
//        User user = new User(1, "Username", "pass", "salt");
//        User user1 = new User(2, "Username2", "pass", "salt");
//        User user2 = new User(3, "Username3", "pass", "salt");
//        User user3 = new User(4, "Username4", "pass", "salt");
//        dataService.users.insert(user);
//        dataService.users.insert(user1);
//        dataService.users.insert(user2);
//        dataService.users.insert(user3);
//        controlUsers.add(user);
//        controlUsers.add(user1);
//        controlUsers.add(user2);
//        controlUsers.add(user3);
//
//        List<User> result = dataService.users.getAll();
//        assertTrue(controlUsers.containsAll(dataService.users.getAll()));
//
//    }
//
//    @Test
//    public void InsertNewUser() throws Exception {
//        List<User> controlUsers = new ArrayList<User>();
//        User user = new User(5, "Jamie", "Password", "Salt");
//        dataService.users.insert(user);
//        controlUsers.add(user);
//        assertTrue(controlUsers.containsAll(dataService.users.getAll()));
//    }
//
//    @Test
//    public void UpdateExistingUser() throws Exception {
//
//    }
//
//    @Test
//    public void DeleteNewUser() throws Exception {
//        List<User> controlUsers = new ArrayList<User>();
//        User user = new User(4, "User5", "Password", "Salt");
//        dataService.users.insert(user);
//
//        // Retrieve the testing billboard then delete it
//        Optional<User> ToDeleteUser = dataService.users.get(4);
//        if (ToDeleteUser.isPresent()) {
//            dataService.users.delete(ToDeleteUser.get());
//
//            // Attempt to retrieve the testing billboard and ensure nothing is returned
//            Optional<User> DeletedUser = dataService.users.get(4);
//            assertTrue(DeletedUser.isEmpty());
//        } else {
//            fail("Error fetching to be deleted billboard.");
//        }
//    }

}
