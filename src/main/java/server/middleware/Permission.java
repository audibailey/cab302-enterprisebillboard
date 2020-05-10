package server.middleware;

import common.models.Billboard;
import common.models.Permissions;
import common.models.User;
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
            if (!req.permissions.canCreateBillboard) return new Unauthorised("Cannot Create Billboards");

            return new Ok();
        }
    }

    public class canEditBillboard extends Action {
        public canEditBillboard() {}
        @Override
        public IActionResult execute(Request req) throws Exception {
            if (!req.permissions.canEditBillboard) {
                if (!(req.body instanceof Billboard)) return new UnsupportedType(Billboard.class);

                Optional<Billboard> billboard = CollectionFactory.getInstance(Billboard.class).get(b -> ((Billboard) req.body).id == b.userId).stream().findFirst();
                if (billboard.isEmpty()) return new BadRequest("Billboard does not exist");

                if (billboard.get().userId != req.session.userId) return new Unauthorised("Not authorised to edit billboards");
            }

            return new Ok();
        }
    }

    public class isSelf extends Action {
        public isSelf() {}
        @Override
        public IActionResult execute(Request req) throws Exception {
            if (!(req.body instanceof User)) return new UnsupportedType(User.class);
            if (req.session.userId != ((User) req.body).id) return new Unauthorised("Not authorised to edit password");

            return new Ok();
        }
    }

    public class canEditUser extends Action {
        public canEditUser() {}
        @Override
        public IActionResult execute(Request req) throws Exception {
            if (!req.permissions.canEditUser) return new Unauthorised("Not authorised to edit user");

            return new Ok();
        }
    }

    public class canScheduleBillboard extends Action {
        public canScheduleBillboard() {}
        @Override
        public IActionResult execute(Request req) throws Exception {
            if (!req.permissions.canScheduleBillboard) return new Unauthorised("Not authorised to schedule billboards");

            return new Ok();
        }
    }

    public class canViewBillboard extends Action {
        public canViewBillboard() {}
        @Override
        public IActionResult execute(Request req) throws Exception {
            if (!req.permissions.canViewBillboard) return new Unauthorised("Not authorised to view billboards");

            return new Ok();
        }
    }

}
