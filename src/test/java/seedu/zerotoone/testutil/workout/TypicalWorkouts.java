package seedu.zerotoone.testutil.workout;

import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_EXERCISE_DUMBBELL_CRUNCH;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_EXERCISE_LUNGES;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_WORKOUT_NAME_ABS_WORKOUT;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_WORKOUT_NAME_ARMS_WORKOUT;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_WORKOUT_NAME_LEGS_WORKOUT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.model.workout.WorkoutList;
import seedu.zerotoone.testutil.exercise.TypicalExercises;

/**
 * A utility class containing a list of {@code Workout} objects to be used in tests.
 */
public class TypicalWorkouts {
    // Manually added - Workout's details found in {@code CommandTestUtil}
    public static final Workout ARMS_WORKOUT = new WorkoutBuilder()
            .withWorkoutName(VALID_WORKOUT_NAME_ARMS_WORKOUT)
            .withWorkoutExercise(TypicalExercises.BENCH_PRESS)
            .withWorkoutExercise(TypicalExercises.DEADLIFT)
            .build();
    public static final Workout LEGS_WORKOUT = new WorkoutBuilder()
            .withWorkoutName(VALID_WORKOUT_NAME_LEGS_WORKOUT)
            .withWorkoutExercise(VALID_EXERCISE_LUNGES)
            .build();
    public static final Workout ABS_WORKOUT = new WorkoutBuilder()
            .withWorkoutName(VALID_WORKOUT_NAME_ABS_WORKOUT)
            .withWorkoutExercise(VALID_EXERCISE_DUMBBELL_CRUNCH)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalWorkouts() {} // prevents instantiation

    /**
     * Returns an {@code WorkoutList} with all the typical workouts.
     */
    public static WorkoutList getTypicalWorkoutList() {
        WorkoutList el = new WorkoutList();
        for (Workout workout : getTypicalWorkouts()) {
            el.addWorkout(workout);
        }
        return el;
    }

    public static List<Workout> getTypicalWorkouts() {
        return new ArrayList<>(Arrays.asList(ARMS_WORKOUT, LEGS_WORKOUT, ABS_WORKOUT));
    }
}
