package server.middleware;

import common.models.Permissions;
import common.models.Session;
import common.router.IActionResult;
import common.router.Request;
import common.router.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.controllers.BillboardController;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PermissionTest {

    Request req = new Request("/foo", "foo", null, null);
    Permissions permission = Permissions.Random(0, "user");

    @Test public void ValidCanEditUserTest() throws Exception {
        permission.canEditUser = true;
        req.permissions = permission;

        IActionResult test = new Permission.canEditUser().execute(req);
        assertEquals(Status.SUCCESS, test.status);
    }

    @Test public void InvalidCanEditUserTest() throws Exception {
        permission.canEditUser = false;
        req.permissions = permission;

        IActionResult test = new Permission.canEditUser().execute(req);
        assertEquals(Status.UNAUTHORIZED, test.status);
    }

}
