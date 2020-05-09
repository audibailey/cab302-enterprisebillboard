package server.router.models;

import common.Status;

public class NotFound extends IActionResult {
    public NotFound() { super(Status.NOT_FOUND); }
}
