package common.xml;

import common.models.Billboard;
import common.utils.XML;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XMLTests {

    Billboard billboard = Billboard.Random(0);

    @Test
    public void TestEmptyBillboard() throws Exception {
        billboard.message = null;
        billboard.picture = null;
        billboard.information = null;
        billboard.backgroundColor = "#000000";

        String xml = XML.toXML(billboard);
        assertEquals(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><billboard background=\"#000000\"/>");
    }

    @Test
    public void TestOnlyMessage() throws Exception {
        billboard.message = "Hello!";
        billboard.messageColor = "#FFFFFF";
        billboard.picture = null;
        billboard.information = null;
        billboard.backgroundColor = "#000000";

        String xml = XML.toXML(billboard);
        assertEquals(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><billboard background=\"#000000\"><message colour=\"#FFFFFF\">Hello!</message></billboard>");
    }

    @Test
    public  void TestNormalBillboard() throws Exception {
        billboard.message = "Hello!";
        billboard.backgroundColor="#ca26ac";
        billboard.informationColor="f756c5";
        billboard.messageColor = "#FFFFFF";
        billboard.information = "HiHello!";

        String xml = XML.toXML(billboard);
        assertEquals(xml,"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><billboard background=\"#ca26ac\"><message colour=\"#FFFFFF\">Hello!</message><information colour=\"f756c5\">HiHello!</information></billboard>");
    }

    @Test
    public void TestOnlyInformation() throws Exception {
        billboard.message = "Hello!";
        billboard.messageColor = "#FFFFFF";
        billboard.picture = null;
        billboard.information = null;
        billboard.backgroundColor = "#000000";

        String xml = XML.toXML(billboard);
        assertEquals(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><billboard background=\"#000000\"><message colour=\"#FFFFFF\">Hello!</message></billboard>");
    }

    @Test
    public void TestBillboardNameWithMessage() throws Exception {
        billboard.name = "newBillboard";
        billboard.message = "What!!!";
        billboard.messageColor = "#FFFFFF";
        billboard.picture = null;
        billboard.information = null;
        billboard.backgroundColor = "#000000";

        String xml = XML.toXML(billboard);
        assertEquals(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><billboard background=\"#000000\"><message colour=\"#FFFFFF\">What!!!</message></billboard>");
    }

    @Test
    public void TestMessageAndInformation() throws Exception {
        billboard.message = "What!!!";
        billboard.messageColor = "#FFFFFF";
        billboard.picture = null;
        billboard.information = "THE";
        billboard.informationColor = "#c6299c";
        billboard.backgroundColor = "#000000";

        String xml = XML.toXML(billboard);
        assertEquals(xml, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><billboard background=\"#000000\"><message colour=\"#FFFFFF\">What!!!</message><information colour=\"#c6299c\">THE</information></billboard>");
    }

    // TODO: Test only picture?

    // TODO: Test all cases for fromXML
}
