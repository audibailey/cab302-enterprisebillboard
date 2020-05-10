package common.router;

public class InternalError extends IActionResult {
    public InternalError(String message) { super(Status.INTERNAL_SERVER_ERROR, message); }
}
