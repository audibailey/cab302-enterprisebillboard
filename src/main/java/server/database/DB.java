package server.database;

import java.sql.*;

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
     * the object to use that database as the connection.
     *
     * @param url:          the DBMS url. Example: jdbc:mysql://127.0.0.1
     * @param username:     the DBMS username
     * @param password:     the DBMS password
     * @param databaseName: the database name
     * @throws SQLException: this exception is just a pass-through
     */
    public DB(String url, String username, String password, String databaseName) throws Exception {
        // Set the JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Connect to the DBMS and populate the schema
        this.database = DriverManager.getConnection(url, username, password);
        populateSchema(url, username, password, databaseName);
    }

    /**
     * Create database, populate the database  and switch the connection to use that
     *
     * @param url:          the DBMS url. Example: jdbc:mysql://127.0.0.1
     * @param username:     the DBMS password
     * @param password:     the DBMS password
     * @param databaseName: the database name
     * @throws SQLException: this exception is just a pass-through
     */
    private void populateSchema(String url, String username, String password, String databaseName) throws SQLException {
        // Create an SQL statement
        Statement sqlStatement = this.database.createStatement();

        // Create and test new database
        sqlStatement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + databaseName);
        sqlStatement.executeUpdate("USE " + databaseName);

        //Create user table
        PreparedStatement create = this.database.prepareStatement("CREATE TABLE IF NOT EXISTS user(userID int NOT NULL,username varchar(255),password varchar(255),salted varchar(255),createBillboard BOOLEAN,editBillboard BOOLEAN,scheduleBillboard BOOLEAN,editUsers BOOLEAN, PRIMARY KEY(userID))");
        create.executeUpdate();
        //Create billboard table
        create = this.database.prepareStatement("CREATE TABLE IF NOT EXISTS billboard(billboardID int NOT NULL,userID int NOT NULL, billboardName varchar(255), message varchar(255), textColor varchar(255), backgroundColor varchar(255),picture LONGTEXT,information varchar(255),locked BOOLEAN, PRIMARY KEY(billboardID),FOREIGN KEY(userID) REFERENCES user(userID))");
        create.executeUpdate();

        //Create schedule table
        create = this.database.prepareStatement("CREATE TABLE IF NOT EXISTS schedule(scheduleID int NOT NULL, billboardID int NOT NULL, startTime DATETIME, duration TIME NOT NULL, minuteInterval int, PRIMARY KEY(scheduleID), FOREIGN KEY(billboardID) REFERENCES billboard(billboardID))");
        create.executeUpdate();
        // End the SQL statement
        sqlStatement.close();

        // Set the new handler to the new database
        this.database = DriverManager.getConnection(url + "/" + databaseName, username, password);
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
