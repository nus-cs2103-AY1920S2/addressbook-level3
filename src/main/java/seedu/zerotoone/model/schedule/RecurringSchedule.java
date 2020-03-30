package seedu.zerotoone.model.schedule;

import java.util.List;
import java.util.Optional;

import seedu.zerotoone.model.exercise.Exercise;

/**
 * STEPH_TODO_JAVADOC
 */
public class RecurringSchedule implements Schedule {

    private final Exercise workoutToSchedule; // TO_CHANGE_EXERCISE_TO_WORKOUT
    private final DateTime startDate;
    private final DateTime endDate;

    // private final List<CanceledScheudledWorkout>
    // private final List<EditedScheduledWorkout>

    public RecurringSchedule(Exercise workoutToSchedule, DateTime startDate, DateTime endDate) {
        // TO_CHANGE_EXERCISE_TO_WORKOUT
        this.workoutToSchedule = workoutToSchedule;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public Exercise getWorkoutToSchedule() {
        return workoutToSchedule;
    }

    @Override
    public Optional<List<ScheduledWorkout>> getScheduledWorkout() {
        return Optional.empty();
        // do I have any scheduled workouts after today?
        // are some instances of them edited/deleted?
        // return the list of scheduled workouts that are not deleted along with those that are edited, that are
        // scheduled after today

        // ScheduledWorkout scheduledWorkout = new ScheduledWorkout(workoutToSchedule, dateTime);
        // return Optional.of(Collections.singletonList(scheduledWorkout));
    }

    @Override
    public boolean isSameSchedule(Schedule other) {
        return false; // STEPH_TODO
    }
}
