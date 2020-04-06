package server.database.schema;

import java.sql.*;

public class schema {

    /**
     * Creates the database, and switches the connection to use the database.
     *
     * @param url:      the DBMS url. Example: jdbc:mysql://127.0.0.1
     * @param username: the DBMS password
     * @param password: the DBMS password
     * @param schema:   the database name/schema
     * @throws SQLException
     */
    public static Connection createDatabase(Connection connection, String url, String username, String password, String schema) throws SQLException {
        Statement sqlStatement = null;
        try {
            // Create statement
            sqlStatement = connection.createStatement();

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
            return DriverManager.getConnection(url + "/" + schema, username, password);
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to the newly created database.", e);
        }
    }

    /**
     * Populates the database schema.
     *
     * @throws SQLException: this exception is just a
     */
    public static void populateSchema(Connection connection) throws SQLException {
        Statement sqlStatement = null;

        try {
            // Create statement
            sqlStatement = connection.createStatement();

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
            sqlStatement = connection.createStatement();

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
                    "CONSTRAINT FK_UserBillboard FOREIGN KEY (userID) REFERENCES user(ID))"
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
            sqlStatement = connection.createStatement();

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
}
