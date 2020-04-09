package server.database.permissions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.models.Permissions;
import server.database.ObjectHandler;

/**
 * This class is responsible for all the permissions object interactions with the database.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 * @author Jamie Martin
 */
public class PermissionsHandler implements ObjectHandler<Permissions> {
    Connection connection;

    // This is the mock "database" used for testing
    List<Permissions> MockDB = new ArrayList<Permissions>();

    /**
     * The PermissionsHandler Constructor.
     *
     * @param connection: This is the database connection from DataService.java.
     */
    public PermissionsHandler(Connection connection) {
        this.connection = connection;
    }

    /**
     * Select user permissions in the database based off id.
     *
     * @param id: the id of the user
     * @return Optional<Permissions>: this returns the user permissions or an optional empty value.
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public Optional<Permissions> get(int id) throws Exception {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Initialise return value
            Optional<Permissions> ReturnPermissions = Optional.empty();

            // Attempt to query the database
            Statement sqlStatement = connection.createStatement();

            // Create a query that selects user permissions based on the id and execute the query
            ResultSet result = sqlStatement.executeQuery("SELECT * FROM PERMISSIONS WHERE id = " + id);

            // Use the result of the database query to create the permissions object and save it
            while (result.next()) {
                ReturnPermissions = Optional.of(Permissions.fromSQL(result));
            }

            // Clean up query
            sqlStatement.close();

            return ReturnPermissions;
        } else {
            // Loop through and find the user permissions with the requested id or return an optional empty value
            for (Permissions p : this.MockDB) {
                if (p.id == id) {
                    return Optional.of(p);
                }
            }
        }
        // If it fails to get a result, return Optional empty
        return Optional.empty();
    }

    /**
     * Select user permissions in the database based off username.
     *
     * @param username: the username of the user
     * @return Optional<Permissions>: this returns the user permissions or an optional empty value.
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public Optional<Permissions> get(String username) throws Exception {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Initialise return value
            Optional<Permissions> ReturnPermissions = Optional.empty();

            // Attempt to query the database
            Statement sqlStatement = connection.createStatement();

            // Create a query that selects user permissions based on the username and execute the query
            ResultSet result = sqlStatement.executeQuery("SELECT * FROM PERMISSIONS WHERE username = '" + username + "'");

            // Use the result of the database query to create the permissions object
            while (result.next()) {
                ReturnPermissions = Optional.of(Permissions.fromSQL(result));
            }

            // Clean up query
            sqlStatement.close();

            return ReturnPermissions;
        } else {
            // Loop through and find the user permissions with the requested name or return an optional empty value
            for (Permissions p : this.MockDB) {
                if (p.username.equals(username)) {
                    return Optional.of(p);
                }
            }
        }
        // If it fails to get a result, return Optional empty
        return Optional.empty();
    }

    /**
     * List all users permissions in the database.
     *
     * @throws SQLException: this exception returns when there is an issue fetching data from the database.
     */
    public List<Permissions> getAll() throws SQLException {
        // Permissions list to be returned
        List<Permissions> permissions = new ArrayList<Permissions>();

        // Check that it's not in testing mode
        if (this.connection != null) {
            // Query the database for the permissions
            Statement sqlStatement = connection.createStatement();

            // Create a query that selects all users and execute the query
            ResultSet result = sqlStatement.executeQuery("SELECT * FROM PERMISSIONS");

            // Use the result of the database query to create the permissions object and add to return array
            while (result.next()) {
                permissions.add(Permissions.fromSQL(result));
            }

            // Clean up query
            sqlStatement.close();
        } else {
            permissions = this.MockDB;
        }

        return permissions;
    }

    /**
     * Insert permissions into the database.
     *
     * @param permissions: this is the requested user permissions to insert.
     * @throws SQLException : this exception returns when there is an issue sending data to the database.
     */
    public void insert(Permissions permissions) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            Statement sqlStatement = connection.createStatement();

            // Create a query that adds the user and execute the query
            String query = "INSERT INTO PERMISSIONS " +
                "(id, username, canCreateBillboard, canEditBillboard, " +
                "canScheduleBillboard, canEditUsers, canViewBillboard)" +
                "VALUES( " + permissions.id + permissions.username + "," + permissions.canCreateBillboard + "," +
                permissions.canEditBillboard + "," + permissions.canScheduleBillboard + "," + permissions.canEditUser + "," + permissions.canViewBillboard + ")";
            sqlStatement.executeUpdate(query);

            // Clean up query
            sqlStatement.close();
        } else {
            this.MockDB.add(permissions);
        }
    }

    /**
     * Update a users permission in the database.
     *
     * @param permissions: this is the requested user permissions to update.
     * @throws SQLException: this exception returns when there is an issue sending data to the database.
     */
    public void update(Permissions permissions) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            Statement sqlStatement = connection.createStatement();

            // Create a query that updates the user permissions and execute the query
            String query = "UPDATE PERMISSIONS SET canCreateBillboard = " +
                permissions.canCreateBillboard +
                ", canEditBillboard = " + permissions.canEditBillboard +
                ", canScheduleBillboard = " + permissions.canScheduleBillboard +
                ", canEditUsers = " + permissions.canEditUser +
                ", canViewBillboard = " + permissions.canViewBillboard +
                " WHERE ID = " + permissions.id;
            sqlStatement.executeUpdate(query);

            // Clean up query
            sqlStatement.close();
        } else {
            // Loop through mock database and find the user permissions to update, then update it
            for (Permissions mockPermissions : this.MockDB) {
                if (mockPermissions.id == permissions.id) {
                    mockPermissions = permissions;
                }
            }
        }
    }

    /**
     * Delete a permission based on id
     *
     * @param permissions: the content of the permissions
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public void delete(Permissions permissions) throws Exception {
        if (this.connection != null) {
            // Query the database for the billboard
            Statement sqlStatement = connection.createStatement();

            // Create a query that deletes the user permissions and executes the query
            sqlStatement.executeUpdate("DELETE FROM PERMISSIONS WHERE id =" + permissions.id);

            // Clean up query
            sqlStatement.close();
        } else {
            this.MockDB.remove(permissions);
        }
    }
}
