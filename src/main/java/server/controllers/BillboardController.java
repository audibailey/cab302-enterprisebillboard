package server.controllers;

import common.models.Billboard;
import common.router.*;
import server.router.*;
import server.sql.CollectionFactory;

import java.util.List;

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
                CollectionFactory.getInstance(Billboard.class).delete((Billboard) req.body);
                return new Ok();
            }

            return new UnsupportedType(Billboard.class);
        }
    }


}

