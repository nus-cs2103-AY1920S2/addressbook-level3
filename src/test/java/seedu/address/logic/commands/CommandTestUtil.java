package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSULT_BEGIN_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSULT_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_LINK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.consults.EditConsultCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.reminders.EditReminderCommand;
import seedu.address.logic.commands.students.EditStudentCommand;
import seedu.address.model.Model;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentTAble;
import seedu.address.testutil.EditConsultDescriptorBuilder;
import seedu.address.testutil.EditReminderDescriptorBuilder;
import seedu.address.testutil.EditStudentDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_MATRICNUMBER_AMY = "A0112675A";
    public static final String VALID_MATRICNUMBER_BOB = "A0125494F";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String MATRICNUMBER_DESC_AMY = " " + PREFIX_MATRIC_NUMBER + VALID_MATRICNUMBER_AMY;
    public static final String MATRICNUMBER_DESC_BOB = " " + PREFIX_MATRIC_NUMBER + VALID_MATRICNUMBER_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_MATRICNUMBER_DESC = " " + PREFIX_MATRIC_NUMBER + "111111";
    // matric numbers is not all numeric
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    // consult valid entries
    public static final String VALID_START_TIME = "2020-10-03 15:00";
    public static final String VALID_END_TIME = "2020-10-03 17:00";
    public static final String VALID_PLACE = "SR1";
    public static final int VALID_INDEX = 1;
    public static final String VALID_START_TIME_2 = "2020-10-03 16:00";
    public static final String VALID_END_TIME_2 = "2020-10-03 19:00";
    public static final String VALID_PLACE_2 = "SR2";

    // consult invalid entries
    public static final String INVALID_DATE_TIME = "2020-10-03 26:00";
    public static final String INVALID_END_TIME_DIFFERENT_DATE = "2020-11-04 14:00";
    public static final String INVALID_DATE_TIME_PAST = "2019-03-03 15:00";
    public static final String INVALID_END_TIME_PAST = "2019-03-03 17:00";
    public static final String INVALID_PLACE = PREFIX_PLACE + "home!";
    public static final int INVALID_INDEX = 0;

    // consult valid command line inputs
    public static final String VALID_START_TIME_INPUT = " " + PREFIX_CONSULT_BEGIN_DATE_TIME + VALID_START_TIME;
    public static final String VALID_END_TIME_INPUT = " " + PREFIX_CONSULT_END_DATE_TIME + VALID_END_TIME;
    public static final String VALID_PLACE_INPUT = " " + PREFIX_PLACE + VALID_PLACE;
    public static final String VALID_STUDENT_INDEX_INPUT = " " + PREFIX_STUDENT + VALID_INDEX;

    // consult invalid command line inputs
    public static final String INVALID_START_TIME_INPUT = " " + PREFIX_CONSULT_BEGIN_DATE_TIME + INVALID_DATE_TIME;
    public static final String INVALID_END_TIME_INPUT = " " + PREFIX_CONSULT_END_DATE_TIME
        + INVALID_END_TIME_DIFFERENT_DATE;
    public static final String INVALID_PLACE_INPUT = " " + PREFIX_PLACE + INVALID_PLACE;
    public static final String INVALID_INDEX_INPUT = PREFIX_STUDENT + "-2";

    // ==================================== Mod related ===========================================
    // Mod valid entries
    public static final String VALID_MODCODE = "CS2103";
    public static final String VALID_MODNAME = "SOFTWARE ENGINEERING";
    public static final String VALID_MODNOTE = "SE is fun!!!";
    public static final String VALID_MODLINK = "http://www.comp.nus.edu.sg/~cs2103";
    public static final String VALID_MODLINK_NAME = "Module Website";

    // Mod invalid entries
    public static final String INVALID_MODCODE = "B1010"; // Not enough characters for department code
    public static final String INVALID_MODLINK = "coursemologyorg"; // Does not fit URL format

    // Mod valid parts
    public static final String VALID_MODCODE_INPUT = " " + PREFIX_MODULE_CODE + VALID_MODCODE;
    public static final String VALID_MODNAME_INPUT = " " + PREFIX_MODULE_NAME + VALID_MODNAME;
    public static final String VALID_MODNOTE_INPUT = " " + PREFIX_MODULE_NOTE + VALID_MODNOTE;
    public static final String VALID_MODLINK_INPUT = " " + PREFIX_MODULE_LINK + VALID_MODLINK;
    public static final String VALID_MODLINK_NAME_INPUT = " " + PREFIX_MODULE_LINK_NAME + VALID_MODLINK_NAME;

    // Mod invalid parts
    public static final String INVALID_MODCODE_INPUT = " " + PREFIX_MODULE_CODE + "B1010";
    public static final String INVALID_MODLINK_INPUT = " " + PREFIX_MODULE_LINK + "coursemologyorg";

    // ================================== Reminder related ========================================
    // reminder valid entries
    public static final String VALID_DESCRIPTION1 = "Return midterms paper to T02";
    public static final String VALID_DESCRIPTION2 = "Makeup tutorial for T03";
    public static final String VALID_DATE1 = "2022-03-18";
    public static final String VALID_DATE2 = "2022-04-10";
    public static final String VALID_TIME1 = "15:00";
    public static final String VALID_TIME2 = "18:00";

    // reminder invalid entries
    public static final String INVALID_DESCRIPTION = " ";
    public static final String INVALID_DATE = "2020-20-04";
    public static final String INVALID_TIME = "27:00";

    // reminder valid command line inputs
    public static final String VALID_DESCRIPTION_INPUT = " " + PREFIX_REMINDER_DESCRIPTION + VALID_DESCRIPTION1;
    public static final String VALID_DATE_INPUT = " " + PREFIX_REMINDER_DATE + VALID_DATE1;
    public static final String VALID_TIME_INPUT = " " + PREFIX_REMINDER_TIME + VALID_TIME1;

    // reminder invalid command line inputs
    public static final String INVALID_DESCRIPTION_INPUT = " " + PREFIX_REMINDER_DESCRIPTION + INVALID_DESCRIPTION;
    public static final String INVALID_DATE_INPUT = " " + PREFIX_REMINDER_DATE + INVALID_DATE;
    public static final String INVALID_TIME_INPUT = " " + PREFIX_REMINDER_TIME + INVALID_TIME;

    public static final EditReminderCommand.EditReminderDescriptor DESC_REMINDER1;
    public static final EditReminderCommand.EditReminderDescriptor DESC_REMINDER2;

    public static final EditStudentCommand.EditStudentDescriptor DESC_AMY;
    public static final EditStudentCommand.EditStudentDescriptor DESC_BOB;

    public static final EditConsultCommand.EditConsultDescriptor DESC_CONSULT1;
    public static final EditConsultCommand.EditConsultDescriptor DESC_CONSULT2;

    static {
        DESC_AMY = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withMatricNumber(VALID_MATRICNUMBER_AMY).withEmail(VALID_EMAIL_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withMatricNumber(VALID_MATRICNUMBER_BOB).withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    static {
        DESC_CONSULT1 = new EditConsultDescriptorBuilder().withBeginDateTime(VALID_START_TIME)
                .withEndDateTime(VALID_END_TIME).withLocation(VALID_PLACE).build();
        DESC_CONSULT2 = new EditConsultDescriptorBuilder().withBeginDateTime(VALID_START_TIME_2)
            .withEndDateTime(VALID_END_TIME_2).withLocation(VALID_PLACE_2).build();
    }

    static {
        DESC_REMINDER1 = new EditReminderDescriptorBuilder().withDescription(VALID_DESCRIPTION1)
                .withDate(VALID_DATE1).withTime(VALID_TIME1).build();
        DESC_REMINDER2 = new EditReminderDescriptorBuilder().withDescription(VALID_DESCRIPTION2)
                .withDate(VALID_DATE2).withTime(VALID_TIME2).build();
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
     * - TAble, filtered student list and selected student in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        StudentTAble expectedStudentTAble = new StudentTAble(actualModel.getStudentTAble());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedStudentTAble, actualModel.getStudentTAble());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the student at the given {@code targetIndex} in the
     * {@code model}'s TAble.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = model.getFilteredStudentList().get(targetIndex.getZeroBased());
        final String[] splitName = student.getName().fullName.split("\\s+");
        model.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredStudentList().size());
    }
}
