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
public class UserPermissions {
    /**
     * The variables of the object User
     */
    public int id;
    public boolean canCreateBillboard;
    public boolean canEditBillboard;
    public boolean canScheduleBillboard;
    public boolean canEditUser;
    public boolean canViewBillboard;


    public UserPermissions() {

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
    public UserPermissions(int id, boolean canCreateBillboard, boolean canEditBillboard, boolean canScheduleBillboard, boolean canEditUser, boolean canViewBillboard) {
        this.id = id;
        this.canCreateBillboard = canCreateBillboard;
        this.canEditBillboard = canEditBillboard;
        this.canScheduleBillboard = canScheduleBillboard;
        this.canEditUser = canEditUser;
        this.canViewBillboard = canViewBillboard;
    }

    public static UserPermissions fromSQL(ResultSet rs) throws SQLException {
        return new UserPermissions(
            rs.getInt("userID"),
            rs.getBoolean("createBillboard"),
            rs.getBoolean("editBillboard"),
            rs.getBoolean("scheduleBillboard"),
            rs.getBoolean("editUsers"),
            rs.getBoolean("viewBillboard")
        );
    }


}
