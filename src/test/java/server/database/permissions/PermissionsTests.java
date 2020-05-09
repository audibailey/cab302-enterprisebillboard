package server.database.permissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import common.utils.RandomFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import common.models.Permissions;
import common.models.User;

/**
 * Test class for the user database handler
 *
 * @author Jamie Martin
 * @author Kevin Huynh
 * @author Perdana Bailey
 */
public class PermissionsTests {

    // The DataService used by all the tests, essentially a database connection.
//    private static DataService dataService;

    /**
     * Connects to the database or a mock database.
     *
     * @throws Exception: this exception returns when there is an issue connecting to the database.
     */
    @BeforeAll
    public static void ConnectToDatabase() throws Exception {
//        dataService = new DataService(true);
//        dataService.permissions.deleteAll();
//        dataService.users.deleteAll();
    }

    /**
     * Tests adding a user and permissions to the database.
     *
     * @throws Exception: this exception returns when there is an issue inserting data into the database.
     */
    @Test
    public void AddUserPermissionsTest() throws Exception {
//        // Create new user and insert into the database
//        User user = User.Random();
//        dataService.users.insert(user);
//
//        // Retrieve the testing user
//        Optional<User> insertedUser = dataService.users.get(user.username);
//        if (insertedUser.isPresent()) {
//            // Create new permission list and insert into the database
//            Permissions permissions = Permissions.Random(insertedUser.get().id, insertedUser.get().username);
//
//            dataService.permissions.insert(permissions);
//            // Retrieve the permission list
//            Optional<Permissions> insertedPermissions = dataService.permissions.get(insertedUser.get().id);
//            if (insertedPermissions.isPresent()) {
//                // Test the user's edit billboard permission value against control edit billboard value
//                assertEquals(insertedPermissions.get().canEditBillboard, permissions.canEditBillboard);
//
//                // Clean up and delete permissions list + user
//                dataService.permissions.delete(insertedPermissions.get());
//                dataService.users.delete(insertedUser.get());
//            } else {
//                fail("Error fetching permissions");
//            }
//        } else {
//            fail("Error fetching user");
//        }

    }

