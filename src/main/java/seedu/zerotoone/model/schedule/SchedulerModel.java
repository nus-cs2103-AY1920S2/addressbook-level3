package seedu.zerotoone.model.schedule;

import javafx.collections.ObservableList;

/**
 *
 */
public interface SchedulerModel {
    boolean hasSchedule(Schedule schedule);
    void addSchedule(Schedule schedule);
    void setSchedule(Schedule scheduleToEdit, Schedule editedSchedule);
    void deleteScheduledWorkout(ScheduledWorkout scheduledWorkoutToDelete);
    ObservableList<ScheduledWorkout> getSortedScheduledWorkoutList();
    ScheduleList getScheduleList();
    void populateSortedScheduledWorkoutList();
}
