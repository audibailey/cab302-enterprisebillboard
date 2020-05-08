package server.endpoints.permission;


import common.Status;
import common.models.Permissions;
import common.models.Request;
import common.models.Response;
import server.database.DataService;
import server.endpoints.schedule.GetScheduleHandler;
import server.endpoints.user.UpdateUserHandler;
import server.middleware.MiddlewareHandler;

import java.sql.SQLException;

/**
 * This class handles how the permission endpoints interact with the permission database handler
 * and the client.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 **/
public class PermissionHandler {
    DataService db;
    MiddlewareHandler middlewareHandler;

    /**
     * The ScheduleHandler Constructor.
     *
     * @param db: This is the DataService handler that connects the Endpoint to the database.
     */
    public PermissionHandler(DataService db, MiddlewareHandler middlewareHandler) {
        this.db = db;
        this.middlewareHandler = middlewareHandler;
    }

    /**
     * The Endpoint PermissionHandler get method, used to list Permissions or retrieve a singular
     * Permission.
     *
     * @param data: This is used as the parameter for the method being used.
     * @param <T>:  This generic determines whether to retrieve a list of Permissions or a single
     *              Permission.
     * @return Response<?>: This is the response to send back to the client.
     */
    private <T> Response<?> get(T data) {
        // Check the object type
        if (data instanceof String) {
            // Get permission by username
            return GetPermissionHandler.getPermissionByUsername(this.db, (String) data);
        } else {
            // return an error response
            return new Response<>(Status.UNSUPPORTED_TYPE, "Unknown parameter received in data field.");
        }
    }

    /**
     * The Endpoint PermissionHandler post method, used to insert Permissions into database
     *
     * @param data: This is used as the parameter for the method being used.
     * @param <T>:  This generic is the Permission being inserted.
     * @return Response<?>: This is the response to send back to the client.
     */
    private <T> Response<?> post(T data) {
        // Check the object data type
        if (data instanceof Permissions) {
            Permissions permission = (Permissions) data;
            // Check if there is an ID
            if (permission.id > 0) {
                try {
                    // Check if the user exist
                    if (db.users.get(permission.username).isPresent()) {
                        return PostPermissionHandler.insertPermission(this.db, permission);
                    } else {
                        return new Response<>(Status.BAD_REQUEST, "User is not existed.");
                    }
                } catch (SQLException e) {
                    // TODO: Console Log this
                    return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to add permission to the database.");
                }

            } else {
                return new Response<>(Status.BAD_REQUEST, "Invalid permission ID.");
            }

        } else {
            return new Response<>(Status.UNSUPPORTED_TYPE, "Unknown parameter received in data field.");
        }
    }


    /**
     * The Endpoint PermissionHandler update method, used to update permissions in the database
     *
     * @param data: This is used as the parameter for the method being used.
     * @param <T>:  This generic is the Permission being updated and its new data.
     * @return Response<?>: This is the response to send back to the client.
     */
    private <T> Response<?> update(T data) {
        // Check the object data type
        if (data instanceof Permissions) {
            Permissions permissions = (Permissions) data;
            // Check the ID
            if (permissions.id > 0) {
                try {
                    if (db.users.get(permissions.username).isPresent()) {
                        return UpdatePermissionHandler.updatePermission(this.db, permissions);
                    } else {
                        return new Response<>(Status.BAD_REQUEST, "User doesn't exist.");
                    }
                } catch (SQLException e) {
                    // TODO: Console Log this
                    return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to add permission to the database.");
                }
            } else {
                return new Response<>(Status.BAD_REQUEST, "Invalid permission ID.");
            }

        } else {
            return new Response<>(Status.UNSUPPORTED_TYPE, "Unknown parameter received in data field.");
        }
    }

    /**
     * The Endpoint PermissionHandler route method, used to determine which type of Endpoint
     * PermissionHandler function is needed.
     *
     * @param request: This is the request that needs to be determined.
     * @return Response<?>: This is the response to send back to the client.
     */
    public Response<?> route(Request<?> request) {
        // Check the methods to determine which type of Endpoint PermissionHandler function is needed.
        switch (request.method) {
            case GET_PERMISSION:
                // Check the token
                if (this.middlewareHandler.checkToken(request.token)) {
                    // Check the permission
                    if (this.middlewareHandler.checkCanEditUser(request.token)) {
                        return this.get(request.data);
                    } else {
                        // Check if the token's user is the same with request.data user
                        if (this.middlewareHandler.checkSameUser(request.token, request.data)) {
                            return this.get(request.data);
                        } else {
                            return new Response<>(Status.UNAUTHORIZED, "User does not have permissions to get permissions.");
                        }
                    }
                } else {
                    return new Response<>(Status.UNAUTHORIZED, "Token invalid.");
                }
            case POST_PERMISSION:
                // Check the token
                if (this.middlewareHandler.checkToken(request.token)) {
                    // Check the permission
                    if (this.middlewareHandler.checkCanEditUser(request.token)) {
                        return this.post(request.data);
                    } else {
                        return new Response<>(Status.UNAUTHORIZED, "User does not have permissions to insert permissions.");
                    }
                } else {
                    return new Response<>(Status.UNAUTHORIZED, "Token invalid.");
                }
            case UPDATE_PERMISSION:
                // Check the token
                if (this.middlewareHandler.checkToken(request.token)) {
                    // Check the permission
                    if (this.middlewareHandler.checkCanEditUser(request.token)) {
                        return this.update(request.data);
                    } else {
                        return new Response<>(Status.UNAUTHORIZED, "User does not have permissions to update permissions.");
                    }
                } else {
                    return new Response<>(Status.UNAUTHORIZED, "Token invalid.");
                }
            default:
                return new Response<>();
        }
    }
}
