package server.controllers.user;

import common.models.Billboard;
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
        return new NotFound();
    }

}
