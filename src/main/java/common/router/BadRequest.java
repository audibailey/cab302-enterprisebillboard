package common.router;

/**
 * An IActionResult for a bad request result.
 *
 * @author Jamie Martin
 */
public class BadRequest extends IActionResult {

    // A constructor with the message in the body that gets returned to the client.
    public BadRequest(String message) {
        super(Status.BAD_REQUEST, message);
    }
}
