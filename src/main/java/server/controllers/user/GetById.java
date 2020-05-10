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

public class GetById extends Action {

    public GetById() { }

    @Override
    public IActionResult execute(Request req) throws Exception {
        String id = req.params.get("id");

        List<User> res = CollectionFactory.getInstance(User.class).get(x -> id == String.valueOf(x.id));

        return new Ok(res);
    }
}
