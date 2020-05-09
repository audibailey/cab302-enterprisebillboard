package server.router.models;

import common.Status;

public class BadRequest extends IActionResult {
    public BadRequest(String message) {
        super(Status.BAD_REQUEST, message);
    }
}
