package server.database;

import java.sql.*;

public class DB {
    private Connection database;

    public DB(String url, String username, String password) throws SQLException {

        this.database = DriverManager.getConnection(url, username, password);

        // Checks Database Exists
        ResultSet resultSet = this.database.getMetaData().getCatalogs();
        boolean databaseExists = false;

        while (resultSet.next()) {
            String databaseName = resultSet.getString(1);

            if (databaseName.equals("30221")) {
                databaseExists = true;
                break;
            }
        }
        resultSet.close();

        if (!databaseExists) {
            populateSchema();
            this.database = DriverManager.getConnection(url + "/30221", username, password);
        } else {
            this.database = DriverManager.getConnection(url + "/30221", username, password);
        }
    }

    private void populateSchema() throws SQLException {
        Statement stmt = this.database.createStatement();
        stmt.executeUpdate("CREATE TABLE a (id int not null primary key, value varchar(20))");
        stmt.close();
    }

    public void closeConnection() throws SQLException {
        this.database.close();
    }
}
