package seedu.zerotoone.testutil;

import static seedu.zerotoone.testutil.CommandTestUtil.VALID_NAME_BENCH_PRESS;
import static seedu.zerotoone.testutil.CommandTestUtil.VALID_NAME_CRUNCHES;
import static seedu.zerotoone.testutil.CommandTestUtil.VALID_NUM_REPS_BENCH_PRESS;
import static seedu.zerotoone.testutil.CommandTestUtil.VALID_NUM_REPS_CRUNCHES;
import static seedu.zerotoone.testutil.CommandTestUtil.VALID_WEIGHT_BENCH_PRESS;
import static seedu.zerotoone.testutil.CommandTestUtil.VALID_WEIGHT_CRUNCHES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseList;

/**
 * A utility class containing a list of {@code Exercise} objects to be used in tests.
 */
public class TypicalExercises {

    public static final Exercise DEADLIFT = new ExerciseBuilder().withExerciseName("Deadlift")
            .withExerciseSet("30", "5").build();
    public static final Exercise SQUAT = new ExerciseBuilder().withExerciseName("Squat")
            .withExerciseSet("20", "6").build();
    public static final Exercise LEG_PRESS = new ExerciseBuilder().withExerciseName("Leg Press")
            .withExerciseSet("15", "2").build();
    public static final Exercise LEG_CURL = new ExerciseBuilder().withExerciseName("Leg Curl")
            .withExerciseSet("20", "4").build();
    public static final Exercise HAMMER_CURL = new ExerciseBuilder().withExerciseName("Hammer Curl")
            .withExerciseSet("25", "4").build();

    // Manually added - Exercise's details found in {@code CommandTestUtil}
    public static final Exercise BENCH_PRESS = new ExerciseBuilder().withExerciseName(VALID_NAME_BENCH_PRESS)
            .withExerciseSet(VALID_WEIGHT_BENCH_PRESS, VALID_NUM_REPS_BENCH_PRESS).build();
    public static final Exercise CRUNCHES = new ExerciseBuilder().withExerciseName(VALID_NAME_CRUNCHES)
            .withExerciseSet(VALID_WEIGHT_CRUNCHES, VALID_NUM_REPS_CRUNCHES).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalExercises() {} // prevents instantiation

    /**
     * Returns an {@code ExerciseList} with all the typical exercises.
     */
    public static ExerciseList getTypicalExerciseList() {
        ExerciseList el = new ExerciseList();
        for (Exercise exercise : getTypicalExercises()) {
            el.addExercise(exercise);
        }
        return el;
    }

    public static List<Exercise> getTypicalExercises() {
        return new ArrayList<>(Arrays.asList(
                BENCH_PRESS, CRUNCHES, DEADLIFT, SQUAT, LEG_PRESS, LEG_CURL, HAMMER_CURL));
    }
}
