package common.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class consists of the user object and its associated methods.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 * @author Jamie Martin
 */
public class User {
    /**
     * The variables of the object User
     */
    public int id;
    public String username;
    public String password;
    public String salt;
    public UserPermissions userPermissions;

    public User() {

    }

    /**
     * User object constructor
     *
     * @param username
     * @param password
     * @param salt
     * @param perms
     */
    public User(String username, String password, String salt, UserPermissions perms) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.userPermissions = perms;
    }

    /**
     * User object constructor
     *
     * @param id
     * @param username
     * @param password
     * @param salt
     * @param perms
     */
    public User(int id, String username, String password, String salt, UserPermissions perms) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.userPermissions = perms;
    }


    /**
     * Change the password of the user
     *
     * @param newPass, the new password hash.
     */
    public void changePassword(String newPass) {
        this.password = newPass;
    }

    /**
     * Try login with the password for this user.
     *
     * @param pass, the password input
     */
    public boolean tryLogin(String pass) {
        return this.password.equals(pass);
    }

    /**
     * Parses the XML string and returns a User object
     * @param xml
     * @return
     */
    public static User fromXML(String xml) {
        return new User();
    }

    /**
     * Parses the Object and returns an XML String
     *
     * @param u
     * @return
     */
    public static String fromObject(User u) {
        return "";
    }

    public static User fromSQL(ResultSet rs, UserPermissions userperms) throws SQLException {
        return new User(
            rs.getInt("ID"),
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("salt"),
            userperms
        );
    }
}
