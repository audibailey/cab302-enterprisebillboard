package common.models;

import java.io.Serializable;
import java.util.List;

public class Request<T> implements Serializable {
    // Obvs these need fixing up
    String route;
    String method;
    List<T> data;

    public Request(String route, String method, List<T> data) {
        this.route = route;
        this.method = method;
        this.data = data;
    }
}
