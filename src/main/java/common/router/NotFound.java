package common.router;

/**
 * An IActionResult for a not found result.
 *
 * @author Jamie Martin
 */
public class NotFound extends IActionResult {
    // A constructor with the message in the body that gets returned to the client.
    public NotFound(String message) {
        super(Status.NOT_FOUND, message);
    }
}
