package seedu.zerotoone.testutil.workout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_WORKOUT_NAME;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_NUM_OF_REPS;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static seedu.zerotoone.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.Command;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.model.workout.WorkoutList;
import seedu.zerotoone.model.workout.PredicateFilterWorkoutName;
import seedu.zerotoone.testutil.CommandTestUtil;

/**
 * Contains helper methods for testing commands.
 */
public class WorkoutCommandTestUtil extends CommandTestUtil {

    public static final String VALID_WORKOUT_NAME_ARMS_WORKOUT = "Arms Workout";
    public static final String VALID_WORKOUT_NAME_LEGS_WORKOUT = "Legs Workout";
    public static final String VALID_WORKOUT_NAME_ABS_WORKOUT = "Abs Workout";
    public static final String VALID_NUM_REPS_BENCH_PRESS = "10";
    public static final String VALID_NUM_REPS_DEADLIFT = "5";
    public static final String VALID_NUM_REPS_OVERHEAD_PRESS = "10";
    public static final String VALID_WEIGHT_BENCH_PRESS = "60";
    public static final String VALID_WEIGHT_DEADLIFT = "65";
    public static final String VALID_WEIGHT_OVERHEAD_PRESS = "30";

    public static final String WORKOUT_NAME_DESC_ARMS_WORKOUT = " " + PREFIX_WORKOUT_NAME
            + VALID_WORKOUT_NAME_ARMS_WORKOUT;
    public static final String WORKOUT_NAME_DESC_LEGS_WORKOUT = " " + PREFIX_WORKOUT_NAME
            + VALID_WORKOUT_NAME_LEGS_WORKOUT;
    public static final String WORKOUT_NAME_DESC_ABS_WORKOUT= " " + PREFIX_WORKOUT_NAME
            + VALID_WORKOUT_NAME_ABS_WORKOUT;

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

    public static final String INVALID_WORKOUT_NAME_DESC =
            " " + PREFIX_WORKOUT_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_NUM_REPS_DESC =
            " " + PREFIX_NUM_OF_REPS + "911a"; // 'a' not allowed in repetitions
    public static final String INVALID_WEIGHT_DESC =
            " " + PREFIX_WEIGHT + "911a"; // 'a' not allowed in weight

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the workout list, filtered workout list and selected workout in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        WorkoutList expectedWorkoutList = new WorkoutList(actualModel.getWorkoutList());
        List<Workout> expectedFilteredList = new ArrayList<>(actualModel.getFilteredWorkoutList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedWorkoutList, actualModel.getWorkoutList());
        assertEquals(expectedFilteredList, actualModel.getFilteredWorkoutList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the workout at the given {@code targetIndex} in the
     * {@code model}'s workout list.
     */
    public static void showWorkoutAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredWorkoutList().size());

        Workout workout = model.getFilteredWorkoutList().get(targetIndex.getZeroBased());
        final String workoutName = workout.getWorkoutName().fullName;
        model.updateFilteredWorkoutList(new PredicateFilterWorkoutName(workoutName));

        assertEquals(1, model.getFilteredWorkoutList().size());
    }
}
