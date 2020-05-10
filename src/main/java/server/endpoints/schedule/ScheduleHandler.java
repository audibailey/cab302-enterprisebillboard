package server.endpoints.schedule;

/**
 * This class handles how the schedule endpoints interact with the schedule database handler
 * and the client.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 **/
public class ScheduleHandler {
//    DataService db;
//    MiddlewareHandler middlewareHandler;
//
//    /**
//     * The ScheduleHandler Constructor.
//     *
//     * @param db: This is the DataService handler that connects the Endpoint to the database.
//     */
//    public ScheduleHandler(DataService db, MiddlewareHandler middlewareHandler) {
//        this.db = db;
//        this.middlewareHandler = middlewareHandler;
//    }
//
//    /**
//     * The Endpoint ScheduleHandler get method, used to list schedules or retrieve a singular
//     * schedule.
//     *
//     * @param data: This is used as the parameter for the method being used.
//     * @param <T>:  This generic determines whether to retrieve a list of schedules or a single
//     *              schedule.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    private <T> Response<?> get(T data) {
//        // Check the object type
//        if (data instanceof Integer) {
//            //Get Schedule using ID
//            return GetScheduleHandler.getScheduleByID(this.db, (int) data);
//        } else if (data instanceof String) {
//            //Get Schedule using billboard name
//            return GetScheduleHandler.getScheduleByBillboardName(this.db, (String) data);
//        } else if (data == null) {
//            //Get all Schedules
//            return GetScheduleHandler.getAllSchedule(this.db);
//        } else {
//            // return an error response
//            return new Response<>(Status.UNSUPPORTED_TYPE, "Unknown parameter received in data field.");
//        }
//    }
//
//    /**
//     * The Endpoint ScheduleHandler post method, used to insert schedules into database
//     *
//     * @param data: This is used as the parameter for the method being used.
//     * @param <T>:  This generic is the schedule being inserted.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    private <T> Response<?> post(T data) {
//        // Check the object data type
//        if (data instanceof Schedule) {
//            Schedule schedule = (Schedule) data;
//            //Check the ID, if the ID is larger than 0 -> continue else return error
//            if (schedule.id > 0) {
//                // Check if the billboard exists
//                try {
//                    if (db.billboards.get(schedule.billboardName).isPresent()) {
//                        return PostScheduleHandler.insertSchedule(this.db, schedule);
//                    } else {
//                        // Return error if no billboard was founded
//                        return new Response<>(Status.BAD_REQUEST, "Billboard doesn't exist.");
//                    }
//                } catch (SQLException e) {
//                    // TODO: Console Log this
//                    return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to add schedule to the database.");
//                }
//            }
//        }
//        return new Response<>();
//    }
//
//    /**
//     * The Endpoint ScheduleHandler delete method, used to delete schedules from the database
//     *
//     * @param data: This is used as the parameter for the method being used.
//     * @param <T>:  This generic is the schedule being deleted.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    private <T> Response<?> delete(T data) {
//        // Check the object data type
//        if (data instanceof Schedule) {
//            Schedule schedule = (Schedule) data;
//            //Check the ID, if the ID is larger than 0 -> continue else return error
//            if (schedule.id > 0) {
//                try {
//                    // Check if billboard exists
//                    if (db.billboards.get(schedule.billboardName).isPresent()) {
//                        return DeleteScheduleHandler.deleteSchedule(this.db, schedule);
//                    } else {
//                        // Return error if no billboard was founded
//                        return new Response<>(Status.BAD_REQUEST, "Billboard doesn't exist.");
//                    }
//                } catch (SQLException e) {
//                    // TODO: Console Log this
//                    return new Response<>(Status.INTERNAL_SERVER_ERROR, "Failed to delete schedule from the database.");
//                }
//            } else {
//                return new Response<>(Status.BAD_REQUEST, "Invalid schedule ID.");
//            }
//        } else {
//            return new Response<>(Status.UNSUPPORTED_TYPE, "Unknown parameter received in data field.");
//        }
//    }
//
//    /**
//     * The Endpoint ScheduleHandler route method, used to determine which type of Endpoint
//     * ScheduleHandler function is needed.
//     *
//     * @param request: This is the request that needs to be determined.
//     * @return Response<?>: This is the response to send back to the client.
//     */
//    public Response<?> route(Request<?> request) {
//        // Check the methods to determine which type of Endpoint ScheduleHandler function is needed.
//        switch (request.method) {
//            case GET_SCHEDULES:
//                // Check the token
//                if (this.middlewareHandler.checkToken(request.token)) {
//                    // Check the permission
//                    if (this.middlewareHandler.checkCanScheduleBillboard(request.token)) {
//                        return this.get(request.data);
//                    } else {
//                        return new Response<>(Status.UNAUTHORIZED, "User does not have permissions to view schedule.");
//                    }
//                } else {
//                    return new Response<>(Status.UNAUTHORIZED, "Token invalid.");
//                }
//            case POST_SCHEDULE:
//                // Check the token
//                if (this.middlewareHandler.checkToken(request.token)) {
//                    // Check the permission
//                    if (this.middlewareHandler.checkCanScheduleBillboard(request.token)) {
//                        return this.post(request.data);
//                    } else {
//                        return new Response<>(Status.UNAUTHORIZED, "User does not have permissions to post schedule.");
//                    }
//                } else {
//                    return new Response<>(Status.UNAUTHORIZED, "Token invalid.");
//                }
//            case DELETE_SCHEDULE:
//                // Check the token
//                if (this.middlewareHandler.checkToken(request.token)) {
//                    // Check the permission
//                    if (this.middlewareHandler.checkCanScheduleBillboard(request.token)) {
//                        return this.delete(request.data);
//                    } else {
//                        return new Response<>(Status.UNAUTHORIZED, "User does not have permissions to delete schedule.");
//                    }
//                } else {
//                    return new Response<>(Status.UNAUTHORIZED, "Token invalid.");
//                }
//            default:
//                return new Response<>(Status.NOT_FOUND, "Route not found.");
//        }
//    }
}
