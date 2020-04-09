package server.database.schedule;

import common.models.Schedule;
import server.database.ObjectHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class is responsible for all the schedule object interactions with the database.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 * @author Jamie Martin
 */
public class ScheduleHandler implements ObjectHandler<Schedule> {
    Connection connection;

    // This is the mock "database" used for testing
    List<Schedule> MockDB = new ArrayList<Schedule>();

    public ScheduleHandler(Connection connection) {
        this.connection = connection;
    }

    /**
     * Get schedule base on ID
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
            ResultSet result = sqlStatement.executeQuery("SELECT * FROM SCHEDULES WHERE id = " + id);

            while (result.next()) {
                return Optional.of(Schedule.fromSQL(result));
            }
            sqlStatement.close();
        } else {
            // Loop through and find the billboard with the requested name or return an optional empty value
            for (Schedule s : this.MockDB) {
                if (s.id == id) {
                    return Optional.of(s);
                }
            }
        }
        // If it fails to get a result, return Optional empty
        return Optional.empty();
    }

    /**
     * List all the schedule
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
            schedules = this.MockDB;
        }

        return schedules;
    }

    /**
     *Insert a schedule into database
     * @param schedule
     * @throws Exception
     */
    public void insert(Schedule schedule) throws Exception {
        if (this.connection != null) {
            Statement sqlStatement = connection.createStatement();
            String query = "INSERT INTO SCHEDULE" +
                "(id, billboardName, startTime, duration, interval)" +
                "VALUES( " + schedule.id + "," + schedule.billboardName + "," +
                schedule.startTime + "," + schedule.duration + "," + schedule.interval
                + ")";
            sqlStatement.executeUpdate(query);

            sqlStatement.close();
        } else {
            this.MockDB.add(schedule);
        }
    }

    /**
     *Update the schedule
     * @param schedule
     * @throws Exception
     */
    public void update(Schedule schedule) throws Exception {
        if (this.connection != null) {
            Statement sqlStatement = connection.createStatement();
            String query = "UPDATE SCHEDULE SET startTime =" + schedule.startTime +
                ", duration =" + schedule.duration +
                ", interval =" + schedule.interval +
                "WHERE schedule.billboardName = " + schedule.billboardName;
            sqlStatement.executeUpdate(query);

            sqlStatement.close();
        } else {
            for (Schedule mockSchedule : this.MockDB) {
                if (mockSchedule.id == schedule.id) {
                    mockSchedule = schedule;
                }
            }
        }
    }


    /**
     * Delete a schedule base on billboard name
     * @param schedule
     * @throws Exception
     */
    public void delete(Schedule schedule) throws Exception {
        if (this.connection != null) {
            Statement sqlStatement = connection.createStatement();

            sqlStatement.executeUpdate("DELETE FROM SCHEDULES WHERE schedules.billboardName = " + schedule.billboardName);

            sqlStatement.close();
        } else {
            this.MockDB.remove(schedule);
        }
    }
}



