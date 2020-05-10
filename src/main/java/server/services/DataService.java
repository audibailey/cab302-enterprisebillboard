package server.services;

import server.sql.Schema;
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

    /**
     * Generates a Dataservice Instance
     * @throws Exception: thrown when unable to connect to database;
     */
    protected DataService() {
        this.connection = startConnection();
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
    private static Connection startConnection() {
        Connection new_dbconn = null;
        try {
            // Configure the database from the prop file, throws error if one
            Properties props = getProps();

            String url = props.getProperty("jdbc.url");
            String schema = props.getProperty("jdbc.schema");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");

            new_dbconn = DriverManager.getConnection(url, username, password);
            // TODO: Put SchemaBuilder into each Collection, Still CREATE DB here though

        } catch (Exception e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
            System.exit(0);
        }
        return new_dbconn;
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
