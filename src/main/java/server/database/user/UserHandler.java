package server.database.user;

import common.models.User;
import server.database.ObjectHandler;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
    List<User> mockDB = new ArrayList<User>();
    int MockDBNum = 0;

    /**
     * The UserHandler Constructor.
     *
     * @param connection: This is the database connection from DataService.java.
     */
    public UserHandler(Connection connection) {
        this.connection = connection;
    }

    /**
     * Selects a user in the database based off id.
     *
     * @param id: the id of the user
     * @return Optional<User>: this returns the user or an optional empty value.
     * @throws SQLException: this exception is a pass-through exception with a no results extended exception
     */
    public Optional<User> get(int id) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Initialise return value
            Optional<User> ReturnUser = Optional.empty();

            // Attempt to query the database
            Statement sqlStatement = connection.createStatement();
            ResultSet result = sqlStatement.executeQuery("SELECT * FROM USERS WHERE id = " + id);

            // Use the result of the database query to create the User object and return it
            while (result.next()) {
                ReturnUser = Optional.of(User.fromSQL(result));
            }

            // Clean up query
            sqlStatement.close();

            return ReturnUser;
        } else {
            // Loop through and find the user with the requested name or return an optional empty value
            for (User u : this.mockDB) {
                if (u.id == id) {
                    return Optional.of(u);
                }
            }
        }

        return Optional.empty();
    }

    /** Selects a User in the database based off the username.
     *
     * @param username: this is the requested user username.
     * @return Optional<User>: this returns the user or an optional empty value.
     * @throws SQLException: this exception is thrown when there is an issue fetching data from the database.
     */
    public Optional<User> get(String username) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Initialise return value
            Optional<User> ReturnUser = Optional.empty();

            // Attempt to query the database
            Statement sqlStatement = this.connection.createStatement();

            // Create a query that selects user based on the name and execute the query
            String query = "SELECT * FROM USERS WHERE username = '" + username + "'";
            ResultSet result = sqlStatement.executeQuery(query);

            // Use the result of the database query to create the User object and return it
            while (result.next()) {
                ReturnUser = Optional.of(User.fromSQL(result));
            }

            // Clean up query
            sqlStatement.close();

            return ReturnUser;
        } else {
            // Loop through and find the user with the requested name or return an optional empty value
            for (User user : this.mockDB) {
                if (user.username.equals(username)) {
                    return Optional.of(user);
                }
            }
        }

        return Optional.empty();
    }

    /**
     * List all users in the database.
     *
     * @throws SQLException: this exception returns when there is an issue fetching data from the database.
     */
    public List<User> getAll() throws SQLException {
        // User's list to be returned
        List<User> users = new ArrayList<>();

        // Check that it's not in testing mode
        if (this.connection != null) {

            // Attempt to query the database
            Statement sqlStatement = connection.createStatement();

            // Create a query that selects all users and execute the query
            ResultSet result = sqlStatement.executeQuery("SELECT * FROM USERS");

            // Use the result of the database query to create user object and add it to the returned list
            while (result.next()) {
                users.add(User.fromSQL(result));
            }

            // Clean up query
            sqlStatement.close();
        } else {
            users = this.mockDB;
        }

        return users;
    }

    /**
     * Insert a user into the database.
     *
     * @param user: this is the requested user to insert.
     * @throws SQLException: this exception returns when there is an issue sending data to the database.
     */
    public void insert(User user) throws SQLException {

        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            Statement sqlStatement = connection.createStatement();

            // Create a query that adds the user and execute the query
            sqlStatement.executeUpdate(
                "INSERT INTO USERS " +
                    "(username, password, salt)" +
                    "VALUES( '" + user.username +
                    "','" + user.password +
                    "','" + user.salt + "')");

            // Clean up query
            sqlStatement.close();
        } else {
            // Emulate auto increment ID
            user.id = MockDBNum;
            this.mockDB.add(user);
            MockDBNum++;
        }
    }

    /**
     * Update a user in the database.
     *
     * @param user: this is the requested user to update.
     * @throws SQLException: this exception returns when there is an issue sending data to the database.
     */
    public void update(User user) throws SQLException {

        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            Statement sqlStatement = connection.createStatement();

            // Create a query that updates the user and execute the query
            String query = "UPDATE USERS SET password = '" +
                user.password +
                "', salt ='" + user.salt +
                "' WHERE ID = " + user.id;

            sqlStatement.executeUpdate(query);

            // Clean up query
            sqlStatement.close();
        } else {
            // Loop through mock database and find the user to update, then update it
            for (User mockUser : this.mockDB) {
                if (mockUser.id == user.id) {
                    mockUser = user;
                }
            }
        }
    }

    /**
     * Delete a user from the database.
     *
     * @param user: this is the requested user to delete.
     * @throws SQLException: this exception is thrown when there is an issue deleting data from the database.
     */
    public void delete(User user) throws SQLException {

        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            Statement sqlStatement = connection.createStatement();

            // Create a query that deletes the user and executes the query
            sqlStatement.executeUpdate("DELETE FROM USERS WHERE id = " + user.id);

            // Clean up query
            sqlStatement.close();
        } else {
            this.mockDB.remove(user);
        }
    }

    /**
     * clears all entries in database for unit test cleanup
     * @throws Exception
     */
    public void deleteAll() throws Exception {
        if (this.connection != null) {
            // Attempt to query the database
            Statement sqlStatement = connection.createStatement();
            // Create a query that deletes the billboard and executes the query
            String query = "DELETE FROM USERS";
            sqlStatement.executeUpdate(query);

            // Cleans up query
            sqlStatement.close();
        } else {
            // Delete all billboards
            mockDB.clear();
        }
    }
}
