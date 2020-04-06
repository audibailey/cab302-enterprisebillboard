package common;

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
    public int billboardName;
    public Date startTime;
    public Duration duration;
    public Duration interval;


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
                    int billboardName,
                    Date startTime,
                    Duration duration,
                    Duration interval) {
        this.id = id;
        this.billboardName = billboardName;
        this.startTime = startTime;
        this.duration = duration;
        this.interval = interval;
    }
}
