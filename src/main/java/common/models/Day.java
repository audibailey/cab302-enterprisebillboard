package common.models;

import java.util.List;

/**
 * This class consists of the Day object and its associated methods.
 *
 * @author Jamie Martin
 */
public class Day {
    public String day;
    public List<String> times;

    public Day(DayOfWeek day, List<String> times) {
        this.day = day.name();
        this.times = times;
    }
}
