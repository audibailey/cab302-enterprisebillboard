package server.middleware;

import common.models.Permissions;
import server.router.*;
import common.router.*;
import server.services.Session;
import server.services.TokenService;
import server.sql.CollectionFactory;

import java.util.List;
import java.util.Optional;

public class Permission {

    public class canCreateBillboard extends Action {
        public canCreateBillboard() {}
        @Override
        public IActionResult execute(Request req) throws Exception {

            Optional<Session> session = TokenService.getInstance().getSessionByToken(req.token);
            if (session.isEmpty()) return new BadRequest("No session");

            Optional<Permissions> perms = CollectionFactory.getInstance(Permissions.class).get(p -> p.username == session.get().username).stream().findFirst();

            if (perms.isEmpty()) return new BadRequest("No perms found");

            if (perms.get().canCreateBillboard) return new Ok();
            else return new Unauthorised();
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
