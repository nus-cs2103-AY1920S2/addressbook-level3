package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.people.PeopleEditCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.PeopleNamePredicate;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.DescriptionContainsKeywordsPredicate;
import seedu.address.model.transaction.Transaction;
import seedu.address.testutil.EditPersonDescriptorBuilder;

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
    public static final String VALID_DESC_AMY = "Chicken Rice";
    public static final String VALID_DESC_BOB = "iPhone";
    public static final String VALID_AMOUNT_AMY = "5";
    public static final String VALID_AMOUNT_BOB = "10";
    public static final String VALID_DATE_AMY = "02/02/2020";
    public static final String VALID_TRANSACTION_INDEX_AMY = "1";
    public static final String VALID_TAG_DEBT = "Debt";
    public static final String VALID_TAG_FOOD = "Food";
    public static final String VALID_TAG_SHOPPING = "Shopping";
    public static final String VALID_DATE = "20/02/2020";
    public static final String VALID_AMOUNT = "1000";
    public static final String VALID_MONTH = "09";
    public static final String VALID_YEAR = "2020";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String OWE_DESC_AMY = " " + PREFIX_NAME + VALID_DESC_AMY + " "
            + PREFIX_AMOUNT + VALID_AMOUNT_AMY + " " + PREFIX_DATE + VALID_DATE_AMY;
    public static final String OWE_DESC_BOB = " " + PREFIX_NAME + VALID_DESC_BOB + " "
            + PREFIX_AMOUNT + VALID_AMOUNT_BOB;
    public static final String RETURNED_DESC_AMY =
            " " + PREFIX_TRANSACTION_INDEX + VALID_TRANSACTION_INDEX_AMY;
    public static final String LEND_DESC_AMY = " " + PREFIX_NAME + VALID_DESC_AMY + " "
            + PREFIX_AMOUNT + VALID_AMOUNT_AMY + " " + PREFIX_DATE + VALID_DATE_AMY;
    public static final String RECEIVED_DESC_AMY =
            " " + PREFIX_TRANSACTION_INDEX + VALID_TRANSACTION_INDEX_AMY;
    public static final String LEND_DESC_BOB = " " + PREFIX_NAME + VALID_DESC_BOB + " "
            + PREFIX_AMOUNT + VALID_AMOUNT_BOB;
    public static final String TAG_DESC_DEBT = " " + PREFIX_TAG + VALID_TAG_DEBT;
    public static final String TAG_DESC_FOOD = " " + PREFIX_TAG + VALID_TAG_FOOD;
    public static final String TAG_DESC_SHOPPING = " " + PREFIX_TAG + VALID_TAG_SHOPPING;

    public static final String VALID_DATE_DESC = " " + PREFIX_DATE + VALID_DATE;
    public static final String VALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + VALID_AMOUNT;
    public static final String VALID_MONTH_DESC = " " + PREFIX_MONTH + VALID_MONTH;
    public static final String VALID_YEAR_DESC = " " + PREFIX_YEAR + VALID_YEAR;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TRANSACTION_DESC = " " + PREFIX_NAME + ""; // description cannot be empty
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "9ab"; // only numbers allowed in amount
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "20 May 2020"; // date in wrong format
    public static final String INVALID_AMOUNT = " " + PREFIX_AMOUNT + "9ab"; // only numbers allowed in amount
    public static final String INVALID_DATE = " " + PREFIX_DATE + "20 May 2020"; // date in wrong format
    public static final String INVALID_TRANSACTION_INDEX =
            " " + PREFIX_TRANSACTION_INDEX + "-5"; // negative index
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "food*"; // '*' not allowed in tags
    public static final String INVALID_MONTH_DESC = " " + PREFIX_MONTH + "args"; // only numbers allowed
    public static final String INVALID_YEAR_DESC = " " + PREFIX_YEAR + "interstellar"; // only numbers allowed

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final PeopleEditCommand.EditPersonDescriptor DESC_AMY;
    public static final PeopleEditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(
            Command command, Model actualModel, CommandResult expectedCommandResult, Model expectedModel) {
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
    public static void assertCommandSuccess(Command command,
                                            Model actualModel,
                                            String expectedMessage,
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
        model.updateFilteredPersonList(new PeopleNamePredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the transaction at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showTransactionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTransactionList().size());

        Transaction transaction = model.getFilteredTransactionList().get(targetIndex.getZeroBased());
        final String[] splitDescription = transaction.getDescription().description.split("\\s+");
        model.updateFilteredTransactionList(new DescriptionContainsKeywordsPredicate(
                Arrays.asList(splitDescription[0])));

        assertEquals(1, model.getFilteredTransactionList().size());
    }
}
