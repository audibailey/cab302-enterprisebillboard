package server.router;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RouterTests {

    private static StringRouter router;
    private static String authentication = "protected";

    static class StringRouter extends GenericRouter<String, String, StringRouter> {
        public StringRouter(String protected_route) {
            super(protected_route);
        }

        @Override protected StringRouter getThis() { return this; }
    }

    @BeforeAll
    public static void PrepRoutes() {
        router = new StringRouter(authentication)
            .ADD_AUTH("/billboard", "canEditBillboard", "a billboard");
    }

    @Test
    public void TestAuthenticated() throws Exception {
        Object[] actions = router.route("/billboard");
        var expectedActions = new String[3];

        expectedActions[0] = authentication;
        expectedActions[1] = "canEditBillboard";
        expectedActions[2] = "a billboard";

        assert actions.length == expectedActions.length;

        for (int i = 0; i < actions.length; i++) {
            String action = (String)actions[i];
            assert action.equals(expectedActions[i]);
        }
    }

    @Test
    public void TestNull() throws Exception {
        Object[] actions = router.route("/null");
        assert actions == null;
    }
}
