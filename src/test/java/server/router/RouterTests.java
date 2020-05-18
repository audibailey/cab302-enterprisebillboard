package server.router;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RouterTests {

    private static StringRouter router;
    private static String authentication = "protected";

    static class StringRouter extends GenericRouter<String, String, StringRouter> {
        public StringRouter() {
            super();
        }

        @Override protected StringRouter getThis() { return this; }
    }

    @BeforeAll
    public static void PrepRoutes() {
        router = new StringRouter()
            .ADD_AUTH("/authnull", "it should be just me on this route")
            .SET_AUTH(authentication)
            .ADD_AUTH("/auth", "permittedToSeeSecret", "a secret")
            .ADD("/unauth", "no secrets here")
            .ADD("/overwritten", "old action")
            .ADD("/overwritten", "new action!");
    }

    @Test
    public void TestAuthNull() throws Exception {
        Object[] actions = router.route("/authnull");
        assert actions != null;

        var expectedActions = new String[1];

        expectedActions[0] = "it should be just me on this route";

        TestEquals(actions, expectedActions);
    }

    @Test
    public void TestAuthenticated() throws Exception {
        Object[] actions = router.route("/auth");
        assert actions != null;

        var expectedActions = new String[3];

        expectedActions[0] = authentication;
        expectedActions[1] = "permittedToSeeSecret";
        expectedActions[2] = "a secret";

        TestEquals(actions, expectedActions);
    }

    @Test
    public void TestUnauthenticated() throws Exception {
        Object[] actions = router.route("/unauth");
        assert actions != null;

        var expectedActions = new String[1];

        expectedActions[0] = "no secrets here";

        TestEquals(actions, expectedActions);
    }

    @Test
    public void TestNull() throws Exception {
        Object[] actions = router.route("/null");
        assert actions == null;
    }

    @Test
    public void TestOverwrite() throws Exception {
        Object[] actions = router.route("/overwritten");
        assert actions != null;

        var expectedActions = new String[1];

        expectedActions[0] = "new action!";

        TestEquals(actions, expectedActions);
    }

    public static void TestEquals(Object[] s1, Object[] s2) throws Exception {
        assert s1.length == s2.length;

        for (int i = 0; i < s1.length; i++) {
            String action = (String)s1[i];
            assert action.equals((String)s2[i]);
        }
    }
}
