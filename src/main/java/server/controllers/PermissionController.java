package server.controllers;

import common.router.*;
import server.router.*;

public class PermissionController {

    public class Get extends Action {
        public Get() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            return new NotFound();
        }
    }

    public class GetById extends Action {
        public GetById() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            return new NotFound();
        }
    }

    public class Insert extends Action {
        public Insert() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            return new NotFound();
        }
    }

    public class Update extends Action {
        public Update() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            return new NotFound();
        }
    }
}
