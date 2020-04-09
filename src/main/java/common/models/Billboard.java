package common.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class consists of the billboard object and its associated methods.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 * @author Jamie Martin
 */
public class Billboard {
    /**
     * The variables of the object billboard
     */
    public int id;
    public String name;
    public String message;
    public String messageColor;
    public byte[] picture;
    public String backgroundColor;
    public String information;
    public String informationColor;
    public boolean locked;
    public int userId;

    public Billboard() {

    }

    /**
     * Billboard object constructor
     *
     * @param id
     * @param name
     * @param message
     * @param messageColor
     * @param picture
     * @param backgroundColor
     * @param information
     * @param informationColor
     * @param locked
     * @param userId
     */
    public Billboard(int id,
                     String name,
                     String message,
                     String messageColor,
                     byte[] picture,
                     String backgroundColor,
                     String information,
                     String informationColor,
                     boolean locked,
                     int userId) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.messageColor = messageColor;
        this.picture = picture;
        this.backgroundColor = backgroundColor;
        this.information = information;
        this.informationColor = informationColor;
        this.locked = locked;
        this.userId = userId;
    }

    public Billboard(
        String name,
        String message,
        String messageColor,
        byte[] picture,
        String backgroundColor,
        String information,
        String informationColor,
        boolean locked,
        int userId) {
        this.name = name;
        this.message = message;
        this.messageColor = messageColor;
        this.picture = picture;
        this.backgroundColor = backgroundColor;
        this.information = information;
        this.informationColor = informationColor;
        this.locked = locked;
        this.userId = userId;
    }

    /**
     * Parses the XML string and returns a Billboard object
     *
     * @param xml
     * @return Billboard
     */
    public static Billboard fromXML(String xml) {
        return new Billboard();
    }

    /**
     * Parses the Object and returns an XML string from it
     *
     * @param b
     * @return String
     */
    public static String toXML(Billboard b) {
        return "";
    }

    public static Billboard fromSQL(ResultSet rs) throws SQLException {
        return new Billboard(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("message"),
            rs.getString("messageColor"),
            rs.getBytes("picture"),
            rs.getString("backgroundColor"),
            rs.getString("information"),
            rs.getString("informationColor"),
            rs.getBoolean("locked"),
            rs.getInt("userId"));
    }

}
