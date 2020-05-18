//package server.router;
//
//import common.models.Permissions;
//import common.router.models.*;
//import common.router.InternalError;
//import server.services.Session;
//import server.services.TokenService;
//import server.sql.CollectionFactory;
//
//import java.util.HashMap;
//import java.util.Optional;

///**
// * A router class to manage controllers for the clients requests. Http-Like
// *
// * @author Jamie Martin
// * @author Perdana Bailey
// */
//public class Router {
//
//    private Class<? extends Action> authenticatedAction;
//
//    // <Path, Array<Actions> Hash Map to store the path to find those Actions on.
//    // Multiple actions are allowable, enabling chaining ie: Authenticate, then Get
//    protected final HashMap<String, Class<? extends Action>[]> routes = new HashMap<>();
//
//    public Router() {
//
//    }
//
//    public Router(Class<? extends Action> action) {
//        this.authenticatedAction = action;
//    }
//
//    /**
//     * Routes a given request and returns the result.
//     * All exceptions inside the actions are handled here and returned to the client.
//     *
//     * @param r: The Request class.
//     * @return IActionResult: The result from performing the given actions.
//     */
//    public IActionResult route(Request r) throws Exception {
//        // Attempt to get the actions from the given path.
//        var actions = routes.get(r.path);
//        // If there's no actions return NotFound.
//        if (actions == null) return new NotFound("No path requests were specified.");
//
//        // Ensure the client can't inject permissions or session information
//        r.permissions = null;
//        r.session = null;
//
//        if (r.token != null) {
//            Optional<Session> session = TokenService.getInstance().getSessionByToken(r.token);
//            if (session.isEmpty()) return new BadRequest("Invalid token, session not found.");
//
//            Optional<Permissions> perms = CollectionFactory.getInstance(Permissions.class).get(p -> p.username.equals(session.get().username)).stream().findFirst();
//            if (perms.isEmpty()) return new BadRequest("No permissions found for your user. Contact an administrator.");
//
//            r.session = session.get();
//            r.permissions = perms.get();
//        }
//
//        // Initialise the return object
//        IActionResult result = null;
//
//        // Loop through actions
//        for (var action : actions) {
//            // if an action is null then we can't perform anything, return NotFound.
//            if (action == null) return new NotFound("A path request was null.");
//
//            // Try and execute the action
//            try {
//                // Execute the action
//                result = action.getDeclaredConstructor().newInstance().execute(r);
//
//                // When Actions are chained, if they don't return a success the loop stops and returns the given result.
//                if (result.status != Status.SUCCESS) {
//                    break;
//                }
//            } catch (Exception e) {
//                // If an internal error occurs display it on the server console with contextual information and a stacktrace.
//                System.out.println("User from " + r.ip + " has requested " + r.path + " which failed internally.");
//                System.out.println("User requested with the parameters: " + r.params + ".");
//                System.out.println("User requested with the token: " + r.token + ".");
//                System.out.println("User requested with the body: " + r.body + ".");
//                //System.out.println("User requested with server injected permissions: " + r.permissions + ".");
//                //System.out.println("User requested with server injected session data: " + r.session + ".");
//                System.out.println("The request error stacktrace will now print: ");
//                e.printStackTrace();
//
//                // Return Internal Error with generic message for the client.
//                return new InternalError("Internal System Error Occurred. Contact system administrator, if issue persists.");
//            }
//        }
//
//        // Return result when the actions are completed, it will only ever return the result of the final action.
//        return result;
//    }
//
//    /**
//     * Add an array of Classes that extend Action to the routes path.
//     *
//     * @param path:    The path you want to set, ie: /login.
//     * @param actions: The actions you want to perform in order given, ie: Insert.class, GetSecret.class.
//     * @return Router: Returns self/this which allows chaining of ADD().ADD().
//     */
//    public Router ADD(String path, Class<? extends Action>... actions) {
//        routes.put(path, actions);
//        return this;
//    }
//
//    /**
//     * Add an array of Classes that extend Action to the routes path with authentication checks.
//     *
//     * @param path: The path you want to set, ie: /login.
//     * @param actions: The actions you want to perform in order given, ie: Insert.class, GetSecret.class.
//     * @return Router: Returns self/this which allows chaining of ADD().ADD().
//     */
//    public Router ADD_AUTH(String path, Class<? extends Action>... actions) {
//        // Ensure there is an authentication class available.
//        if (authenticatedAction == null) {
//            return ADD(path, actions);
//        }
//
//        // Add the authentication class and the actions.
//        return ADD(path, combine(authenticatedAction, actions));
//    }
//
//    /**
//     * This function combines multiple Actions.
//     *
//     * @param action: The base action other actions are combining into.
//     * @param actions: The other actions that are combining into the base.
//     * @return Class<? extends Action>[]: Returns the list of combined actions.
//     */
//    public Class<? extends Action>[] combine(Class<? extends Action> action, Class<? extends Action>... actions) {
//
//        Class<? extends Action>[] temp = new Class[actions.length + 1];
//
//        for (int i = 0; i < actions.length + 1; i++) {
//            if (i == 0) temp[i] = action;
//            else temp[i] = actions[i-1];
//        }
//
//        return temp;
//    }
//}
