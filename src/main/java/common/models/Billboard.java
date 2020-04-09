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
     * The variables of the object billboard.
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
    public int userID;

    /**
     * An empty constructor just for creating the object.
     */
    public Billboard() {

    }

    /**
     * Billboard object constructor with an id.
     *
     * @param id:               database billboard ID as an int.
     * @param name:             billboard name as a string.
     * @param message:          billboard message as a string.
     * @param messageColor:     billboard message colour as a string.
     * @param picture:          billboard picture as a byte array.
     * @param backgroundColor:  billboard background colour as a string.
     * @param information:      billboard information as a string.
     * @param informationColor: billboard information colour as a string.
     * @param locked:           billboard scheduled as a boolean.
     * @param userID:           owner ID as an int.
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
                     int userID) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.messageColor = messageColor;
        this.picture = picture;
        this.backgroundColor = backgroundColor;
        this.information = information;
        this.informationColor = informationColor;
        this.locked = locked;
        this.userID = userID;
    }

    /**
     * Billboard object constructor without an id.
     *
     * @param name:             billboard name as a string.
     * @param message:          billboard message as a string.
     * @param messageColor:     billboard message colour as a string.
     * @param picture:          billboard picture as a byte array.
     * @param backgroundColor:  billboard background colour as a string.
     * @param information:      billboard information as a string.
     * @param informationColor: billboard information colour as a string.
     * @param locked:           billboard scheduled as a boolean.
     * @param userID:           owner ID as an int.
     */
    public Billboard(
        String name,
        String message,
        String messageColor,
        byte[] picture,
        String backgroundColor,
        String information,
        String informationColor,
        boolean locked,
        int userID) {
        this.name = name;
        this.message = message;
        this.messageColor = messageColor;
        this.picture = picture;
        this.backgroundColor = backgroundColor;
        this.information = information;
        this.informationColor = informationColor;
        this.locked = locked;
        this.userID = userID;
    }

    /**
     * Parses the XML string and returns a Billboard object.
     *
     * @param xml
     * @return Billboard
     */
    public static Billboard fromXML(String xml) {
        return new Billboard();
    }

    /**
     * Parses the Object and returns an XML string from it.
     *
     * @param b
     * @return String
     */
    public static String fromObject(Billboard b) {
        return "";
    }

    /**
     * Parses the SQL result set and returns a Billboard object.
     *
     * @param rs: the result set from an SQL SELECT query.
     * @return Billboard: the billboard object after converting from SQL.
     * @throws SQLException: this is thrown when there is an issue with getting values from the query.
     */
    public static Billboard fromSQL(ResultSet rs) throws SQLException {
        return new Billboard(
            rs.getInt("ID"),
            rs.getString("name"),
            rs.getString("message"),
            rs.getString("messageColor"),
            rs.getBytes("picture"),
            rs.getString("backgroundColor"),
            rs.getString("information"),
            rs.getString("informationColor"),
            rs.getBoolean("locked"),
            rs.getInt("userID"));
    }

}
