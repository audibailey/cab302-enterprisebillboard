package common.models;

import java.io.Serializable;
import java.util.List;

public class Response<T> implements Serializable {
    // Obvs these need fixing up
    public String error;
    public List<T> data;

    public Response(List<T> data, String error) {
        this.error = error;
        this.data = data;
    }
}
