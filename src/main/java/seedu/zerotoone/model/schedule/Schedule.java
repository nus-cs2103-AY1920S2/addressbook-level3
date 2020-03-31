package seedu.zerotoone.model.schedule;

import java.util.List;
import java.util.Optional;

import seedu.zerotoone.model.workout.Workout;

/**
 * STEPH_TODO_JAVADOC
 */
public interface Schedule {
    Workout getWorkoutToSchedule();
    Optional<List<ScheduledWorkout>> getScheduledWorkout();
    boolean isSameSchedule(Schedule other);
}
