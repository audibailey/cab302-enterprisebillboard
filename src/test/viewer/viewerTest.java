package viewer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.function.Executable;
import viewer.Main.*;

public class viewerTest {
    @Test
    public void testGreaterThaZero() throws Exception {
        assertEquals(Main.ExampleFunction(1), 1);
    }

    @Test
    public void testEqualZero() {
        assertThrows(Exception.class, () -> Main.ExampleFunction(0));
    }

    @Test
    public void testLessThanZero() {
        assertThrows(Exception.class, () -> Main.ExampleFunction(-1));
    }
}
