package server.router;

import java.util.HashMap;

/**
 * A router class to manage ActionLike actions on a Path. Http-Like
 *
 * @author Jamie Martin
 * @author Perdana Bailey
 */
public abstract class GenericRouter<Path, ActionLike, RouterLike extends GenericRouter<Path, ActionLike, RouterLike>> {
    protected abstract RouterLike getThis();

    private ActionLike authenticatedAction;

    // <Path, Array<ActionLike> Hash Map to store the path to find those Actions on.
    // Multiple actions are allowable, enabling chaining ie: Authenticate, then Get
    protected final HashMap<Path, ActionLike[]> routes = new HashMap<>();

    public GenericRouter() { }

    /**
     * Routes a given Path and returns the Actions to do.
     *
     * @param path: The Path class.
     * @return ActionLike[]: The result found using the given Path.
     */
    public ActionLike[] route(Path path) {
        // Attempt to get the actions from the given path, null if none.
        return routes.get(path);
    }

    /**
     * Add an array of Classes that extend Action to the routes path.
     *
     * @param path:    The Path you want to set, ie: String "/login".
     * @param actions: The ActionLike actions you want to perform in order given, ie: Insert.class, GetSecret.class.
     * @return RouterLike: Returns self/this which allows chaining of ADD().ADD().
     */
    public RouterLike ADD(Path path, ActionLike... actions) {
        routes.put(path, actions);
        return getThis();
    }

    /**
     * Add an auth Action that appends itself to the start of ADD_AUTH() paths.
     *
     * @param action: The ActionLike action you want to perform.
     * @return RouterLike: Returns self/this which allows chaining of ADD().ADD().
     */
    public RouterLike SET_AUTH(ActionLike action) {
        this.authenticatedAction = action;
        return getThis();
    }

    /**
     * Add an array of Classes that extend Action to the routes path with authentication checks.
     *
     * @param path:    The Path you want to set, ie: String "/login".
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
