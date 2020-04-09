package common.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Date;

/**
 * This class consists of the Schedule object and its associated methods.
 *
 * @author Perdana Bailey
 * @author Jamie Martin
 */
public class Schedule {
    /**
     * The variables of the object schedule
     */
    public int id;
    public String billboardName;
    public Date startTime;
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
                    Date startTime,
                    int duration,
                    int interval) {
        this.id = id;
        this.billboardName = billboardName;
        this.startTime = startTime;
        this.duration = duration;
        this.interval = interval;
    }

    /**
     * Parses the XML string and returns a Schedule object.
     *
     * @param xml: the xml to be converted as a string.
     * @return Schedule: the Schedule object after converting from XML.
     */
    public static Schedule fromXML(String xml) {
        return new Schedule();
    }

    /**
     * Parses the Schedule object and returns an XML string from it.
     *
     * @param schedule: the schedule to be converted to an XML string.
     * @return String: the XML after converting from schedule object.
     */
    public static String toXML(Schedule schedule) {
        return "";
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
            rs.getTime("startTime"),
            rs.getInt("duration"),
            rs.getInt("interval"));
    }
}
