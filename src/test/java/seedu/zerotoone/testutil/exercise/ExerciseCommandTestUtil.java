package seedu.zerotoone.testutil.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_EXERCISE_NAME;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_NUM_OF_REPS;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static seedu.zerotoone.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.Command;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseList;
import seedu.zerotoone.model.exercise.PredicateFilterExerciseName;
import seedu.zerotoone.testutil.CommandTestUtil;

/**
 * Contains helper methods for testing commands.
 */
public class ExerciseCommandTestUtil extends CommandTestUtil {

    public static final String VALID_EXERCISE_NAME_BENCH_PRESS = "Bench Press";
    public static final String VALID_EXERCISE_NAME_DEADLIFT = "Deadlift";
    public static final String VALID_EXERCISE_NAME_OVERHEAD_PRESS = "Overhead Press";
    public static final String VALID_NUM_REPS_BENCH_PRESS = "10";
    public static final String VALID_NUM_REPS_DEADLIFT = "5";
    public static final String VALID_NUM_REPS_OVERHEAD_PRESS = "10";
    public static final String VALID_WEIGHT_BENCH_PRESS = "60";
    public static final String VALID_WEIGHT_DEADLIFT = "65";
    public static final String VALID_WEIGHT_OVERHEAD_PRESS = "30";

    public static final String EXERCISE_NAME_DESC_BENCH_PRESS = " " + PREFIX_EXERCISE_NAME
            + VALID_EXERCISE_NAME_BENCH_PRESS;
    public static final String EXERCISE_NAME_DESC_DEADLIFT = " " + PREFIX_EXERCISE_NAME
            + VALID_EXERCISE_NAME_DEADLIFT;
    public static final String EXERCISE_NAME_DESC_OVERHEAD_PRESS = " " + PREFIX_EXERCISE_NAME
            + VALID_EXERCISE_NAME_OVERHEAD_PRESS;
    public static final String NUM_REPS_DESC_BENCH_PRESS = " " + PREFIX_NUM_OF_REPS
            + VALID_NUM_REPS_BENCH_PRESS;
    public static final String NUM_REPS_DESC_DEADLIFT = " " + PREFIX_NUM_OF_REPS
            + VALID_NUM_REPS_DEADLIFT;
    public static final String NUM_REPS_DESC_OVERHEAD_PRESS = " " + PREFIX_NUM_OF_REPS
            + VALID_NUM_REPS_OVERHEAD_PRESS;
    public static final String WEIGHT_DESC_BENCH_PRESS = " " + PREFIX_WEIGHT
            + VALID_WEIGHT_BENCH_PRESS;
    public static final String WEIGHT_DESC_DEADLIFT = " " + PREFIX_WEIGHT
            + VALID_WEIGHT_DEADLIFT;
    public static final String WEIGHT_DESC_OVERHEAD_PRESS = " " + PREFIX_WEIGHT
            + VALID_WEIGHT_OVERHEAD_PRESS;

    public static final String INVALID_EXERCISE_NAME_DESC =
            " " + PREFIX_EXERCISE_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_NUM_REPS_DESC =
            " " + PREFIX_NUM_OF_REPS + "911a"; // 'a' not allowed in repetitions
    public static final String INVALID_WEIGHT_DESC =
            " " + PREFIX_WEIGHT + "911a"; // 'a' not allowed in weight

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the exercise list, filtered exercise list and selected exercise in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ExerciseList expectedExerciseList = new ExerciseList(actualModel.getExerciseList());
        List<Exercise> expectedFilteredList = new ArrayList<>(actualModel.getFilteredExerciseList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedExerciseList, actualModel.getExerciseList());
        assertEquals(expectedFilteredList, actualModel.getFilteredExerciseList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the exercise at the given {@code targetIndex} in the
     * {@code model}'s exercise list.
     */
    public static void showExerciseAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredExerciseList().size());

        Exercise exercise = model.getFilteredExerciseList().get(targetIndex.getZeroBased());
        final String exerciseName = exercise.getExerciseName().fullName;
        model.updateFilteredExerciseList(new PredicateFilterExerciseName(exerciseName));

        assertEquals(1, model.getFilteredExerciseList().size());
    }
}
