package server.services;

import server.sql.SchemaBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * A singleton Class that handles all the database interactions for the server.
 *
 * @author Jamie Martin
 * @author Kevin Huynh
 * @author Perdana Bailey
 */
public class DataService {
    private Connection connection;

    protected DataService() {
        try {
            this.connection = startConnection();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a Dataservice Instance for Unit Tests if required
     * @param debug: whether the database should run using a mock list or the actual db
     * @throws Exception: thrown when unable to connect to database;
     */
    public DataService(boolean debug) throws Exception {
        if (!debug) {
            this.connection = startConnection();
        }
    }

    /**
     * Ensures the DataService is a singleton when getConnection() is called
     */
    private static class DataServiceHolder {
        private final static DataService INSTANCE = new DataService();
    }

    public static Connection getConnection() {
        return DataServiceHolder.INSTANCE.connection;
    }

    /**
     * Get a connection to database
     *
     * @return Connection object
     * @throws Exception: thrown when unable to configure database connection from props.
     */
    private static Connection startConnection() throws Exception {
        // Configure the database from the prop file, throws error if one
        Properties props = getProps();

        String url = props.getProperty("jdbc.url");
        String db = props.getProperty("jdbc.schema");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        try {
            Connection pre_dbconn = DriverManager.getConnection(url, username, password);
            Connection new_dbconn = SchemaBuilder.createDatabase(pre_dbconn, url, username, password, db);
            SchemaBuilder.populateSchema(new_dbconn);
            // TODO: Put SchemaBuilder into each Collection, Still CREATE DB here though

            return new_dbconn;
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }

    /**
     * Gets the properties from db.props for the database connection.
     *
     * @return Properties: the required properties to connect to the database.
     * @throws IOException:          thrown when props file not found or when unable to read props file or close prop file stream.
     * @throws NullPointerException: thrown when props file stream is empty.
     */
    private static Properties getProps() throws IOException, NullPointerException {

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
     * This closes an active database connection.
     *
     * @throws SQLException: thrown when unable to close connection to database.
     */
    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
