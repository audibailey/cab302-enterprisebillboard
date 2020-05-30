package common.models;

import common.sql.SQLITE;
import common.utils.RandomFactory;

import java.io.Serializable;

/**
 * This class consists of the user object and its associated methods.
 *
 * @author Perdana Bailey
 * @author Hieu Nghia Huynh
 * @author Jamie Martin
 */
@SQLITE
public class User extends Object implements Serializable {
    /**
     * The variables of the object User
     */
    @SQLITE(type="INTEGER PRIMARY KEY AUTOINCREMENT")
    public int id;

    @SQLITE(type="VARCHAR(255) NOT NULL UNIQUE")
    public String username;

    @SQLITE(type="VARCHAR(255)")
    public String password;

    @SQLITE(type="VARCHAR(255)")
    public String salt;

    /**
     * An empty constructor just for creating the object.
     */
    public User() {

    }

    /**
     * User object constructor
     *
     * @param username: users username.
     * @param password: users password hash.
     * @param salt:     users password salt.
     */
    public User(String username, String password, String salt) {
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    /**
     * User object constructor
     *
     * @param id: users id.
     * @param username: users username.
     * @param password: users password.
     * @param salt: users salt.
     */
    public User(int id, String username, String password, String salt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    /**
     * Generates a User object with random variables.
     *
     * @return User: a randomised User object.
     */
    public static User Random() {
        return new User(
            RandomFactory.String(),
            RandomFactory.String(),
            RandomFactory.String()
        );
    }
}
