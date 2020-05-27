package common.utils;

import java.util.Date;

/**
 * Simple utility functions for time conversions.
 *
 * @author Jamie Martin
 */
public class Time {
    public static String minutesToTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }

    public static int timeToMinute(Date date) {
        int hours = date.getHours();
        int minutes = date.getMinutes();
        return (hours * 60) + (minutes);
    }
}
