package server.controllers;

import common.models.Billboard;
import common.models.Schedule;
import common.models.User;
import common.router.*;
import server.router.*;
import server.sql.CollectionFactory;

import java.util.List;

public class ScheduleController {

    public class Get extends Action {
        public Get() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            List<Schedule> res = CollectionFactory.getInstance(Schedule.class).get(x -> true);

            return new Ok(res);
        }
    }

    public class GetById extends Action {
        public GetById() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            String id = req.params.get("id");
            if (id == null) return new BadRequest("No ID");

            List<Schedule> res = CollectionFactory.getInstance(Schedule.class).get(x -> id == String.valueOf(x.id));
            return new Ok(res);
        }
    }

    public class Insert extends Action {
        public Insert() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            if (req.body instanceof Schedule) {
                CollectionFactory.getInstance(Schedule.class).insert((Schedule) req.body);
                return new Ok();
            }

            return new UnsupportedType(Schedule.class);
        }
    }

    public class Delete extends Action {
        public Delete() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            if (req.body instanceof Schedule) {
                CollectionFactory.getInstance(Schedule.class).delete((Schedule) req.body);
                return new Ok();
            }

            return new UnsupportedType(Schedule.class);
        }
    }
}
