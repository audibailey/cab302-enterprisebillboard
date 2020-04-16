package common.models;

public class Status {

    public String message;
    public boolean error;

    public Status(String message, boolean error) {
        this.message = message;
        this.error = error;
    }
}

