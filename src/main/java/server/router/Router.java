package server.router;

import common.router.Status;
import common.router.Request;
import common.router.BadRequest;
import common.router.IActionResult;
import common.router.NotFound;

import java.util.Collections;
import java.util.HashMap;

/**
 * A router class to manage controllers for the clients requests. Http-Like
 *
 * @author Jamie Martin
 */
public class Router {

    private Class<? extends Action> authenticatedAction;

    // <Path, Array<Actions> Hash Map to store the path to find those Actions on.
    // Multiple actions are allowable, enabling chaining ie: Authenticate, then Get
    protected final HashMap<String, Class<? extends Action>[]> routes = new HashMap<>();

    public Router() {

    }

    public Router(Class<? extends Action> action) {
        this.authenticatedAction = action;
    }

    /**
     * Routes a given request and returns the result.
     * All exceptions inside the actions are handled here and returned to the client.
     * @param r: The Request class
     * @return The IActionResult from performing the given actions
     */
    public IActionResult route(Request r) {
        // attempt to get the actions from the given path
        var actions = routes.get(r.path);
        // if there's no actions return NotFound
        if (actions == null) return new NotFound();

        IActionResult result = null;

        // Loop through actions
        for (var action: actions) {
            // if an action is null then we can't perform anything, return NotFound
            if (action == null) return new NotFound();

            // Try and execute the action
            try {
                // Execute the action
                result = action.getDeclaredConstructor().newInstance().execute(r);
                // When Actions are chained, if they don't return a success the loop stops and returns the given result
                if (result.status != Status.SUCCESS) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                // catch all errors here and return BadRequest with the Exception message
                return new BadRequest(e.getMessage());
            }
        }

        // TODO: Remove after testing
        assert result != null;

        return result;
    }

    /**
     * Add an array of Classes that extend Action to the routes path
     * @param path: The path you want to set, ie: /login
     * @param actions: The actions you want to perform in order given, ie: Authorize.class, GetSecret.class
     * @return this, allows chaining of ADD().ADD();
     */
    public Router ADD(String path, Class<? extends Action>... actions) {
        routes.put(path, actions);
        return this;
    }

    public Router ADD_AUTH(String path, Class<? extends Action>... actions) {
        if (authenticatedAction == null) {
            return ADD(path, actions);
        }

        return ADD(path, combine(authenticatedAction, actions));
    }

    public Class<? extends Action>[] combine(Class<? extends Action> action, Class<? extends Action>... actions) {
        Class<? extends Action>[] temp = new Class[actions.length + 1];

        for (int i = 0; i < actions.length + 1; i++) {
            if (i == 0) temp[i] = action;
            else temp[i] = actions[i-1];
        }

        return temp;
    }
}
