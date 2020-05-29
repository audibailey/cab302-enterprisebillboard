package server.middleware;

import common.models.Permissions;
import common.utils.session.Session;
import common.router.Response;
import common.router.Request;
import common.router.response.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationTest {

    Request req = new Request("/foo", "foo", null, null);
    Permissions permission = Permissions.Random(0, "user");
    Session session = new Session(0, "user", permission);

    @Test
    public void ValidAuthenticationTest() throws Exception {
        // TODO: Determine whether to test here or test directly at TokenService.

        //  TODO: Could also be that we insert into the TokenService instance and then test at the Controller level
        // String token = "123";
        // TokenService.getInstance().insertSession(new Session(..., token));
        // session.token = token;
        // ActionResult test = new Authentication.Authenticate().execute(req);
        // assertEquals(Status.SUCCESS, test.status);
    }

    @Test public void InvalidAuthenticationTest() throws Exception {
        session.token = null;

        Response test = new Authentication.Authenticate().execute(req);
        assertEquals(Status.UNAUTHORIZED, test.status);
    }
}
