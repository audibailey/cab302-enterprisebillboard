package common.models;

import common.utils.RandomFactory;

import java.io.Serializable;
import java.time.Instant;

/**
 * This class consists of the Schedule object and its associated methods.
 *
 * @author Perdana Bailey
 * @author Jamie Martin
 * @author Kevin Huynh
 */
@SQLITE(type="FOREIGN KEY(billboardName) REFERENCES Billboard(name)")
public class Schedule implements Serializable {
    /**
     * The variables of the object schedule
     */
    @SQLITE(type="INTEGER PRIMARY KEY AUTOINCREMENT")
    public int id;

    @SQLITE(type="VARCHAR(255) NOT NULL UNIQUE")
    public String billboardName;

    @SQLITE(type="DATETIME NOT NULL")
    public Instant startTime;

    @SQLITE(type="DATETIME NOT NULL")
    public Instant createTime; // Time when create the schedule

    @SQLITE(type="INTEGER")
    public int duration;

    @SQLITE(type="INTEGER")
    public int interval;

    /**
     * An empty constructor just for creating the object.
     */
    public Schedule() {

    }

    /**
     * Schedule object constructor
     *
     * @param id:            schedules id.
     * @param billboardName: schedules billboard name.
     * @param startTime:     schedules billboard starting time.
     * @param createTime:    schedules billboard creating time.
     * @param duration:      schedules billboard duration.
     * @param interval:      schedules billboard interval.
     */
    public Schedule(int id,
                    String billboardName,
                    Instant startTime,
                    Instant createTime,
                    int duration,
                    int interval) {
        this.id = id;
        this.billboardName = billboardName;
        this.startTime = startTime;
        this.createTime = createTime;
        this.duration = duration;
        this.interval = interval;
    }

    /**
     * Schedule object constructor
     *
     * @param billboardName: schedules billboard name.
     * @param startTime:     schedules billboard starting time.
     * @param duration:      schedules billboard duration.
     * @param createTime:    schedules billboard create time.
     * @param interval:      schedules billboard interval.
     */
    public Schedule(
        String billboardName,
        Instant startTime,
        Instant createTime,
        int duration,
        int interval) {
        this.billboardName = billboardName;
        this.startTime = startTime;
        this.createTime = createTime;
        this.duration = duration;
        this.interval = interval;
    }

    /**
     * Generates a random Schedule object with random variables
     *
     * @param billboardName: the name of the billboard.
     * @return a randomised schedule object.
     */
    public static Schedule Random(String billboardName) {
        return new Schedule(
            billboardName,
            RandomFactory.Instant(),
            RandomFactory.Instant(),
            RandomFactory.Int(30),
            RandomFactory.Int(60)
        );
    }
}
