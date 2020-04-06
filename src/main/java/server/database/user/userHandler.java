package server.database.user;

import common.Billboard;
import common.User;
import server.database.DBHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class userHandler implements DBHandler<User> {
    private static Connection dbconn;

    public userHandler(Connection connection) {
        this.dbconn = connection;
    }

    /**
     * Selects a billboard in the database based off billboard name.
     *
     * @param billboardName : the name of the billboard
     * @return
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public Optional<User> get(String userName) throws Exception {
        // Query the database for the billboard
        Statement sqlStatement = dbconn.createStatement();
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
                    username,
                    canCreateBillboard,
                    canEditBillboard,
                    canScheduleBillboard,
                    canEditUser,
                    canViewBillboard);
                sqlStatement.close();

            }

        } else {
            sqlStatement.close();
            throw new Exception("No results.");
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
        User temp;
        // Query the database for the billboard
        Statement sqlStatement = dbconn.createStatement();
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
                boolean canCreateBillboard = result.getBoolean("createBillboard");
                boolean canEditBillboard = result.getBoolean("editBillboard");
                boolean canScheduleBillboard = result.getBoolean("scheduleBillboard");
                boolean canEditUser = result.getBoolean("editUsers");
                boolean canViewBillboard = result.getBoolean("viewBillboard");
                temp = new User(ID,
                    username,
                    canCreateBillboard,
                    canEditBillboard,
                    canScheduleBillboard,
                    canEditUser,
                    canViewBillboard);
                users.add(temp);
            }

        } else {
            throw new Exception("No results.");
        }
        return users;
    }

    @Override
    public void insert(User user) throws Exception {

        Statement sqlStatement = dbconn.createStatement();
        String query = "INSERT INTO user " +
            "(username, password, createBillboard, editBillboard, " +
            " scheduleBillboard, editUsers, viewBillboard)" +
            "VALUES( " + user.username + "," + user.password + ',' + user.canCreateBillboard + "," +
            user.canEditBillboard + "," + user.canScheduleBillboard + "," +
            user.canEditUser + "," + user.canViewBillboard + "," + ")";

        boolean check = sqlStatement.execute(query);
        if (!check) {
            throw new Exception("Error in insert");
        }
    }

    @Override
    public void update(User user) throws Exception {

    }

    /**
     * Delete a user base on userName
     *
     * @param userName: the name of the billboard
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public void delete(User user) throws Exception {
        // Query the database for the billboard
        Statement sqlStatement = dbconn.createStatement();
        String query = "DELETE FROM user WHERE user.username = " + user.username;
        int fetchResult = sqlStatement.executeUpdate(query);
        sqlStatement.close();

        // Check if there was any rows affected in the database
        if (fetchResult == 0) {
            throw new Exception("No user with such name in database");
        }
    }
}
