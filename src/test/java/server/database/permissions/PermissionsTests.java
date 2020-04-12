package server.database.permissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import common.models.Permissions;
import common.models.User;
import server.database.DataService;

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

    /**
     * Tests adding a user and permissions to the database.
     *
     * @throws Exception: this exception returns when there is an issue inserting data into the database.
     */
    @Test
    public void AddUserPermissionsTest() throws Exception {
        //Create new permissions list
        User TestUser = new User("Username1", "Password", "Salt");
        dataService.users.insert(TestUser);

        Optional<User> InsertedUser = dataService.users.get(TestUser.username);
        if (InsertedUser.isPresent()) {
            Permissions TestPermissions = new Permissions(InsertedUser.get().id, InsertedUser.get().username,
                true,
                true,
                false,
                false,
                true);
            dataService.permissions.insert(TestPermissions);
            Optional<Permissions> InsertedPermissions = dataService.permissions.get(InsertedUser.get().id);
            if (InsertedPermissions.isPresent()) {
                assertEquals(InsertedPermissions.get().canEditBillboard, TestPermissions.canEditBillboard);

                dataService.permissions.delete(InsertedPermissions.get());
                dataService.users.delete(InsertedUser.get());
            } else {
                fail("Error fetching permissions");
            }
        } else {
            fail("Error fetching permissions");
        }

    }

    /**
     * Tests getting all user permissions from the database.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetAllUsersPermissions() throws Exception {
        List<User> ControlUsers = new ArrayList<User>();
        List<Permissions> ControlPermissions = new ArrayList<Permissions>();

        User TestUser1 = new User("Username1", "Password", "Salt");
        dataService.users.insert(TestUser1);
        ControlUsers.add(TestUser1);

        Optional<User> InsertedUser1 = dataService.users.get(TestUser1.username);
        if (InsertedUser1.isPresent()) {
            Permissions TestPermissions = new Permissions(InsertedUser1.get().id, InsertedUser1.get().username,
                true,
                true,
                false,
                false,
                true);
            dataService.permissions.insert(TestPermissions);
            ControlPermissions.add(TestPermissions);
        } else {
            fail("Error fetching permissions");
        }

        User TestUser2 = new User("Username2", "Password", "Salt");
        dataService.users.insert(TestUser2);
        ControlUsers.add(TestUser2);

        Optional<User> InsertedUser2 = dataService.users.get(TestUser2.username);
        if (InsertedUser2.isPresent()) {
            Permissions TestPermissions = new Permissions(InsertedUser2.get().id, InsertedUser2.get().username,
                true,
                true,
                false,
                true,
                false);
            dataService.permissions.insert(TestPermissions);
            ControlPermissions.add(TestPermissions);
        } else {
            fail("Error fetching permissions");
        }

        User TestUser3 = new User("Username3", "Password", "Salt");
        dataService.users.insert(TestUser3);
        ControlUsers.add(TestUser3);

        Optional<User> InsertedUser3 = dataService.users.get(TestUser3.username);
        if (InsertedUser3.isPresent()) {
            Permissions TestPermissions = new Permissions(InsertedUser3.get().id, InsertedUser3.get().username,
                true,
                true,
                false,
                false,
                true);
            dataService.permissions.insert(TestPermissions);
            ControlPermissions.add(TestPermissions);
        } else {
            fail("Error fetching permissions");
        }

        List<Permissions> ListPermissions = dataService.permissions.getAll();
        assertEquals(
            ControlPermissions.stream().map(user -> user.username).collect(Collectors.toList()),
            ListPermissions.stream().map(user -> user.username).collect(Collectors.toList())
        );

        assertEquals(
            ControlPermissions.stream().map(user -> user.canViewBillboard).collect(Collectors.toList()),
            ListPermissions.stream().map(user -> user.canViewBillboard).collect(Collectors.toList())
        );

        List<Permissions> DeleteListPermissions = dataService.permissions.getAll();
        List<Permissions> PermissionsDeleteList = new ArrayList<Permissions>(DeleteListPermissions);
        for (Permissions perms : PermissionsDeleteList) {
            dataService.permissions.delete(perms);
        }

        List<User> DeleteListUsers = dataService.users.getAll();
        List<User> UserDeleteList = new ArrayList<User>(DeleteListUsers);
        for (User user : UserDeleteList) {
            dataService.users.delete(user);
        }

    }

    /**
     * Tests getting a users permissions from the database using the user name.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetUserByUsername() throws Exception {
        User TestUser1 = new User("Username1", "Password", "Salt");
        dataService.users.insert(TestUser1);

        Optional<User> InsertedUser = dataService.users.get(TestUser1.username);
        if (InsertedUser.isPresent()) {
            Permissions TestPermissions = new Permissions(InsertedUser.get().id, InsertedUser.get().username,
                true,
                true,
                false,
                false,
                true);
            dataService.permissions.insert(TestPermissions);
        } else {
            fail("Error fetching permissions");
        }

        // Retrieve the testing User
        Optional<Permissions> GotUser = dataService.permissions.get(InsertedUser.get().username);
        if (GotUser.isPresent()) {
            // Test the retrieved User username against the control username
            assertTrue(GotUser.get().canEditBillboard);


            // Cleanup and delete the user
            Optional<User> DeletingUser = dataService.users.get(GotUser.get().username);
            if (DeletingUser.isPresent()) {
                dataService.permissions.delete(GotUser.get());
                dataService.users.delete(DeletingUser.get());
            } else {
                fail("Error fetching permissions");
            }
        } else {
            fail("Error fetching billboard.");
        }

    }

    /**
     * Tests getting a users permissions from the database using the id.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetUserByID() throws Exception {
        User TestUser = new User("Username1", "Password", "Salt");
        dataService.users.insert(TestUser);

        Optional<User> InsertedUser = dataService.users.get(TestUser.username);
        if (InsertedUser.isPresent()) {
            Permissions TestPermissions = new Permissions(InsertedUser.get().id, InsertedUser.get().username,
                true,
                true,
                false,
                false,
                true);
            dataService.permissions.insert(TestPermissions);
        } else {
            fail("Error fetching permissions");
        }

        // Retrieve the testing User
        Optional<Permissions> GotUser = dataService.permissions.get(InsertedUser.get().username);
        if (GotUser.isPresent()) {
            // Test the retrieved User username against the control username
            assertTrue(GotUser.get().canEditBillboard);


            // Cleanup and delete the user
            Optional<User> DeletingUser = dataService.users.get(GotUser.get().username);
            if (DeletingUser.isPresent()) {
                dataService.permissions.delete(GotUser.get());
                dataService.users.delete(DeletingUser.get());
            } else {
                fail("Error fetching permissions");
            }
        } else {
            fail("Error fetching billboard.");
        }

    }

    /**
     * Tests changing a users permission property in the database.
     *
     * @throws Exception: this exception returns when there is an issue changing data in the database.
     */
    @Test
    public void UpdateExistingPermission() throws Exception {
        // Create a testing user and insert it into the database
        User TestUser = new User("Username1", "Password", "Salt");
        dataService.users.insert(TestUser);

        Optional<User> InsertedUser = dataService.users.get(TestUser.username);
        if (InsertedUser.isPresent()) {
            Permissions TestPermissions = new Permissions(InsertedUser.get().id, InsertedUser.get().username,
                true,
                true,
                false,
                false,
                true);
            dataService.permissions.insert(TestPermissions);
        } else {
            fail("Error fetching permissions");
        }

        Optional<Permissions> ChangePermission = dataService.permissions.get(InsertedUser.get().id);
        if (ChangePermission.isPresent()) {
            assertFalse(ChangePermission.get().canEditUser);
            ChangePermission.get().canEditUser = true;
            dataService.permissions.update(ChangePermission.get());

            Optional<Permissions> ChangedPermission = dataService.permissions.get(InsertedUser.get().id);
            if (ChangedPermission.isPresent()) {
                assertTrue(ChangedPermission.get().canEditUser);

                // Cleanup and delete the user
                Optional<User> DeletingUser = dataService.users.get(InsertedUser.get().username);
                if (DeletingUser.isPresent()) {
                    dataService.permissions.delete(ChangedPermission.get());
                    dataService.users.delete(DeletingUser.get());
                } else {
                    fail("Error fetching permissions");
                }

            } else {
                fail("Error fetching permissions");
            }

        } else {
            fail("Error fetching permissions");
        }
    }

    /**
     * Tests deleting a user permission in the database.
     *
     * @throws Exception: this exception returns when there is an issue deleting data in the database.
     */
    @Test
    public void DeleteNewUserPermissions() throws Exception {
        // Create a testing User
        User TestUser = new User("Username1", "Password", "Salt");
        dataService.users.insert(TestUser);
        Optional<User> InsertedUser = dataService.users.get(TestUser.username);
        if (InsertedUser.isPresent()) {
            Permissions TestPermissions = new Permissions(InsertedUser.get().id, InsertedUser.get().username,
                true,
                true,
                false,
                false,
                true);
            dataService.permissions.insert(TestPermissions);
        } else {
            fail("Error fetching permissions");
        }

        Optional<Permissions> DeletePermission = dataService.permissions.get(InsertedUser.get().id);
        if (DeletePermission.isPresent()) {
            dataService.permissions.delete(DeletePermission.get());
            Optional<Permissions> DeletedPermission = dataService.permissions.get(InsertedUser.get().id);
            assertTrue(DeletedPermission.isEmpty());

            // Cleanup and delete the user
            Optional<User> DeletingUser = dataService.users.get(InsertedUser.get().username);
            if (DeletingUser.isPresent()) {
                dataService.users.delete(DeletingUser.get());
            } else {
                fail("Error fetching permissions");
            }

        } else {
            fail("Error fetching permissions");
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
