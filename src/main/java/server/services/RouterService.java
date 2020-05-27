package server.services;

import server.router.GenericRouter;
import server.router.Action;

/**
 * A singleton Class that handles all the router interactions for the server.
 *
 * @author Jamie Martin
 * @author Kevin Huynh
 * @author Perdana Bailey
 */
public class RouterService {

    private static Router router;

    public static class Router extends GenericRouter<String, Class<? extends Action>, Router> {
        public Router() { super(); }

        @Override protected Router getThis() { return this; }
    }

    /**
     * Generates a RouterService Instance
     * @throws Exception;
     * */
    protected RouterService() {
        this.router = new Router();
    }

    /**
     * Ensures the RouterService is a singleton when getInstance() is called
     */
    private static class RouterServiceHolder {
        private final static RouterService INSTANCE = new RouterService();
    }

    public static Router getInstance() {
        return RouterService.RouterServiceHolder.INSTANCE.router;
    }

}
