package seedu.zerotoone.model.exercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.CommandTestUtil.VALID_NAME_CRUNCHES;
import static seedu.zerotoone.testutil.CommandTestUtil.VALID_NUM_REPS_CRUNCHES;
import static seedu.zerotoone.testutil.CommandTestUtil.VALID_WEIGHT_CRUNCHES;
import static seedu.zerotoone.testutil.TypicalExercises.BENCH_PRESS;
import static seedu.zerotoone.testutil.TypicalExercises.CRUNCHES;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.testutil.ExerciseBuilder;

public class ExerciseTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Exercise exercise = new ExerciseBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> exercise.getExerciseSets().remove(0));
    }

    @Test
    public void isSameExercise() {
        // same object -> returns true
        assertTrue(BENCH_PRESS.isSameExercise(BENCH_PRESS));

        // null -> returns false
        assertFalse(BENCH_PRESS.isSameExercise(null));

        // same name, same attributes -> returns true
        Exercise editedBenchPress = new ExerciseBuilder(BENCH_PRESS).build();
        assertTrue(BENCH_PRESS.isSameExercise(editedBenchPress));

        // different name -> returns false
        editedBenchPress = new ExerciseBuilder(BENCH_PRESS)
                .withExerciseName(VALID_NAME_CRUNCHES)
                .build();
        assertFalse(BENCH_PRESS.isSameExercise(editedBenchPress));

        // different exerciseSet -> returns true
        editedBenchPress = new ExerciseBuilder(BENCH_PRESS)
                .withExerciseSet(VALID_WEIGHT_CRUNCHES, VALID_NUM_REPS_CRUNCHES)
                .build();
        assertTrue(BENCH_PRESS.isSameExercise(editedBenchPress));

        // different name, different exerciseSet -> returns false
        editedBenchPress = new ExerciseBuilder(BENCH_PRESS)
                .withExerciseName(VALID_NAME_CRUNCHES)
                .withExerciseSet(VALID_WEIGHT_CRUNCHES, VALID_NUM_REPS_CRUNCHES)
                .build();
        assertFalse(BENCH_PRESS.isSameExercise(editedBenchPress));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Exercise benchPressCopy = new ExerciseBuilder(BENCH_PRESS).build();
        assertTrue(BENCH_PRESS.equals(benchPressCopy));

        // same object -> returns true
        assertTrue(BENCH_PRESS.equals(BENCH_PRESS));

        // null -> returns false
        assertFalse(BENCH_PRESS.equals(null));

        // different type -> returns false
        assertFalse(BENCH_PRESS.equals(5));

        // different exercise -> returns false
        assertFalse(BENCH_PRESS.equals(CRUNCHES));

        // different name -> returns false
        Exercise editedBenchPress = new ExerciseBuilder(BENCH_PRESS).withExerciseName(VALID_NAME_CRUNCHES).build();
        assertFalse(BENCH_PRESS.equals(editedBenchPress));

        // different exerciseSet -> returns false
        editedBenchPress = new ExerciseBuilder(BENCH_PRESS)
                .withExerciseSet(VALID_WEIGHT_CRUNCHES, VALID_NUM_REPS_CRUNCHES).build();
        assertFalse(BENCH_PRESS.equals(editedBenchPress));
    }
}
