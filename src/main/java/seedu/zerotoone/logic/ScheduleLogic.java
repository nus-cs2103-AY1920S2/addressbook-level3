package seedu.zerotoone.logic;

import javafx.collections.ObservableList;
import seedu.zerotoone.model.schedule.ScheduledWorkout;

/**
 * Logic for Schedule.
 */
public interface ScheduleLogic {
    ObservableList<ScheduledWorkout> getSortedScheduledWorkoutList();

    void showdownTimer();
}
