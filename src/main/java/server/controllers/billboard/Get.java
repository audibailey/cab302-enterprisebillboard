package server.controllers.billboard;

import common.models.Billboard;
import server.router.Action;
import server.router.Request;
import server.router.models.IActionResult;
import server.router.models.Ok;
import server.sql.CollectionFactory;

import java.util.List;

public class Get extends Action {

    public Get() { }

    @Override
    public IActionResult execute(Request req) throws Exception {

        List<Billboard> res = CollectionFactory.getInstance(Billboard.class).get(x -> true);

        return new Ok(res);
    }

}
