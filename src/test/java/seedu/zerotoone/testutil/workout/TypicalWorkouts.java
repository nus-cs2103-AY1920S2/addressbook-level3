package seedu.zerotoone.testutil.workout;

import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_WORKOUT_NAME_BENCH_PRESS;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_WORKOUT_NAME_DEADLIFT;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_WORKOUT_NAME_OVERHEAD_PRESS;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_NUM_REPS_BENCH_PRESS;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_NUM_REPS_DEADLIFT;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_NUM_REPS_OVERHEAD_PRESS;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_WEIGHT_BENCH_PRESS;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_WEIGHT_DEADLIFT;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_WEIGHT_OVERHEAD_PRESS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.model.workout.WorkoutList;

/**
 * A utility class containing a list of {@code Workout} objects to be used in tests.
 */
public class TypicalWorkouts {
    // Manually added - Workout's details found in {@code CommandTestUtil}
    public static final Workout BENCH_PRESS = new WorkoutBuilder()
            .withWorkoutName(VALID_WORKOUT_NAME_ARMS_WORKOUT)
            .withWorkoutExercises(VALID_EXERCISE_BENCH_PRESS, VALID_EXERCISE_DEADLIFT)
            .build();
    public static final Workout DEADLIFT = new WorkoutBuilder()
            .withWorkoutName(VALID_WORKOUT_NAME_DEADLIFT)
            .withWorkoutExercises(VALID_WEIGHT_DEADLIFT, VALID_NUM_REPS_DEADLIFT)
            .build();
    public static final Workout OVERHEAD_PRESS = new WorkoutBuilder()
            .withWorkoutName(VALID_WORKOUT_NAME_OVERHEAD_PRESS)
            .withWorkoutExercises(VALID_WEIGHT_OVERHEAD_PRESS, VALID_NUM_REPS_OVERHEAD_PRESS)
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
        return new ArrayList<>(Arrays.asList(BENCH_PRESS, DEADLIFT));
    }
}
