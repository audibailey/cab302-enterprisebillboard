package server.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
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

        Properties props = null;

        // Configure the database from the prop file
        try {
            props = getProps();
        } catch (IOException e) {
            throw new IOException("Failed to get db.props values.", e);
        } catch (NullPointerException e) {
            throw new NullPointerException("Failed to get db.props.values.");
        }

        String url = props.getProperty("jdbc.url");
        String schema = props.getProperty("jdbc.schema");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        // Connect to the DBMS and populate the schema
        try {
            this.database = DriverManager.getConnection(url, username, password);
            createDatabase(url, username, password, schema);
            populateSchema();
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to database and migrate it.", e);
        }

    }

    /**
     * Gets the properties from db.props for the database connection.
     *
     * @return Properties: the required properties to connect to the database
     * @throws IOException:          Thrown when props file not found or when unable to read props file or close prop file stream
     * @throws NullPointerException: Thrown when props file stream is empty
     */
    private Properties getProps() throws IOException, NullPointerException {

        // Initialize variables
        Properties props = new Properties();
        FileInputStream in = null;

        // Read the props file into the properties object
        try {
            in = new FileInputStream("./db.props");
            props.load(in);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("db.props not in root of directory.");
        } catch (IOException e) {
            throw new IOException("Error reading db.props.", e);
        } catch (NullPointerException e) {
            throw new NullPointerException("No configuration information in db.props file.");
        } finally {
            // Close file stream
            try {
                in.close();
            } catch (IOException e) {
                throw new IOException("Error closing db.props.", e);
            } catch (NullPointerException e) {
                throw new NullPointerException("Error closing db.props. db.props doesn't exist anymore.");
            }
        }

        // Return the properties object
        return props;
    }

    /**
     * Creates the database, and switches the connection to use the database.
     *
     * @param url:      the DBMS url. Example: jdbc:mysql://127.0.0.1
     * @param username: the DBMS password
     * @param password: the DBMS password
     * @param schema:   the database name/schema
     * @throws SQLException
     */
    private void createDatabase(String url, String username, String password, String schema) throws SQLException {
        Statement sqlStatement = null;
        try {
            // Create statement
            sqlStatement = this.database.createStatement();

            // Create and test new database
            sqlStatement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + schema);
            sqlStatement.executeUpdate("USE " + schema);
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Failed to create database, took too long.", e);
        } catch (SQLException e) {
            throw new SQLException("Failed to create database.", e);
        } finally {
            try {
                sqlStatement.close();
            } catch (SQLException e) {
                throw new SQLException("Failed to close connection to database after making database", e);
            }
        }

        // Set the new handler to the new database
        try {
            this.database = DriverManager.getConnection(url + "/" + schema, username, password);
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to the newly created database.", e);
        }
    }

    /**
     * Populates the database schema.
     *
     * @throws SQLException: this exception is just a
     */
    private void populateSchema() throws SQLException {
        Statement sqlStatement = null;

        try {
            // Create statement
            sqlStatement = this.database.createStatement();

            // Create user table
            sqlStatement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS user(" +
                    "ID int NOT NULL," +
                    "username varchar(255)," +
                    "password varchar(255)," +
                    "salted varchar(255)," +
                    "createBillboard BOOLEAN," +
                    "editBillboard BOOLEAN," +
                    "scheduleBillboard BOOLEAN," +
                    "editUsers BOOLEAN, " +
                    "viewBillboard BOOLEAN, " +
                    "PRIMARY KEY(ID))"
            );
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Failed to create user table, took too long.", e);
        } catch (SQLException e) {
            throw new SQLException("Failed to create user table.", e);
        } finally {
            try {
                sqlStatement.close();
            } catch (SQLException e) {
                throw new SQLException("Failed to close connection to database after making user table.", e);
            }
        }

        try {
            // Create statement
            sqlStatement = this.database.createStatement();

            // Create billboard table
            sqlStatement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS billboard(" +
                    "billboardID int NOT NULL," +
                    "userID int NOT NULL," +
                    "billboardName varchar(255), " +
                    "message varchar(255), " +
                    "textColor varchar(255), " +
                    "backgroundColor varchar(255)," +
                    "picture LONGTEXT," +
                    "information varchar(255)," +
                    "locked BOOLEAN, " +
                    "PRIMARY KEY(billboardID)," +
                    "FOREIGN KEY(userID) REFERENCES user(ID))"
            );
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Failed to create billboard table, took too long.", e);
        } catch (SQLException e) {
            throw new SQLException("Failed to create billboard table", e);
        } finally {
            try {
                sqlStatement.close();
            } catch (SQLException e) {
                throw new SQLException("Failed to close connection to database after making billboard table.", e);
            }
        }


        try {
            // Create statement
            sqlStatement = this.database.createStatement();
            // Create billboard table
            sqlStatement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS billboard(" +
                    "ID int NOT NULL," +
                    "userID int NOT NULL," +
                    "name varchar(255), " +
                    "message varchar(255), " +
                    "messageColor varchar(7), " +
                    "picture LONGTEXT," +
                    "backgroundColor varchar(7)," +
                    "information varchar(255)," +
                    "informationColor varchar(7)," +
                    "locked BOOLEAN, " +
                    "PRIMARY KEY(ID)," +
                    "FOREIGN KEY(userID) REFERENCES user(ID))"
            );

            // Create schedule table
            sqlStatement.executeUpdate("CREATE TABLE IF NOT EXISTS schedule(" +
                "ID int NOT NULL," +
                "billboardID int NOT NULL," +
                "startTime DATETIME," +
                "duration TIME NOT NULL," +
                "minuteInterval int," +
                "PRIMARY KEY(ID)," +
                "FOREIGN KEY(billboardID) REFERENCES billboard(ID))"
            );
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Failed to create schedule table, took too long.", e);
        } catch (SQLException e) {
            throw new SQLException("Failed to create schedule table", e);
        } finally {
            try {
                sqlStatement.close();
            } catch (SQLException e) {
                throw new SQLException("Failed to close connection to database after making schedule table.", e);
            }
        }
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
        String query = "SELECT * FROM BILLBOARD WHERE billboard.name = " + billboardName;
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
     * Selects all billboards in the database base on lock state.
     *
     * @param lock : true for a billboard that is currently on a schedule, false for billboard that is not on a schedule
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public List<Billboard> getBillboard(boolean lock) throws Exception {
        // Billboard to be returned
        List<Billboard> billboard = new ArrayList<>();
        Billboard temp;
        // Query the database for the billboard
        Statement sqlStatement = this.database.createStatement();
        String query = "SELECT * FROM billboard WHERE billboard.locked = " + lock;
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
     * List all billboards in the database.
     *
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public List<Billboard> listBillboard() throws Exception {
        // Billboard to be returned
        List<Billboard> billboard = new ArrayList<>();
        Billboard temp;
        // Query the database for the billboard
        Statement sqlStatement = this.database.createStatement();
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
     * Delete a billboard base on billboardName
     *
     * @param billboardName: the name of the billboard
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
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

    /**
     * Create a billboard if it's not in the database or edit a billboard if it's already exists.
     *
     * @param billboardName: the name of the billboard
     * @param billboard      : a billboard object with contents in it
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
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
                    "(userID, name, message, " +
                    "messageColor, picture, backgroundColor," +
                    " information, informationColor, locked)" +
                    "VALUES( " + billboard.userID + "," + billboard.name + "," +
                    billboard.message + "," + billboard.messageColor + "," + Arrays.toString(billboard.picture) + "," +
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
                    ", picture = " + Arrays.toString(billboard.picture) + ", backgroundColor = " + billboard.backgroundColor +
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

    //User functions

    /**
     * List all billboards in the database.
     *
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public List<User> listUsers() throws Exception {
        // User's list to be returned
        List<User> users = new ArrayList<>();
        User temp;
        // Query the database for the billboard
        Statement sqlStatement = this.database.createStatement();
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

    /**
     * Delete a user base on userName
     *
     * @param userName: the name of the billboard
     * @throws Exception: this exception is a pass-through exception with a no results extended exception
     */
    public String deleteUser(String userName) throws Exception {
        // Query the database for the billboard
        Statement sqlStatement = this.database.createStatement();
        String query = "DELETE FROM user WHERE user.username = " + userName;
        int fetchResult = sqlStatement.executeUpdate(query);
        sqlStatement.close();

        // Check if there was any rows affected in the database
        if (fetchResult == 1) {
            return "User deleted";
        } else {
            throw new Exception("No user with such name in database");
        }
    }


    /**
     * Closes the connection to the database.
     * <b>WARNING THIS WILL RENDER THE DB OBJECT USELESS!</b>
     *
     * @throws SQLException, this exception is just a pass-through
     */
    public void closeConnection() throws SQLException {
        try {
            this.database.close();
        } catch (SQLException e) {
            throw new SQLException("Failed to close connection to database!", e);
        }
    }
}
