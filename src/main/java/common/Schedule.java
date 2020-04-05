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
    int id;
    private int billboardId;
    private Date startTime;
    private Duration duration;
    private Duration interval;


    /**
     * Schedule object constructor
     *
     * @param id
     * @param billboardId
     * @param startTime
     * @param duration
     * @param interval
     */
    public Schedule(int id,
                    int billboardId,
                    Date startTime,
                    Duration duration,
                    Duration interval) {
        this.id = id;
        this.billboardId = billboardId;
        this.startTime = startTime;
        this.duration = duration;
        this.interval = interval;
    }
}
