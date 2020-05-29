package common.models;

import client.components.table.DisplayAs;
import client.components.table.Editable;
import common.sql.SQLITE;
import common.utils.RandomFactory;

import java.io.Serializable;

/**
 * This class consists of the user's permissions object and its associated methods.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 * @author Jamie Martin
 */
@SQLITE(type="FOREIGN KEY(id) REFERENCES User(id), FOREIGN KEY(username) REFERENCES User(username)")
public class Permissions implements Serializable, Editable {
    /**
     * The variables of the object Permissions
     */
    @SQLITE(type="INTEGER PRIMARY KEY AUTOINCREMENT")
    public int id;

    @SQLITE(type="VARCHAR(255) NOT NULL UNIQUE")
    public String username;

    @SQLITE(type="BOOLEAN")
    public boolean canCreateBillboard = false;

    @SQLITE(type="BOOLEAN")
    public boolean canEditBillboard  = false;

    @SQLITE(type="BOOLEAN")
    public boolean canScheduleBillboard = false;

    @SQLITE(type="BOOLEAN")
    public boolean canEditUser = false;

    /**
     * An empty constructor just for creating the object.
     */
    public Permissions() {

    }

    /**
     * Permissions object constructor
     *
     * @param username:             permissions username.
     * @param canCreateBillboard:   permissions canCreateBillboard permission.
     * @param canEditBillboard:     permissions canEditBillboard permission.
     * @param canScheduleBillboard: permissions canScheduleBillboard permission.
     * @param canEditUser:          permissions canEditUser permission.
     */
    public Permissions(String username, boolean canCreateBillboard, boolean canEditBillboard, boolean canScheduleBillboard, boolean canEditUser) {
        this.username = username;
        this.canCreateBillboard = canCreateBillboard;
        this.canEditBillboard = canEditBillboard;
        this.canScheduleBillboard = canScheduleBillboard;
        this.canEditUser = canEditUser;
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
     */
    public Permissions(int id, String username, boolean canCreateBillboard, boolean canEditBillboard, boolean canScheduleBillboard, boolean canEditUser) {
        this.id = id;
        this.username = username;
        this.canCreateBillboard = canCreateBillboard;
        this.canEditBillboard = canEditBillboard;
        this.canScheduleBillboard = canScheduleBillboard;
        this.canEditUser = canEditUser;
    }

    // Display annotation data relative to the objects fields
    @DisplayAs(value = "Id", index = 0)
    public int getId() {
        return id;
    }

    @DisplayAs(value = "Username", index = 1)
    public String getUsername() {
        return username;
    }

    @DisplayAs(value = "Can Create Billboard", index = 2, editable = true)
    public Boolean getCanCreateBillboard() {
        return canCreateBillboard;
    }

    public void setCanCreateBillboard(Boolean b) { canCreateBillboard = b; }

    @DisplayAs(value = "Can Edit Billboard", index = 3, editable = true)
    public Boolean getCanEditBillboard() {
        return canEditBillboard;
    }

    public void setCanEditBillboard(Boolean b) { canEditBillboard = b; }

    @DisplayAs(value = "Can Schedule Billboard", index = 4, editable = true)
    public Boolean getCanScheduleBillboard() {
        return canScheduleBillboard;
    }

    public void setCanScheduleBillboard(Boolean b) { canScheduleBillboard = b; }

    @DisplayAs(value = "Can Edit User", index = 5, editable = true)
    public Boolean getCanEditUser() {
        return canEditUser;
    }

    public void setCanEditUser(Boolean b) { canEditUser = b; }

    @Override
    public boolean isEditable() {
        return true;
    }

    /**
     * Generates a random permissions object with random variables
     * @param id: the id of the user
     * @param username: the user's username
     * @return a randomised permissions object
     */
    public static Permissions Random(int id, String username) {
        return new Permissions(
            id,
            username,
            RandomFactory.Boolean(),
            RandomFactory.Boolean(),
            RandomFactory.Boolean(),
            RandomFactory.Boolean()
        );
    }
}
