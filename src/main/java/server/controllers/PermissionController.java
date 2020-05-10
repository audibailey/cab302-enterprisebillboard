package server.controllers;

import common.models.Billboard;
import common.models.Permissions;
import common.models.Schedule;
import common.models.User;
import common.router.*;
import server.middleware.Permission;
import server.router.*;
import server.services.Session;
import server.services.TokenService;
import server.sql.CollectionFactory;

import java.util.List;
import java.util.Optional;

/**
 * This class acts as the controller with all the Actions related to the permissions request path.
 *
 * @author Jamie Martin
 * @author Kevin Huynh
 * @author Perdana Bailey
 */
public class PermissionController {

    /**
     * This Action is the get all Action for the permissions.
     */
    public class Get extends Action {
        // Generic Get action constructor.
        public Get() { }

        // Override the execute to run the get function of the permissions collection.
        @Override
        public IActionResult execute(Request req) throws Exception {

            Optional<Session> session = TokenService.getInstance().getSessionByToken(req.token);
            if (session.isEmpty()) return new BadRequest("No valid session");

            Optional<Permissions> perms = CollectionFactory.getInstance(Permissions.class).get(p -> p.username == session.get().username).stream().findFirst();
            if (perms.isEmpty()) return new BadRequest("No valid permissions");

            Permissions permissions = perms.get();
            if (permissions.canEditUser || session.get().username == ((Permissions)req.body).username)
            {
                List<Permissions> permissionsList = CollectionFactory.getInstance(Permissions.class).get(permission -> true);
                return new Ok(permissionsList);
            }

            return new BadRequest("Can't view other users' permissions");
        }
    }

    /**
     * This Action is the GetByID Action for the permissions.
     */
    public class GetById extends Action {
        // Generic GetById action constructor.
        public GetById() { }

        // Override the execute to run the get function of the permissions collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            String id = req.params.get("id");

            // Ensure id field is not null.
            if (id == null) {
                return new BadRequest("Must specify a permission ID.");
            }

            // Get list of permissions with the ID as specified. This should only return 1 permission.
            List<Permissions> permissionsList = CollectionFactory.getInstance(Permissions.class).get(
                permissions -> id.equals(String.valueOf(permissions.id))
            );

            // Return a success IActionResult with the list of permissions.
            return new Ok(permissionsList);
        }
    }

    /**
     * This Action is the Insert Action for the permissions.
     */
    public class Insert extends Action {
        // Generic Insert action constructor.
        public Insert() { }

        // Override the execute to run the insert function of the permissions collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            // Ensure the body is of type permissions.
            if (req.body instanceof Permissions) {
                // Attempt to insert the permission into the database then return a success IActionResult.
                CollectionFactory.getInstance(Permissions.class).insert((Permissions) req.body);
                return new Ok();
            }

            // Return an error on incorrect body type.
            return new UnsupportedType(Permissions.class);
        }
    }

    /**
     * This Action is the Update Action for the permissions.
     */
    public class Update extends Action {
        // Generic Update action constructor.
        public Update() { }

        // Override the execute to run the update function of the permissions collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            // Ensure the body is of type permissions.
            if (req.body instanceof Permissions) {
                // Attempt to update the permission in the database then return a success IActionResult.
                CollectionFactory.getInstance(Permissions.class).update((Permissions) req.body);
                return new Ok();
            }

            // Return an error on incorrect body type.
            return new UnsupportedType(Permissions.class);
        }
    }
}
