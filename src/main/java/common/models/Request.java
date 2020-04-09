package common.models;

public class Request {
    // Obvs these need fixing up
    String route;
    String method;
    String data;

    public Request() {

    }

    /**
     * Parses the XMl string and returns a new Request
     * @param xml
     * @return Request
     */
    public static Request fromXML(String xml) {
        return new Request();
    }

    /**
     * Parses the Object and returns an XML string
     * @param r
     * @return String
     */
    public static String toXML(Request r) {
        return "";
    }
}
