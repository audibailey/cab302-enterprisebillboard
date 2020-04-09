package common.models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class consists of the user's permissions object and its associated methods.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 * @author Jamie Martin
 */
public class Permissions {
    /**
     * The variables of the object User
     */
    public int id;
    public String username;
    public boolean canCreateBillboard;
    public boolean canEditBillboard;
    public boolean canScheduleBillboard;
    public boolean canEditUser;
    public boolean canViewBillboard;


    public Permissions() {

    }

    /**
     * User object constructor
     *
     * @param username
     * @param canCreateBillboard
     * @param canEditBillboard
     * @param canScheduleBillboard
     * @param canEditUser
     * @param canViewBillboard
     */
    public Permissions(int id, String username, boolean canCreateBillboard, boolean canEditBillboard, boolean canScheduleBillboard, boolean canEditUser, boolean canViewBillboard) {
        this.id = id;
        this.username = username;
        this.canCreateBillboard = canCreateBillboard;
        this.canEditBillboard = canEditBillboard;
        this.canScheduleBillboard = canScheduleBillboard;
        this.canEditUser = canEditUser;
        this.canViewBillboard = canViewBillboard;
    }

    /**
     * Parses the XML string and returns a Permissions object
     *
     * @param xml
     * @return Permissions
     */
    public static Permissions fromXML(String xml) {
        return new Permissions();
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

    public static Permissions fromSQL(ResultSet rs) throws SQLException {
        return new Permissions(
            rs.getInt("id"),
            rs.getString("username"),
            rs.getBoolean("canCreateBillboard"),
            rs.getBoolean("canEditBillboard"),
            rs.getBoolean("canScheduleBillboard"),
            rs.getBoolean("canEditUser"),
            rs.getBoolean("canViewBillboard"));
    }
}
