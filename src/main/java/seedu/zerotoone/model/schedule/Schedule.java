package seedu.zerotoone.model.schedule;

import java.util.List;
import java.util.Optional;

import seedu.zerotoone.model.exercise.Exercise;

/**
 * STEPH_TODO_JAVADOC
 */
public interface Schedule {
    Exercise getWorkoutToSchedule(); // TO_CHANGE_EXERCISE_TO_WORKOUT
    Optional<List<ScheduledWorkout>> getScheduledWorkout();
}
