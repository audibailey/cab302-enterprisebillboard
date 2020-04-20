package server.database.billboard;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import common.models.Billboard;
import server.database.ObjectHandler;

/**
 * This class is responsible for all the billboard object interactions with the database.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 * @author Jamie Martin
 */
public class BillboardHandler implements ObjectHandler<Billboard> {
    // This is the database connection
    Connection connection;

    // This is the mock "database" used for testing
    List<Billboard> mockDB = new ArrayList<Billboard>();

    /**
     * The BillboardHandler Constructor.
     *
     * @param connection: This is the database connection from DataService.java.
     */
    public BillboardHandler(Connection connection) {
        this.connection = connection;
    }

    /**
     * Selects a billboard in the database based off the billboard id.
     *
     * @param id: this is the requested billboard id
     * @return Optional<Billboard>: this returns the billboard or an optional empty value.
     * @throws SQLException: this exception returns when there is an issue fetching data from the database.
     */
    public Optional<Billboard> get(int id) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Initialise the return value
            Optional<Billboard> ReturnedValue = Optional.empty();

            // Attempt to query the database
            Statement sqlStatement = this.connection.createStatement();
            // Create a query that selects billboards based on the name and execute the query
            String query = "SELECT * FROM BILLBOARDS WHERE id = '" + id + "'";

            ResultSet result = sqlStatement.executeQuery(query);

            // Use the result of the database query to create billboard object and return it
            while (result.next()) {
                ReturnedValue = Optional.of(Billboard.fromSQL(result));
            }

            // Clean up query
            sqlStatement.close();

