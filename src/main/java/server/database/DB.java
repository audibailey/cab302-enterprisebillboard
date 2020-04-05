package server.database;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import common.*;

/**
 * This class consists of the database handler. All methods that require direct access
 * to the database are present in this file, along with the database connection and any
 * other methods that work with the database connection.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 */
public class DB {
    /**
     * Main point of contact for the database
     */
    private Connection database;

    /**
     * Creates the database object and connects to the database management software.
     * If the DBMS doesn't have the database, create the database and update
     * the object to use that database as the connection as well as populate it.
     *
     * @throws Exception: this exception is just a pass-through
     */
    public DB() throws Exception {

        // Configure the database from the prop file
        Properties props = getProps();
        String url = props.getProperty("jdbc.url");
        String schema = props.getProperty("jdbc.schema");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        // Connect to the DBMS and populate the schema
        this.database = DriverManager.getConnection(url, username, password);
        populateSchema(url, username, password, schema);
    }

    /**
     * Gets the properties from db.props for the database connection;
     *
     * @return Properties: the required properties to connect to the database
     * @throws Exception: this exception is just a pass-through
     */
    private Properties getProps() throws Exception {

        // Initialize variables
        Properties props = new Properties();
        FileInputStream in;

        // Read the props file into the properties object
        in = new FileInputStream("./db.props");
        props.load(in);
        in.close();

        // Return the properties object
        return props;
    }

    /**
     * Creates the database, populates the database and switches the connection to use the database;
     *
     * @param url:          the DBMS url. Example: jdbc:mysql://127.0.0.1
     * @param username:     the DBMS password
     * @param password:     the DBMS password
     * @param schema:       the database name/schema
     * @throws SQLException: this exception is just a pass-through
     */
    private void populateSchema(String url, String username, String password, String schema) throws SQLException {
        // Create an SQL statement
        Statement sqlStatement = this.database.createStatement();

        // Create and test new database
        sqlStatement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + schema);
        sqlStatement.executeUpdate("USE " + schema);

        // Create user table
        sqlStatement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS user(" +
                "userID int NOT NULL," +
                "username varchar(255)," +
                "password varchar(255)," +
                "salted varchar(255)," +
                "createBillboard BOOLEAN," +
                "editBillboard BOOLEAN," +
                "scheduleBillboard BOOLEAN," +
                "editUsers BOOLEAN, " +
                "PRIMARY KEY(userID))"
        );

        // Create billboard table
        sqlStatement.executeUpdate(
            "CREATE TABLE IF NOT EXISTS billboard(" +
                "billboardID int NOT NULL," +
                "userID int NOT NULL," +
                "billboardName varchar(255), " +
                "message varchar(255), " +
                "messageColor varchar(7), " +
                "picture LONGTEXT," +
                "backgroundColor varchar(7)," +
                "information varchar(255)," +
                "informationColor varchar(7)," +
                "locked BOOLEAN, " +
                "PRIMARY KEY(billboardID)," +
                "FOREIGN KEY(userID) REFERENCES user(userID))"
        );

        // Create schedule table
        sqlStatement.executeUpdate("CREATE TABLE IF NOT EXISTS schedule(" +
            "scheduleID int NOT NULL," +
            "billboardID int NOT NULL," +
            "startTime DATETIME," +
            "duration TIME NOT NULL," +
            "minuteInterval int," +
            "PRIMARY KEY(scheduleID)," +
            "FOREIGN KEY(billboardID) REFERENCES billboard(billboardID))"
        );

        // End the SQL statement
        sqlStatement.close();

