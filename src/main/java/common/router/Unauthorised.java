package common.router;

public class Unauthorised extends IActionResult {
    public Unauthorised() { super(Status.UNAUTHORIZED); }
    public Unauthorised(String message) {
        super(Status.UNAUTHORIZED, message);
    }
}
