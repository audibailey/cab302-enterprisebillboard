package common.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Date;

/**
 * This class consists of the Schedule object and its associated methods.
 *
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
     * @param id
     * @param billboardName
     * @param startTime
     * @param duration
     * @param interval
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
     * Parses the XML String and returns a new Schedule Object
     * @param xml
     * @return
     */
    public static Schedule fromXML(String xml) {
        return new Schedule();
    }

    /**
     * Parses the Object and returns an XML String
     * @param s
     * @return
     */
    public static String toXML(Schedule s) {
        return "";
    }

    public static Schedule fromSQL(ResultSet rs) throws SQLException {
        return new Schedule(
            rs.getInt("id"),
            rs.getString("billboardName"),
            rs.getTime("startTime"),
            rs.getInt("duration"),
            rs.getInt("interval"));
    }
}
