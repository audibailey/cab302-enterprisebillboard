package server.sql;

import common.models.User;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public class CollectionTests {
    // Only test fromSQL(), other funcs should work provided the StatementBuilder works correctly

    @Test
    public void TestLifeCycle() throws Exception {
//        var user = User.Random();
//
//        CollectionFactory.getInstance(User.class).insert(user);
//
//        List<User> users = CollectionFactory.getInstance(User.class).get(u -> true);
//
//        User foundUser = null;
//
//        for (var u : users) {
//            if (u.username.equals(user.username)) foundUser = u;
//        }
//
//        assert foundUser != null;
//
//        CollectionFactory.getInstance(User.class).delete(foundUser);
//
//        List<User> users2 = CollectionFactory.getInstance(User.class).get(u -> true);
//
//        User deletedUser = null;
//
//        for (var u : users2) {
//            if (u.username.equals(user.username)) deletedUser = u;
//        }
//
//        assert(deletedUser == null);
    }

}
