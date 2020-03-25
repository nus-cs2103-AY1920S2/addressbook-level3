package seedu.zerotoone.testutil.workout;

import java.util.ArrayList;
import java.util.List;

import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.model.workout.WorkoutName;
import seedu.zerotoone.testutil.exercise.TypicalExercises;

/**
 * A utility class to help with building Workout objects.
 */
public class WorkoutBuilder {

    public static final String DEFAULT_WORKOUT_NAME = "Bench Press";

    private WorkoutName workoutName;
    private List<Exercise> workoutExercises;

    public WorkoutBuilder() {
        workoutName = new WorkoutName(DEFAULT_WORKOUT_NAME);
        workoutExercises = new ArrayList<>();
    }

    /**
     * Initializes the WorkoutBuilder with the data of {@code workoutToCopy}.
     */
    public WorkoutBuilder(Workout workoutToCopy) {
        workoutName = workoutToCopy.getWorkoutName();
        workoutExercises = workoutToCopy.getWorkoutExercises();
    }

    /**
     * Exercises the {@code WorkoutName} of the {@code Workout} that we are building.
     */
    public WorkoutBuilder withWorkoutName(String workoutName) {
        this.workoutName = new WorkoutName(workoutName);
        return this;
    }

    /**
     * Exercises the {@code workoutExercises} of the {@code Workout} that we are building.
     */
    public WorkoutBuilder withWorkoutExercises(String weight, String numReps) {
        List<Exercise> workoutExercisesCopy = new ArrayList<>(workoutExercises);
        workoutExercisesCopy.add(TypicalExercises.BENCH_PRESS);
        workoutExercisesCopy.add(TypicalExercises.DEADLIFT);
        workoutExercises = workoutExercisesCopy;
        return this;
    }

    public Workout build() {
        return new Workout(workoutName, workoutExercises);
    }

}
