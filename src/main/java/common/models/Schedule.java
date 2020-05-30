package common.models;

import client.components.table.DisplayAs;
import client.components.table.Editable;
import common.sql.SQLITE;
import common.utils.RandomFactory;
import common.utils.scheduling.Time;

import java.io.Serializable;
import java.time.Instant;

/**
 * This class consists of the Schedule object and its associated methods.
 *
 * @author Perdana Bailey
 * @author Jamie Martin
 * @author Hieu Nghia Huynh
 */
@SQLITE(type="FOREIGN KEY(billboardName) REFERENCES Billboard(name)")
public class Schedule implements Serializable, Editable {
    /**
     * The variables of the object schedule
     */
    @SQLITE(type="INTEGER PRIMARY KEY AUTOINCREMENT")
    public int id;

    @SQLITE(type="VARCHAR(255) NOT NULL")
    public String billboardName;

    // 0 - 7
    // Every, Sun - Sat
    @SQLITE(type="INTEGER NOT NULL")
    public int dayOfWeek = 0;

    // 0 - 1440 / 1439?
    // 00:00 - 23:59
    @SQLITE(type="INTEGER NOT NULL")
    public int start = 0;

    @SQLITE(type="DATETIME NOT NULL")
    public Instant createTime = Instant.now(); // Time when create the schedule

    @SQLITE(type="INTEGER NOT NULL")
    public int duration;

    @SQLITE(type="INTEGER NOT NULL")
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
     * @param start:     schedules billboard starting time.
     * @param createTime:    schedules billboard creating time.
     * @param duration:      schedules billboard duration.
     * @param interval:      schedules billboard interval.
     */
    public Schedule(int id,
                    String billboardName,
                    int dayOfWeek,
                    int start,
                    Instant createTime,
                    int duration,
                    int interval) {
        this.id = id;
        this.billboardName = billboardName;
        this.dayOfWeek = dayOfWeek;
        this.start = start;
        this.createTime = createTime;
        this.duration = duration;
        this.interval = interval;
    }

    /**
     * Schedule object constructor
     *
     * @param billboardName: schedules billboard name.
     * @param start:     schedules billboard starting time.
     * @param duration:      schedules billboard duration.
     * @param createTime:    schedules billboard create time.
     * @param interval:      schedules billboard interval.
     */
    public Schedule(
        String billboardName,
        int dayOfWeek,
        int start,
        Instant createTime,
        int duration,
        int interval) {
        this.billboardName = billboardName;
        this.dayOfWeek = dayOfWeek;
        this.start = start;
        this.createTime = createTime;
        this.duration = duration;
        this.interval = interval;
    }

    // Display annotation data relative to the objects fields
    @DisplayAs(value = "Id", index = 0)
    public int getId() {
        return id;
    }

    @DisplayAs(value = "Billboard Name", index = 1)
    public String getName() {
        return billboardName;
    }

    @DisplayAs(value = "Day Of Week", index = 2)
    public String getDayOfWeek() {
        switch (dayOfWeek) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default:
                return "Every";
        }
    }

    @DisplayAs(value = "Start Time", index = 3)
    public String getStart() {
        return Time.minutesToTime(start);
    }

    @DisplayAs(value = "Duration", index = 4)
    public int getDuration() {
        return duration;
    }

    @DisplayAs(value = "Interval", index = 5)
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
            RandomFactory.Int(7),
            RandomFactory.Int(1400),
            Instant.now(),
            RandomFactory.Int(30),
            RandomFactory.Int(60)
        );
    }
}
