package server.database.user;

import common.models.Permissions;
import common.models.User;
import server.database.ObjectHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.models.Billboard;
import common.models.UserPermissions;

/**
 * This class is responsible for all the user object interactions with the database.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 * @author Jamie Martin
 */
public class UserHandler implements ObjectHandler<User> {
    // This is the database connection
    Connection connection;

    // This is the mock "database" used for testing
    List<User> MockDB = new ArrayList<User>();

    /**
     * The UserdHandler Constructor.
     *
     * @param connection: This is the database connection from DataService.java.
     */
    public UserHandler(Connection connection) {
        this.connection = connection;
    }

    /**
     * Selects a user in the database based off user name.
     *
     * @param id: the id of the user
     * @return
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public Optional<User> get(int id) throws Exception {
        // Check that it's not in testing mode
        if (this.connection != null) {

            Statement sqlStatement = connection.createStatement();

            ResultSet result = sqlStatement.executeQuery("SELECT * FROM USERS WHERE USERS.id = " + id);

            while (result.next()) {
                return Optional.of(User.fromSQL(result));

            }
            sqlStatement.close();
        } else {
            for (User u : this.MockDB) {
                if (u.id == id) {
                    return Optional.of(u);
                }
            }
        }

        return Optional.empty();
    }

    /** Selects a User in the database based off the username.
     *
     * @param UserName: this is the requested user username.
     * @return Optional<User>: this returns the user or an optional empty value.
     * @throws SQLException: this exception is thrown when there is an issue fetching data from the database.
     */
    public Optional<User> get(String UserName) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            try (Statement sqlStatement = this.connection.createStatement()) {
                // Create a query that selects billboards based on the name and execute the query
                String query = "SELECT * FROM USERS WHERE USERS.username = '" + UserName + "'";
                ResultSet result = sqlStatement.executeQuery(query);

                // Use the result of the database query to create billboard object and return it
                while (result.next()) {
                    return Optional.of(User.fromSQL(result));
                }

                // If it fails to get a result, return Optional empty
                return Optional.empty();
            } catch (SQLException e) {
                // Throw an exception
                throw new SQLException(String.format("Failed to fetch billboard from database. Error: %s.", e.toString()));
            }
        } else {
            // Loop through and find the billboard with the requested name or return an optional empty value
            for (User user : this.MockDB) {
                if (user.username.equals(UserName)) {
                    return Optional.of(user);
                }
            }

            return Optional.empty();
        }

    }

    /**
     * List all users in the database.
     *
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public List<User> getAll() throws Exception {
        // User's list to be returned
        List<User> users = new ArrayList<>();

        // Query the database for the billboard
        Statement sqlStatement = connection.createStatement();

        // Use the result of the database to create billboard object
        ResultSet result = sqlStatement.executeQuery("SELECT * FROM USERS");

        while (result.next()) {
            users.add(User.fromSQL(result));
        }

        sqlStatement.close();

        return users;
    }

    /**
     *
     * @param user
     * @throws Exception
     */
    public void insert(User user) throws Exception {
        Statement sqlStatement = connection.createStatement();

        sqlStatement.executeUpdate(
            "INSERT INTO USERS " +
                 "(username, password, salt)" +
                 "VALUES( " + user.username +
                 "," + user.password +
                 "," + user.salt + ")");

        sqlStatement.close();
    }

    /**
     *
     * @param user
     * @throws Exception
     */
    public void update(User user) throws Exception {
        if (this.connection != null) {
            Statement sqlStatement = connection.createStatement();

            sqlStatement.executeUpdate("");

            sqlStatement.close();
        } else {
            // use mockdb
        }
    }

    /**
     * Delete a user base on userName
     *
     * @param user: the content of the user
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public void delete(User user) throws Exception {
        if (this.connection != null) {
            Statement sqlStatement = connection.createStatement();

            sqlStatement.executeUpdate("DELETE FROM USERS WHERE USERS.id = " + user.id);

            sqlStatement.close();
        } else {
            // use mockdb
        }
    }
}
