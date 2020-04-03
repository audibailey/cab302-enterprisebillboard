package xml;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ServerTest {

    private int port;
    private ServerSocket server;

    public static void main(String[] args) throws IOException {
        new ServerTest(12345).run();
    }

    public ServerTest(int port) {
        this.port = port;
    }

    public void run() throws IOException {
        server = new ServerSocket(port) {
            protected void finalize() throws IOException {
                this.close();
            }
        };
        System.out.println("Port 12345 is now open.");

        while (true) {
            // accepts a new client
            Socket client = server.accept();//
            System.out.println("Connection established with client: " + client.getInetAddress().getHostAddress());

            // create a new thread for client handling
            new Thread(new ClientHandler(this, client.getInputStream(), client.getOutputStream())).start();
        }
    }
}

class MyHandler extends DefaultHandler {
    private Billboard billboard = null;

    boolean valid = false;
    boolean bName = false;
    boolean bMessage = false;
    boolean bPicture = false;
    boolean bInfo = false;
    boolean bLocked = false;
    boolean bOwner = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("request")) {
            // initialize Employee object and set id attribute
            if (attributes.getValue("code").equals(StatusCodes.POSTBILLBOARD.toString())) {
                billboard = new Billboard();
                valid = true;
            }
        } else if (qName.equalsIgnoreCase("billboardName")) {
            // set boolean values for fields, will be used in setting Employee variables
            bName = true;
        } else if (qName.equalsIgnoreCase("billboardMessage")) {
            bMessage = true;
        } else if (qName.equalsIgnoreCase("billboardPic")) {
            bPicture = true;
        } else if (qName.equalsIgnoreCase("billboardInfo")) {
            bInfo = true;
        } else if (qName.equalsIgnoreCase("billboardLocked")) {
            bLocked = true;
        } else if (qName.equalsIgnoreCase("billboardOwner")) {
            bOwner = true;
        } else if (qName.equalsIgnoreCase("token")) {

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (valid) {
            if (bName) {
                billboard.setBillboardName(new String(ch, start, length));
                bName = false;
            } else if (bMessage) {
                billboard.setBillboardMessage(new String(ch, start, length));
                bMessage = false;
            } else if (bPicture) {
                billboard.setBillboardPic(new String(ch, start, length));
                bPicture = false;
            } else if (bInfo) {
                billboard.setBillboardInfo(new String(ch, start, length));
                bInfo = false;
            } else if (bLocked) {
                billboard.setBillboardLocked(Boolean.parseBoolean(new String(ch, start, length)));
            } else if (bOwner) {
                billboard.setBillboardOwner(new String(ch, start, length));
            }
        }
    }

    public Billboard getBillboard() {
        return billboard;
    }
}

class ClientHandler implements Runnable {

    private ServerTest server;
    private InputStream clientIn;
    private OutputStream clientOut;

    public ClientHandler(ServerTest server, InputStream clientIn, OutputStream clientOut) {
        this.server = server;
        this.clientIn = clientIn;
        this.clientOut = clientOut;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int numberOfBytesToRead = this.clientIn.available();

                if (numberOfBytesToRead > 0) {
                    Billboard billboard;

                    System.out.println("numberOfBytesToRead in socket receive buffer : " + numberOfBytesToRead);

                    byte[] messageFromTCPClient = new byte[numberOfBytesToRead];

                    int readByteNumber = this.clientIn.read(messageFromTCPClient);

                    System.out.println("readByteNumber: " + readByteNumber);
                    System.out.println(new String(messageFromTCPClient, 0, readByteNumber));

                    ByteArrayInputStream inputStream = new ByteArrayInputStream(messageFromTCPClient);
                    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                    SAXParser saxParser = saxParserFactory.newSAXParser();
                    MyHandler handler = new MyHandler();
                    saxParser.parse(inputStream, handler);
                    billboard = handler.getBillboard();

                    System.out.println(billboard.getBillboardName());
                }
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
