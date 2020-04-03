package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientTest {

    private String host;
    private int port;

    public static void main(String[] args) throws Exception {
        new ClientTest("127.0.0.1", 12345).run();
    }

    public ClientTest(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        // connect client to server
        Socket client = new Socket(host, port);

        // create a new thread for server messages handling
        new Thread(new ReceivedMessagesHandler(client.getInputStream())).start();

        DataOutputStream socketOut = new DataOutputStream(client.getOutputStream());

        System.out.println("Send messages: ");
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String test = sc.nextLine();
            DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder icBuilder = icFactory.newDocumentBuilder();
            Document doc = icBuilder.newDocument();
            Element mainRootElement = doc.createElement("request");
            mainRootElement.setAttribute("code", StatusCodes.POSTBILLBOARD.toString());
            mainRootElement.appendChild(makeRequestElements(doc, "token", test));
            mainRootElement.appendChild(makeRequestElements(doc, "billboardName", test + " title"));
            mainRootElement.appendChild(makeRequestElements(doc, "billboardMessage", "test message"));
            mainRootElement.appendChild(makeRequestElements(doc, "billboardPic", "iVBORw0KGgoAAAANSUhEUgAAABkAAAAZCAYAAADE6YVjAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6MEVBMTczNDg3QzA5MTFFNjk3ODM5NjQyRjE2RjA3QTkiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6MEVBMTczNDk3QzA5MTFFNjk3ODM5NjQyRjE2RjA3QTkiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDowRUExNzM0NjdDMDkxMUU2OTc4Mzk2NDJGMTZGMDdBOSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDowRUExNzM0NzdDMDkxMUU2OTc4Mzk2NDJGMTZGMDdBOSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PjjUmssAAAGASURBVHjatJaxTsMwEIbpIzDA6FaMMPYJkDKzVYU+QFeEGPIKfYU8AETkCYI6wANkZQwIKRNDB1hA0Jrf0rk6WXZ8BvWkb4kv99vn89kDrfVexBSYgVNwDA7AN+jAK3gEd+AlGMGIBFDgFvzouK3JV/lihQTOwLtOtw9wIRG5pJn91Tbgqk9kSk7GViADrTD4HCyZ0NQnomi51sb0fUyCMQEbp2WpU67IjfNjwcYyoUDhjJVcZBjYBy40j4wXgaobWoe8Z6Y80CJBwFpunepIzt2AUgFjtXXshNXjVmMh+K+zzp/CMs0CqeuzrxSRpbOKfdCkiMTS1VBQ41uxMyQR2qbrXiiwYN3ACh1FDmsdK2Eu4J6Tlo31dYVtCY88h5ELZIJJ+IRMzBHfyJINrigNkt5VsRiub9nXICdsYyVd2NcVvA3ScE5t2rb5JuEeyZnAhmLt9NK63vX1O5Pe8XaPSuGq1uTrfUgMEp9EJ+CQvr+BJ/AAKvAcCiAR+bf9CjAAluzmdX4AEIIAAAAASUVORK5CYII="));
            mainRootElement.appendChild(makeRequestElements(doc, "billboardInfo", "test info"));
            mainRootElement.appendChild(makeRequestElements(doc, "billboardLocked", "true"));
            mainRootElement.appendChild(makeRequestElements(doc, "billboardOwner", "audibailey"));
            doc.appendChild(mainRootElement);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            byte[] messageResp = writer.getBuffer().toString().getBytes(StandardCharsets.UTF_8);
            System.out.println(messageResp.length);
//            socketOut.writeInt(messageResp.length);
            System.out.println(new String(messageResp));
            socketOut.write(messageResp);
            socketOut.flush();
        }
        sc.close();
        client.close();
    }

    private Node makeRequestElements(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}

class ReceivedMessagesHandler implements Runnable {

    private InputStream server;

    public ReceivedMessagesHandler(InputStream server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            DataInputStream clientBufferedIn = new DataInputStream(new BufferedInputStream(this.server));

            int len = clientBufferedIn.readInt();
            if (len > 0) {
                byte[] message = new byte[len];
                clientBufferedIn.readFully(message);
                System.out.println(new String(message));
            }

            Thread.sleep(100);

        } catch (Exception e) {
            e.getMessage();
        }

    }
}
