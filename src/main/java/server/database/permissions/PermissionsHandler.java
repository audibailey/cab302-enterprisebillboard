package server.database.permissions;

import java.sql.*;
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
    // This is the database connection
    Connection connection;

    // This is the mock "database" used for testing
    List<Permissions> mockDB = new ArrayList<Permissions>();

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
     * @param userId: the id of the user.
     * @return Optional<Permissions>: this returns the user permissions or an optional empty value.
     * @throws SQLException: this exception is thrown when there is an issue fetching data from the database.
     */
    public Optional<Permissions> get(int userId) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Initialise return value
            Optional<Permissions> ReturnPermissions = Optional.empty();
            // Create a query that gets the permission
            String query = "SELECT * FROM PERMISSIONS WHERE id = ?";
            // Attempt to query the database
            PreparedStatement pstmt = connection.prepareStatement(query);
            // Clear all parameters before insert
            pstmt.clearParameters();
            // Fill the parameters with the data and execute query
            pstmt.setInt(1, userId);

            // Create a query that selects user permissions based on the id and execute the query
            ResultSet result = pstmt.executeQuery();

            // Use the result of the database query to create the permissions object and save it
            while (result.next()) {
                ReturnPermissions = Optional.of(Permissions.fromSQL(result));
            }

            // Clean up query
            pstmt.close();

            return ReturnPermissions;
        } else {
            // Loop through and find the user permissions with the requested id or return an optional empty value
            for (Permissions p : this.mockDB) {
                if (p.id == userId) {
                    return Optional.of(p);
                }
            }

            return Optional.empty();
        }
    }

    /**
     * Select user permissions in the database based off username.
     *
     * @param username: the username of the user
     * @return Optional<Permissions>: this returns the user permissions or an optional empty value.
     * @throws SQLException: this exception is thrown when there is an issue fetching data from the database.
     */
    public Optional<Permissions> get(String username) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Initialise return value
            Optional<Permissions> ReturnPermissions = Optional.empty();
            // Create a query that updates the user and execute the query
            String query = "SELECT * FROM PERMISSIONS WHERE username = ?";
            // Attempt to query the database
            PreparedStatement pstmt = connection.prepareStatement(query);
            // Clear all parameters before insert
            pstmt.clearParameters();
            // Fill the parameters with the data and execute query
            pstmt.setString(1, username);

            // Create a query that selects user permissions based on the username and execute the query
            ResultSet result = pstmt.executeQuery();

            // Use the result of the database query to create the permissions object
            while (result.next()) {
                ReturnPermissions = Optional.of(Permissions.fromSQL(result));
            }

            // Clean up query
            pstmt.close();

            return ReturnPermissions;
        } else {
            // Loop through and find the user permissions with the requested name or return an optional empty value
            for (Permissions p : this.mockDB) {
                if (p.username.equals(username)) {
                    return Optional.of(p);
                }
            }
            return Optional.empty();
        }
    }

    /**
     * List all users permissions in the database.
     *
     * @return List<Permissions>: this returns the list of user permissions requested or a optional empty value.
     * @throws SQLException: this exception returns when there is an issue fetching data from the database.
     */
    public List<Permissions> getAll() throws SQLException {
        // Permissions list to be returned
        List<Permissions> permissions = new ArrayList<Permissions>();

        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
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
            permissions = this.mockDB;
        }

        return permissions;
    }

    /**
     * Insert permissions into the database.
     *
     * @param permissions: this is the requested user permissions to insert.
     * @throws SQLException: this exception returns when there is an issue sending data to the database.
     */
    public void insert(Permissions permissions) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Create a query that adds the user and execute the query

            String query = "INSERT INTO PERMISSIONS (id, username, canCreateBillboard, " +
                "canEditBillboard, canScheduleBillboard, canEditUsers, canViewBillboard) " +
                "VALUES (?,?,?,?,?,?,?)";

            // Attempt to query the database
            PreparedStatement pstmt = connection.prepareStatement(query);
            // Clear all parameters before insert
            pstmt.clearParameters();
            // Fill in the parameters and execute query
            pstmt.setInt(1, permissions.id);
            pstmt.setString(2, permissions.username);
            pstmt.setBoolean(3, permissions.canCreateBillboard);
            pstmt.setBoolean(4, permissions.canEditBillboard);
            pstmt.setBoolean(5, permissions.canScheduleBillboard);
            pstmt.setBoolean(6, permissions.canEditUser);
            pstmt.setBoolean(7, permissions.canViewBillboard);
            pstmt.executeUpdate();

            // Clean up query
            pstmt.close();
        } else {
            this.mockDB.add(permissions);
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


            // Create a query that updates the user permissions and execute the query

            String query = "UPDATE PERMISSIONS SET " +
                "canCreateBillboard = ?, canEditBillboard = ?, canScheduleBillboard = ?, " +
                "canEditUsers = ?, canViewBillboard = ? WHERE ID =?";
            // Attempt to query the database
            PreparedStatement pstmt = connection.prepareStatement(query);
            // Clear all parameters before insert
            pstmt.clearParameters();
            // Fill in the parameters and execute query
            pstmt.setBoolean(1, permissions.canCreateBillboard);
            pstmt.setBoolean(2, permissions.canEditBillboard);
            pstmt.setBoolean(3, permissions.canScheduleBillboard);
            pstmt.setBoolean(4, permissions.canEditUser);
            pstmt.setBoolean(5, permissions.canViewBillboard);
            pstmt.setInt(6, permissions.id);
            pstmt.executeUpdate();

            // Clean up query
            pstmt.close();
        } else {
            // Loop through mock database and find the user permissions to update, then update it
            for (Permissions mockPermissions : this.mockDB) {
                if (mockPermissions.id == permissions.id) {
                    mockPermissions = permissions;
                }
            }
        }
    }

    /**
     * Delete a users permissions from the database.
     *
     * @param permissions: the content of the permissions
     * @throws SQLException: this exception is thrown when there is an issue deleting data from the database.
     */
    public void delete(Permissions permissions) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {


            // Create a query that deletes the user permissions and executes the query
            String query = "DELETE FROM PERMISSIONS WHERE id = ?";
            // Attempt to query the database
            PreparedStatement pstmt = connection.prepareStatement(query);
            // Clear all parameters before insert
            pstmt.clearParameters();
            // Fill in the parameters and execute query
            pstmt.setInt(1, permissions.id);
            pstmt.executeUpdate();
            // Clean up query
            pstmt.close();
        } else {
            this.mockDB.remove(permissions);
        }
    }

    /**
     * Clears all Permission entries in database for unit test cleanup.
     *
     * @throws SQLException: this exception is thrown when there is an issue deleting data from the database.
     */
    public void deleteAll() throws SQLException {
        if (this.connection != null) {
            // Attempt to query the database
            Statement sqlStatement = connection.createStatement();
            // Create a query that deletes the billboard and executes the query
            sqlStatement.executeUpdate("DELETE FROM PERMISSIONS");

            // Cleans up query
            sqlStatement.close();
        } else {
            // Delete all billboards
            mockDB.clear();
        }
    }
}
