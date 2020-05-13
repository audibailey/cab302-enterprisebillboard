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
     */
    public static class Authenticate extends Action {
        public Authenticate(){}

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
            if (req.token == null || !TokenService.getInstance().verify(req.token)) return new Unauthorised("Token is invalid.");

            return new Ok();
        }
    }



    // TODO: NEW class to check when token expires
}
