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
            if (id == null) return new BadRequest("Parameter required: id");

            List<common.models.Billboard> res = CollectionFactory.getInstance(Billboard.class).get(x -> id == String.valueOf(x.id));

            return new Ok(res);
        }
    }

    public class GetByLock extends Action {
        public GetByLock() {}

        @Override
        public IActionResult execute(Request req) throws Exception {
            String lock = req.params.get("lock");
            if (lock == null) return new BadRequest("Parameter required: lock");

            var lockBool = Boolean.getBoolean(lock);
            List<Billboard> res = CollectionFactory.getInstance(Billboard.class).get(x -> lockBool == x.locked);

            return new Ok(res);
        }
    }

    public class Insert extends Action {
        public Insert() {}

        @Override
        public IActionResult execute(Request req) throws Exception {
            if (!(req.body instanceof Billboard)) return new UnsupportedType(Billboard.class);

            CollectionFactory.getInstance(Billboard.class).insert((Billboard) req.body);
            return new Ok();
        }
    }

    public class Update extends Action {
        public Update() {}

        @Override
        public IActionResult execute(Request req) throws Exception {
            if (!(req.body instanceof Billboard)) return new UnsupportedType(Billboard.class);

            CollectionFactory.getInstance(Billboard.class).delete((Billboard) req.body);
            return new Ok();
        }
    }

    public class Delete extends Action {
        public Delete() {}

        @Override
        public IActionResult execute(Request req) throws Exception {
            if (!(req.body instanceof Billboard)) return new UnsupportedType(Billboard.class);

            CollectionFactory.getInstance(Billboard.class).delete((Billboard) req.body);
            return new Ok();
        }
    }


}

