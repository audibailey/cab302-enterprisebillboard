package server.database.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.models.Permissions;
import server.database.ObjectHandler;

public abstract class PermissionHandler implements ObjectHandler<Permissions> {
    // This is the database connection
    Connection connection;

    // This is the mock "database" used for testing
    List<Permissions> MockDB = new ArrayList<Permissions>();

    /**
     * Selects a UserPermission in the database based off the userID.
     *
     * @param UserID: this is the requested users ID.
     * @return Optional<UserPermission>: this returns the user permissions or an optional empty value.
     * @throws SQLException: this exception is thrown when there is an issue fetching data from the database.
     */
    private Optional<Permissions> GetUserPermissions(int UserID) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Initialise return value
            Optional<Permissions> ReturnedPerms = Optional.empty();

            // Attempt to query the database
            try (Statement sqlStatement = this.connection.createStatement()) {
                // Create a query that selects users permissions based on the name and execute the query
                String query = "SELECT * FROM USERPERMISSIONS WHERE ID = " + UserID;
                ResultSet result = sqlStatement.executeQuery(query);

                // Use the result of the database query to create UserPermission object and return it
                while (result.next()) {
                    ReturnedPerms = Optional.of(Permissions.fromSQL(result));
                }

                // Clean up query
                sqlStatement.close();

                return ReturnedPerms;
            } catch (SQLException e) {
                // Throw an exception
                throw new SQLException(String.format("Failed to fetch billboard from database. Error: %s.", e.toString()));
            }
        } else {
            // Loop through and find the billboard with the requested name or return an optional empty value
            for (Permissions userperm : this.MockDB) {
                if (userperm.id == UserID) {
                    return Optional.of(userperm);
                }
            }

            // If it fails to get a result, return Optional empty
            return Optional.empty();
        }
    }

    /**
     * Insert a users permission into the database.
     *
     * @param permissions: this is the requested users permissions to insert.
     * @throws SQLException: this exception returns when there is an issue sending data to the database.
     */
    public void insert(Permissions permissions) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            Statement sqlStatement = connection.createStatement();
            // Create a query that inserts the user permissions and executes the query
            String InsertUserPermissionsQuery = "INSERT INTO userPermissions " +
                "(id, username, createBillboard, editBillboard, " +
                "scheduleBillboard, editUsers, viewBillboard)" +
                "VALUES( " +
                permissions.id + "," +
                permissions.username + "," +
                permissions.canCreateBillboard + "," +
                permissions.canEditBillboard + "," +
                permissions.canScheduleBillboard + "," +
                permissions.canEditUser + "," +
                permissions.canViewBillboard + ")";
            sqlStatement.execute(InsertUserPermissionsQuery);

            // Clean up query
            sqlStatement.close();
        } else {
            MockDB.add(permissions);
        }
    }

    /**
     * Update a users permissions in the database.
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
            String query = "UPDATE userPermissions SET createBillboard =" +
                permissions.canCreateBillboard +
                ", editBillboard =" + permissions.canEditBillboard +
                ", scheduleBillboard =" + permissions.canScheduleBillboard +
                ", editUsers =" + permissions.canEditUser +
                ", viewBillboard =" + permissions.canViewBillboard +
                " WHERE ID = " + permissions.id;
            sqlStatement.executeUpdate(query);

            // Clean up query
            sqlStatement.close();
        } else {
            // Loop through mock database and find the users permission to update, then update it
            for (Permissions mockPermission : this.MockDB) {
                if (mockPermission.id == permissions.id) {
                    mockPermission = permissions;
                }
            }
        }
    }

    /**
     * Delete a users permissions from the database.
     *
     * @param permissions: this is the requested users permissions to delete.
     * @throws SQLException: this exception is thrown when there is an issue deleting data from the database.
     */
    public void delete(Permissions permissions) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            Statement sqlStatement = connection.createStatement();
            // Create a query that deletes the user permissions and executes the query
            String query = "DELETE FROM userpermissions WHERE ID = " + permissions.id;
            sqlStatement.executeUpdate(query);

            // Cleans up query
            sqlStatement.close();
        } else {
            // Delete billboard
            MockDB.remove(permissions);
        }
    }
}
