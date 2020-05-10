package server.controllers.auth;

import common.models.User;
import server.router.Action;
import server.router.Request;
import server.router.models.BadRequest;
import server.router.models.IActionResult;
import server.router.models.NotFound;
import server.router.models.Ok;
import server.services.TokenService;

public class Login extends Action {

    public Login() {}

    @Override
    public IActionResult execute(Request req) throws Exception {
        var username = req.params.get("username");
        var password = req.params.get("password");

        if (true /* check user exists */) {
            var token = TokenService.getInstance().loginUser(username, password);

            if (token != null) return new Ok(token);
        }

        return new BadRequest("Failed to login");
    }
}
