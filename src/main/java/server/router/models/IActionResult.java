package server.router.models;

import common.Status;
import java.io.Serializable;

public class IActionResult implements Serializable {
    /**
     * The variables of the object billboard.
     */
    public Status status;
    public String message;
    public Object body;

    /**
     * Constructor for the Response object.
     *
     * @param status: An enum that determines if the request was successful.
     */
    public IActionResult(Status status) {
        this.status = status;
    }

    public IActionResult(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public IActionResult(Status status, Object body) {
        this.status = status;
        this.body = body;
    }

    /**
     * Constructor for the Response object.
     *
     * @param status: An enum that determines if the request was successful.
     * @param body:   This acts as the result of the request.
     */
    public IActionResult(Status status, String message, Object body) {
        this.status = status;
        this.message = message;
        this.body = body;
    }
}
