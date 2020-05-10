package common.router;

import java.io.Serializable;

/**
 * This class is the Response object.
 *
 * @author Perdana Bailey
 * @author Jamie Martin
 */
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

    /**
     * Constructor for the Response object.
     *
     * @param status: An enum that determines if the request was successful.
     * @param message: the message given
     */
    public IActionResult(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * Constructor for the Response object.
     *
     * @param status: An enum that determines if the request was successful.
     * @param body: the body Object of the return
     */
    public IActionResult(Status status, Object body) {
        this.status = status;
        this.body = body;
    }

    /**
     * Constructor for the Response object.
     *
     * @param status: An enum that determines if the request was successful.
     * @param message: the message given
     * @param body:   This acts as the result of the request.
     */
    public IActionResult(Status status, String message, Object body) {
        this.status = status;
        this.message = message;
        this.body = body;
    }
}
