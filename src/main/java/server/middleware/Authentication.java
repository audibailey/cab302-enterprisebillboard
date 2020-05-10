package server.middleware;

import common.models.User;
import common.router.*;
import server.router.*;
import server.services.TokenService;

import java.util.Optional;

/**
 * This class handles the authentication action.
 *
 * @author Jamie Martin
 * @author Perdana Bailey
 */
public class Authentication {

    /**
     * This class extends action for authenticating users. It ensures the user is using a valid token.
     *
     * @author Jamie Martin
     */
    public class Authenticate extends Action {
        // Empty constructor to just initialise the authenticate class.
        public Authenticate() {
        }

        /**
         * Override the execution with the token validation check to authorise the user.
         *
         * @param req: The user request.
         * @return IActionResult: This object is for the router to determine the users authentication.
         * @throws Exception: Pass through the server error from the verify function when checking the token.
         */
        @Override
        public IActionResult execute(Request req) throws Exception {
            // If the token is null or expired return an Unauthorised Result
            if (req.token == null || !TokenService.getInstance().verify(req.token)) {
                return new Unauthorised("Token is invalid.");
            } else {
                return new Ok();
            }
        }
    }

    /**
     * This class extends action for logging in users. It "logs" the user in and generates a token.
     *
     * @author Jamie Martin
     * @author Perdana Bailey
     */
    public class Login extends Action {
        // Empty constructor to just initialise the login class.
        public Login() {
        }

        /**
         * Override the default execute function with the login of the user.
         *
         * @param req: The user request.
         * @return IActionResult: This object is for the router that returns a token or a Unauthenticated.
         * @throws Exception: Pass through the server error from the checkUserExists or tryLogin function.
         */
        @Override
        public IActionResult execute(Request req) throws Exception {
            String username = req.params.get("username");
            String password = req.params.get("password");

            // Ensure the fields are not null.
            if (username == null) {
                return new BadRequest("Must specify a username.");
            } else if (password == null) {
                return new BadRequest("Must specify a password.");
            }

            // Ensure the user exists.
            Optional<User> user = TokenService.getInstance().checkUserExists(username);
            if (user.isPresent()) {
                // Attempt to log the user in and request for the token.
                String token = TokenService.getInstance().tryLogin(user.get(), password);

                // If the token is null that means the password is incorrect.
                if (token == null) {
                    return new BadRequest("Incorrect password.");
                }

                // Return a success IActionResult with the token.
                // TODO: RETURN SESSION and PERMISSIONS
                return new Ok(token);
            } else {
                // If the user doesn't exist tell the client it's an invalid username.
                return new BadRequest("Invalid username.");
            }
        }
    }

    /**
     * This class extends action for logging out the user. It "logs" the user out and removes them from the session.
     *
     * @author Jamie Martin
     * @author Perdana Bailey
     */
    public class Logout extends Action {
        // Empty constructor to just initialise the login class.
        public Logout() {
        }

        /**
         * Override the default execute function with the logging out of the user.
         *
         * @param req: The user request.
         * @return IActionResult: This object is for the router that ensures a successful logout.
         * @throws Exception: Pass through the server error from the tryLogout function.
         */
        @Override
        public IActionResult execute(Request req) throws Exception {
            String token = req.token;

            // Ensure the token is not null.
            if (token == null) {
                return new Unauthorised("Must provide token to logout.");
            }

            // Attempt to log the user out and return a success empty IActionResult.
            TokenService.getInstance().tryLogout(token);
            return new Ok();
        }
    }

    // TODO: NEW class to check when token expires
}
