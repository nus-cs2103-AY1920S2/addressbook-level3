package seedu.zerotoone.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.zerotoone.testutil.Assert.assertThrows;

import seedu.zerotoone.logic.Logic;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.ModelManager;
import seedu.zerotoone.model.userprefs.UserPrefs;

/**
 * Contains helper methods to test LogicManager
 */
public class LogicManagerTestUtil {
    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    public static void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel, Logic logic, Model model) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    public static void assertParseException(String inputCommand, String expectedMessage, Logic logic, Model model) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage, logic, model);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    public static void assertCommandException(String inputCommand, String expectedMessage, Logic logic, Model model) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage, logic, model);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    public static void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Logic logic, Model model) {
        Model expectedModel = new ModelManager(new UserPrefs(), model.getExerciseList());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel, logic, model);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    public static void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel, Logic logic, Model model) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }
}
