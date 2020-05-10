package server.endpoints.billboard;


import common.router.*;
import server.router.*;

/**
 * This class handles how the billboard endpoints interact with the billboard database handler
 * and the client.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 */
public class BillboardHandler {
//
//    DataService db;
//    MiddlewareHandler middlewareHandler;
//
//    /**
//     * The BillboardHandler Constructor.
//     *
//     * @param db: This is the DataService handler that connects the Endpoint to the database.
//     */
//    public BillboardHandler(DataService db, MiddlewareHandler middlewareHandler) {
//        this.db = db;
//        this.middlewareHandler = middlewareHandler;
//    }
//
//    /**
//     * The Endpoint BillboardHandler get method, used to list billboards or retrieve a singular
//     * billboard.
//     *
//     * @param data: This is used as the parameter for the method being used.
//     * @param <T>:  This generic determines whether to retrieve a list of billboards or a single
//     *              billboard.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    private <T> Response<?> get(T data) {
//        // Check the object type
//        if (data instanceof Boolean) {
//            // Check if the request is listing locked or unlocked billboards
//            if ((Boolean) data) {
//                // Fetch the locked billboards and return the response to the client
//                return GetBillboardHandler.getLockedBillboards(this.db);
//            } else {
//                // Fetch the unlocked billboards and return the response to the client
//                return GetBillboardHandler.getUnlockedBillboards(this.db);
//            }
//        } else if (data instanceof Integer) {
//            // get billboard using ID
//            return GetBillboardHandler.getBillboardsByID(this.db, (int) data);
//        } else if (data instanceof String) {
//            // get billboard using billboard name
//            return GetBillboardHandler.getBillboardsByName(this.db, (String) data);
//        } else if (data == null) {
//            // get all
//            return GetBillboardHandler.getAllBillboards(this.db);
//        } else {
//            // return an error response
//            return new Response<>(Status.UNSUPPORTED_TYPE, "Unknown parameter received in data field.");
//        }
//    }
//
//    /**
//     * The Endpoint BillboardHandler post method, used to insert billboards into database
//     *
//     * @param data: This is used as the parameter for the method being used.
//     * @param <T>:  This generic determines whether to retrieve a list of billboards or a single
//     *              billboard.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    private <T> Response<?> post(T data) {
//        // Check the object data type
//        if (data instanceof Billboard) {
//            Billboard bb = (Billboard) data;
//            // Check if there is an ID
//            if (bb.id > 0) {
//                // Check the name && userID -> make sure no other names and the user ID exists else return error
//                try {
//                    if (db.billboards.get(bb.name).isEmpty() && db.users.get(bb.userId).isPresent()) {
//                        return PostBillboardHandler.insertBillboard(this.db, bb);
//                    } else {
//                        return new Response<>(Status.BAD_REQUEST, "Billboard already exists or user doesn't exist.");
//                    }
//                } catch (SQLException e) {
//                    // TODO: Console Log this
//                    return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to add billboard to the database.");
//                }
//            } else {
//                return new Response<>(Status.BAD_REQUEST, "Billboard object contains ID.");
//            }
//        } else {
//            return new Response<>(Status.UNSUPPORTED_TYPE, "Unknown parameter received in data field.");
//        }
//    }
//
//    /**
//     * The Endpoint BillboardHandler delete method, used to delete billboards from the database
//     *
//     * @param data: This is used as the parameter for the method being used.
//     * @param <T>:  This generic determines whether to retrieve a list of billboards or a single
//     *              billboard.
//     * @param perm: This is used to determine the permission of a user. Perm = 1 when user have editBillboard
//     *              permission, perm = 2 when user doesn't have editBillboard permission
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    private <T> Response<?> delete(T data, int perm) {
//        // Check the object data type
//        if (data instanceof Billboard) {
//            Billboard bb = (Billboard) data;
//            //Check the ID, if ID is larger than 0 -> continue else return error
//            if (bb.id > 0) {
//                if (perm == 1) {
//                    return DeleteBillboardHandler.deleteBillboard(this.db, bb);
//                } else {
//                    // Check the schedule status, if it's not locked we can delete else return error
//                    if (!bb.locked) {
//                        return DeleteBillboardHandler.deleteBillboard(this.db, bb);
//                    } else {
//                        return new Response<>(Status.BAD_REQUEST, "Can't delete billboard that is scheduled");
//                    }
//                }
//            } else {
//                return new Response<>(Status.BAD_REQUEST, "Invalid billboard ID.");
//            }
//        } else {
//            return new Response<>(Status.UNSUPPORTED_TYPE, "Unknown parameter received in data field.");
//        }
//    }
//
//    /**
//     * The Endpoint BillboardHandler update method, used to update billboards in the database
//     *
//     * @param data: This is used as the parameter for the method being used.
//     * @param <T>:  This generic determines whether to retrieve a list of billboards or a single
//     *              billboard.
//     * @param perm  : This is used to determine the permission of a user. Perm = 1 when user have editBillboard
//     *              permission, perm = 2 when user doesn't have editBillboard permission
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    private <T> Response<?> update(T data, int perm) {
//        // Check the object data type
//        if (data instanceof Billboard) {
//            Billboard bb = (Billboard) data;
//            //Check the ID, if ID is larger than 0 -> continue else return error
//            if (bb.id > 0) {
//                // If perm ==1 (user has editAll permission), update the billboard
//                if (perm == 1) {
//                    return UpdateBillboardHandler.updateBillboard(this.db, bb);
//                }
//                // Else user has createBillboard permission
//                else {
//                    // Check the schedule status, if it's not locked we can edit else return error
//                    if (!bb.locked) {
//                        return UpdateBillboardHandler.updateBillboard(this.db, bb);
//                    } else {
//                        return new Response<>(
//                            Status.BAD_REQUEST,
//                            "Can't update billboard that is scheduled");
//                    }
//                }
//
//
//            } else {
//                return new Response<>(Status.BAD_REQUEST, "Invalid billboard ID.");
//            }
//
//        } else {
//            return new Response<>(Status.UNSUPPORTED_TYPE, "Unknown parameter received in data field.");
//        }
//    }
//
//    /**
//     * The Endpoint BillboardHandler route method, used to determine which type of Endpoint
//     * BillboardHandler function is needed.
//     *
//     * @param request: This is the request that needs to be determined.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    public Response<?> route(Request<?> request) {
//        // Check the methods to determine which type of Endpoint BillboardHandler function is needed.
//        switch (request.method) {
//            case GET_BILLBOARDS:
//                // This is the get function, it requires no middleware
//                return this.get(request.data);
//            case POST_BILLBOARD:
//                // Check the token
//                if (this.middlewareHandler.checkToken(request.token)) {
//                    // Check the permissions
//                    if (this.middlewareHandler.checkCanViewBillboard(request.token)) {
//                        return this.post(request.data);
//                    } else {
//                        return new Response<>(Status.UNAUTHORIZED, "User does not have permissions to post billboard.");
//                    }
//                } else {
//                    return new Response<>(Status.UNAUTHORIZED, "Token invalid.");
//                }
//            case DELETE_BILLBOARD:
//                // Check the token
//                if (this.middlewareHandler.checkToken(request.token)) {
//                    // Check if user is deleting their own billboard
//                    if (this.middlewareHandler.checkCanEditBillboard(request.token)) {
//
//                        return this.delete(request.data, 1);
//                    }
//                    // Check if user is deleting other's billboard
//                    else if (this.middlewareHandler.checkCanCreateBillboard(request.token)) {
//                        if (this.middlewareHandler.checkOwnBillboard(request.token, request.data)) {
//                            return this.delete(request.data, 2);
//                        } else {
//                            return new Response<>(Status.UNAUTHORIZED, "Can't delete other user's billboard");
//                        }
//                    } else {
//                        return new Response<>(Status.UNAUTHORIZED, "User does not have permissions to delete billboard.");
//                    }
//                } else {
//                    return new Response<>(Status.UNAUTHORIZED, "Token invalid.");
//                }
//            case UPDATE_BILLBOARD:
//                // Check the token
//                if (this.middlewareHandler.checkToken(request.token)) {
//                    // Check if user is updating their own billboard
//                    if (this.middlewareHandler.checkCanEditBillboard(request.token)) {
//
//                        return this.update(request.data, 1);
//                    }
//                    // Check if user is updating other's billboard
//                    else if (this.middlewareHandler.checkCanCreateBillboard(request.token)) {
//                        if (this.middlewareHandler.checkOwnBillboard(request.token, request.data)) {
//                            return this.update(request.data, 2);
//                        } else {
//                            return new Response<>(Status.UNAUTHORIZED, "Can't update other user's billboard");
//                        }
//                    } else {
//                        return new Response<>(Status.UNAUTHORIZED, "User does not have permissions to edit billboard.");
//                    }
//                } else {
//                    return new Response<>(Status.UNAUTHORIZED, "Token invalid.");
//                }
//            default:
//                return new Response<>(Status.NOT_FOUND, "Route not found.");
//        }
//    }
//
}
