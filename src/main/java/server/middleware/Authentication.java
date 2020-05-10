package server.middleware;

import common.router.*;
import server.router.*;
import server.services.TokenService;

public class Authentication {

    public class Authenticate extends Action {
        public Authenticate() {}

        @Override
        public IActionResult execute(Request req) throws Exception {
            if (req.token == null || !TokenService.getInstance().verify(req.token)) return new Unauthorised();

            return new Ok();
        }
    }

    public class Login extends Action {
        public Login() {}

        @Override
        public IActionResult execute(Request req) throws Exception {
            var username = req.params.get("username");
            var password = req.params.get("password");

            var token = TokenService.getInstance().tryLogin(username, password);
            if (token != null) return new Ok(token);

            return new BadRequest("Failed to login");
        }
    }

    public class Logout extends Action {
        public Logout() {}

        @Override
        public IActionResult execute(Request req) throws Exception {
            var token = req.token;

            TokenService.getInstance().tryLogout(token);

            return new Ok();
        }
    }
}
