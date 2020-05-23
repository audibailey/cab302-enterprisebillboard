package common.models;

import client.components.table.DisplayAs;
import client.components.table.Editable;
import common.utils.RandomFactory;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * This class consists of the Schedule object and its associated methods.
 *
 * @author Perdana Bailey
 * @author Jamie Martin
 * @author Kevin Huynh
 */
@SQLITE(type="FOREIGN KEY(billboardName) REFERENCES Billboard(name)")
public class Schedule implements Serializable, Editable {
    /**
     * The variables of the object schedule
     */
    @SQLITE(type="INTEGER PRIMARY KEY AUTOINCREMENT")
    public int id;

    @SQLITE(type="VARCHAR(255) NOT NULL UNIQUE")
    public String billboardName;

    @SQLITE(type="DATETIME NOT NULL")
    public Instant startTime = Instant.MIN;

    @SQLITE(type="DATETIME NOT NULL")
    public Instant createTime = Instant.MIN; // Time when create the schedule

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

    @DisplayAs(value = "Id", index = 0)
    public int getId() {
        return id;
    }

    @DisplayAs(value = "Billboard Name", index = 1)
    public String getName() {
        return billboardName;
    }

    @DisplayAs(value = "Start Time", index = 2)
    public String getStartTime() {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
        return DATE_TIME_FORMATTER.format(startTime);
    }

    @DisplayAs(value = "Duration", index = 3)
    public int getDuration() {
        return duration;
    }

    @DisplayAs(value = "Interval", index = 4)
    public int getInterval() {
        return interval;
    }

    @Override
    public boolean isEditable() {
        return false;
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
            Instant.now(),
            RandomFactory.Int(30),
            RandomFactory.Int(60)
        );
    }
}
