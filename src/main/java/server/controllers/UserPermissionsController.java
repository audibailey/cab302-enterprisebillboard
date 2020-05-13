package server.controllers;

import common.models.Permissions;
import common.models.User;
import common.models.UserPermissions;
import common.router.IActionResult;
import common.router.Ok;
import common.router.Request;
import common.router.UnsupportedType;
import common.utils.RandomFactory;
import server.router.Action;
import server.sql.CollectionFactory;

import static common.utils.HashingFactory.encodeHex;
import static common.utils.HashingFactory.hashPassword;

/**
 * This class acts as the controller with all the Actions related to the schedule request path.
 *
 * @author Jamie Martin
 * @author Kevin Huynh
 * @author Perdana Bailey
 */
public class UserPermissionsController {

    /**
     * This Action is the Insert Action for the users.
     */
    public static class Insert extends Action {
        // Generic Insert action constructor
        public Insert() { }

        // Override the execute to run the insert function of the user collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            // Ensure the body is of type user.
            if (!(req.body instanceof UserPermissions)) return new UnsupportedType(UserPermissions.class);

            UserPermissions userPermissions = (UserPermissions)req.body;
            if (userPermissions.user == null || userPermissions.permissions == null) return new UnsupportedType(UserPermissions.class);

            User user = userPermissions.user;
            Permissions permissions = userPermissions.permissions;

            // Hash the password supplied and set the respective user objects for database insertion.
            byte[] salt = RandomFactory.String().getBytes();
            byte[] password = hashPassword(user.password, salt, 64);
            user.salt = encodeHex(salt);
            user.password = encodeHex(password);

            // Attempt to insert the user into the database then return a success IActionResult.
            CollectionFactory.getInstance(User.class).insert(user);
            permissions.username = user.username;
            CollectionFactory.getInstance(Permissions.class).insert(permissions);

            return new Ok();
        }
    }

}
