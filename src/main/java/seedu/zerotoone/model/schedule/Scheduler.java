package seedu.zerotoone.model.schedule;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

/**
 * STEPH_TODO_JAVADOC
 */
public class Scheduler {

    private final ScheduleList scheduleList;
    private final ScheduledWorkoutList scheduledWorkoutList;
    private final SortedList<ScheduledWorkout> sortedScheduledWorkoutList;

    public Scheduler() {
        this.scheduleList = new ScheduleList();
        this.scheduledWorkoutList = new ScheduledWorkoutList();
        this.sortedScheduledWorkoutList = new SortedList<>(this.scheduledWorkoutList.getScheduledWorkoutList());
    }

    public ObservableList<ScheduledWorkout> getSortedScheduledWorkoutList() {
        return sortedScheduledWorkoutList;
    }

    /**
     *
     * @param schedule
     * @return
     */
    public boolean hasSchedule(Schedule schedule) {
        requireNonNull(schedule);
        return scheduleList.hasSchedule(schedule);
    }

    /**
     *
     * @param schedule
     */
    public void addSchedule(Schedule schedule) {
        requireNonNull(schedule);
        scheduleList.addSchedule(schedule);
    }
}
