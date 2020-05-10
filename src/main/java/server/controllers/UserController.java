package server.controllers;

import common.models.User;
import common.router.*;
import server.router.*;
import server.sql.CollectionFactory;

import java.util.List;

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

            return new BadRequest("Not a User");
        }
    }

    public class Update extends Action {
        public Update() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            if (req.body instanceof User) {
                CollectionFactory.getInstance(User.class).update((User) req.body);
                return new Ok();
            }

            return new BadRequest("Not a User");
        }
    }

    public class Delete extends Action {
        public Delete() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            // DELETE PERMS TOO
            if (req.body instanceof User) {
                CollectionFactory.getInstance(User.class).delete((User) req.body);
                return new Ok();
            }

            return new BadRequest("Not a user");
        }
    }
}
