package common.models;

import java.util.List;

public class Day {
    public String day;
    public List<String> times;

    public Day(DayOfWeek day, List<String> times) {
        this.day = day.name();
        this.times = times;
    }
}
