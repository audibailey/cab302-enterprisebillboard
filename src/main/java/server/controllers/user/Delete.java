package server.controllers.user;

import common.models.Billboard;
import common.models.User;
import server.router.Action;
import server.router.Request;
import server.router.models.BadRequest;
import server.router.models.IActionResult;
import server.router.models.NotFound;
import server.router.models.Ok;
import server.sql.Collection;
import server.sql.CollectionFactory;

public class Delete extends Action {

    public Delete() { }

    @Override
    public IActionResult execute(Request req) throws Exception {
        // DELETE PERMS TOO
        if (req.body instanceof User) {
            CollectionFactory.getInstance(User.class).delete((User) req.body);
            return new Ok();
        }
        return new NotFound();
    }

}
