package server.middleware;

import server.router.*;
import common.router.*;

public class Permission {

    public class canCreateBillboard extends Action {
        public canCreateBillboard() {}
        @Override
        public IActionResult execute(Request req) throws Exception {

            return new Ok();
        }
    }

    public class canEditBillboard extends Action {
        public canEditBillboard() {}
        @Override
        public IActionResult execute(Request req) throws Exception {

            return new Ok();
        }
    }

    public class canEditUser extends Action {
        public canEditUser() {}
        @Override
        public IActionResult execute(Request req) throws Exception {

            return new Ok();
        }
    }

    public class canScheduleBillboard extends Action {
        public canScheduleBillboard() {}
        @Override
        public IActionResult execute(Request req) throws Exception {

            return new Ok();
        }
    }

    public class canViewBillboard extends Action {
        public canViewBillboard() {}
        @Override
        public IActionResult execute(Request req) throws Exception {

            return new Ok();
        }
    }
}
