package common.router;

/**
 * An IActionResult for an internal server error result.
 *
 * @author Jamie Martin
 */
public class InternalError extends IActionResult {
    // A constructor with the message in the body that gets returned to the client.
    public InternalError(String message) { super(Status.INTERNAL_SERVER_ERROR, message); }
}