            return ReturnedValue;
        } else {
            // Loop through and find the billboard with the requested name or return an optional empty value
            for (Billboard billboard : this.mockDB) {
                if (billboard.id == id) {
                    return Optional.of(billboard);
                }
            }

            // If it fails to get a result, return Optional empty
            return Optional.empty();
        }
    }

    /**
     * Selects a billboard in the database based off the billboard name.
     *
     * @param billboardName: this is the requested billboard name
     * @return Optional<Billboard>: this returns the billboard or an optional empty value.
     * @throws SQLException: this exception is thrown when there is an issue fetching data from the database.
     */
    public Optional<Billboard> get(String billboardName) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Initialise the return value
            Optional<Billboard> ReturnedValue = Optional.empty();

            // Attempt to query the database
            Statement sqlStatement = this.connection.createStatement();
            // Create a query that selects billboards based on the name and execute the query
            String query = "SELECT * FROM BILLBOARDS WHERE name = '" + billboardName + "'";

            ResultSet result = sqlStatement.executeQuery(query);

            // Use the result of the database query to create billboard object and return it
            while (result.next()) {
                ReturnedValue = Optional.of(Billboard.fromSQL(result));
            }

            // Clean up query
            sqlStatement.close();

            return ReturnedValue;
        } else {
            // Loop through and find the billboard with the requested name or return an optional empty value
            for (Billboard billboard : this.mockDB) {
                if (billboard.name.equals(billboardName)) {
                    return Optional.of(billboard);
                }
            }

            // If it fails to get a result, return Optional empty
            return Optional.empty();
        }
    }

    /**
     * Selects all billboards in the database
     *
     * @return Optional<List < Billboard>>: this returns the list of billboards requested or a optional empty value.
     * @throws SQLException: this exception returns when there is an issue fetching data from the database.
     */
    public List<Billboard> getAll() throws Exception {
        // Billboard to be returned
        List<Billboard> billboards = new ArrayList<>();

        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            Statement sqlStatement = this.connection.createStatement();
            // Create a query that selects billboards based on the lock and execute the query
            String query = "SELECT * FROM BILLBOARDS";

            ResultSet result = sqlStatement.executeQuery(query);

            // Use the result of the database query to create billboard object and add it to the returned list
            while (result.next()) {
                billboards.add(Billboard.fromSQL(result));
            }

            // Clean up query
            sqlStatement.close();
        } else {
            billboards = this.mockDB;
        }

      return billboards;
    }

    /**
     * Selects all billboards in the database dependant on if the billboard has been scheduled or not (locked).
     *
     * @param lock: this is the boolean that determines the type of billboard to return.
     * @return List < Billboard>: this returns the list of billboards requested or a optional empty value.
     * @throws SQLException: this exception returns when there is an issue fetching data from the database.
     */
    public List<Billboard> getAll(boolean lock) throws Exception {
        // Billboard to be returned
        List<Billboard> billboards = new ArrayList<>();

        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            Statement sqlStatement = this.connection.createStatement();
            // Create a query that selects billboards based on the lock and execute the query
            String query = "SELECT * FROM BILLBOARDS WHERE locked = " + lock;

            ResultSet result = sqlStatement.executeQuery(query);

            // Use the result of the database query to create billboard object and add it to the returned list
            while (result.next()) {
                billboards.add(Billboard.fromSQL(result));
            }

            // Clean up query
            sqlStatement.close();
        } else {
            // Loop through and find the billboard with the lock status and add to billboards
            for (Billboard b : this.mockDB) {
                if (b.locked == lock) {
                    billboards.add(b);
                }
            }
        }

        return billboards;
    }

    /**
     * Insert a billboard into the database.
     *
     * @param billboard: this is the requested billboard to insert.
     * @throws SQLException: this exception returns when there is an issue sending data to the database.
     */
    public void insert(Billboard billboard) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {


            // Create a query that inserts the billboard and executes the query

            String query = "INSERT INTO BILLBOARDS" +
                "(userID, name, message," +
                " messageColor, picture, backgroundColor," +
                " information, informationColor, locked)" +
                "VALUES (?,?,?,?,?,?,?,?,?)";
            // Attempt to query the database
            PreparedStatement pstmt = connection.prepareStatement(query);
            // Clear all parameters before insert
            pstmt.clearParameters();
            // Fill the parameters with the data and execute query
            pstmt.setInt(1, billboard.userId);
            pstmt.setString(2, billboard.name);
            pstmt.setString(3, billboard.message);
            pstmt.setString(4, billboard.messageColor);
            pstmt.setBytes(5, billboard.picture);
            pstmt.setString(6, billboard.backgroundColor);
            pstmt.setString(7, billboard.information);
            pstmt.setString(8, billboard.informationColor);
            pstmt.setBoolean(9, billboard.locked);

            pstmt.executeUpdate();

            // Clean up query
            pstmt.close();
        } else {
            mockDB.add(billboard);
        }
    }

    /**
     * Update a billboard in the database.
     *
     * @param billboard: this is the requested billboard to update.
     * @throws SQLException: this exception returns when there is an issue sending data to the database.
     */
    public void update(Billboard billboard) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {


            // Create a query that inserts the billboard
            String query = "UPDATE BILLBOARDS SET " +
                "name = ?, message = ?, messageColor = ?," +
                " picture =?, backgroundColor = ?, information =?," +
                " informationColor =?, locked =? WHERE ID = ?";
            // Attempt to query the database
            PreparedStatement pstmt = connection.prepareStatement(query);
            // Clear all parameters before insert
            pstmt.clearParameters();
            // Fill the parameters with the data and execute query
            pstmt.setString(1, billboard.name);
            pstmt.setString(2, billboard.message);
            pstmt.setString(3, billboard.messageColor);
            pstmt.setBytes(4, billboard.picture);
            pstmt.setString(5, billboard.backgroundColor);
            pstmt.setString(6, billboard.information);
            pstmt.setString(7, billboard.informationColor);
            pstmt.setBoolean(8, billboard.locked);
            pstmt.setInt(9, billboard.id);

            pstmt.executeUpdate();

            // Clean up query
            pstmt.close();
//          sqlStatement.close();
        } else {
            // Loop through mock database and find the billboard to update, then update it
            for (Billboard mockBillboard : this.mockDB) {
                if (mockBillboard.id  == billboard.id) {
                    mockBillboard = billboard;
                }
            }
        }
    }

    /**
     * Delete a billboard from the database.
     *
     * @param billboard: this is the requested billboard to delete.
     * @throws SQLException: this exception is thrown when there is an issue deleting data from the database.
     */
    public void delete(Billboard billboard) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            Statement sqlStatement = connection.createStatement();
            // Create a query that deletes the billboard and executes the query
            String query = "DELETE FROM BILLBOARDS WHERE ID = " + billboard.id;
            sqlStatement.executeUpdate(query);

            // Cleans up query
            sqlStatement.close();
        } else {
            // Delete billboard
            mockDB.remove(billboard);
        }
    }

    /**
     * Clears Billboard all entries in database for unit test cleanup.
     *
     * @throws SQLException: this exception is thrown when there is an issue deleting data from the database.
     */
    public void deleteAll() throws SQLException {
        if (this.connection != null) {
            // Attempt to query the database
            Statement sqlStatement = connection.createStatement();
            // Create a query that deletes the billboard and executes the query
            String query = "DELETE FROM BILLBOARDS";
            sqlStatement.executeUpdate(query);

            // Cleans up query
            sqlStatement.close();
        } else {
            // Delete all billboards
            mockDB.clear();
        }
    }
}
