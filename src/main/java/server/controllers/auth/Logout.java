package server.controllers.auth;

import common.models.User;
import server.router.Action;
import server.router.Request;
import server.router.models.IActionResult;
import server.router.models.NotFound;
import server.router.models.Ok;
import server.services.TokenService;

public class Logout extends Action {

    public Logout() {}

    @Override
    public IActionResult execute(Request req) throws Exception {
        var token = req.token;

        TokenService.getInstance().logoutUser(token);

        return new Ok();
    }
}
