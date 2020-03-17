package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.OrderBook;
import seedu.address.model.order.NameContainsKeywordsPredicate;
import seedu.address.model.order.Order;
import seedu.address.testutil.EditOrderDescriptorBuilder;

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
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TIMESTAMP_AMY = "2020-01-13 2200";
    public static final String VALID_TIMESTAMP_BOB = "2019-12-02 1500";
    public static final String VALID_WAREHOUSE_AMY = "5 Toh Guan Rd E, #02-30 S608831";
    public static final String VALID_WAREHOUSE_BOB = "5 Toh Guan Rd E, #02-30 S608831";
    public static final String VALID_COMMENT_NIL = "NIL";
    public static final String VALID_COMMENT_INSTRUCTION = "Leave the parcel at the riser";
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
    public static final String DELIVERY_TIMESTAMP_DESC_AMY = " " + PREFIX_DELIVERY_TIMESTAMP + VALID_TIMESTAMP_AMY;
    public static final String DELIVERY_TIMESTAMP_DESC_BOB = " " + PREFIX_DELIVERY_TIMESTAMP + VALID_TIMESTAMP_BOB;
    public static final String WAREHOUSE_DESC_AMY = " " + PREFIX_WAREHOUSE + VALID_WAREHOUSE_AMY;
    public static final String WAREHOUSE_DESC_BOB = " " + PREFIX_WAREHOUSE + VALID_WAREHOUSE_BOB;
    public static final String COMMENT_DESC_NIL = " " + PREFIX_COMMENT + VALID_COMMENT_NIL;
    public static final String COMMENT_DESC_INSTRUCTION = " " + PREFIX_COMMENT + VALID_COMMENT_INSTRUCTION;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    // Required valid date and time, date or time only will not be accepted for timestamp
    // Date only
    public static final String INVALID_DELIVERY_TIMESTAMP_DATE_ONLY = " " + PREFIX_DELIVERY_TIMESTAMP + "2019-10-02";
    // Time only
    public static final String INVALID_DELIVERY_TIMESTAMP_TIME_ONLY = " " + PREFIX_DELIVERY_TIMESTAMP + "2315";
    // Invalid Date - 2019 is not a leap year
    public static final String INVALID_DELIVERY_TIMESTAMP_DATE = " " + PREFIX_DELIVERY_TIMESTAMP + "2019-02-32 1500";
    // Invalid Time
    public static final String INVALID_DELIVERY_TIMESTAMP_TIME = " " + PREFIX_DELIVERY_TIMESTAMP + "2019-10-02 2401";
    public static final String INVALID_WAREHOUSE_DESC = " " + PREFIX_WAREHOUSE + ""; // empty string not allowed
    public static final String INVALID_COMMENT_DESC = " " + PREFIX_COMMENT; // empty string not allowed for comment
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditOrderDescriptor DESC_AMY;
    public static final EditCommand.EditOrderDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditOrderDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTimeStamp(VALID_TIMESTAMP_AMY).withWarehouse(VALID_WAREHOUSE_AMY)
                .withComment(VALID_COMMENT_INSTRUCTION).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditOrderDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTimeStamp(VALID_TIMESTAMP_BOB).withWarehouse(VALID_WAREHOUSE_BOB)
                .withComment(VALID_COMMENT_INSTRUCTION).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
     * - the order book, filtered order list and selected order in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        OrderBook expectedAddressBook = new OrderBook(actualModel.getOrderBook());
        List<Order> expectedFilteredList = new ArrayList<>(actualModel.getFilteredOrderList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getOrderBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredOrderList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the order at the given {@code targetIndex} in the
     * {@code model}'s order book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredOrderList().size());

        Order order = model.getFilteredOrderList().get(targetIndex.getZeroBased());
        final String[] splitName = order.getName().fullName.split("\\s+");
        model.updateFilteredOrderList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredOrderList().size());
    }

}
