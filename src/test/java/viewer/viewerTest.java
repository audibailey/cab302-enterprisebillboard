package viewer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import viewer.*;

public class viewerTest {
    @Test
    public void greaterThanZeroTestCase() throws Exception {
        assertEquals(Main.ExampleFunction(1), 1);
    }

    @Test
    public void equalZeroTestCase() {
        assertThrows(Exception.class, () -> Main.ExampleFunction(0));
    }

    @Test
    public void lessThanZeroTestCase() {
        assertThrows(Exception.class, () -> Main.ExampleFunction(-1));
    }
}
