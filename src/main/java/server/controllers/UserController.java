package server.controllers;

import common.models.Permissions;
import common.models.User;
import common.router.*;
import server.router.*;
import server.services.Session;
import server.services.TokenService;
import server.sql.CollectionFactory;

import java.util.List;
import java.util.Optional;

public class UserController {

    public class Get extends Action {
        public Get() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            List<User> res = CollectionFactory.getInstance(User.class).get(x -> true);

            return new Ok(res);
        }
    }

    public class GetById extends Action {
        public GetById() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            String id = req.params.get("id");

            if (id == null) return new BadRequest("No id");

            List<User> res = CollectionFactory.getInstance(User.class).get(x -> id == String.valueOf(x.id));

            return new Ok(res);
        }
    }

    public class Insert extends Action {
        public Insert() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            if (req.body instanceof User) {
                CollectionFactory.getInstance(User.class).insert((User) req.body);
                return new Ok();
            }

            return new UnsupportedType(User.class);
        }
    }

    public class Update extends Action {
        public Update() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            if (req.body instanceof User) {
                Optional<Session> session = TokenService.getInstance().getSessionByToken(req.token);
                if (session.isEmpty()) return new BadRequest("No valid session");

                Optional<Permissions> perms = CollectionFactory.getInstance(Permissions.class).get(p -> p.username == session.get().username).stream().findFirst();
                if (perms.isEmpty()) return new BadRequest("No valid perms");

                Permissions permissions = perms.get();

                if (permissions.canEditUser) {
                    CollectionFactory.getInstance(User.class).update((User) req.body);
                    return new Ok();
                }
                else
                {
                    if (session.get().userId == ((User) req.body).id)
                    {
                        CollectionFactory.getInstance(User.class).update((User) req.body);
                        return new Ok();
                    }
                }

            }

            return new UnsupportedType(User.class);
        }
    }

    public class Delete extends Action {
        public Delete() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            // DELETE PERMS TOO
            if (req.body instanceof User) {
                CollectionFactory.getInstance(User.class).delete((User) req.body);
                CollectionFactory.getInstance(Permissions.class).delete((Permissions) req.body);
                return new Ok();
            }

            return new UnsupportedType(User.class);
        }
    }
}
