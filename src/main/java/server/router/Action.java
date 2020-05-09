package server.router;

import server.router.models.IActionResult;
import server.router.models.NotFound;

public abstract class Action {

    public IActionResult execute(Request r) throws Exception {
        return new NotFound();
    }
}
