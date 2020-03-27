package seedu.zerotoone.model.schedule;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * STEPH_TODO_JAVADOC
 */
public class Scheduler {

    private final ScheduleList scheduleList;
    private final ScheduledWorkoutList scheduledWorkoutList;
    private final FilteredList<ScheduledWorkout> filteredScheduledWorkoutList;

    public Scheduler() {
        this.scheduleList = new ScheduleList();
        this.scheduledWorkoutList = new ScheduledWorkoutList();
        this.filteredScheduledWorkoutList = new FilteredList<>(this.scheduledWorkoutList.getScheduledWorkoutList());
    }

    public ObservableList<ScheduledWorkout> getFilteredScheduledWorkoutList() {
        return filteredScheduledWorkoutList;
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
