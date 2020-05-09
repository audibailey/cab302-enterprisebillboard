package server.router.models;

import common.Status;

public class Ok extends IActionResult {

    public Ok() { super(Status.SUCCESS); }

    public Ok(Object body) {
        super(Status.SUCCESS, body);
    }
}
