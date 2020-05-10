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

/**
 * This class handles permission checks to ensure the server fulfill the request if the user has the correct permissions.
 *
 * @author Jamie Martin
 * @author Perdana Bailey
 */
public class Permission {

    /**
     * This is an Action class that ensures the user can create billboards.
     */
    public class canCreateBillboard extends Action {
        // Generic canCreateBillboard Constructor.
        public canCreateBillboard() {}

        /**
         * Override the default execute function with permission check.
         *
         * @param req: The user request.
         * @return IActionResult: This object is for the router that returns whether they have the specified permission.
         * @throws Exception: Pass through server error.
         */
        @Override
        public IActionResult execute(Request req) throws Exception {
            if (!req.permissions.canCreateBillboard) return new Unauthorised("Cannot Create Billboards. ");

            return new Ok();
        }
    }


    /**
     * This is an Action class that ensures the user can edit billboards.
     */
    public class canEditBillboard extends Action {
        public canEditBillboard() {}

        /**
         * Override the default execute function with permission check.
         *
         * @param req: The user request.
         * @return IActionResult: This object is for the router that returns whether they have the specified permission.
         * @throws Exception: Pass through server error.
         */
        @Override
        public IActionResult execute(Request req) throws Exception {
            if (!req.permissions.canEditBillboard) {
                if (!(req.body instanceof Billboard)) return new UnsupportedType(Billboard.class);

                Optional<Billboard> billboard = CollectionFactory.getInstance(Billboard.class).get(b -> ((Billboard) req.body).id == b.id).stream().findFirst();
                if (billboard.isEmpty()) return new BadRequest("Billboard does not exist. ");

                if (billboard.get().userId != req.session.userId) return new Unauthorised("Not authorised to edit billboards. ");
            }

            return new Ok();
        }
    }

    /**
     * This is an Action class that ensures the user has self permissions on billboards.
     */
    public class isSelf extends Action {
        public isSelf() {}

        /**
         * Override the default execute function with permission check.
         *
         * @param req: The user request.
         * @return IActionResult: This object is for the router that returns whether they have the specified permission.
         * @throws Exception: Pass through server error.
         */
        @Override
        public IActionResult execute(Request req) throws Exception {
            if (!(req.body instanceof User)) return new UnsupportedType(User.class);
            if (req.session.userId != ((User) req.body).id) return new Unauthorised("Not authorised to edit password. ");

            return new Ok();
        }
    }

    /**
     * This is an Action class that ensures the user can create users.
     */
    public class canEditUser extends Action {
        public canEditUser() {}

        /**
         * Override the default execute function with permission check.
         *
         * @param req: The user request.
         * @return IActionResult: This object is for the router that returns whether they have the specified permission.
         * @throws Exception: Pass through server error.
         */
        @Override
        public IActionResult execute(Request req) throws Exception {
            if (!req.permissions.canEditUser) return new Unauthorised("Not authorised to edit user. ");

            return new Ok();
        }
    }

    /**
     * This is an Action class that ensures the user can schedule billboards.
     */
    public class canScheduleBillboard extends Action {
        public canScheduleBillboard() {}

        /**
         * Override the default execute function with permission check.
         *
         * @param req: The user request.
         * @return IActionResult: This object is for the router that returns whether they have the specified permission.
         * @throws Exception: Pass through server error.
         */
        @Override
        public IActionResult execute(Request req) throws Exception {
            if (!req.permissions.canScheduleBillboard) return new Unauthorised("Not authorised to schedule billboards. ");

            return new Ok();
        }
    }

    /**
     * This is an Action class that ensures the user can view billboards.
     */
    public class canViewBillboard extends Action {
        public canViewBillboard() {}

        /**
         * Override the default execute function with permission check.
         *
         * @param req: The user request.
         * @return IActionResult: This object is for the router that returns whether they have the specified permission.
         * @throws Exception: Pass through server error.
         */
        @Override
        public IActionResult execute(Request req) throws Exception {
            if (!req.permissions.canViewBillboard) return new Unauthorised("Not authorised to view billboards. ");

            return new Ok();
        }
    }

}
