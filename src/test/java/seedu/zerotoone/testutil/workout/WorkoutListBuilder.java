package seedu.zerotoone.testutil.workout;

import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.model.workout.WorkoutList;

/**
 * A utility class to help with building WorkoutList objects.
 * Example usage: <br>
 *     {@code WorkoutList workoutList =
 *          new WorkoutListBuilder().withWorkout(new Workout(...)).build();}
 */
public class WorkoutListBuilder {

    private WorkoutList workoutList;

    public WorkoutListBuilder() {
        workoutList = new WorkoutList();
    }

    public WorkoutListBuilder(WorkoutList workoutList) {
        this.workoutList = workoutList;
    }

    /**
     * Adds a new {@code Workout} to the {@code WorkoutList} that we are building.
     */
    public WorkoutListBuilder withWorkout(Workout workout) {
        workoutList.addWorkout(workout);
        return this;
    }

    public WorkoutList build() {
        return workoutList;
    }
}
