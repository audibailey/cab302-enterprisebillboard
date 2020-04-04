package common;

import java.time.Duration;
import java.util.Date;

/**
 * This class consists of the billboard object and its associated methods.
 *
 * @author Perdana Bailey
 * @author Kevin Huynh
 * @author Jamie Martin
 */
public class Billboard implements IBillboard {

    /**
     * The variables of the object billboard
     */
    private int billboardID;
    private String name;
    private String message;
    private byte[] img;
    private String information;
    private boolean locked;
    private Date startTime;
    private Duration duration;
    private Duration interval;
    private int userID;

    /**
     * Billboard object constructor
     *
     * @param billboardID
     * @param name
     * @param message
     * @param img
     * @param information
     * @param locked
     * @param startTime
     * @param duration
     * @param interval
     * @param userID
     */
    public Billboard(int billboardID, String name, String message, byte[] img, String information, boolean locked, Date startTime, Duration duration, Duration interval, int userID) {
        this.billboardID = billboardID;
        this.name = name;
        this.message = message;
        this.img = img;
        this.information = information;
        this.locked = locked;
        this.startTime = startTime;
        this.duration = duration;
        this.interval = interval;
        this.userID = userID;
    }
}
