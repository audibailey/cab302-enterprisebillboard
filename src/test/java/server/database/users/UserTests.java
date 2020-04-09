package server.database.users;

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
 */
public class UserTests {

    // The DataService used by all the tests, essentially a database connection.
    private static DataService dataService;
    List<User> controlUsers = new ArrayList<User>();

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
    public void GetByIdNewUser() throws Exception {

    }

    @Test
    public void GetByIdExistingUser() throws Exception {

    }

    @Test
    public void GetByUsernameNewUser() throws Exception {

    }

    @Test
    public void GetByUsernameExistingUser() throws Exception {

    }

    @Test
    public void InsertNewUser() throws Exception {
        User user = new User(5, "User", "Password", "Salt");
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

    }

    @Test
    public void DeleteExistingUser() throws Exception {

    }


}
