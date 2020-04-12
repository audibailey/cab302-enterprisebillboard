package common.models;

import java.io.Serializable;
import java.util.List;

public class Request<T> implements Serializable {
    // Obvs these need fixing up
    public String method;
    public T data;

    public Request(String method, T data) {
        this.method = method;
        this.data = data;
    }
}
