package common.models;

import java.io.Serializable;
import java.util.List;

public class Response<T> implements Serializable {
    // Obvs these need fixing up
    public Status status;
    public List<T> data;

    public Response() {

    }

    public Response(List<T> data, Status status) {
        this.status = status;
        this.data = data;
    }
}

