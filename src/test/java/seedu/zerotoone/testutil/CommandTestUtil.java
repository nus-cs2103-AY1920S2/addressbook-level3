package seedu.zerotoone.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_WEIGHT;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.zerotoone.logic.parser.CliSyntax.PREFIX_NUM_OF_REPS;
import static seedu.zerotoone.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.zerotoone.commons.core.index.Index;
import seedu.zerotoone.logic.commands.Command;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
// import seedu.zerotoone.logic.commands.exercise.EditCommand;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseList;
import seedu.zerotoone.model.exercise.PredicateFilterExerciseName;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_BENCH_PRESS = "Bench Press";
    public static final String VALID_NAME_CRUNCHES = "Crunches";
    public static final String VALID_NUM_REPS_BENCH_PRESS = "3";
    public static final String VALID_NUM_REPS_CRUNCHES = "4";
    public static final String VALID_WEIGHT_BENCH_PRESS = "25";
    public static final String VALID_WEIGHT_CRUNCHES = "15";

    public static final String NAME_DESC_BENCH_PRESS = " " + PREFIX_NAME + VALID_NAME_BENCH_PRESS;
    public static final String NAME_DESC_CRUNCHES = " " + PREFIX_NAME + VALID_NAME_CRUNCHES;
    public static final String NUM_REPS_DESC_BENCH_PRESS = " " + PREFIX_NUM_OF_REPS + VALID_NUM_REPS_BENCH_PRESS;
    public static final String NUM_REPS_DESC_CRUNCHES = " " + PREFIX_NUM_OF_REPS + VALID_NUM_REPS_CRUNCHES;
    public static final String WEIGHT_DESC_BENCH_PRESS = " " + PREFIX_WEIGHT + VALID_WEIGHT_BENCH_PRESS;
    public static final String WEIGHT_DESC_CRUNCHES = " " + PREFIX_WEIGHT + VALID_WEIGHT_CRUNCHES;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_NUM_REPS_DESC = " " + PREFIX_NUM_OF_REPS + "911a"; // 'a' not allowed in repetitions
    public static final String INVALID_WEIGHT_DESC = " " + PREFIX_WEIGHT + "911a"; // 'a' not allowed in weight

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    // public static final EditCommand.EditExerciseDescriptor DESC_BENCH_PRESS;
    // public static final EditCommand.EditExerciseDescriptor DESC_CRUNCHES;

    static {
        // DESC_BENCH_PRESS = new EditExerciseDescriptorBuilder().withName(VALID_NAME_BENCH_PRESS)
        //         .withPhone(VALID_NUM_REPS_BENCH_PRESS).withEmail(VALID_WEIGHT_BENCH_PRESS).build();
        // DESC_CRUNCHES = new EditExerciseDescriptorBuilder().withName(VALID_NAME_CRUNCHES)
        //         .withPhone(VALID_NUM_REPS_CRUNCHES).withEmail(VALID_WEIGHT_CRUNCHES).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered exercise list and selected exercise in {@code actualModel} remain unchanged
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
     * {@code model}'s address book.
     */
    public static void showExerciseAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredExerciseList().size());

        Exercise exercise = model.getFilteredExerciseList().get(targetIndex.getZeroBased());
        final String name = exercise.getExerciseName().fullName;
        model.updateFilteredExerciseList(new PredicateFilterExerciseName(name));

        assertEquals(1, model.getFilteredExerciseList().size());
    }

}
