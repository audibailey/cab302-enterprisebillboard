package common.models;

import java.io.Serializable;

import common.Status;

/**
 * This class is the Response object.
 *
 * @author Perdana Bailey
 * @author Jamie Martin
 */
public class Response<T> implements Serializable {
    /**
     * The variables of the object billboard.
     */
    public Status status;
    public T data;

    /**
     * An empty constructor just for creating the object.
     */
    public Response() {

    }

    /**
     * Constructor for the Response object.
     *
     * @param status: An enum that determines if the request was successful.
     * @param data:   This acts as the result of the request.
     */
    public Response(Status status, T data) {
        this.status = status;
        this.data = data;
    }
}

