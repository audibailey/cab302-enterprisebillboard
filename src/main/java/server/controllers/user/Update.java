package server.controllers.user;

import common.models.Billboard;
import common.models.User;
import server.router.Action;
import server.router.Request;
import server.router.models.BadRequest;
import server.router.models.IActionResult;
import server.router.models.NotFound;
import server.router.models.Ok;
import server.sql.CollectionFactory;

public class Update extends Action {

    public Update() { }

    @Override
    public IActionResult execute(Request req) throws Exception {
        if (req.body instanceof User) {
            CollectionFactory.getInstance(User.class).update((User) req.body);
            return new Ok();
        }

        return new BadRequest("Not a User");
    }

}
