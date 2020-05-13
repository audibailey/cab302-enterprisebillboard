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
    public static class Get extends Action {
        // Generic Get action constructor.
        public Get() { }

        // Override the execute to run the get function of the permissions collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            // Get list of all billboards.
            List<Permissions> permissionsList = CollectionFactory.getInstance(Permissions.class).get(x -> true);

            // Return a success IActionResult with the list of permissions.
            return new Ok(permissionsList);
        }
    }

    /**
     * This Action is the GetByUsername Action for the permissions.
     */
    public static class GetByUsername extends Action {
        // Generic GetById action constructor.
        public GetByUsername() { }

        // Override the execute to run the get function of the permissions collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            String username = req.params.get("username");
            
            // Ensure id field is not null.
            if (username == null) {
                return new BadRequest("Must specify a username.");
            }

            // Get list of permissions with the ID as specified. This should only return 1 permission.
            List<Permissions> permissionsList = CollectionFactory.getInstance(Permissions.class).get(
                permissions -> username.equals(String.valueOf(permissions.username))
            );

            // Return a success IActionResult with the list of permissions.
            return new Ok(permissionsList);
        }

    }

    /**
     * This Action is the Update Action for the permissions.
     */
    public static class Update extends Action {
        // Generic Update action constructor.
        public Update() { }

        // Override the execute to run the update function of the permissions collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            // Return an error on incorrect body type.
            if (!(req.body instanceof Permissions)) return new UnsupportedType(Permissions.class);

            // Attempt to update the permission in the database then return a success IActionResult.
            CollectionFactory.getInstance(Permissions.class).update((Permissions) req.body);
            return new Ok();
        }
    }
}
