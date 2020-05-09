package server.router;

import java.util.HashMap;

public class Request {

    public String path;
    public String token;
    public String ip;
    public HashMap<String, String> params;
    public Object body;

    public Request() {

    }

    public Request(String path, String token, HashMap<String, String> params) {
        this.path = path;
        this.token = token;
        this.params = params;
    }

    public Request(String path, String token, HashMap<String, String> params, Object body) {
        this.path = path;
        this.token = token;
        this.params = params;
        this.body = body;
    }
}
