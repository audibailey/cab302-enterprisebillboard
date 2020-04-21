package server.endpoints.billboard;


import common.Status;
import common.models.Billboard;
import common.models.Request;
import common.models.Response;
import server.database.DataService;
import server.middleware.MiddlewareHandler;

/**
 * This class handles how the billboard endpoints interact with the billboard database handler
 * and the client.
 *
 * @author Perdana Bailey
 */
public class BillboardHandler {

    DataService db;
    MiddlewareHandler middlewareHandler;

    /**
     * The BillboardHandler Constructor.
     *
     * @param db: This is the DataService handler that connects the Endpoint to the database.
     */
    public BillboardHandler(DataService db, MiddlewareHandler middlewareHandler) {
        this.db = db;
        this.middlewareHandler = middlewareHandler;
    }

    /**
     * The Endpoint BillboardHandler get method, used to list billboards or retrieve a singular
     * billboard.
     *
     * @param data: This is used as the parameter for the method being used.
     * @param <T>:  This generic determines whether to retrieve a list of billboards or a single
     *              billboard.
     * @return Response<?>: This is the response to send back to the client.
     */
    private <T> Response<?> get(T data) {
        // Check the object type
        if (data instanceof Boolean) {
            // Check if the request is listing locked or unlocked billboards
            if ((Boolean) data) {
                // Fetch the locked billboards and return the response to the client
                return GetBillboardHandler.getLockedBillboards(this.db);
            } else {
                // Fetch the unlocked billboards and return the response to the client
                return GetBillboardHandler.getUnlockedBillboards(this.db);
            }
        } else if (data instanceof Integer) {
            // get billboard using ID
            return new Response<>();
        } else if (data instanceof String) {
            // get billboard using billboard name
            return new Response<>();
        } else if (data == null) {
            // get all
            return new Response<>();
        } else {
            // return an error response
            return new Response<>(Status.FAILED, "Invalid Request: Unknown Get parameter.");
        }
    }

    /**
     * The Endpoint BillboardHandler route method, used to determine which type of Endpoint
     * BillboardHandler function is needed.
     *
     * @param request: This is the request that needs to be determined.
     * @return Response<?>: This is the response to send back to the client.
     */
    public Response<?> route(Request<?> request) {
        // Check the methods to determine which type of Endpoint BillboardHandler function is needed.
        switch (request.method) {
            case GET_BILLBOARDS:
                // This is the get function
                return this.get(request.data);
            case POST_BILLBOARD:
                if (this.middlewareHandler.checkToken(request.token)) {
                    if (this.middlewareHandler.checkCanViewBillboard(request.token)) {
                        return this.get(request.data);
                    } else {
                        return new Response<>(Status.FAILED, "Invalid Request: Unauthorized.");
                    }
                } else {
                    return new Response<>(Status.FAILED, "Invalid Request: Token invalid.");
                }
            default:
                return new Response<>();
        }
    }

}
