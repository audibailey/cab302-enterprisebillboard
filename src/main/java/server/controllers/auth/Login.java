package server.controllers.auth;

import common.models.User;
import server.router.Action;
import server.router.Request;
import server.router.models.IActionResult;
import server.router.models.NotFound;
import server.router.models.Ok;
import server.services.TokenService;

public class Login extends Action {

    public Login() {}

    @Override
    public IActionResult execute(Request req) throws Exception {
        if (req.body instanceof User) {
            // First check user exists, etc
            return new Ok(TokenService.getInstance().loginUser((User) req.body));
        }

        return new NotFound();
    }
}
