package server.ControllerTest;

import common.models.Permissions;
import common.models.User;
import common.models.UserPermissions;
import common.router.IActionResult;
import common.router.Request;
import common.router.Status;
import org.junit.jupiter.api.Test;
import server.controllers.UserController;
import server.controllers.UserPermissionsController;
import server.sql.CollectionFactory;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    @Test
    public void testNormalInsert() throws Exception {
        // Generate the user data to insert
        User testUser = User.Random();
        testUser.username = "UserControllerTest";
        testUser.password = "1234";
        Permissions testPerm = Permissions.Random(testUser.id, testUser.username);
        UserPermissions temp = new UserPermissions(testUser, testPerm);

        // Insert the user data new UserPermissionsController.Insert().execute(req);
        Request req = new Request(null, "blah", null, temp);
        // Get the user data new UserController.GetUsername().execute(req);

        IActionResult test = new UserPermissionsController.Insert().execute(req);
        assertEquals(Status.SUCCESS,test.status);

    }

    @Test
    public void testNormalJDelete() throws Exception{
        // Get all user from database
        List<User> userList = CollectionFactory.getInstance(User.class).get(user -> true);

        // Find a user to delete
        User deleteUser = null;
        for (User temp : userList) {
            if (temp.username.equals("UserControllerTest")) {
                deleteUser = temp;
                break;
            }
        }

        // Create new request
        Request req = new Request(null, "blah", null, deleteUser);

        // Delete the user
        IActionResult test = new UserController.Delete().execute(req);
        assertEquals(Status.SUCCESS,test.status);
    }

}
