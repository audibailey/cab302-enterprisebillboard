package server.router.models;

import common.Status;

public class Unauthorised extends IActionResult {
    public Unauthorised() { super(Status.UNAUTHORIZED); }
    public Unauthorised(String message) {
        super(Status.UNAUTHORIZED, message);
    }
}
