package common.models;

import java.io.Serializable;
import java.util.List;

public class Response<T> implements Serializable {
    // Obvs these need fixing up
    String error;
    List<T> data;

    public Response(List<T> data, String error) {
        this.error = error;
        this.data = data;
    }
}
