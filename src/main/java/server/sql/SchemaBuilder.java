package server.sql;

import java.sql.*;

public class SchemaBuilder {

    /**
     * Creates the database, and switches the connection to use the database.
     *
     * @param url:      the DBMS url. Example: jdbc:mysql://127.0.0.1
     * @param username: the DBMS password
     * @param password: the DBMS password
     * @param schema:   the database name/schema
     * @throws SQLException : Description
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
                "CREATE TABLE IF NOT EXISTS USER(" +
                    "id int NOT NULL AUTO_INCREMENT UNIQUE," +
                    "username varchar(255) UNIQUE," +
                    "password varchar(255)," +
                    "salt varchar(255)," +
                    "PRIMARY KEY(id))"
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

            // Create user permission table
            sqlStatement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS PERMISSION(" +
                    "id int NOT NULL UNIQUE," +
                    "username varchar(255) NOT NULL UNIQUE," +
                    "canCreateBillboard BOOLEAN," +
                    "canEditBillboard BOOLEAN," +
                    "canScheduleBillboard BOOLEAN," +
                    "canEditUsers BOOLEAN, " +
                    "canViewBillboard BOOLEAN, " +
                    "PRIMARY KEY(id)," +
                    "CONSTRAINT FK_USERPERMID FOREIGN KEY (id) REFERENCES USER(id)," +
                    "CONSTRAINT FK_USERPERMUNAME FOREIGN KEY (username) REFERENCES USER(username))"
            );
        } catch (SQLTimeoutException e) {
            throw new SQLTimeoutException("Failed to create user's permissions table, took too long.", e);
        } catch (SQLException e) {
            throw new SQLException("Failed to create user's permissions table.", e);
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
                "CREATE TABLE IF NOT EXISTS BILLBOARD(" +
                    "id int NOT NULL AUTO_INCREMENT UNIQUE," +
                    "userId int NOT NULL," +
                    "name varchar(255) UNIQUE, " +
                    "message varchar(255), " +
                    "messageColor varchar(7), " +
                    "picture BLOB," +
                    "backgroundColor varchar(7)," +
                    "information varchar(255)," +
                    "informationColor varchar(7)," +
                    "locked BOOLEAN, " +
                    "PRIMARY KEY(id)," +
                    "CONSTRAINT FK_UserBillboard FOREIGN KEY (userId) REFERENCES USER(id))"
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
            sqlStatement.executeUpdate("CREATE TABLE IF NOT EXISTS SCHEDULE(" +
                "id int NOT NULL AUTO_INCREMENT UNIQUE," +
                "billboardName varchar(255) NOT NULL UNIQUE," +
                "startTime TIMESTAMP NOT NULL," +
                "createTime TIMESTAMP NOT NULL," +
                "duration int NOT NULL," +
                "`interval` int NOT NULL," +
                "PRIMARY KEY(id)," +
                "CONSTRAINT FK_BillboardSchedule FOREIGN KEY (billboardName) REFERENCES BILLBOARD(name))"
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
