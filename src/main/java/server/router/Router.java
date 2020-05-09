package server.router;

import common.Status;
import server.router.models.*;

import java.util.HashMap;

public class Router {

    protected final HashMap<String, Class<? extends Action>[]> routes = new HashMap<>();

    public IActionResult route(Request r) {
        var actions = routes.get(r.path);

        IActionResult result = null;

        for (var action: actions) {
            if (action == null) return new NotFound();

            try {
                result = action.getDeclaredConstructor().newInstance().execute(r);
                if (result.status != Status.SUCCESS) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new BadRequest(e.getMessage());
            }
        }

        assert result != null;
        return result;
    }

    public Router ADD(String path, Class<? extends Action>... targets) {
        routes.put(path, targets);
        return this;
    }
}
