package server.router;

import common.router.Status;
import common.router.Request;
import common.router.IActionResult;
import common.router.NotFound;
import common.router.InternalError;

import java.util.HashMap;

/**
 * A router class to manage controllers for the clients requests. Http-Like
 *
 * @author Jamie Martin
 * @author Perdana Bailey
 */
public class Router {

    // <Path, Array<Actions> Hash Map to store the path to find those Actions on.
    // Multiple actions are allowable, enabling chaining ie: Authenticate, then Get
    protected final HashMap<String, Class<? extends Action>[]> routes = new HashMap<>();

    /**
     * Routes a given request and returns the result.
     * All exceptions inside the actions are handled here and returned to the client.
     *
     * @param r: The Request class.
     * @return IActionResult: The result from performing the given actions.
     */
    public IActionResult route(Request r) {
        // Attempt to get the actions from the given path.
        var actions = routes.get(r.path);
        // If there's no actions return NotFound.
        if (actions == null) return new NotFound("No path requests were specified.");

        // Initialise the return object
        IActionResult result = null;

        // Loop through actions
        for (var action : actions) {
            // if an action is null then we can't perform anything, return NotFound.
            if (action == null) return new NotFound("A path request was null.");

            // Try and execute the action
            try {
                // Execute the action
                result = action.getDeclaredConstructor().newInstance().execute(r);

                // When Actions are chained, if they don't return a success the loop stops and returns the given result.
                if (result.status != Status.SUCCESS) {
                    break;
                }
            } catch (Exception e) {
                // If an internal error occurs display it on the server console with contextual information and a stacktrace.
                System.out.println("User from " + r.ip + " has requested " + r.path + " which failed internally.");
                System.out.println("User requested with the parameters: " + r.params + ".");
                System.out.println("User requested with the token: " + r.token + ".");
                System.out.println("User requested with the body: " + r.body + ".");
                System.out.println("The request error stacktrace will now print: ");
                e.printStackTrace();

                // Return Internal Error with generic message for the client.
                return new InternalError("Internal System Error Occurred. Contact system administrator, if issue persists.");
            }
        }

        // Return result when the actions are completed, it will only ever return the result of the final action.
        return result;
    }

    /**
     * Add an array of Classes that extend Action to the routes path.
     *
     * @param path:    The path you want to set, ie: /login.
     * @param actions: The actions you want to perform in order given, ie: Authorize.class, GetSecret.class.
     * @return Router: Returns self/this which allows chaining of ADD().ADD();.
     */
    public Router ADD(String path, Class<? extends Action>... actions) {
        routes.put(path, actions);
        return this;
    }
}
