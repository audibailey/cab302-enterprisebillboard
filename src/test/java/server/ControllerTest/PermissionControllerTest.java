package server.ControllerTest;

import common.models.Billboard;
import common.models.Permissions;
import common.models.User;
import common.router.IActionResult;
import common.router.Request;
import common.router.Status;
import org.junit.jupiter.api.Test;
import server.controllers.BillboardController;
import server.controllers.PermissionController;
import server.sql.CollectionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PermissionControllerTest {
    @Test
    public void testGetAllPermissions() throws Exception {
        // Create new request.
        Request req = new Request(null, "blah", null, null);

        // Get all the permissions from database
        IActionResult result = new PermissionController.Get().execute(req);
        assertEquals(result.status, Status.SUCCESS);
    }
    @Test
    public void testGetPermissionsByName() throws Exception {
        // Create params
        HashMap<String, String> params = new HashMap<>();
        String permUser = "kevin";
        params.put("username", permUser);

        // Create new request.
        Request req = new Request(null, "blah", params, null);

        // Get all the permissions from database
        IActionResult result = new PermissionController.GetByUsername().execute(req);
        assertEquals(result.status, Status.SUCCESS);
    }
    @Test
    public void testUpdatePermissions() throws Exception {
        // Create params
        HashMap<String, String> params = new HashMap<>();
        String permUser = "kevin";
        params.put("username", permUser);

        // Create new request.
        Request req = new Request(null, "blah", params, null);

        // Get all the permissions from database
        IActionResult result = new PermissionController.GetByUsername().execute(req);
        Permissions perm =(Permissions) ((ArrayList) result.body).get(0);
        perm.canScheduleBillboard = true;

        req = new Request(null, "blah", null, perm);

        result = new PermissionController.Update().execute(req);
        assertEquals(result.status, Status.SUCCESS);
    }

}
