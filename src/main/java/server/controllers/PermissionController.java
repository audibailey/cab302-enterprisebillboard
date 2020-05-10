package server.controllers;

import common.models.Billboard;
import common.models.Permissions;
import common.models.Schedule;
import common.models.User;
import common.router.*;
import server.middleware.Permission;
import server.router.*;
import server.services.Session;
import server.services.TokenService;
import server.sql.CollectionFactory;

import java.util.List;
import java.util.Optional;

public class PermissionController {

    public class Get extends Action {
        public Get() { }

        @Override
        public IActionResult execute(Request req) throws Exception {

            Optional<Session> session = TokenService.getInstance().getSessionByToken(req.token);
            if (session.isEmpty()) return new BadRequest("No valid session");

            Optional<Permissions> perms = CollectionFactory.getInstance(Permissions.class).get(p -> p.username == session.get().username).stream().findFirst();
            if (perms.isEmpty()) return new BadRequest("No valid permissions");

            Permissions permissions = perms.get();
            if (permissions.canEditUser)
            {
                List<Permissions> res = CollectionFactory.getInstance(Permissions.class).get(x -> true);
                return new Ok(res);
            }
            else
            {
                if (session.get().username == ((Permissions)req.body).username)
                {
                    List<Permissions> res = CollectionFactory.getInstance(Permissions.class).get(x -> true);
                    return new Ok(res);
                }
            }
            return new BadRequest("Can't view other users' permissions");
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
