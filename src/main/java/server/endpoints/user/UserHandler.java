package server.endpoints.user;

import common.Status;
import common.models.Billboard;
import common.models.Request;
import common.models.Response;
import common.models.User;
import server.database.DataService;
import server.endpoints.billboard.GetBillboardHandler;
import server.endpoints.billboard.PostBillboardHandler;
import server.endpoints.billboard.UpdateBillboardHandler;
import server.middleware.MiddlewareHandler;

import java.sql.SQLException;

/**
 * This class handles how the user endpoints interact with the user database handler
 * and the client.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 **/
public class UserHandler {
    DataService db;
    MiddlewareHandler middlewareHandler;

    /**
     * The UserHandler Constructor.
     *
     * @param db: This is the DataService handler that connects the Endpoint to the database.
     */
    public UserHandler(DataService db, MiddlewareHandler middlewareHandler) {
        this.db = db;
        this.middlewareHandler = middlewareHandler;
    }

    /**
     * The Endpoint UserHandler get method, used to list users or retrieve a singular
     * user.
     *
     * @param data: This is used as the parameter for the method being used.
     * @param <T>:  This generic determines whether to retrieve a list of users or a single
     *              user.
     * @return Response<?>: This is the response to send back to the client.
     */
    private <T> Response<?> get(T data) {

        // Check the object type
        if (data instanceof Integer) {
            // get user using ID
            return GetUserHandler.getUserByID(this.db, (int) data);
        } else if (data instanceof String) {
            // get user using username
            return GetUserHandler.getUserByUsername(this.db, (String) data);
        } else if (data == null) {
            // get all users
            return GetUserHandler.getAllUsers(this.db);
        } else {
            // return an error response
            return new Response<>(Status.UNSUPPORTED_TYPE, "Unknown parameter received in data field.");
        }
    }

    /**
     * The Endpoint UserHandler post method, used to insert users into database
     *
     * @param data: This is used as the parameter for the method being used.
     * @param <T>:  This generic is the user being inserted.
     * @return Response<?>: This is the response to send back to the client.
     */
    private <T> Response<?> post(T data) {
        // Check the object data type
        if (data instanceof User) {
            User user = (User) data;
            // Check if there is an ID
            if (user.id > 0) {
                // Check the name -> make sure no other username  exists else return error
                try {
                    if (db.users.get(user.id).isEmpty()) {
                        return PostUserHandler.insertUser(this.db, user);
                        //return new Response<>();
                    } else {
                        return new Response<>(Status.BAD_REQUEST, "User already existed.");
                    }
                } catch (SQLException e) {
                    // TODO: Console Log this
                    return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to add user to the database.");
                }
            } else {
                return new Response<>(Status.BAD_REQUEST, "User object contains ID.");
            }
        } else {
            return new Response<>(Status.UNSUPPORTED_TYPE, "Unknown parameter received in data field.");
        }
    }

    /**
     * The Endpoint UserHandler delete method, used to delete users from the database
     *
     * @param data: This is used as the parameter for the method being used.
     * @param <T>:  This generic is the user being deleted.
     * @return Response<?>: This is the response to send back to the client.
     */
    private <T> Response<?> delete(T data) {
        // Check the object data type
        if (data instanceof User) {
            User user = (User) data;
            //Check the ID, if the ID is larger than 0 -> continue else return error
            if (user.id > 0) {
                try {
                    if (db.users.get(user.username).isPresent()) {
                        return DeleteUserHandler.deleteUser(this.db, user);
                    } else {
                        return new Response<>(Status.BAD_REQUEST, "User doesn't exist.");
                    }
                } catch (SQLException e) {
                    // TODO: Console Log this
                    return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to add user to the database.");
                }
            } else {
                return new Response<>(Status.BAD_REQUEST, "Invalid user ID.");
            }
        } else {
            return new Response<>(Status.UNSUPPORTED_TYPE, "Unknown parameter received in data field.");
        }
    }

    /**
     * The Endpoint UserHandler update method, used to update user in the database
     *
     * @param data: This is used as the parameter for the method being used.
     * @param <T>:  This generic is the user being updated and its new data.
     * @return Response<?>: This is the response to send back to the client.
     */
    private <T> Response<?> update(T data) {
        // Check the object data type
        if (data instanceof User) {
            User user = (User) data;
            //Check the ID, if the ID is larger than 0 -> continue else return error
            if (user.id > 0) {
                try {
                    if (db.users.get(user.username).isPresent()) {
                        return UpdateUserHandler.updateUser(this.db, user);
                    } else {
                        return new Response<>(Status.BAD_REQUEST, "User doesn't exist.");
                    }
                } catch (SQLException e) {
                    // TODO: Console Log this
                    return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to add user to the database.");
                }
            } else {
                return new Response<>(Status.BAD_REQUEST, "Invalid user ID.");
            }
        } else {
            return new Response<>(Status.UNSUPPORTED_TYPE, "Unknown parameter received in data field.");
        }
    }

    /**
     * The Endpoint UserHandler route method, used to determine which type of Endpoint
     * UserHandler function is needed.
     *
     * @param request: This is the request that needs to be determined.
     * @return Response<?>: This is the response to send back to the client.
     */
    public Response<?> route(Request<?> request) {
        // Check the methods to determine which type of Endpoint UserHandler function is needed.
        switch (request.method) {
            case GET_USERS:
                return this.get(request.data);
            case POST_USER:
                // Check the token
                if (this.middlewareHandler.checkToken(request.token)) {
                    // Check the permission
                    if (this.middlewareHandler.checkCanEditUser(request.token)) {
                        return this.post(request.data);
                    } else {
                        return new Response<>(Status.UNAUTHORIZED, "User does not have permissions to post user.");
                    }
                } else {
                    return new Response<>(Status.UNAUTHORIZED, "Token invalid.");
                }
            case DELETE_USER:
                //Check the token
                if (this.middlewareHandler.checkToken(request.token)) {
                    //
                    if (this.middlewareHandler.checkCanEditUser(request.token)) {
                        return this.delete(request.data);
                    } else {
                        return new Response<>(Status.UNAUTHORIZED, "User does not have permissions to delete other users.");
                    }
                } else {
                    return new Response<>(Status.UNAUTHORIZED, "Token invalid.");
                }
            case UPDATE_USER:
                //Check the token
                if (this.middlewareHandler.checkToken(request.token)) {
                    //
                    if (this.middlewareHandler.checkCanEditUser(request.token)) {
                        return this.update(request.data);
                    } else {
                        return new Response<>(Status.UNAUTHORIZED, "User does not have permissions to update other users.");
                    }
                } else {
                    return new Response<>(Status.UNAUTHORIZED, "Token invalid.");
                }
            default:
                return new Response<>(Status.NOT_FOUND, "Route not found.");
        }
    }

}
