package common.models;

public class Response {
    // Obvs these need fixing up
    String error;
    String data;

    public Response() {

    }

    /**
     * Parses the XMl string and returns a new Response
     * @param xml
     * @return Response
     */
    public static Response fromXML(String xml) {
        return new Response();
    }

    /**
     * Parses the Object and returns an XML String
     * @param r
     * @return
     */
    public static String fromObject(Response r) {
        return "";
    }
}
