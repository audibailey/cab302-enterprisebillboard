package server.controllers.auth;

import server.router.Action;
import server.router.Request;
import server.router.models.IActionResult;
import server.router.models.NotFound;
import server.router.models.Ok;
import server.router.models.Unauthorised;
import server.services.TokenService;

public class Auth extends Action {

    public Auth() {}

    @Override
    public IActionResult execute(Request req) throws Exception {
        if (req.token == null || !TokenService.GetInstance().verify(req.token)) return new Unauthorised();

        return new Ok();
    }
}
