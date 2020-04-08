package server.database.user;

import common.models.User;
import common.models.UserPermissions;
import server.database.ObjectHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserHandler implements ObjectHandler<User> {
    Connection connection;

    public UserHandler(Connection connection) {
        this.connection = connection;
    }

    /**
     * Selects a billboard in the database based off billboard name.
     *
     * @param billboardName : the name of the billboard
     * @return
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public Optional<User> get(String userName) throws Exception {
        if (connection != null) {
            // Query the database for the billboard
            Statement sqlStatement = connection.createStatement();
            String query = "SELECT * FROM USER WHERE user.name = " + userName;
            boolean fetchResult = sqlStatement.execute(query);
            User temp = null;

            // Check if there was a result
            if (fetchResult) {
                // Use the result of the database to create billboard object
                ResultSet result = sqlStatement.executeQuery(query);
                while (result.next()) {

                    int ID = result.getInt("ID");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    boolean canCreateBillboard = result.getBoolean("createBillboard");
                    boolean canEditBillboard = result.getBoolean("editBillboard");
                    boolean canScheduleBillboard = result.getBoolean("scheduleBillboard");
                    boolean canEditUser = result.getBoolean("editUsers");
                    boolean canViewBillboard = result.getBoolean("viewBillboard");
                    temp = new User(ID,
                        username);
                    sqlStatement.close();

                }

            } else {
                sqlStatement.close();
                throw new Exception("No results.");
            }

            return Optional.ofNullable(temp);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Get user permissions
     **/
    public Optional<UserPermissions> getUserPermissions(String username) throws Exception {
        UserPermissions temp = null;
        Statement sqlStatement = connection.createStatement();
        String query = "SELECT * FROM userPermissions WHERE userPermissions.username = " + username;

        boolean checked = sqlStatement.execute(query);

        if (checked) {
            ResultSet result = sqlStatement.executeQuery(query);
            while (result.next()) {
                boolean canCreateBillboard = result.getBoolean("createBillboard");
                boolean canEditBillboard = result.getBoolean("editBillboard");
                boolean canScheduleBillboard = result.getBoolean("scheduleBillboard");
                boolean canEditUser = result.getBoolean("editUsers");
                boolean canViewBillboard = result.getBoolean("viewBillboard");

                temp = new UserPermissions(username, canCreateBillboard, canEditBillboard, canScheduleBillboard, canEditUser, canViewBillboard);
                sqlStatement.close();
            }

        } else {
            sqlStatement.close();
            throw new Exception("Error in getting user permissions");
        }

        return Optional.ofNullable(temp);
    }

    /**
     * List all billboards in the database.
     *
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public List<User> getAll() throws Exception {
        // User's list to be returned
        List<User> users = new ArrayList<>();
        List<UserPermissions> userPermissions = new ArrayList<>();
        User temp;
        UserPermissions temp1;
        // Query the database for the billboard
        Statement sqlStatement = connection.createStatement();
        String query = "SELECT * FROM user";

        boolean fetchResult = sqlStatement.execute(query);
        sqlStatement.close();

        // Check if there was a result
        if (fetchResult) {
            // Use the result of the database to create billboard object
            ResultSet result = sqlStatement.executeQuery(query);

            while (result.next()) {
                int ID = result.getInt("ID");
                String username = result.getString("username");
                String password = result.getString("password");


                String query1 = "SELECT * FROM userPermissions WHERE userPermissions.username = " + username;
                ResultSet result1 = sqlStatement.executeQuery(query);
                String userName = result1.getString("username");
                boolean canCreateBillboard = result1.getBoolean("createBillboard");
                boolean canEditBillboard = result1.getBoolean("editBillboard");
                boolean canScheduleBillboard = result1.getBoolean("scheduleBillboard");
                boolean canEditUser = result1.getBoolean("editUsers");
                boolean canViewBillboard = result1.getBoolean("viewBillboard");
                temp = new User(ID, username);
                temp1 = new UserPermissions(userName, canCreateBillboard,
                    canEditBillboard,
                    canScheduleBillboard,
                    canEditUser
                    , canViewBillboard);

                users.add(temp);
                userPermissions.add(temp1);
            }

        } else {
            throw new Exception("No results.");
        }
        return users;
    }

    public void insert(User user, UserPermissions userPermissions) throws Exception {

        Statement sqlStatement = connection.createStatement();
        String query = "INSERT INTO user " +
            "(username, password)" +
            "VALUES( " + user.username + "," + user.password + ")";

        String query1 = "INSERT INTO userPermissions " +
            "(username, createBillboard, editBillboard, " +
            "scheduleBillboard, editUsers, viewBillboard)" +
            "VALUES( " + userPermissions.username + "," + userPermissions.canCreateBillboard + "," +
            userPermissions.canEditBillboard + "," + userPermissions.canScheduleBillboard + "," + userPermissions.canEditUser + "," + userPermissions.canViewBillboard + ")";
        boolean check = sqlStatement.execute(query);
        if (!check) {
            throw new Exception("Error in insert");
        }
    }

    public void setPermissions(User user, UserPermissions userPermissions) throws Exception {
        Statement sqlStatement = connection.createStatement();
        String query = "UPDATE userPermissions SET createBillboard =" + userPermissions.canCreateBillboard +
            ", editBillboard =" + userPermissions.canEditBillboard +
            ", scheduleBillboard =" + userPermissions.canScheduleBillboard +
            ", editUsers =" + userPermissions.canEditUser +
            ", viewBillboard =" + userPermissions.canViewBillboard +
            " WHERE username = " + user.username;
        int checked = sqlStatement.executeUpdate(query);
        if (checked == 0) {
            throw new Exception("Error in updating user");
        }

    }

    /**
     * Delete a user base on userName
     *
     * @param user: the content of the user
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public void delete(User user) throws Exception {
        // Query the database for the billboard
        Statement sqlStatement = connection.createStatement();
        String query = "DELETE FROM user WHERE user.username = " + user.username;
        String query1 = "DELETE FROM userPermissions WHERE userPermissions.name =" + user.username;
        int fetchResult = sqlStatement.executeUpdate(query);
        int fetchResult1 = sqlStatement.executeUpdate(query1);
        sqlStatement.close();

        // Check if there was any rows affected in the database
        if (fetchResult == 0 || fetchResult1 == 0) {
            throw new Exception("No user with such name in database");
        }
    }
}
