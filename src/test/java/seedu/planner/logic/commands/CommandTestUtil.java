package seedu.planner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_STUDENT_SEM;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_STUDENT_YEAR;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.planner.testutil.Assert.assertThrows;

import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.model.Model;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_ALICE = "Alice";
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_DEGREE_YEAR = PREFIX_STUDENT_YEAR + "1";
    public static final String VALID_SEMESTER = PREFIX_STUDENT_SEM + "st1";
    public static final String VALID_MAJOR_CS = "CS";
    public static final String VALID_MAJOR_CS_LOWER = "cs";
    public static final String VALID_MAJOR_IS = "IS";

    public static final String TIMETABLE_DESC = " " + VALID_DEGREE_YEAR + " " + VALID_SEMESTER;
    public static final String NAME_DESC_ALICE = " " + PREFIX_NAME + VALID_NAME_ALICE;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String MAJOR_DESC_CS = " " + PREFIX_MAJOR + VALID_MAJOR_CS;
    public static final String MAJOR_DESC_CS_LOWER = " " + PREFIX_MAJOR + VALID_MAJOR_CS_LOWER;
    public static final String MAJOR_DESC_IS = " " + PREFIX_MAJOR + VALID_MAJOR_IS;
    public static final String SEM_DESC = " " + VALID_SEMESTER;
    public static final String DEGREE_YEAR_DESC = " " + VALID_DEGREE_YEAR;

    public static final String INVALID_DEGREE_YEAR = PREFIX_STUDENT_YEAR + "7"; // degree years are from 0 to 6
    public static final String INVALID_SEM = PREFIX_STUDENT_SEM + "asdf";
    public static final String INVALID_MAJOR = PREFIX_MAJOR + "as df";

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_MAJOR_DESC = " " + PREFIX_MAJOR + "CS*"; // '*' not allowed in majors
    public static final String INVALID_TIMETABLE_SEM_DESC = " " + VALID_DEGREE_YEAR + " " + INVALID_SEM;
    public static final String INVALID_TIMETABLE_YEAR_DESC = " " + INVALID_DEGREE_YEAR + " " + INVALID_SEM;
    public static final String INVALID_TIMETABLE_EMPTY_DESC = " " + PREFIX_STUDENT_SEM + " " + PREFIX_STUDENT_SEM;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String INVALID_SUBCOMMAND = "xyfaskdfasdf";

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}.
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
     * - the execution of {@code command} is successful
     * - the {@code actualModel} matches {@code expectedModel}.
     */
    public static CommandResult assertCommandSuccess(Command command, Model actualModel, Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedModel, actualModel);
            return result;
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the planner book, filtered person list and selected person in {@code actualModel} remain unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
    }

}
