package server.controllers;

import common.models.Permissions;
import common.models.User;
import common.router.*;
import server.router.*;
import server.services.Session;
import server.services.TokenService;
import server.sql.CollectionFactory;

import java.util.List;
import java.util.Optional;

/**
 * This class acts as the controller with all the Actions related to the user request path.
 *
 * @author Jamie Martin
 * @author Kevin Huynh
 * @author Perdana Bailey
 */
public class UserController {

    /**
     * This Action is the get all Action for the users.
     */
    public class Get extends Action {
        // Generic Get action constructor.
        public Get() { }

        // Override the execute to run the get function of the user collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            // Get list of all users.
            List<User> userList = CollectionFactory.getInstance(User.class).get(x -> true);

            // Return a success IActionResult with the list of users.
            return new Ok(userList);
        }
    }

    /**
     * This Action is the GetByID Action for the users.
     */
    public class GetById extends Action {
        // Generic GetById action constructor.
        public GetById() { }

        // Override the execute to run the get function of the user collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            String id = req.params.get("id");

            // Ensure id field is not null.
            if (id == null) {
                return new BadRequest("Must specify a user ID.");
            }

            // Get list of users with the ID as specified. This should only return 1 user.
            List<User> userList = CollectionFactory.getInstance(User.class).get(
                user -> id.equals(String.valueOf(user.id))
            );

            // Return a success IActionResult with the list of users.
            return new Ok(userList);
        }
    }

    /**
     * This Action is the Insert Action for the users.
     */
    public class Insert extends Action {
        // Generic Insert action constructor
        public Insert() { }

        // Override the execute to run the insert function of the user collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            // Ensure the body is of type user.
            if (req.body instanceof User) {
                // TODO: Generate hash for database
                // Attempt to insert the user into the database then return a success IActionResult.
                CollectionFactory.getInstance(User.class).insert((User) req.body);
                return new Ok();
            }

            // Return an error on incorrect body type.
            return new UnsupportedType(User.class);
        }
    }

    /**
     * This Action is the Update Action for the users.
     */
    public class Update extends Action {
        // Generic Update action constructor.
        public Update() { }

        // Override the execute to run the update function of the user collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            // Ensure the body is of type user.
            if (req.body instanceof User) {
                // Attempt to update the user into the database then return a success IActionResult.
                CollectionFactory.getInstance(User.class).update((User) req.body);
                return new Ok();
            }

            // Return an error on incorrect body type.
            return new UnsupportedType(User.class);
        }
    }

    public class UpdatePassword extends Action {
        public UpdatePassword() { }

        @Override
        public IActionResult execute(Request req) throws Exception {
            if (!(req.body instanceof User)) return new UnsupportedType(User.class);

            // TODO: UPDATE PASSWORD FUNCTION
            // CollectionFactory.getInstance(User.class).update((User) req.body);
            return new Ok();
        }
    }

    /**
     * This Action is the Delete Action for the users.
     */
    public class Delete extends Action {
        // Generic Delete action constructor.
        public Delete() { }

        // Override the execute to run the delete function of the user collection.
        @Override
        public IActionResult execute(Request req) throws Exception {
            // Ensure the body is of type user.
            if (req.body instanceof User) {
                // Attempt to delete the user and permission in the database then return a success IActionResult.
                CollectionFactory.getInstance(Permissions.class).delete((Permissions) req.body);
                CollectionFactory.getInstance(User.class).delete((User) req.body);
                return new Ok();
            }

            // Return an error on incorrect body type.
            return new UnsupportedType(User.class);
        }
    }
}
