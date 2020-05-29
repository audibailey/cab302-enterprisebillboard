package server.controllers;

import common.models.Permissions;
import common.models.User;
import common.models.UserPermissions;
import common.router.IActionResult;
import common.router.Request;
import common.router.Status;
import common.utils.HashingFactory;
import common.utils.RandomFactory;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    @Test
    public void InsertNewUser() throws Exception {
        // Generate the user data to insert
        User testUser = new User(RandomFactory.String(), HashingFactory.hashPassword("1234"), null);
        Permissions testPerm = Permissions.Random(testUser.id, testUser.username);
        UserPermissions temp = new UserPermissions(testUser, testPerm);

        // Create new request and Insert the user data
        Request req = new Request(null, "blah", null, temp);
        IActionResult test = new UserPermissionsController.Insert().execute(req);

        assertEquals(Status.SUCCESS, test.status);
    }

    @Test
    public void InsertExistUser() throws Exception {
        // Generate the user data to insert
        User testUser = new User("ExistedUser", HashingFactory.hashPassword("1234"), null);
        Permissions testPerm = Permissions.Random(testUser.id, testUser.username);
        UserPermissions temp = new UserPermissions(testUser, testPerm);

        // Create new request and insert the user data
        Request req = new Request(null, "blah", null, temp);
        IActionResult test = new UserPermissionsController.Insert().execute(req);

        User newOne = User.Random();
        newOne.username = "ExistedUser";
        Permissions newPerm = Permissions.Random(newOne.id, newOne.username);
        UserPermissions new1 = new UserPermissions(newOne, newPerm);

        req = new Request(null, "blah", null, new1);

        IActionResult result = new UserPermissionsController.Insert().execute(req);
        assertEquals(Status.BAD_REQUEST, result.status);

    }


    @Test
    public void DeleteExistUser() throws Exception {
        // Generate the user data to insert
        User testUser = new User("DeleteUser", HashingFactory.hashPassword("1234"), null);
        Permissions testPerm = Permissions.Random(testUser.id, testUser.username);
        UserPermissions temp = new UserPermissions(testUser, testPerm);

        // Insert the user data new UserPermissionsController.Insert().execute(req);
        Request req = new Request(null, "blah", null, temp);

        IActionResult test = new UserPermissionsController.Insert().execute(req);

        HashMap<String, String> params = new HashMap<>();
        params.put("username", "DeleteUser");

        // Create new request
        req = new Request(null, "blah", params, null);

        // Delete the user
        IActionResult result = new UserController.Delete().execute(req);
        assertEquals(Status.SUCCESS, result.status);
    }

    @Test
    public void DeleteNonExist() throws Exception {
        // Generate the user data to insert
        User testUser = new User("DeleteUser", HashingFactory.hashPassword("1234"), null);
        Permissions testPerm = Permissions.Random(testUser.id, testUser.username);
        UserPermissions temp = new UserPermissions(testUser, testPerm);

        // Insert the user data new UserPermissionsController.Insert().execute(req);
        Request req = new Request(null, "blah", null, temp);

        IActionResult test = new UserPermissionsController.Insert().execute(req);


        HashMap<String, String> params = new HashMap<>();
        params.put("username", "Weirdo");

        // Create new request
        req = new Request(null, "blah", params, null);

        // Delete the user
        IActionResult result = new UserController.Delete().execute(req);
        assertEquals(Status.BAD_REQUEST, result.status);
    }

    @Test
    public void GetAllUsers() throws Exception {
        // Create new request
        Request req = new Request(null, "blah", null, null);
        IActionResult result = new PermissionController.Get().execute(req);

        assertEquals(Status.SUCCESS, result.status);
    }

    @Test
    public void UpdatePassword() throws Exception {
        // Generate the user data to insert
        User testUser = new User("kevin", HashingFactory.hashPassword("1234"), null);
        Permissions testPerm = Permissions.Random(testUser.id, testUser.username);
        UserPermissions temp = new UserPermissions(testUser, testPerm);

        // Create new request and Insert the user data
        Request req = new Request(null, "blah", null, temp);
         new UserPermissionsController.Insert().execute(req);

        HashMap<String, String> params = new HashMap<>();

        String permUser = "kevin";
        String newPassword = HashingFactory.hashPassword("12");
        params.put("username", permUser);
        params.put("password", newPassword);
        req = new Request(null,"blah",params,null);
        IActionResult result = new UserController.UpdatePassword().execute(req);
        assertEquals(Status.SUCCESS, result.status);
    }
}
