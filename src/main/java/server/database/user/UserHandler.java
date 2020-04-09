package server.database.user;

import common.models.Permissions;
import common.models.User;
import server.database.ObjectHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserHandler implements ObjectHandler<User> {
    Connection connection;

    // This is the mock "database" used for testing
    List<User> mockdb = new ArrayList<User>();

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

            ResultSet result = sqlStatement.executeQuery("SELECT * FROM USERS WHERE user.id = " + id);

            while (result.next()) {
                return Optional.of(User.fromSQL(result));

            }
            sqlStatement.close();
        } else {
            for (User u : this.mockdb) {
                if (u.id == id) {
                    return Optional.of(u);
                }
            }
        }

        return Optional.empty();
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
        ResultSet result = sqlStatement.executeQuery("SELECT * FROM user");

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
            "INSERT INTO user " +
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

            sqlStatement.executeUpdate("DELETE FROM user WHERE user.username = " + user.username);

            sqlStatement.close();
        } else {
            // use mockdb
        }

    }
}