    /**
     * Tests getting all user permissions from the database.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetAllUsersPermissions() throws Exception {
//        // Create the array to test against based on the testing users and permission lists
//        List<User> controlUsers = new ArrayList<User>();
//        List<Permissions> controlPermissions = new ArrayList<Permissions>();
//
//        // Create the first user and insert into the database
//        User user1 = User.Random();
//
//        dataService.users.insert(user1);
//        // Add the user to control list
//        controlUsers.add(user1);
//
//        //Retrieve the testing user
//        Optional<User> insertedUser = dataService.users.get(user1.username);
//        if (insertedUser.isPresent()) {
//            // Create a new permission list for the first user and insert into the database
//            Permissions permissions = Permissions.Random(insertedUser.get().id, insertedUser.get().username);
//
//            dataService.permissions.insert(permissions);
//            // Add the permission to control list
//            controlPermissions.add(permissions);
//        } else {
//            fail("Error fetching permissions");
//        }
//
//        // Create the second user and insert into the database
//        User user2 = User.Random();
//        dataService.users.insert(user2);
//        // Add the user to control list
//        controlUsers.add(user2);
//
//        //Retrieve the second testing user
//        Optional<User> insertedUser2 = dataService.users.get(user2.username);
//        if (insertedUser2.isPresent()) {
//            // Create a new permission list for the second user and insert into the database
//            Permissions permissions = Permissions.Random(insertedUser2.get().id, insertedUser2.get().username);
//
//            dataService.permissions.insert(permissions);
//            // Add the permission to control list
//            controlPermissions.add(permissions);
//        } else {
//            fail("Error fetching user");
//        }
//
//        // Create the third user and insert into the database
//        User user3 = User.Random();
//        dataService.users.insert(user3);
//        // Add the user to control list
//        controlUsers.add(user3);
//
//        //Retrieve the third testing user
//        Optional<User> insertedUser3 = dataService.users.get(user3.username);
//        if (insertedUser3.isPresent()) {
//            // Create a new permission list for the third user and insert into the database
//            Permissions permissions = Permissions.Random(insertedUser3.get().id, insertedUser3.get().username);
//
//            dataService.permissions.insert(permissions);
//            // Add the permission to control list
//            controlPermissions.add(permissions);
//        } else {
//            fail("Error fetching user");
//        }
//
//        // Retrieve the testing permission lists
//        List<Permissions> listPermissions = dataService.permissions.getAll();
//
//        // Ensure they are the same
//        assertEquals(
//            controlPermissions.stream().map(user -> user.username).collect(Collectors.toList()),
//            listPermissions.stream().map(user -> user.username).collect(Collectors.toList())
//        );
//
//        assertEquals(
//            controlPermissions.stream().map(user -> user.canViewBillboard).collect(Collectors.toList()),
//            listPermissions.stream().map(user -> user.canViewBillboard).collect(Collectors.toList())
//        );
//
//        // Clean up and delete all permissions + users
//        dataService.permissions.deleteAll();
//        dataService.users.deleteAll();
    }

    /**
     * Tests getting a users permissions from the database using the user name.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetUserByUsername() throws Exception {
//        // Create a new user and insert into the database
//        User user = User.Random();
//        dataService.users.insert(user);
//
//        // Retrieve the testing user
//        Optional<User> insertedUser = dataService.users.get(user.username);
//        if (insertedUser.isPresent()) {
//            // Create new permission list and insert into the database
//            Permissions permissions = Permissions.Random(insertedUser.get().id, insertedUser.get().username);
//
//            dataService.permissions.insert(permissions);
//
//            // Retrieve the testing User
//            Optional<Permissions> gotUser = dataService.permissions.get(insertedUser.get().username);
//            if (gotUser.isPresent()) {
//                // Test the retrieved User edit billboard value against the control edit billboard value
//                assertEquals(permissions.canEditBillboard, gotUser.get().canEditBillboard);
//
//                // Cleanup and delete the user + permissions
//                Optional<User> deletingUser = dataService.users.get(gotUser.get().username);
//                if (deletingUser.isPresent()) {
//                    dataService.permissions.delete(gotUser.get());
//                    dataService.users.delete(deletingUser.get());
//                } else {
//                    fail("Error fetching permissions");
//                }
//            } else {
//                fail("Error fetching user");
//            }
//        } else {
//            fail("Error fetching user");
//        }

    }

    /**
     * Tests getting a users permissions from the database using the id.
     *
     * @throws Exception: this exception returns when there is an issue fetching data from the database.
     */
    @Test
    public void GetUserByID() throws Exception {
//        // Create new user and insert into database
//        User user = User.Random();
//        dataService.users.insert(user);
//
//        // Retrieve the testing user
//        Optional<User> insertedUser = dataService.users.get(user.username);
//        if (insertedUser.isPresent()) {
//            // Create a new permission for the user and insert into the database
//            Permissions permissions = Permissions.Random(insertedUser.get().id, insertedUser.get().username);
//
//            dataService.permissions.insert(permissions);
//
//            // Retrieve the testing User
//            Optional<Permissions> gotPermissions = dataService.permissions.get(insertedUser.get().username);
//            if (gotPermissions.isPresent()) {
//                // Test the retrieved User permission
//                assertEquals(permissions.canEditBillboard, gotPermissions.get().canEditBillboard);
//
//                // Cleanup and delete the user
//                Optional<User> deletingUser = dataService.users.get(gotPermissions.get().username);
//                if (deletingUser.isPresent()) {
//                    dataService.permissions.delete(gotPermissions.get());
//                    dataService.users.delete(deletingUser.get());
//                } else {
//                    fail("Error fetching permissions");
//                }
//            } else {
//                fail("Error fetching user");
//            }
//
//        } else {
//            fail("Error fetching user");
//        }
    }

