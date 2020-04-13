package seedu.zerotoone.logic.statistics;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.zerotoone.model.session.CompletedWorkout;

/**
 * The type Workout count data point.
 */
public class WorkoutCountDataPoint extends DataPoint {
    /**
     * Instantiates a new Workout count data point.
     */
    public WorkoutCountDataPoint() {
        super("Total workout count", "0");
    }

    @Override
    public void calculate(List<CompletedWorkout> completedWorkouts) {
        requireNonNull(completedWorkouts);
        this.result = completedWorkouts.size() + " workouts";
    }
}
