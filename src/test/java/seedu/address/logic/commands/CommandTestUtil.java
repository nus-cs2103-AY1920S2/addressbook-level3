package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUISINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ESTHOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUM_DAYS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPERATING_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RESTAURANT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISITED;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditAssignmentDescriptor;
import seedu.address.logic.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_BIRTHDAY_AMY = "05-31";
    public static final String VALID_BIRTHDAY_BOB = "08-25";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_REMARK_AMY = "Likes skiing.";
    public static final String VALID_REMARK_BOB = "Favourite pastime: Eating";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String BIRTHDAY_DESC_AMY = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_AMY;
    public static final String DELETE_TAG_DESC = " " + PREFIX_DELETE_TAG + VALID_TAG_FRIEND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_BIRTHDAY_DESC = " " + PREFIX_BIRTHDAY + "31-05"; // wrong format for date
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String VALID_TITLE_CS2103 = "CS2103 tP";
    public static final String VALID_DEADLINE_CS2103 = "2020-12-11 14:00";
    public static final String VALID_WORKLOAD_CS2103 = "11.5";
    public static final String VALID_STATUS_CS2103 = "Completed";
    public static final String VALID_TITLE_CS3243 = "CS3243 Tut";
    public static final String VALID_DEADLINE_CS3243 = "2020-11-11 14:00";
    public static final String VALID_WORKLOAD_CS3243 = "5";
    public static final String VALID_STATUS_CS3243 = "Uncompleted";

    public static final String INVALID_TITLE = "";
    public static final String INVALID_DEADLINE = "2020-16-05 13:00";
    public static final String INVALID_WORKLOAD = "ghjgj";
    public static final String INVALID_STATUS = "done";

    public static final String TITLE_DESC_CS2103 = " " + PREFIX_TITLE + VALID_TITLE_CS2103;
    public static final String DEADLINE_DESC_CS2103 = " " + PREFIX_DEADLINE + VALID_DEADLINE_CS2103;
    public static final String WORKLOAD_DESC_CS2103 = " " + PREFIX_ESTHOURS + VALID_WORKLOAD_CS2103;
    public static final String STATUS_DESC_CS2103 = " " + PREFIX_STATUS + VALID_STATUS_CS2103;
    public static final String TITLE_DESC_CS3243 = " " + PREFIX_TITLE + VALID_TITLE_CS3243;
    public static final String DEADLINE_DESC_CS3243 = " " + PREFIX_DEADLINE + VALID_DEADLINE_CS3243;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + INVALID_TITLE;
    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_DEADLINE + INVALID_DEADLINE;
    public static final String INVALID_WORKLOAD_DESC = " " + PREFIX_ESTHOURS + INVALID_WORKLOAD;
    public static final String INVALID_STATUS_DESC = " " + PREFIX_STATUS + INVALID_STATUS;

    public static final String VALID_NAME_AMEENS = "Ameens";
    public static final String VALID_LOCATION_AMEENS = "Clementi";
    public static final String VALID_HOURS_AMEENS = "0900:2300";
    public static final String VALID_PRICE_AMEENS = "$";
    public static final String VALID_CUISINE_AMEENS = "Indian";
    public static final String VALID_VISITED_AMEENS = "Yes";

    public static final String DESC_NAME_AMEENS = " " + PREFIX_RESTAURANT + VALID_NAME_AMEENS;
    public static final String DESC_LOCATION_AMEENS = " " + PREFIX_LOCATION + VALID_LOCATION_AMEENS;
    public static final String DESC_HOURS_AMEENS = " " + PREFIX_OPERATING_HOURS + VALID_HOURS_AMEENS;
    public static final String DESC_PRICE_AMEENS = " " + PREFIX_PRICE + VALID_PRICE_AMEENS;
    public static final String DESC_CUISINE_AMEENS = " " + PREFIX_CUISINE + VALID_CUISINE_AMEENS;
    public static final String DESC_VISITED_AMEENS = " " + PREFIX_VISITED + VALID_VISITED_AMEENS;

    public static final String VALID_NUM_DAYS = "5";
    public static final String INVALID_NUM_DAYS = "0";

    public static final String DESC_INVALID_NUM_DAYS = " " + PREFIX_NUM_DAYS + INVALID_NUM_DAYS;
    public static final String DESC_VALID_NUM_DAYS = " " + PREFIX_NUM_DAYS + VALID_NUM_DAYS;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditPersonDescriptor DESC_AMY;
    public static final EditPersonDescriptor DESC_BOB;
    public static final EditAssignmentDescriptor DESC_CS2103;
    public static final EditAssignmentDescriptor DESC_CS3243;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withTagsToAdd(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withBirthday(VALID_BIRTHDAY_BOB).withTagsToAdd(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        DESC_CS2103 = new EditAssignmentDescriptorBuilder().withTitle(VALID_TITLE_CS2103)
            .withDeadline(VALID_DEADLINE_CS2103).withStatus(VALID_STATUS_CS2103)
            .withWorkload(VALID_WORKLOAD_CS2103).build();
        DESC_CS3243 = new EditAssignmentDescriptorBuilder().withTitle(VALID_TITLE_CS3243)
            .withDeadline(VALID_DEADLINE_CS3243).withStatus(VALID_STATUS_CS3243)
            .withWorkload(VALID_WORKLOAD_CS3243).build();
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
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
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
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }
}
