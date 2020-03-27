package seedu.zerotoone.model.schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * STEPH_TODO_JAVADOC
 */
public class ScheduleList {

    private final List<Schedule> schedules;

    public ScheduleList() {
        this.schedules = new ArrayList<>();
    }

    public boolean hasSchedule(Schedule schedule) {
        return schedules.contains(schedule);
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }
}
