package server.database.schedule;

import com.mysql.cj.xdevapi.SqlStatement;
import common.models.Billboard;
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

    public ScheduleHandler(Connection connection) {
        this.connection = connection;
    }

//    public Optional<Schedule> get(String name) throws Exception {
//        return Optional.empty();
//    }

    public List<Schedule> getAll() throws Exception {
        // Billboard to be returned
        List<Schedule> schedules = new ArrayList<>();
        Schedule temp;
        // Query the database for the billboard
        Statement sqlStatement = connection.createStatement();
        String query = "SELECT * FROM schedule";
        boolean fetchResult = sqlStatement.execute(query);
        sqlStatement.close();

        // Check if there was a result
        if (fetchResult) {
            // Use the result of the database to create billboard object
            ResultSet result = sqlStatement.executeQuery(query);
            while (result.next()) {
                int scheduleID = result.getInt("ID");
                String billboardName = result.getString("billboardName");
                Date startTime = result.getTime("startTime");
                int duration = result.getInt("duration");
                int interval = result.getInt("interval");
                temp = new Schedule(scheduleID, billboardName, startTime, duration, interval);
                schedules.add(temp);
            }

        } else {
            throw new Exception("No results.");
        }
        return schedules;
    }

    public void scheduleBillboard(Schedule schedule) throws Exception {
        Statement sqlStatement = connection.createStatement();
        String query = "INSERT INTO schedule" +
            "(billboardName, startTime, duration, minuteInterval)" +
            "VALUES( " + schedule.billboardName + "," + schedule.startTime + "," +
            schedule.duration + "," + schedule.interval + ")";
        int fetchResult = sqlStatement.executeUpdate(query);
        if (fetchResult == 0) {
            throw new Exception("Error in inserting schedule");
        }
    }

//    public void update(Schedule schedule) throws Exception {
//
//    }

    public void deleteFromSchedule(Schedule schedule) throws Exception {
        Statement sqlStatement = connection.createStatement();
        String query = "DELETE FROM SCHEDULE WHERE schedule.billboardName = " + schedule.billboardName;
        int fetchResult = sqlStatement.executeUpdate(query);
        sqlStatement.close();

        // Check if there was any rows affected in the database
        if (fetchResult == 0) {
            throw new Exception("No billboard with such name in database");
        }
    }
}



