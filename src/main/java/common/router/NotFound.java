package common.router;

public class NotFound extends IActionResult {
    public NotFound() {
        super(Status.NOT_FOUND);
    }

    public NotFound(String message) {
        super(Status.NOT_FOUND, message);
    }
}
