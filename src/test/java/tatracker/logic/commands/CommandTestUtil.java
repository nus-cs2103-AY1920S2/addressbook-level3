package tatracker.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tatracker.logic.parser.Prefixes.EMAIL;
import static tatracker.logic.parser.Prefixes.GROUP;
import static tatracker.logic.parser.Prefixes.MATRIC;
import static tatracker.logic.parser.Prefixes.MODULE;
import static tatracker.logic.parser.Prefixes.NAME;
import static tatracker.logic.parser.Prefixes.PHONE;
import static tatracker.logic.parser.Prefixes.RATING;
import static tatracker.logic.parser.Prefixes.TAG;
import static tatracker.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import tatracker.commons.core.LogsCenter;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.logic.commands.student.EditStudentCommand;
import tatracker.model.Model;
import tatracker.model.TaTracker;
import tatracker.model.student.Student;
import tatracker.testutil.student.EditStudentDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_MATRIC_AMY = "A0123456J";
    public static final String VALID_MATRIC_BOB = "A0987654K";
    public static final int VALID_RATING_AMY = 5;
    public static final int VALID_RATING_BOB = 2;
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_MODULE_CS2030 = "CS2030";
    public static final String VALID_MODULE_CS2040 = "CS2040";
    public static final String VALID_GROUP_T04 = "T04";
    public static final String VALID_GROUP_L08 = "L08";

    public static final String NAME_DESC_AMY = " " + NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + EMAIL + VALID_EMAIL_BOB;
    public static final String MATRIC_DESC_AMY = " " + MATRIC + VALID_MATRIC_AMY;
    public static final String MATRIC_DESC_BOB = " " + MATRIC + VALID_MATRIC_BOB;
    public static final String RATING_DESC_AMY = " " + RATING + VALID_RATING_AMY;
    public static final String RATING_DESC_BOB = " " + RATING + VALID_RATING_BOB;
    public static final String TAG_DESC_FRIEND = " " + TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + TAG + VALID_TAG_HUSBAND;

    public static final String MODULE_DESC_CS2030 = " " + MODULE + VALID_MODULE_CS2030;
    public static final String MODULE_DESC_CS2040 = " " + MODULE + VALID_MODULE_CS2040;
    public static final String GROUP_DESC_T04 = " " + GROUP + VALID_GROUP_T04;
    public static final String GROUP_DESC_L08 = " " + GROUP + VALID_GROUP_L08;

    public static final String INVALID_NAME_DESC = " " + NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_MATRIC_DESC = " " + MATRIC + "!0123456&"; // '&' not allow in matrics
    public static final String INVALID_RATING_DESC = " " + RATING + "!0123456&"; // rating
    public static final String INVALID_TAG_DESC = " " + TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditStudentCommand.EditStudentDescriptor DESC_AMY;
    public static final EditStudentCommand.EditStudentDescriptor DESC_BOB;

    private static final Logger logger = LogsCenter.getLogger(CommandTestUtil.class);

    static {
        DESC_AMY = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
            logger.warning(ce.getMessage());
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, Action.NONE);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}, and an {@code expectedAction}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                                   Model expectedModel, Action expectedAction) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, expectedAction);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the ta-tracker, filtered student list and selected student in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TaTracker expectedTaTracker = new TaTracker(actualModel.getTaTracker());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedTaTracker, actualModel.getTaTracker());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
    }

    //@@author Chuayijing
    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertFilterSessionCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                                         Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, Action.FILTER_SESSION);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }


}
