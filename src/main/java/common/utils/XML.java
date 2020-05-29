package common.utils;

import client.services.BillboardService;
import common.models.Billboard;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class XML {

    public static String readFile(String path) throws Exception {
        return Files.readString(Paths.get(path));
    }

    public static Document toDocument(String xml) throws Exception {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xml));

        return db.parse(is);
    }

    public static String fromDocument(Document document) throws Exception {
        DOMSource domSource = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);

        return writer.toString();
    }

    public static void saveDocument(Document document, File file) throws Exception {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(file);

        transformer.transform(domSource, streamResult);
    }

    public static Billboard fromXML(String xml, Billboard selectedBillboard) throws Exception {
        Document document = toDocument(xml);

        // parse variables
        String billboardColour = getAttributeValue("billboard", "background", document);
        String message = getTagValue("message", document);
        String messageColor = getAttributeValue("message", "colour", document);

        String pictureUrl = getAttributeValue("picture", "url", document);
        String pictureData = getAttributeValue("picture", "data", document);

        String information = getTagValue("information", document);
        String informationColour = getAttributeValue("information", "colour", document);

        Billboard billboard = selectedBillboard;

        billboard.backgroundColor = billboardColour == null ? "#FFFFFF" : billboardColour;
        billboard.message = message;
        billboard.messageColor = messageColor == null ? "#000000" : messageColor;

        billboard.information = information;
        billboard.informationColor = informationColour == null ? "#FFFFFF" : informationColour;

        if (pictureUrl != null && pictureData != null) throw new Exception("Picture cannot have both url and data");
        else if (pictureUrl != null) {
            String encoded =  Picture.getByteArrayFromImageURL(pictureUrl);
            byte[] base64 = Base64.getDecoder().decode(encoded);
            BufferedImage pictureOutput = ImageIO.read(new ByteArrayInputStream(base64));

            if (pictureOutput != null) billboard.picture = encoded;
        }
        else if (pictureData != null) billboard.picture = pictureData;
        else billboard.picture = null;

        return billboard;
    }

    public static String toXML(Billboard billboard) throws Exception {
        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        // root element
        Element root = document.createElement("billboard");
        root.setAttribute("background", billboard.backgroundColor);
        document.appendChild(root);

        if (billboard.message != null && !billboard.message.isEmpty()) {
            Element element = document.createElement("message");
            element.setTextContent(billboard.message);
            element.setAttribute("colour", billboard.messageColor);

            root.appendChild(element);
        }

        if (billboard.picture != null && !billboard.picture.isEmpty()) {
            Element element = document.createElement("picture");
            element.setAttribute("data", billboard.picture);

            root.appendChild(element);
        }

        if (billboard.information != null && !billboard.information.isBlank()) {
            Element element = document.createElement("information");
            element.setTextContent(billboard.information);
            element.setAttribute("colour", billboard.informationColor);

            root.appendChild(element);
        }

        return fromDocument(document);
    }

    private static String getTagValue(String tag, Document document) {
        try {
            NodeList nodeList = document.getElementsByTagName(tag).item(0).getChildNodes();
            Node node = nodeList.item(0);
            return node.getNodeValue();
        } catch (Exception e) {
            return null;
        }
    }

    private static String getAttributeValue(String tag, String attribute, Document document) {
        try {
            return document.getElementsByTagName(tag).item(0).getAttributes().getNamedItem(attribute).getNodeValue();
        } catch (Exception e) {
            return null;
        }
    }
}
