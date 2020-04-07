package server.database.billboard;

import common.Billboard;
import server.database.ObjectHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BillboardHandler extends ObjectHandler<Billboard> {

    public BillboardHandler(Connection connection) {
        super(connection);
    }

    /**
     * Selects all billboards in the database.
     *
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public List<Billboard> getAll(boolean lock) throws Exception {
        // Billboard to be returned
        List<Billboard> billboard = new ArrayList<>();
        Billboard temp;
        // Query the database for the billboard
        Statement sqlStatement = connection.createStatement();
        String query = "SELECT * FROM BILLBOARD WHERE Billboard.locked = " + lock;
        boolean fetchResult = sqlStatement.execute(query);

        // Check if there was a result
        if (fetchResult) {
            // Use the result of the database to create billboard object
            ResultSet result = sqlStatement.executeQuery(query);
            while (result.next()) {
                int billboardID = result.getInt("ID");
                String name = result.getString("name");
                String message = result.getString("message");
                String messageColor = result.getString("messageColor");
                byte[] picture = result.getBytes("picture");
                String backgroundColor = result.getString("backgroundColor");
                String information = result.getString("information");
                String informationColor = result.getString("informationColor");
                boolean locked = result.getBoolean("locked");
                int userID = result.getInt("userID");
                temp = new Billboard(billboardID,
                    name,
                    message,
                    messageColor,
                    picture,
                    backgroundColor,
                    information,
                    informationColor,
                    locked,
                    userID);
                billboard.add(temp);
            }
        } else {
            sqlStatement.close();
            throw new Exception("No results.");
        }

        sqlStatement.close();

        return billboard;
    }

    /**
     * Selects a billboard in the database based off billboard name.
     *
     * @param billboardName : the name of the billboard
     * @return
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public Optional<Billboard> get(String billboardName) throws Exception {
        // Billboard to be returned
        Billboard billboard = null;

        // Query the database for the billboard
        Statement sqlStatement = connection.createStatement();
        String query = "SELECT * FROM BILLBOARD WHERE billboard.name = " + billboardName;
        boolean fetchResult = sqlStatement.execute(query);

        // Check if there was a result
        if (fetchResult) {
            // Use the result of the database to create billboard object
            ResultSet result = sqlStatement.executeQuery(query);
            while (result.next()) {

                int billboardID = result.getInt("ID");
                String name = result.getString("name");
                String message = result.getString("message");
                String messageColor = result.getString("textColor");
                byte[] picture = result.getBytes("picture");
                String backgroundColor = result.getString("backgroundColor");
                String information = result.getString("information");
                String informationColor = result.getString("informationColor");
                boolean locked = result.getBoolean("locked");
                int userID = result.getInt("userID");
                billboard = new Billboard(billboardID,
                    name,
                    message,
                    messageColor,
                    picture,
                    backgroundColor,
                    information,
                    informationColor,
                    locked,
                    userID);
                sqlStatement.close();

            }

        } else {
            sqlStatement.close();
            throw new Exception("No results.");
        }

        return Optional.ofNullable(billboard);
    }

    /**
     * List all billboards in the database.
     *
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public List<Billboard> getAll() throws Exception {
        // Billboard to be returned
        List<Billboard> billboard = new ArrayList<>();
        Billboard temp;
        // Query the database for the billboard
        Statement sqlStatement = connection.createStatement();
        String query = "SELECT * FROM billboard";
        boolean fetchResult = sqlStatement.execute(query);
        sqlStatement.close();

        // Check if there was a result
        if (fetchResult) {
            // Use the result of the database to create billboard object
            ResultSet result = sqlStatement.executeQuery(query);
            while (result.next()) {
                int billboardID = result.getInt("ID");
                String name = result.getString("name");
                String message = result.getString("message");
                String messageColor = result.getString("textColor");
                byte[] picture = result.getBytes("picture");
                String backgroundColor = result.getString("backgroundColor");
                String information = result.getString("information");
                String informationColor = result.getString("informationColor");
                boolean locked = result.getBoolean("locked");
                int userID = result.getInt("userID");
                temp = new Billboard(billboardID,
                    name,
                    message,
                    messageColor,
                    picture,
                    backgroundColor,
                    information,
                    informationColor,
                    locked,
                    userID);
                billboard.add(temp);
            }

        } else {
            throw new Exception("No results.");
        }
        return billboard;
    }

    /**
     * Create a billboard if it's not in the database
     *
     * @param billboardName: the name of the billboard
     * @param billboard      : a billboard object with contents in it
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public void insert(Billboard billboard) throws Exception {

        // Query the database for the billboard
        Statement sqlStatement = connection.createStatement();

        String query = "INSERT INTO billboard " +
            "(userID, name, message, " +
            "messageColor, picture, backgroundColor," +
            " information, informationColor, locked)" +
            "VALUES( " + billboard.userID + "," + billboard.name + "," +
            billboard.message + "," + billboard.messageColor + "," + Arrays.toString(billboard.picture) + "," +
            billboard.backgroundColor + "," + billboard.information + "," + billboard.informationColor +
            "," + billboard.locked + ")";

        boolean check = sqlStatement.execute(query);

        if (!check) {
            throw new Exception("Error in insert");
        }

    }

    public void update(Billboard billboard) throws Exception {
        // Query the database for the billboard
        Statement sqlStatement = connection.createStatement();

        String query = "UPDATE billboard SET message = " + billboard.message + ", messageColor =" + billboard.messageColor +
            ", picture = " + Arrays.toString(billboard.picture) + ", backgroundColor = " + billboard.backgroundColor +
            ", information = " + billboard.information + ", informationColor = " + billboard.informationColor + ", locked =" + billboard.locked +
            "WHERE name = " + billboard.name;
        int checked = sqlStatement.executeUpdate(query);
        if (checked == 0) {
            throw new Exception("Error in updating");
        }
    }

    /**
     * Delete a billboard base on billboardName
     *
     * @param billboardName: the name of the billboard
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public void delete(Billboard billboard) throws Exception {
        // Query the database for the billboard
        Statement sqlStatement = connection.createStatement();
        String query = "DELETE FROM billboard WHERE billboard.name = " + billboard.name;
        int fetchResult = sqlStatement.executeUpdate(query);
        sqlStatement.close();

        // Check if there was any rows affected in the database
        if (fetchResult == 0) {
            throw new Exception("No billboard with such name in database");
        }
    }
}
