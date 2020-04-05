package common;

import java.sql.Time;
import java.time.Duration;


public class Schedule {
    /**
     * The variables of the object Schedule
     */
    private int scheduleID;
    private int billboardID;
    private Duration duration;
    private Time startTime;
    private int minuteInterval;

    /**
     * User object constructor
     *
     * @param scheduleID
     * @param billboardID
     * @param startTime
     * @param duration
     * @param minuteInterval
     */

    public Schedule(int scheduleID, int billboardID, Time startTime, Duration duration, int minuteInterval) {
        this.scheduleID = scheduleID;
        this.billboardID = billboardID;
        this.duration = duration;
        this.startTime = startTime;
        this.minuteInterval = minuteInterval;

    }
}