    /**
     * Tests changing a users permission property in the database.
     *
     * @throws Exception: this exception returns when there is an issue changing data in the database.
     */
    @Test
    public void UpdateExistingPermission() throws Exception {
//        // Create a testing user and insert it into the database
//        User user = User.Random();
//        dataService.users.insert(user);
//
//        // Retrieve the testing user
//        Optional<User> insertedUser = dataService.users.get(user.username);
//        if (insertedUser.isPresent()) {
//            // Create a new permission for the user and insert into the database
//            Permissions permissions = Permissions.Random(insertedUser.get().id, insertedUser.get().username);
//
//            dataService.permissions.insert(permissions);
//
//            // Retrieve the permissions list
//            Optional<Permissions> changePermission = dataService.permissions.get(insertedUser.get().id);
//            if (changePermission.isPresent()) {
//                // Test if the User's edit user is false or not
//                assertEquals(permissions.canEditUser, changePermission.get().canEditUser);
//                // Change the edit user's permission to true and update to the database
//                boolean randomBoolean = RandomFactory.Boolean();
//
//                changePermission.get().canEditUser = randomBoolean;
//                dataService.permissions.update(changePermission.get());
//
//                // Retrieve the permission after updated
//                Optional<Permissions> changedPermission = dataService.permissions.get(insertedUser.get().id);
//                if (changedPermission.isPresent()) {
//                    // Test if the User's edit user is true or not
//                    assertEquals(randomBoolean, changedPermission.get().canEditUser);
//
//                    // Cleanup and delete the user
//                    Optional<User> deletingUser = dataService.users.get(insertedUser.get().username);
//                    if (deletingUser.isPresent()) {
//                        dataService.permissions.delete(changedPermission.get());
//                        dataService.users.delete(deletingUser.get());
//                    } else {
//                        fail("Error fetching user");
//                    }
//                } else {
//                    fail("Error fetching permissions");
//                }
//
//            } else {
//                fail("Error fetching permissions");
//            }
//
//        } else {
//            fail("Error fetching user");
//        }
    }

    /**
     * Tests deleting a user permission in the database.
     *
     * @throws Exception: this exception returns when there is an issue deleting data in the database.
     */
    @Test
    public void DeleteNewUserPermissions() throws Exception {
//        // Create a testing User
//        User user = User.Random();
//        dataService.users.insert(user);
//        // Retrieve the testing user
//        Optional<User> insertedUser = dataService.users.get(user.username);
//        if (insertedUser.isPresent()) {
//            // Create a new permission for the user and insert into the database
//            Permissions testPermissions = Permissions.Random(insertedUser.get().id, insertedUser.get().username);
//            dataService.permissions.insert(testPermissions);
//        } else {
//            fail("Error fetching user");
//        }
//        // Retrieve the permission list
//        Optional<Permissions> deletePermission = dataService.permissions.get(insertedUser.get().id);
//        if (deletePermission.isPresent()) {
//            // Delete the permission from the server
//            dataService.permissions.delete(deletePermission.get());
//            // Retrieve the user's permission after deleted and check if it's empty or not
//            Optional<Permissions> deletedPermission = dataService.permissions.get(insertedUser.get().id);
//            assertTrue(deletedPermission.isEmpty());
//
//            // Cleanup and delete the user
//            Optional<User> deletingUser = dataService.users.get(insertedUser.get().username);
//            if (deletingUser.isPresent()) {
//                dataService.users.delete(deletingUser.get());
//            } else {
//                fail("Error fetching user");
//            }
//        } else {
//            fail("Error fetching permissions");
//        }
    }

    /**
     * Disconnects from the database.
     *
     * @throws Exception: this exception returns when there is an issue disconnecting from the database.
     */
    @AfterAll
    public static void DisconnectDatabase() throws Exception {
//        dataService.closeConnection();
    }
}
