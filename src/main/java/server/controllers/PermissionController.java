package server.controllers;

import common.models.Billboard;
import common.models.Permissions;
import common.models.Schedule;
import common.models.User;
import common.router.*;
import server.middleware.Permission;
import server.router.*;
import server.sql.CollectionFactory;

import java.util.List;

public class PermissionController {

    public class Get extends Action {
        public Get() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            List<Permissions> res = CollectionFactory.getInstance(Permissions.class).get(x -> true);

            return new Ok(res);
        }
    }

    public class GetById extends Action {
        public GetById() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            String id = req.params.get("id");

            List<Permissions> res = CollectionFactory.getInstance(Permissions.class).get(x -> id == String.valueOf(x.id));

            return new Ok(res);
        }
    }

    public class Insert extends Action {
        public Insert() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            if (req.body instanceof Permissions) {
                CollectionFactory.getInstance(Permissions.class).insert((Permissions) req.body);
                return new Ok();
            }

            return new UnsupportedType(Permissions.class);
        }
    }

    public class Update extends Action {
        public Update() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            if (req.body instanceof Permissions) {
                CollectionFactory.getInstance(Permissions.class).update((Permissions) req.body);
                return new Ok();
            }

            return new UnsupportedType(Permissions.class);
        }
    }
}
