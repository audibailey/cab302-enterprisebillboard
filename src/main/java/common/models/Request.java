package common.models;

import common.Method;

import java.io.Serializable;

public class Request<T> implements Serializable {
    // Obvs these need fixing up
    public Method method;
    public T data;
    public Object params;

    public Request() {

    }

    public Request(Method method, T data) {
        this.method = method;
        this.data = data;
    }
}
