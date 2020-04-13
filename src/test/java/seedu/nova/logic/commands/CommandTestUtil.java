package seedu.nova.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_WEEK;
import static seedu.nova.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.nova.commons.core.index.Index;
import seedu.nova.logic.commands.abcommands.AbEditCommand;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.model.AddressBook;
import seedu.nova.model.Model;
import seedu.nova.model.person.NameContainsKeywordsPredicate;
import seedu.nova.model.person.Person;
import seedu.nova.testutil.EditPersonDescriptorBuilder;

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
    public static final String VALID_TAG_TEAMMATE = "teammate";
    public static final String VALID_TAG_CLASSMATE = "classmate";
    public static final String VALID_TAG_HUSBAND = "teammate";
    public static final String VALID_TAG_FRIEND = "classmate";
    public static final String VALID_PROJECTIP = "ip";
    public static final String VALID_PROJECTTP = "tp";
    public static final String VALID_WEEK = "1";
    public static final String VALID_WEEK_2 = "2";
    public static final String VALID_TASK = "1";
    public static final String VALID_TASK2 = "2";
    public static final String VALID_TASKDESC = "Example task description";
    public static final String VALID_TASKDESC_2 = "Example task description 2";
    public static final String VALID_NOTE = "Example note";
    public static final String VALID_NOTE2 = "Example note2";

    public static final String VALID_CONSULTATION_DESC = "Design Principles";
    public static final String VALID_CONSULTATION_DESC_2 = "Sequence Diagrams";
    public static final String VALID_CONS_VENUE = "COM2-0203";
    public static final String VALID_CONS_TIME_DATE = "2020-03-20 16:00 17:00";

    public static final String VALID_MEETING_DESC = "Project Meeting";
    public static final String VALID_MEETING_DESC_2 = "Meeting for GUI";
    public static final String VALID_MEETING_VENUE = "COM1-B108";
    public static final String VALID_MEETING_TIME_DATE = "2020-03-09 14:00 15:00";

    public static final String VALID_STUDY_DESC = "UML Diagrams";
    public static final String VALID_STUDY_DESC_2 = "Software Design";
    public static final String VALID_STUDY_VENUE = "Home";
    public static final String VALID_STUDY_TIME_DATE = "2020-03-12 11:00 12:00";

    public static final String VALID_LESSON_DESC = "CS2103T Tutorial";
    public static final String VALID_LESSON_DESC_2 = "CS2103T Lecture";
    public static final String VALID_LESSON_VENUE = "COM1 B1-03";
    public static final String VALID_LESSON_TIME = "Friday 10:00 11:00";

    public static final String VALID_VENUE_2 = "COM1-0201";
    public static final String VALID_TIME_DATE_2 = "2020-03-20 12:00 13:00";
    public static final String VALID_TIME_2 = "Friday 12:00 13:00";


    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String TAG_DESC_CLASSMATE = " " + PREFIX_CATEGORY + VALID_TAG_CLASSMATE;
    public static final String TAG_DESC_TEAMMATE = " " + PREFIX_CATEGORY + VALID_TAG_TEAMMATE;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_CATEGORY + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_CATEGORY + VALID_TAG_HUSBAND;
    public static final String PROJECT_IP = " " + PREFIX_PROJECT + VALID_PROJECTIP;
    public static final String PROJECT_TP = " " + PREFIX_PROJECT + VALID_PROJECTTP;
    public static final String WEEK_ONE = " " + PREFIX_WEEK + VALID_WEEK;
    public static final String WEEK_TWO = " " + PREFIX_WEEK + VALID_WEEK_2;
    public static final String PT_TASK = " " + PREFIX_TASK + VALID_TASK;
    public static final String PT_TASK2 = " " + PREFIX_TASK + VALID_TASK2;
    public static final String PT_TASKDESC = " " + PREFIX_DESC + VALID_TASKDESC;
    public static final String PT_TASKDESC_2 = " " + PREFIX_DESC + VALID_TASKDESC_2;
    public static final String PT_NOTE = " " + PREFIX_DESC + VALID_NOTE;
    public static final String PT_NOTE2 = " " + PREFIX_DESC + VALID_NOTE2;

    public static final String CONSULTATION_DESC = " " + PREFIX_DESC + VALID_CONSULTATION_DESC;
    public static final String CONSULTATION_DESC_2 = " " + PREFIX_DESC + VALID_CONSULTATION_DESC_2;
    public static final String CONS_VENUE = " " + PREFIX_VENUE + VALID_CONS_VENUE;
    public static final String CONS_TIME_DATE = " " + PREFIX_TIME + VALID_CONS_TIME_DATE;

    public static final String MEETING_DESC = " " + PREFIX_DESC + VALID_MEETING_DESC;
    public static final String MEETING_DESC_2 = " " + PREFIX_DESC + VALID_MEETING_DESC_2;
    public static final String MEETING_VENUE = " " + PREFIX_VENUE + VALID_MEETING_VENUE;
    public static final String MEETING_TIME_DATE = " " + PREFIX_TIME + VALID_MEETING_TIME_DATE;

    public static final String STUDY_DESC = " " + PREFIX_DESC + VALID_STUDY_DESC;
    public static final String STUDY_DESC_2 = " " + PREFIX_DESC + VALID_STUDY_DESC_2;
    public static final String STUDY_VENUE = " " + PREFIX_VENUE + VALID_STUDY_VENUE;
    public static final String STUDY_TIME_DATE = " " + PREFIX_TIME + VALID_STUDY_TIME_DATE;

    public static final String LESSON_DESC = " " + PREFIX_DESC + VALID_LESSON_DESC;
    public static final String LESSON_DESC_2 = " " + PREFIX_DESC + VALID_LESSON_DESC_2;
    public static final String LESSON_VENUE = " " + PREFIX_VENUE + VALID_LESSON_VENUE;
    public static final String LESSON_TIME = " " + PREFIX_TIME + VALID_LESSON_TIME;

    public static final String VENUE_2 = " " + PREFIX_VENUE + VALID_VENUE_2;
    public static final String TIME_DATE_2 = " " + PREFIX_TIME + VALID_TIME_DATE_2;
    public static final String TIME_2 = " " + PREFIX_TIME + VALID_TIME_2;


    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TAG_DESC = " " + PREFIX_CATEGORY + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_PROJECT = " " + PREFIX_PROJECT + "";
    public static final String INVALID_WEEK = " " + PREFIX_WEEK + "-1";
    public static final String INVALID_WEEK2 = " " + PREFIX_WEEK + "14";
    public static final String INVALID_TASK = " " + PREFIX_TASK + "0";
    public static final String INVALID_TASKDESC = " " + PREFIX_DESC + "";
    public static final String INVALID_NOTE = " " + PREFIX_DESC + "";

    public static final String INVALID_TIME_DATE_1 = " " + PREFIX_TIME + "2020-02-30 14:00 16:00";
    public static final String INVALID_TIME_DATE_2 = " " + PREFIX_TIME + "2020-02-20 17:00 16:00";
    public static final String INVALID_TIME_DATE_3 = " " + PREFIX_TIME + "2020-02-20 14:00";
    public static final String INVALID_TIME_DATE_4 = " " + PREFIX_TIME + "14:00 16:00";
    public static final String INVALID_TIME_DATE_5 = " " + PREFIX_TIME + "2020-02-20 14:00 25:00";

    public static final String INVALID_TIME_1 = " " + PREFIX_TIME + "freeday 14:00 16:00";
    public static final String INVALID_TIME_2 = " " + PREFIX_TIME + "Friday 17:00 16:00";
    public static final String INVALID_TIME_3 = " " + PREFIX_TIME + "Friday 14:00";
    public static final String INVALID_TIME_4 = " " + PREFIX_TIME + "14:00 16:00";
    public static final String INVALID_TIME_5 = " " + PREFIX_TIME + "Friday 14:00 25:00";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final AbEditCommand.EditPersonDescriptor DESC_AMY;
    public static final AbEditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withTags(VALID_TAG_CLASSMATE).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_TEAMMATE, VALID_TAG_CLASSMATE).build();
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
     * - the nova book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s nova book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

}
