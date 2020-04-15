package common.models;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;

/**
 * This class consists of the Schedule object and its associated methods.
 *
 * @author Perdana Bailey
 * @author Jamie Martin
 */
public class Schedule implements Serializable {
    /**
     * The variables of the object schedule
     */
    public int id;
    public String billboardName;
    public Instant startTime;
    public int duration;
    public int interval;

    public Schedule() {

    }

    /**
     * Schedule object constructor
     *
     * @param id:            schedules id.
     * @param billboardName: schedules billboard name.
     * @param startTime:     schedules billboard starting time.
     * @param duration:      schedules billboard duration.
     * @param interval:      schedules billboard interval.
     */
    public Schedule(int id,
                    String billboardName,
                    Instant startTime,
                    int duration,
                    int interval) {
        this.id = id;
        this.billboardName = billboardName;
        this.startTime = startTime;
        this.duration = duration;
        this.interval = interval;
    }

    /**
     * Schedule object constructor
     *
     * @param billboardName: schedules billboard name.
     * @param startTime:     schedules billboard starting time.
     * @param duration:      schedules billboard duration.
     * @param interval:      schedules billboard interval.
     */
    public Schedule(
        String billboardName,
        Instant startTime,
        int duration,
        int interval) {
        this.billboardName = billboardName;
        this.startTime = startTime;
        this.duration = duration;
        this.interval = interval;
    }

    /**
     * Parses the SQL result set and returns a schedule object.
     *
     * @param rs: the result set from an SQL SELECT query.
     * @return Schedule: the schedule object after converting from SQL.
     * @throws SQLException: this is thrown when there is an issue with getting values from the query.
     */
    public static Schedule fromSQL(ResultSet rs) throws SQLException {
        return new Schedule(
            rs.getInt("id"),
            rs.getString("billboardName"),
            rs.getTimestamp("startTime").toInstant(),
            rs.getInt("duration"),
            rs.getInt("interval"));
    }
}
