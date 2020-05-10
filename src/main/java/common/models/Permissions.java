package common.models;

import common.utils.RandomFactory;

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
@SQL(type="PRIMARY KEY(id), CONSTRAINT FK_USERPERMID FOREIGN KEY (id) REFERENCES USER(id), CONSTRAINT FK_USERPERMUNAME FOREIGN KEY (username) REFERENCES USER(username))")
@SQLITE(type="FOREIGN KEY(id) REFERENCES User(id), FOREIGN KEY(username) REFERENCES User(username)")
public class Permissions implements Serializable {
    /**
     * The variables of the object Permissions
     */
    @SQL(type="int NOT NULL AUTO_INCREMENT UNIQUE")
    @SQLITE(type="INTEGER PRIMARY KEY AUTOINCREMENT")
    public int id;
    @SQL(type="varchar(255) NOT NULL UNIQUE")
    @SQLITE(type="VARCHAR(255) NOT NULL UNIQUE")
    public String username;
    @SQL(type="BOOLEAN")
    @SQLITE(type="BOOLEAN")
    public boolean canCreateBillboard;
    @SQL(type="BOOLEAN")
    @SQLITE(type="BOOLEAN")
    public boolean canEditBillboard;
    @SQL(type="BOOLEAN")
    @SQLITE(type="BOOLEAN")
    public boolean canScheduleBillboard;
    @SQL(type="BOOLEAN")
    @SQLITE(type="BOOLEAN")
    public boolean canEditUser;
    @SQL(type="BOOLEAN")
    @SQLITE(type="BOOLEAN")
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
            RandomFactory.Boolean(),
            RandomFactory.Boolean()
        );
    }
}
