package server.database.schedule;

import com.mysql.cj.xdevapi.SqlStatement;
import common.models.Billboard;
import common.models.Permissions;
import common.models.Schedule;
import server.database.ObjectHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ScheduleHandler implements ObjectHandler<Schedule> {
    Connection connection;

    // This is the mock "database" used for testing
    List<Schedule> mockdb = new ArrayList<Schedule>();

    public ScheduleHandler(Connection connection) {
        this.connection = connection;
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Optional<Schedule> get(int id) throws Exception {
        // Check that it's not in testing mode
        if (this.connection != null) {
            // Query the database for the billboard
            Statement sqlStatement = connection.createStatement();

            // Use the result of the database to create billboard object
            ResultSet result = sqlStatement.executeQuery("SELECT * FROM SCHEDULES WHERE schedules.id = " + id);

            while (result.next()) {
                return Optional.of(Schedule.fromSQL(result));
            }
            sqlStatement.close();
        } else {
            // Loop through and find the billboard with the requested name or return an optional empty value
            for (Schedule s : this.mockdb) {
                if (s.id == id) {
                    return Optional.of(s);
                }
            }
        }
        // If it fails to get a result, return Optional empty
        return Optional.empty();
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public List<Schedule> getAll() throws Exception {
        List<Schedule> schedules = new ArrayList<>();
        // Check that it's not in testing mode
        if (this.connection != null) {
            Statement sqlStatement = connection.createStatement();
            String query = "SELECT * FROM SCHEDULES";

            ResultSet result = sqlStatement.executeQuery(query);
            while (result.next()) {
                schedules.add(Schedule.fromSQL(result));
            }
            sqlStatement.close();
        } else {
            schedules = this.mockdb;
        }

        return schedules;
    }

    /**
     *
     * @param schedule
     * @throws Exception
     */
    public void insert(Schedule schedule) throws Exception {
        if (this.connection != null) {
            Statement sqlStatement = connection.createStatement();

            sqlStatement.executeUpdate("");

            sqlStatement.close();
        } else {
            // use mockdb
        }
    }

    /**
     *
     * @param schedule
     * @throws Exception
     */
    public void update(Schedule schedule) throws Exception {
        if (this.connection != null) {
            Statement sqlStatement = connection.createStatement();

            sqlStatement.executeUpdate("");

            sqlStatement.close();
        } else {
            // use mockdb
        }
    }

    /**
     *
     * @param schedule
     * @throws Exception
     */
    public void delete(Schedule schedule) throws Exception {
        if (this.connection != null) {
            Statement sqlStatement = connection.createStatement();

            sqlStatement.executeUpdate("DELETE FROM SCHEDULES WHERE schedules.id = " + schedule.id);

            sqlStatement.close();
        } else {
            // use mockdb
        }
    }
}



