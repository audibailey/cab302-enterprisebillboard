package server.database.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.models.Billboard;
import common.models.User;
import common.models.UserPermissions;
import server.database.ObjectHandler;

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
    List<UserPermissions> MockDBPerms = new ArrayList<UserPermissions>();

    /**
     * The UserdHandler Constructor.
     *
     * @param connection: This is the database connection from DataService.java.
     */
    public UserHandler(Connection connection) {
        this.connection = connection;
    }

    /**
     * Selects a User in the database based off the username.
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
                String query = "SELECT * FROM USER WHERE USER.username = '" + UserName + "'";
                ResultSet result = sqlStatement.executeQuery(query);

                // Use the result of the database query to create billboard object and return it
                while (result.next()) {
                    Optional<UserPermissions> userperms = GetUserPermissions(result.getInt("userID"));
                    if (userperms.isPresent()) {
                        return Optional.of(User.fromSQL(result, userperms.get()));
                    }
                }

                // If it fails to get a result, return Optional empty
                return Optional.empty();
            } catch (SQLException e) {
                // Throw an exception
                throw new SQLException(String.format("Failed to fetch billboard from database. Error: %s.", e.toString()));
            }
        } else {
            // Loop through and find the billboard with the requested name or return an optional empty value
            // TODO: fetch the perms before returning it
            for (User user : this.MockDB) {
                if (user.username.equals(UserName)) {
                    return Optional.of(user);
                }
            }

            return Optional.empty();
        }

    }

    public Optional<UserPermissions> GetUserPermissions(int UserID) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            try (Statement sqlStatement = this.connection.createStatement()) {
                // Create a query that selects billboards based on the name and execute the query
                String query = "SELECT * FROM USERPERMISSIONS WHERE ID = " + UserID;
                ResultSet result = sqlStatement.executeQuery(query);

                // Use the result of the database query to create billboard object and return it
                while (result.next()) {
                    return Optional.of(UserPermissions.fromSQL(result));
                }

                // If it fails to get a result, return Optional empty
                return Optional.empty();
            } catch (SQLException e) {
                // Throw an exception
                throw new SQLException(String.format("Failed to fetch billboard from database. Error: %s.", e.toString()));
            }
        } else {
            // Loop through and find the billboard with the requested name or return an optional empty value
            for (UserPermissions userperm : this.MockDBPerms) {
                if (userperm.id == UserID) {
                    return Optional.of(userperm);
                }
            }

            return Optional.empty();
        }
    }

//    /**
//     * List all billboards in the database.
//     *
//     * @throws Exception: this exception is a pass-through exception with a no results extended exception
//     */
//    public List<User> getAll() throws Exception {
//        // User's list to be returned
//        List<User> users = new ArrayList<>();
//        List<UserPermissions> userPermissions = new ArrayList<>();
//        User temp;
//        UserPermissions temp1;
//        // Query the database for the billboard
//        Statement sqlStatement = connection.createStatement();
//        String query = "SELECT * FROM user";
//
//        boolean fetchResult = sqlStatement.execute(query);
//        sqlStatement.close();
//
//        // Check if there was a result
//        if (fetchResult) {
//            // Use the result of the database to create billboard object
//            ResultSet result = sqlStatement.executeQuery(query);
//
//            while (result.next()) {
//                int ID = result.getInt("ID");
//                String username = result.getString("username");
//                String password = result.getString("password");
//
//
//                String query1 = "SELECT * FROM userPermissions WHERE userPermissions.username = " + username;
//                ResultSet result1 = sqlStatement.executeQuery(query);
//                String userName = result1.getString("username");
//                boolean canCreateBillboard = result1.getBoolean("createBillboard");
//                boolean canEditBillboard = result1.getBoolean("editBillboard");
//                boolean canScheduleBillboard = result1.getBoolean("scheduleBillboard");
//                boolean canEditUser = result1.getBoolean("editUsers");
//                boolean canViewBillboard = result1.getBoolean("viewBillboard");
//                temp = new User(ID, username);
//                temp1 = new UserPermissions(userName, canCreateBillboard,
//                    canEditBillboard,
//                    canScheduleBillboard,
//                    canEditUser
//                    , canViewBillboard);
//
//                users.add(temp);
//                userPermissions.add(temp1);
//            }
//
//        } else {
//            throw new Exception("No results.");
//        }
//        return users;
//    }

//    public void insert(User user, UserPermissions userPermissions) throws Exception {
//
//        Statement sqlStatement = connection.createStatement();
//        String query = "INSERT INTO user " +
//            "(username, password)" +
//            "VALUES( " + user.username + "," + user.password + ")";
//
//        String query1 = "INSERT INTO userPermissions " +
//            "(username, createBillboard, editBillboard, " +
//            "scheduleBillboard, editUsers, viewBillboard)" +
//            "VALUES( " + userPermissions.username + "," + userPermissions.canCreateBillboard + "," +
//            userPermissions.canEditBillboard + "," + userPermissions.canScheduleBillboard + "," + userPermissions.canEditUser + "," + userPermissions.canViewBillboard + ")";
//        boolean check = sqlStatement.execute(query);
//        if (!check) {
//            throw new Exception("Error in insert");
//        }
//    }
//
//    public void setPermissions(User user, UserPermissions userPermissions) throws Exception {
//        Statement sqlStatement = connection.createStatement();
//        String query = "UPDATE userPermissions SET createBillboard =" + userPermissions.canCreateBillboard +
//            ", editBillboard =" + userPermissions.canEditBillboard +
//            ", scheduleBillboard =" + userPermissions.canScheduleBillboard +
//            ", editUsers =" + userPermissions.canEditUser +
//            ", viewBillboard =" + userPermissions.canViewBillboard +
//            " WHERE username = " + user.username;
//        int checked = sqlStatement.executeUpdate(query);
//        if (checked == 0) {
//            throw new Exception("Error in updating user");
//        }
//
//    }
//
//    /**
//     * Delete a user base on userName
//     *
//     * @param user: the content of the user
//     * @throws Exception: this exception is a pass-through exception with a no results extended exception
//     */
//    public void delete(User user) throws Exception {
//        // Query the database for the billboard
//        Statement sqlStatement = connection.createStatement();
//        String query = "DELETE FROM user WHERE user.username = " + user.username;
//        String query1 = "DELETE FROM userPermissions WHERE userPermissions.name =" + user.username;
//        int fetchResult = sqlStatement.executeUpdate(query);
//        int fetchResult1 = sqlStatement.executeUpdate(query1);
//        sqlStatement.close();
//
//        // Check if there was any rows affected in the database
//        if (fetchResult == 0 || fetchResult1 == 0) {
//            throw new Exception("No user with such name in database");
//        }
//    }
}
