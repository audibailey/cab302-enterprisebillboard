package server.router;

import common.router.*;
import java.util.HashMap;

public abstract class GenericRouter<Path, ActionLike, RouterLike extends GenericRouter<Path, ActionLike, RouterLike>> {
    protected abstract RouterLike getThis();

    private ActionLike authenticatedAction;

    // <Path, Array<Actions> Hash Map to store the path to find those Actions on.
    // Multiple actions are allowable, enabling chaining ie: Authenticate, then Get
    protected final HashMap<Path, ActionLike[]> routes = new HashMap<>();

    public GenericRouter() { }

    public GenericRouter(ActionLike action) {
        this.authenticatedAction = action;
    }

    /**
     * Routes a given request and returns the result.
     * All exceptions inside the actions are handled here and returned to the client.
     *
     * @param path: The Request class.
     * @return IActionResult: The result from performing the given actions.
     */
    public ActionLike[] route(Path path) throws Exception {
        // Attempt to get the actions from the given path.
        return routes.get(path);
    }

    /**
     * Add an array of Classes that extend Action to the routes path.
     *
     * @param path:    The path you want to set, ie: /login.
     * @param actions: The actions you want to perform in order given, ie: Insert.class, GetSecret.class.
     * @return Router: Returns self/this which allows chaining of ADD().ADD().
     */
    public RouterLike ADD(Path path, ActionLike... actions) {
        routes.put(path, actions);
        return getThis();
    }

    /**
     * Add an array of Classes that extend Action to the routes path with authentication checks.
     *
     * @param path: The path you want to set, ie: /login.
     * @param actions: The actions you want to perform in order given, ie: Insert.class, GetSecret.class.
     * @return Router: Returns self/this which allows chaining of ADD().ADD().
     */
    public RouterLike ADD_AUTH(Path path, ActionLike... actions) {
        // Ensure there is an authentication class available.
        if (authenticatedAction == null) {
            ADD(path, actions);
            return getThis();
        }

        // Add the authentication class and the actions.
        ADD(path, combine(authenticatedAction, actions));
        return getThis();
    }

    /**
     * This function combines multiple Actions.
     *
     * @param action: The base action other actions are combining into.
     * @param actions: The other actions that are combining into the base.
     * @return ActionLike[]: Returns the list of combined actions.
     */
    public ActionLike[] combine(ActionLike action, ActionLike... actions) {

        ActionLike[] temp = (ActionLike[]) new Object[actions.length + 1];

        for (int i = 0; i < actions.length + 1; i++) {
            if (i == 0) temp[i] = action;
            else temp[i] = actions[i-1];
        }

        return temp;
    }
}
