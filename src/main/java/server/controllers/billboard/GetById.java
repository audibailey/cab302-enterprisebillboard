package server.controllers.billboard;

import common.models.Billboard;
import server.router.Action;
import server.router.Request;
import server.router.models.IActionResult;
import server.router.models.Ok;
import server.sql.CollectionFactory;

import java.util.List;

public class GetById extends Action {

    public GetById() { }

    @Override
    public IActionResult execute(Request req) throws Exception {
        String id = req.params.get("id");

        List<Billboard> res = CollectionFactory.getInstance(Billboard.class).get(x -> id == String.valueOf(x.id));

        return new Ok(res);
    }
}
