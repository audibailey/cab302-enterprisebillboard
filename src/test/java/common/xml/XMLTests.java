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

}
