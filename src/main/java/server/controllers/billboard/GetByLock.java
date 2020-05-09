package server.controllers.billboard;

import common.models.Billboard;
import server.router.Action;
import server.router.Request;
import server.router.models.IActionResult;
import server.router.models.NotFound;
import server.router.models.Ok;
import server.sql.CollectionFactory;

import java.util.List;

public class GetByLock extends Action {

    public GetByLock() { }

    @Override
    public IActionResult execute(Request req) throws Exception {

        String lock = req.params.get("lock");

        List<Billboard> res = CollectionFactory.getInstance(Billboard.class).get(x -> String.valueOf(x.locked) == lock);

        return new Ok(res);
    }
}
