package common.models;

import java.io.Serializable;

import common.Methods;

/**
 * This class is the Request object.
 *
 * @author Perdana Bailey
 * @author Jamie Martin
 */
public class Request<T> implements Serializable {

    /**
     * The variables of the object Request.
     */
    public Methods method;
    public String token;
    public T data;

    /**
     * An empty constructor just for creating the object.
     */
    public Request() {

    }

    /**
     * Constructor for the Request object.
     *
     * @param method: An enum that determines the route the clients request will take.
     * @param token:  A token to authenticate the clients request.
     * @param data:   This acts as the parameter of the request.
     */
    public Request(Methods method, String token, T data) {
        this.method = method;
        this.token = token;
        this.data = data;
    }
}
