package server.database.billboard;

import common.models.Billboard;
import server.database.ObjectHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    List<Billboard> mockdb = new ArrayList<Billboard>();

    /**
     * The BillboardHandler Constructor.
     *
     * @param connection: This is the database connection from DataService.java.
     */
    public BillboardHandler(Connection connection) {
        this.connection = connection;
    }

    /**
     * Selects all billboards in the database dependant on if the billboard has been scheduled or not (locked).
     *
     * @param lock: this is the boolean that determines the type of billboard to return.
     * @return Optional<List < Billboard>>: this returns the list of billboards requested or a optional empty value.
     * @throws SQLException: this exception returns when there is an issue fetching data from the database.
     */
    public Optional<List<Billboard>> getAll(boolean lock) throws SQLException {
        // Billboard to be returned
        List<Billboard> billboards = new ArrayList<>();

        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            try (Statement sqlStatement = this.connection.createStatement()) {
                // Create a query that selects billboards based on the lock and execute the query
                String query = "SELECT * FROM BILLBOARD WHERE Billboard.locked = " + lock;
                ResultSet result = sqlStatement.executeQuery(query);

                // Use the result of the database query to create billboard object and add it to the returned list
                while (result.next()) {
                    billboards.add(Billboard.fromSQL(result));
                }

                // If the billboard list is empty return optional empty
                if (billboards.isEmpty()) {
                    return Optional.empty();
                } else {
                    return Optional.of(billboards);
                }
            } catch (SQLException e) {
                // Throw an exception
                throw new SQLException(String.format("Failed to fetch list of billboards from database. Error: %s.", e.toString()));
            }
        } else {
            // Loop through and find the billboard with the lock status and add to billboards
            for (Billboard billboard : this.mockdb) {
                if (billboard.locked == lock) {
                    billboards.add(billboard);
                }
            }

            // If billboards is empty return optional empty or return billboard
            if (billboards.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(billboards);
            }
        }
    }

    /**
     * Selects a billboard in the database based off the billboard name.
     *
     * @param BillboardName: this is the requested billboard name
     * @return Optional<Billboard>: this returns the billboard or an optional empty value.
     * @throws SQLException: this exception returns when there is an issue fetching data from the database.
     */
    public Optional<Billboard> get(String BillboardName) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            try (Statement sqlStatement = this.connection.createStatement()) {
                // Create a query that selects billboards based on the name and execute the query
                String query = "SELECT * FROM BILLBOARD WHERE billboard.name = '" + BillboardName + "'";
                ResultSet result = sqlStatement.executeQuery(query);

                // Use the result of the database query to create billboard object and return it
                while (result.next()) {
                    return Optional.of(Billboard.fromSQL(result));
                }

                // If it fails to get a result, return Optional empty
                return Optional.empty();
            } catch (SQLException e) {
                // Throw an exception
                throw new SQLException(String.format("Failed to fetch billboard from database. Error: %s.", e.toString()));
            }
        } else {
            // Loop through and find the billboard with the requested name or return an optional empty value
            for (Billboard billboard : this.mockdb) {
                if (billboard.name.equals(BillboardName)) {
                    return Optional.of(billboard);
                }
            }

            return Optional.empty();
        }

    }

    /**
     * List all billboards in the database.
     *
     * @return Optional<List < Billboard>>: this returns the list of billboards or an optional empty value.
     * @throws SQLException: this exception returns when there is an issue fetching data from the database.
     */
    public Optional<List<Billboard>> getAll() throws SQLException {
        // Billboard to be returned
        List<Billboard> billboards = new ArrayList<>();

        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            try (Statement sqlStatement = this.connection.createStatement()) {
                // Create a query that selects all billboards and execute the query
                String query = "SELECT * FROM billboard";
                ResultSet result = sqlStatement.executeQuery(query);

                // Use the result of the database query to create billboard object and add it to the returned list
                while (result.next()) {
                    billboards.add(Billboard.fromSQL(result));
                }

                // If the billboard list is empty return optional empty
                if (billboards.isEmpty()) {
                    return Optional.empty();
                } else {
                    return Optional.of(billboards);
                }
            } catch (SQLException e) {
                // Throw an exception
                throw new SQLException(String.format("Failed to fetch list of billboards from database. Error: %s.", e.toString()));
            }
        } else {
            // Check if the mockdb has values, if not return nothing
            if (this.mockdb.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(this.mockdb);
            }
        }
    }

    /**
     * Insert a billboard into the database.
     *
     * @param billboard: this is the requested billboard to insert.
     * @return boolean: this returns whether the billboard was inserted or not.
     * @throws SQLException: this exception returns when there is an issue sending data to the database.
     */
    public boolean insert(Billboard billboard) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            try (Statement sqlStatement = connection.createStatement()) {
                // Create a query that inserts the billboard and executes the query, return if the query executed
                String query = "INSERT INTO billboard " +
                    "(userID, name, message, " +
                    "messageColor, picture, backgroundColor," +
                    " information, informationColor, locked)" +
                    "VALUES(" + billboard.userID + ",'" + billboard.name + "','" +
                    billboard.message + "','" + billboard.messageColor + "','" + Arrays.toString(billboard.picture) + "','" +
                    billboard.backgroundColor + "','" + billboard.information + "','" + billboard.informationColor +
                    "'," + billboard.locked + ")";

                return sqlStatement.execute(query);

            } catch (SQLException e) {
                // Throw an exception
                throw new SQLException(String.format("Failed to insert billboard into database. Error: %s.", e.toString()));
            }
        } else {
            mockdb.add(billboard);
            return mockdb.contains(billboard);
        }
    }

    /**
     * Update a billboard in the database.
     *
     * @param billboard: this is the requested billboard to update.
     * @throws SQLException: this exception returns when there is an issue sending data to the database.
     * @return: this returns whether the billboard was updated or not.
     */
    public int update(Billboard billboard) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            try (Statement sqlStatement = connection.createStatement()) {
                // Create a query that inserts the billboard and executes the query, return if the query executed
                String query = "UPDATE billboard SET name = '" + billboard.name + "', message = '" + billboard.message + "', messageColor ='" + billboard.messageColor +
                    "', picture = '" + Arrays.toString(billboard.picture) + "', backgroundColor = '" + billboard.backgroundColor +
                    "', information = '" + billboard.information + "', informationColor = '" + billboard.informationColor + "', locked =" + billboard.locked +
                    " WHERE ID = " + billboard.id;

                return sqlStatement.executeUpdate(query);

            } catch (SQLException e) {
                // Throw an exception
                throw new SQLException(String.format("Failed to update billboard in database. Error: %s.", e.toString()));
            }
        } else {
            // Loop through mock database and find the billboard to update, then update it and return true.
            for (Billboard mockbillboard : this.mockdb) {
                if (mockbillboard.name.equals(billboard.name)) {
                    mockbillboard.userID = billboard.userID;
                    mockbillboard.message = billboard.message;
                    mockbillboard.messageColor = billboard.messageColor;
                    mockbillboard.backgroundColor = billboard.backgroundColor;
                    mockbillboard.information = billboard.information;
                    mockbillboard.informationColor = billboard.informationColor;
                    mockbillboard.picture = billboard.picture;
                    mockbillboard.locked = billboard.locked;

                    return 1;
                }
            }

            // Return false if billboard not found in mockdb.
            return 0;
        }
    }

    /**
     * Delete a billboard from the database.
     *
     * @param billboard: this is the requested billboard to delete.
     * @throws SQLException:
     */
    public int delete(Billboard billboard) throws SQLException {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Attempt to query the database
            try (Statement sqlStatement = connection.createStatement()) {
                // Create a query that inserts the billboard and executes the query, return if the query executed
                String query = "DELETE FROM billboard WHERE ID = " + billboard.id;

                return sqlStatement.executeUpdate(query);

            } catch (SQLException e) {
                // Throw an exception
                throw new SQLException(String.format("Failed to update billboard in database. Error: %s.", e.toString()));
            }
        } else {
            // Delete billboard
            mockdb.remove(billboard);
            return 1;
        }
    }
}
