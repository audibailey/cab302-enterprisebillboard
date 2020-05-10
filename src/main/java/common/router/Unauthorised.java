package common.router;

/**
 * An IActionResult for an unauthorised result.
 *
 * @author Jamie Martin
 */
public class Unauthorised extends IActionResult {
    // A constructor with the message in the body that gets returned to the client.
    public Unauthorised(String message) {
        super(Status.UNAUTHORIZED, message);
    }
}
