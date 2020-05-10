package server.controllers;

import common.models.Billboard;
import common.models.Permissions;
import common.router.*;
import server.router.*;
import server.services.Session;
import server.services.TokenService;
import server.sql.CollectionFactory;

import java.util.Optional;
import java.util.List;

/**
 * This controller has all the Actions related to the fetch of
 */
public class BillboardController {

    public class Get extends Action {
        public Get() {}

        @Override
        public IActionResult execute(Request req) throws Exception {
            List<Billboard> res = CollectionFactory.getInstance(Billboard.class).get(x -> true);

            return new Ok(res);
        }
    }

    public class GetById extends Action {
        public GetById() {}

        @Override
        public IActionResult execute(Request req) throws Exception {
            String id = req.params.get("id");

            List<common.models.Billboard> res = CollectionFactory.getInstance(Billboard.class).get(x -> id == String.valueOf(x.id));

            return new Ok(res);
        }
    }

    public class GetByLock extends Action {
        public GetByLock() {}

        @Override
        public IActionResult execute(Request req) throws Exception {
            String lock = req.params.get("lock");

            List<Billboard> res = CollectionFactory.getInstance(Billboard.class).get(x -> String.valueOf(x.locked) == lock);

            return new Ok(res);
        }
    }

    public class Insert extends Action {
        public Insert() {}

        @Override
        public IActionResult execute(Request req) throws Exception {
            if (req.body instanceof Billboard) {
                CollectionFactory.getInstance(Billboard.class).insert((Billboard) req.body);
                return new Ok();
            }

            return new UnsupportedType(Billboard.class);
        }
    }

    public class Update extends Action {
        public Update() {}

        @Override
        public IActionResult execute(Request req) throws Exception {
            if (req.body instanceof Billboard) {
                CollectionFactory.getInstance(Billboard.class).update((Billboard) req.body);
                return new Ok();
            }

            return new UnsupportedType(Billboard.class);
        }
    }

    public class Delete extends Action {
        public Delete() {}

        @Override
        public IActionResult execute(Request req) throws Exception {
            if (req.body instanceof Billboard) {
                Optional<Session> session = TokenService.getInstance().getSessionByToken(req.token);
                if (session.isEmpty()) return new BadRequest("No valid session");

                Optional<Permissions> perms = CollectionFactory.getInstance(Permissions.class).get(p -> p.username == session.get().username).stream().findFirst();
                if (perms.isEmpty()) return new BadRequest("No valid perms");

                Permissions permissions = perms.get();

                if (permissions.canEditBillboard) {
                    CollectionFactory.getInstance(Billboard.class).delete((Billboard) req.body);
                    return new Ok();
                } else {
                    Optional<Billboard> billboard = CollectionFactory.getInstance(Billboard.class).get(b -> b.userId == session.get().userId).stream().findFirst();
                    if (billboard.isEmpty()) return new BadRequest("User cannot delete this billboard");
                    
                    CollectionFactory.getInstance(Billboard.class).delete((Billboard) req.body);
                    return new Ok();
                }
            }

            return new UnsupportedType(Billboard.class);
        }
    }


}

