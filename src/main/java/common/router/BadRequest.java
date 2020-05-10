package common.router;

public class BadRequest extends IActionResult {
    public BadRequest(String message) {
        super(Status.BAD_REQUEST, message);
    }
}
