package seedu.zerotoone.model.schedule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * STEPH_TODO_JAVADOC
 */
public class ScheduleList implements Iterable<Schedule> {

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

    @Override
    public Iterator<Schedule> iterator() {
        return schedules.iterator();
    }
}
