package common.models;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class consists of the user's permissions object and its associated methods.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 * @author Jamie Martin
 */
public class Permissions implements Serializable {
    /**
     * The variables of the object Permissions
     */
    public int id;
    public String username;
    public boolean canCreateBillboard;
    public boolean canEditBillboard;
    public boolean canScheduleBillboard;
    public boolean canEditUser;
    public boolean canViewBillboard;


    /**
     * An empty constructor just for creating the object.
     */
    public Permissions() {

    }

    /**
     * Permissions object constructor
     *
     * @param id:                   permissions id.
     * @param username:             permissions username.
     * @param canCreateBillboard:   permissions canCreateBillboard permission.
     * @param canEditBillboard:     permissions canEditBillboard permission.
     * @param canScheduleBillboard: permissions canScheduleBillboard permission.
     * @param canEditUser:          permissions canEditUser permission.
     * @param canViewBillboard:     permissions canViewBillboard permission.
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
     * Parses the SQL result set and returns a permissions object.
     *
     * @param rs: the result set from an SQL SELECT query.
     * @return Billboard: the permissions object after converting from SQL.
     * @throws SQLException: this is thrown when there is an issue with getting values from the query.
     */
    public static Permissions fromSQL(ResultSet rs) throws SQLException {
        return new Permissions(
            rs.getInt("id"),
            rs.getString("username"),
            rs.getBoolean("canCreateBillboard"),
            rs.getBoolean("canEditBillboard"),
            rs.getBoolean("canScheduleBillboard"),
            rs.getBoolean("canEditUsers"),
            rs.getBoolean("canViewBillboard"));
    }
}