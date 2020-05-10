package server.router;

import common.router.*;

/**
 * The Action Class, this is used in the Router to ensure given executions contain the necessary methods to run
 *
 * @author Jamie Martin
 */
public abstract class Action {

    /**
     * The executor of the Action. This is overridden when making new Action
     * @param r: The Request class
     * @return The IActionResult from performing the given actions, default NotFound
     */
    public IActionResult execute(Request r) throws Exception {
        return new NotFound();
    }
}
