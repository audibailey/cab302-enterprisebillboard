package server.database.users;

import common.models.Billboard;
import common.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.database.DataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the user database handler
 *
 * @author Jamie Martin
 * @author Kevin Huynh
 */
public class UserTests {

    // The DataService used by all the tests, essentially a database connection.
    private static DataService dataService;
    static List<User> controlUsers = new ArrayList<r>();

    /**
     * Connects to the database or a mock database.
     *
     * @throws Exception: this exception returns when there is an issue connecting to the database.
     */
    @BeforeAll
    public static void ConnectToDatabase() throws Exception {
        dataService = new DataService(true);
    }

    @Test
    public void GetUserByID() throws Exception {
        User user = new User(22, "Username", "pass", "salt");
        User user1 = new User(33, "User1", "pass", "salt");
        dataService.users.insert(user);
        dataService.users.insert(user1);
        controlUsers.add(user);
        controlUsers.add(user1);

        Optional<User> GotUser = dataService.users.get(22);
        if (GotUser.isPresent()) {
            // Test the retrieve user ID against the control ID
            assertEquals(GotUser.get().id, 22);

            // Cleanup and delete the user
            dataService.users.delete(GotUser.get());
        } else {
            fail("Error fetching user.");
        }
    }

    @Test
    public void GetAllUsers() throws Exception {
        User user = new User(1, "Username", "pass", "salt");
        User user1 = new User(2, "Username2", "pass", "salt");
        User user2 = new User(3, "Username3", "pass", "salt");
        User user3 = new User(4, "Username4", "pass", "salt");
        dataService.users.insert(user);
        dataService.users.insert(user1);
        dataService.users.insert(user2);
        dataService.users.insert(user3);
        List<User> result = dataService.users.getAll();
        controlUsers.add(user);
        controlUsers.add(user1);
        controlUsers.add(user2);
        controlUsers.add(user3);
        System.out.println(result);
        assertTrue(controlUsers.containsAll(dataService.users.getAll()));

    }

    @Test
    public void GetUserByUsername() throws Exception {
        User user = new User(12, "Username12", "pass", "salt");
        User user1 = new User(13, "User12", "pass", "salt");
        User user2 = new User(14, "AnotherUser", "pass", "salt");
        dataService.users.insert(user);
        dataService.users.insert(user1);
        dataService.users.insert(user2);

        Optional<User> GotUser = dataService.users.get("User12");
        if (GotUser.isPresent()) {
            // Test the retrieve username against the control username
            assertEquals(GotUser.get().username, "User12");
        } else {
            fail("Error fetching user.");
        }

    }

    @Test
    public void GetByUsernameNewUser() throws Exception {

    }

    @Test
    public void GetByUsernameExistingUser() throws Exception {

    }

    @Test
    public void InsertNewUser() throws Exception {
        User user = new User(5, "Jamie", "Password", "Salt");
        dataService.users.insert(user);
        controlUsers.add(user);
        assertTrue(controlUsers.containsAll(dataService.users.getAll()));
    }

    @Test
    public void InsertExistingUser() throws Exception {

    }

    @Test
    public void UpdateNewUser() throws Exception {

    }

    @Test
    public void UpdateExistingUser() throws Exception {

    }

    @Test
    public void DeleteNewUser() throws Exception {
        User user = new User(4, "User5", "Password", "Salt");
        dataService.users.insert(user);

        // Retrieve the testing billboard then delete it
        Optional<User> ToDeleteUser = dataService.users.get(4);
        if (ToDeleteUser.isPresent()) {
            dataService.users.delete(ToDeleteUser.get());

            // Attempt to retrieve the testing billboard and ensure nothing is returned
            Optional<User> DeletedUser = dataService.users.get(4);
            assertTrue(DeletedUser.isEmpty());
        } else {
            fail("Error fetching to be deleted billboard.");
        }
    }

    @Test
    public void DeleteExistingUser() throws Exception {

    }


}