        // Set the new handler to the new database
        this.database = DriverManager.getConnection(url + "/" + schema, username, password);
    }

    /**
     * Selects a billboard in the database based off billboard name.
     *
     * @param billboardName : the name of the billboard
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public Billboard getBillboard(String billboardName) throws Exception {
        // Billboard to be returned
        Billboard billboard = null;

        // Query the database for the billboard
        Statement sqlStatement = this.database.createStatement();
        String query = "SELECT * FROM BILLBOARD WHERE BILLBOARD.name = " + billboardName;
        boolean fetchResult = sqlStatement.execute(query);
        sqlStatement.close();

        // Check if there was a result
        if (fetchResult) {
            // Use the result of the database to create billboard object
            ResultSet result = sqlStatement.executeQuery(query);
            while (result.next()) {

                int billboardID = result.getInt("billboardID");
                String name = result.getString("billboardName");
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
            }

        } else {
            throw new Exception("No results.");
        }
        return billboard;
    }

    /**
     * Selects all billboards in the database.
     *
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public List<Billboard> getBillboard(boolean lock) throws Exception {
        // Billboard to be returned
        List<Billboard> billboard = new ArrayList<>();
        Billboard temp;
        // Query the database for the billboard
        Statement sqlStatement = this.database.createStatement();
        String query = "SELECT * FROM BILLBOARD WHERE Billboard.locked = " + lock;
        boolean fetchResult = sqlStatement.execute(query);
        sqlStatement.close();

        // Check if there was a result
        if (fetchResult) {
            // Use the result of the database to create billboard object
            ResultSet result = sqlStatement.executeQuery(query);
            while (result.next()) {
                int billboardID = result.getInt("billboardID");
                String name = result.getString("billboardName");
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

    public String deleteBillboard(String billboardName) throws Exception {
        // Query the database for the billboard
        Statement sqlStatement = this.database.createStatement();
        String query = "DELETE FROM billboard WHERE billboard.name = " + billboardName;
        int fetchResult = sqlStatement.executeUpdate(query);
        sqlStatement.close();

        // Check if there was any rows affected in the database
        if (fetchResult == 1) {
            return "Billboard deleted";
        } else {
            throw new Exception("No billboard with such name in database");
        }
    }

    public String upsertBillboard(String billboardName, Billboard billboard) throws Exception {


        // Query the database for the billboard
        Statement sqlStatement = this.database.createStatement();

        //Try to select the billboard first to check if it's in the database or not
        String query = "SELECT * FROM billboard WHERE billboard.name = " + billboardName;

        boolean fetchResult = sqlStatement.execute(query);
        sqlStatement.close();

        // Check if there was a result
        if (fetchResult) {
            ResultSet existedBillboard = sqlStatement.executeQuery(query);
            if (existedBillboard == null) {
                query = "INSERT INTO billboard " +
                    "(userID, billboardName, message, " +
                    "messageColor, picture, backgroundColor," +
                    " information, informationColor, locked)" +
                    "VALUES( " + billboard.userID + "," + billboard.name + "," +
                    billboard.message + "," + billboard.messageColor + "," + billboard.picture + "," +
                    billboard.backgroundColor + "," + billboard.information + "," + billboard.informationColor +
                    "," + billboard.locked + ")";

                boolean check = sqlStatement.execute(query);

                if (check) {
                    return "Insert successfully";
                } else {
                    throw new Exception("Error in insert");
                }
            } else {
                query = "UPDATE billboard SET message = " + billboard.message + ", messageColor =" + billboard.messageColor +
                    ", picture = " + billboard.picture + ", backgroundColor = " + billboard.backgroundColor +
                    ", information = " + billboard.information + ", informationColor = " + billboard.informationColor + ", locked =" + billboard.locked +
                    "WHERE billboard.name = " + billboardName;
                int checked = sqlStatement.executeUpdate(query);
                if (checked > 0) {
                    return "Update successfully";
                } else {
                    throw new Exception("Error in updating");
                }
            }
        } else {
            throw new Exception("No billboard with such name in database");
        }
    }

    /**
     * Closes the connection to the database.
     * <b>WARNING THIS WILL RENDER THE DB OBJECT USELESS!</b>
     *
     * @throws SQLException, this exception is just a pass-through
     */
    public void closeConnection() throws SQLException {
        this.database.close();
    }
}
