package common.router;

public class Ok extends IActionResult {

    public Ok() { super(Status.SUCCESS); }

    public Ok(Object body) {
        super(Status.SUCCESS, body);
    }
}
