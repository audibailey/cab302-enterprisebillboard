package server.database.permissions;

import common.models.Billboard;
import common.models.Permissions;
import common.models.User;
import server.database.ObjectHandler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PermissionsHandler implements ObjectHandler<Permissions> {
    Connection connection;

    // This is the mock "database" used for testing
    List<Permissions> mockdb = new ArrayList<Permissions>();

    public PermissionsHandler(Connection connection) {
        this.connection = connection;
    }

    /**
     * Select user permissions in the database based off username.
     *
     * @param id: the id of the user
     * @return
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public Optional<Permissions> get(int id) throws Exception {
        // Check that it's not in testing mode
        if (this.connection != null) {
            Statement sqlStatement = connection.createStatement();

            ResultSet result = sqlStatement.executeQuery("SELECT * FROM PERMISSIONS WHERE permissions.id = " + id);

            while (result.next()) {
                return Optional.of(Permissions.fromSQL(result));
            }
            sqlStatement.close();
        } else {
            for (Permissions p : this.mockdb) {
                if (p.id == id) {
                    return Optional.of(p);
                }
            }
        }
        // If it fails to get a result, return Optional empty
        return Optional.empty();
    }

    /**
     * List all users in the database.
     *
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public List<Permissions> getAll() throws Exception {
        // Permissions list to be returned
        List<Permissions> permissions = new ArrayList<Permissions>();

        // Check that it's not in testing mode
        if (this.connection != null) {
            // Query the database for the permissions
            Statement sqlStatement = connection.createStatement();

            // Use the result of the database to create billboard object
            ResultSet result = sqlStatement.executeQuery("SELECT * FROM PERMISSIONS");

            while (result.next()) {
                permissions.add(Permissions.fromSQL(result));
            }
            sqlStatement.close();
        } else {
            // Loop through and find the permissions with the lock status and add to billboards
            permissions = this.mockdb;
        }

        return permissions;
    }

    public void insert(Permissions permissions) throws Exception {
        if (this.connection != null) {
            Statement sqlStatement = connection.createStatement();

            String query = "INSERT INTO PERMISSIONS " +
                "(id, username, canCreateBillboard, canEditBillboard, " +
                "canScheduleBillboard, canEditUsers, canViewBillboard)" +
                "VALUES( " + permissions.id + permissions.username + "," + permissions.canCreateBillboard + "," +
                permissions.canEditBillboard + "," + permissions.canScheduleBillboard + "," + permissions.canEditUser + "," + permissions.canViewBillboard + ")";

            sqlStatement.executeUpdate(query);

            sqlStatement.close();
        } else {
            // use mockdb
        }
    }

    /**
     *
     * @param permissions
     * @throws Exception
     */
    public void update(Permissions permissions) throws Exception {
        if (this.connection != null) {
            Statement sqlStatement = connection.createStatement();

            sqlStatement.executeUpdate("");

            sqlStatement.close();
        } else {
            // use mockdb
        }
    }

    /**
     * Delete a permission based on id
     *
     * @param permissions: the content of the permisssions
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public void delete(Permissions permissions) throws Exception {
        if (this.connection != null) {
            // Query the database for the billboard
            Statement sqlStatement = connection.createStatement();

            sqlStatement.executeUpdate("DELETE FROM PERMISSIONS WHERE permissions.id =" + permissions.id);

            sqlStatement.close();
        } else {
            // use mockdb
        }
    }
}
