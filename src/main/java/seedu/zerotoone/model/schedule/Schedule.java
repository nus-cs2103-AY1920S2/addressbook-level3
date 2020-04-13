package seedu.zerotoone.model.schedule;

import java.util.List;
import java.util.Optional;

import seedu.zerotoone.model.workout.WorkoutName;

/**
 * Represents a Schedule that is able to return ScheduledWorkout when requested.
 */
public interface Schedule {
    WorkoutName getWorkoutNameToSchedule();
    DateTime getDateTime();
    Optional<List<ScheduledWorkout>> getScheduledWorkout(DateTime now);
    boolean isSameSchedule(Schedule other);
}
