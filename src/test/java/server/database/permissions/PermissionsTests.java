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
        // Create new user and insert into the database
        User TestUser = new User("Username1", "Password", "Salt");
        dataService.users.insert(TestUser);

        // Retrieve the testing user
        Optional<User> InsertedUser = dataService.users.get(TestUser.username);
        if (InsertedUser.isPresent()) {
            // Create new permission list and insert into the database
            Permissions TestPermissions = new Permissions(InsertedUser.get().id, InsertedUser.get().username,
                true,
                true,
                false,
                false,
                true);
            dataService.permissions.insert(TestPermissions);
            // Retrieve the permission list
            Optional<Permissions> InsertedPermissions = dataService.permissions.get(InsertedUser.get().id);
            if (InsertedPermissions.isPresent()) {
                // Test the user's edit billboard permission value against control edit billboard value
                assertEquals(InsertedPermissions.get().canEditBillboard, TestPermissions.canEditBillboard);

                // Clean up and delete permissions list + user
                dataService.permissions.delete(InsertedPermissions.get());
                dataService.users.delete(InsertedUser.get());
            } else {
                fail("Error fetching permissions");
            }
        } else {
            fail("Error fetching user");
        }

    }

    /**
     * Tests getting all user permissions from the database.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetAllUsersPermissions() throws Exception {
        // Create the array to test against based on the testing users and permission lists
        List<User> ControlUsers = new ArrayList<User>();
        List<Permissions> ControlPermissions = new ArrayList<Permissions>();

        // Create the first user and insert into the database
        User TestUser1 = new User("Username1", "Password", "Salt");
        dataService.users.insert(TestUser1);
        // Add the user to control list
        ControlUsers.add(TestUser1);

        //Retrieve the testing user
        Optional<User> InsertedUser1 = dataService.users.get(TestUser1.username);
        if (InsertedUser1.isPresent()) {
            // Create a new permission list for the first user and insert into the database
            Permissions TestPermissions = new Permissions(InsertedUser1.get().id, InsertedUser1.get().username,
                true,
                true,
                false,
                false,
                true);
            dataService.permissions.insert(TestPermissions);
            // Add the permission to control list
            ControlPermissions.add(TestPermissions);
        } else {
            fail("Error fetching permissions");
        }

        // Create the second user and insert into the database
        User TestUser2 = new User("Username2", "Password", "Salt");
        dataService.users.insert(TestUser2);
        // Add the user to control list
        ControlUsers.add(TestUser2);

        //Retrieve the second testing user
        Optional<User> InsertedUser2 = dataService.users.get(TestUser2.username);
        if (InsertedUser2.isPresent()) {
            // Create a new permission list for the second user and insert into the database
            Permissions TestPermissions = new Permissions(InsertedUser2.get().id, InsertedUser2.get().username,
                true,
                true,
                false,
                true,
                false);
            dataService.permissions.insert(TestPermissions);
            // Add the permission to control list
            ControlPermissions.add(TestPermissions);
        } else {
            fail("Error fetching user");
        }
        // Create the third user and insert into the database
        User TestUser3 = new User("Username3", "Password", "Salt");
        dataService.users.insert(TestUser3);
        // Add the user to control list
        ControlUsers.add(TestUser3);

        //Retrieve the third testing user
        Optional<User> InsertedUser3 = dataService.users.get(TestUser3.username);
        if (InsertedUser3.isPresent()) {
            // Create a new permission list for the third user and insert into the database
            Permissions TestPermissions = new Permissions(InsertedUser3.get().id, InsertedUser3.get().username,
                true,
                true,
                false,
                false,
                true);
            dataService.permissions.insert(TestPermissions);
            // Add the permission to control list
            ControlPermissions.add(TestPermissions);
        } else {
            fail("Error fetching user");
        }

        // Retrieve the testing permission lists
        List<Permissions> ListPermissions = dataService.permissions.getAll();

        // Ensure they are the same
        assertEquals(
            ControlPermissions.stream().map(user -> user.username).collect(Collectors.toList()),
            ListPermissions.stream().map(user -> user.username).collect(Collectors.toList())
        );

        assertEquals(
            ControlPermissions.stream().map(user -> user.canViewBillboard).collect(Collectors.toList()),
            ListPermissions.stream().map(user -> user.canViewBillboard).collect(Collectors.toList())
        );

        // Clean up and delete all permissions + users
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
        // Create a new user and insert into the database
        User TestUser1 = new User("Username1", "Password", "Salt");
        dataService.users.insert(TestUser1);

        // Retrieve the testing user
        Optional<User> InsertedUser = dataService.users.get(TestUser1.username);
        if (InsertedUser.isPresent()) {
            // Create new permission list and insert into the database
            Permissions TestPermissions = new Permissions(InsertedUser.get().id, InsertedUser.get().username,
                true,
                true,
                false,
                false,
                true);
            dataService.permissions.insert(TestPermissions);
        } else {
            fail("Error fetching user");
        }

        // Retrieve the testing User
        Optional<Permissions> GotUser = dataService.permissions.get(InsertedUser.get().username);
        if (GotUser.isPresent()) {
            // Test the retrieved User edit billboard value against the control edit billboard value
            assertTrue(GotUser.get().canEditBillboard);


            // Cleanup and delete the user + permissions
            Optional<User> DeletingUser = dataService.users.get(GotUser.get().username);
            if (DeletingUser.isPresent()) {
                dataService.permissions.delete(GotUser.get());
                dataService.users.delete(DeletingUser.get());
            } else {
                fail("Error fetching permissions");
            }
        } else {
            fail("Error fetching user");
        }

    }

    /**
     * Tests getting a users permissions from the database using the id.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetUserByID() throws Exception {
        // Create new user and insert into database
        User TestUser = new User("Username1", "Password", "Salt");
        dataService.users.insert(TestUser);

        // Retrieve the testing user
        Optional<User> InsertedUser = dataService.users.get(TestUser.username);
        if (InsertedUser.isPresent()) {
            // Create a new permission for the user and insert into the database
            Permissions TestPermissions = new Permissions(InsertedUser.get().id, InsertedUser.get().username,
                true,
                true,
                false,
                false,
                true);
            dataService.permissions.insert(TestPermissions);
        } else {
            fail("Error fetching user");
        }

        // Retrieve the testing User
        Optional<Permissions> GotUser = dataService.permissions.get(InsertedUser.get().username);
        if (GotUser.isPresent()) {
            // Test the retrieved User permission
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
            fail("Error fetching user");
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

        // Retrieve the testing user
        Optional<User> InsertedUser = dataService.users.get(TestUser.username);
        if (InsertedUser.isPresent()) {
            // Create a new permission for the user and insert into the database
            Permissions TestPermissions = new Permissions(InsertedUser.get().id, InsertedUser.get().username,
                true,
                true,
                false,
                false,
                true);
            dataService.permissions.insert(TestPermissions);
        } else {
            fail("Error fetching user");
        }

        // Retrieve the permissions list
        Optional<Permissions> ChangePermission = dataService.permissions.get(InsertedUser.get().id);
        if (ChangePermission.isPresent()) {
            // Test if the User's edit user is false or not
            assertFalse(ChangePermission.get().canEditUser);
            // Change the edit user's permission to true and update to the database
            ChangePermission.get().canEditUser = true;
            dataService.permissions.update(ChangePermission.get());

            // Retrieve the permission after updated
            Optional<Permissions> ChangedPermission = dataService.permissions.get(InsertedUser.get().id);
            if (ChangedPermission.isPresent()) {
                // Test if the User's edit user is true or not
                assertTrue(ChangedPermission.get().canEditUser);

                // Cleanup and delete the user
                Optional<User> DeletingUser = dataService.users.get(InsertedUser.get().username);
                if (DeletingUser.isPresent()) {
                    dataService.permissions.delete(ChangedPermission.get());
                    dataService.users.delete(DeletingUser.get());
                } else {
                    fail("Error fetching user");
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
        // Retrieve the testing user
        Optional<User> InsertedUser = dataService.users.get(TestUser.username);
        if (InsertedUser.isPresent()) {
            // Create a new permission for the user and insert into the database
            Permissions TestPermissions = new Permissions(InsertedUser.get().id, InsertedUser.get().username,
                true,
                true,
                false,
                false,
                true);
            dataService.permissions.insert(TestPermissions);
        } else {
            fail("Error fetching user");
        }
        // Retrieve the permission list
        Optional<Permissions> DeletePermission = dataService.permissions.get(InsertedUser.get().id);
        if (DeletePermission.isPresent()) {
            // Delete the permission from the server
            dataService.permissions.delete(DeletePermission.get());
            // Retrieve the user's permission after deleted and check if it's empty or not
            Optional<Permissions> DeletedPermission = dataService.permissions.get(InsertedUser.get().id);
            assertTrue(DeletedPermission.isEmpty());

            // Cleanup and delete the user
            Optional<User> DeletingUser = dataService.users.get(InsertedUser.get().username);
            if (DeletingUser.isPresent()) {
                dataService.users.delete(DeletingUser.get());
            } else {
                fail("Error fetching user");
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
