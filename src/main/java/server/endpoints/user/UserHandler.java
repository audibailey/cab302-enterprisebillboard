package server.endpoints.user;

import common.models.Request;
import common.models.Response;
import server.database.DataService;
import server.middleware.MiddlewareHandler;

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
        return new Response<>();
    }

    /**
     * The Endpoint UserHandler post method, used to insert users into database
     *
     * @param data: This is used as the parameter for the method being used.
     * @param <T>:  This generic is the user being inserted.
     * @return Response<?>: This is the response to send back to the client.
     */
    private <T> Response<?> post(T data) {
        return new Response<>();
    }

    /**
     * The Endpoint UserHandler delete method, used to delete users from the database
     *
     * @param data: This is used as the parameter for the method being used.
     * @param <T>:  This generic is the user being deleted.
     * @return Response<?>: This is the response to send back to the client.
     */
    private <T> Response<?> delete(T data) {
        return new Response<>();
    }

    /**
     * The Endpoint BillboardHandler update method, used to update billboards in the database
     *
     * @param data: This is used as the parameter for the method being used.
     * @param <T>:  This generic is the user being updated and its new data.
     * @return Response<?>: This is the response to send back to the client.
     */
    private <T> Response<?> update(T data) {
        return new Response<>();
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
                return new Response<>();
            case UPDATE_USER:
                return new Response<>();
            case POST_USER:
                return new Response<>();
            case DELETE_USER:
                return new Response<>();
            default:
                return new Response<>();
        }
    }

}
