package server.controllers.user;

import common.models.Billboard;
import common.models.User;
import server.router.Action;
import server.router.Request;
import server.router.models.IActionResult;
import server.router.models.NotFound;
import server.router.models.Ok;
import server.sql.CollectionFactory;

import java.util.List;

public class Get extends Action {

    public Get() { }

    @Override
    public IActionResult execute(Request req) throws Exception {
        List<User> res = CollectionFactory.getInstance(User.class).get(x -> true);

        return new Ok(res);
    }

}
