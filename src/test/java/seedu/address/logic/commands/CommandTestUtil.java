package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COSTPRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_THRESHOLD;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.customer.EditCustomerCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.product.EditProductCommand;
import seedu.address.model.InventorySystem;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.NameContainsKeywordsPredicate;
import seedu.address.model.product.DescriptionContainsKeywordsPredicate;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.CustomerContainsKeywordPredicate;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.QuantityThreshold;
import seedu.address.testutil.customer.EditCustomerDescriptorBuilder;
import seedu.address.testutil.product.EditProductDescriptorBuilder;

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
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_DESCRIPTION_WATCH = "Black watch";
    public static final String VALID_DESCRIPTION_BAG = "Black bag";
    public static final String VALID_COSTPRICE_WATCH = "249";
    public static final String VALID_COSTPRICE_BAG = "149";
    public static final String VALID_PRICE_WATCH = "11";
    public static final String VALID_PRICE_BAG = "22";
    public static final String VALID_QUANTITY_WATCH = "1";
    public static final String VALID_QUANTITY_BAG = "2";
    public static final String VALID_SALES_WATCH = "22";
    public static final String VALID_SALES_BAG = "44";
    public static final String VALID_THRESHOLD_WATCH = "20";
    public static final String VALID_THRESHOLD_BAG = "12";

    public static final String VALID_CUSTOMER_INDEX_AMY = "1";
    public static final String VALID_PRODUCT_INDEX_BAG = "1";
    public static final String VALID_DATETIME_AMY_BAG = "2020-03-01 10:00";
    public static final String VALID_QUANTITY_AMY_BAG = "1";
    public static final String VALID_MONEY_AMY_BAG = "30";
    public static final String VALID_DESCRIPTION_AMY_BAG = "under discount";

    public static final String INVALID_CUSTOMER_INDEX_AMY = "-1";
    public static final String INVALID_PRODUCT_INDEX_BAG = "0";
    public static final String INVALID_DATETIME_AMY_BAG = "2020-03-0110:00";
    public static final String INVALID_MONEY_AMY_BAG = "-30";

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

    public static final String DESCRIPTION_DESC_BAG = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BAG;
    public static final String DESCRIPTION_DESC_WATCH = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_WATCH;
    public static final String COSTPRICE_DESC_BAG = " " + PREFIX_COSTPRICE + VALID_COSTPRICE_BAG;
    public static final String COSTPRICE_DESC_WATCH = " " + PREFIX_COSTPRICE + VALID_COSTPRICE_WATCH;
    public static final String PRICE_DESC_BAG = " " + PREFIX_PRICE + VALID_PRICE_BAG;
    public static final String PRICE_DESC_WATCH = " " + PREFIX_PRICE + VALID_PRICE_WATCH;
    public static final String QUANTITY_DESC_BAG = " " + PREFIX_QUANTITY + VALID_QUANTITY_BAG;
    public static final String QUANTITY_DESC_WATCH = " " + PREFIX_QUANTITY + VALID_QUANTITY_WATCH;
    public static final String SALES_DESC_BAG = " " + PREFIX_SALES + VALID_SALES_BAG;
    public static final String SALES_DESC_WATCH = " " + PREFIX_SALES + VALID_SALES_WATCH;
    public static final String THRESHOLD_DESC_BAG = " " + PREFIX_THRESHOLD + VALID_THRESHOLD_BAG;
    public static final String THRESHOLD_DESC_WATCH = " " + PREFIX_THRESHOLD + VALID_THRESHOLD_WATCH;

    public static final String CUSTOMER_INDEX_DESC_AMY_BAG = " " + PREFIX_CUSTOMER + VALID_CUSTOMER_INDEX_AMY;
    public static final String PRODUCT_INDEX_DESC_AMY_BAG = " " + PREFIX_PRODUCT + VALID_PRODUCT_INDEX_BAG;
    public static final String DATETIME_DESC_AMY_BAG = " " + PREFIX_DATETIME + VALID_DATETIME_AMY_BAG;
    public static final String QUANTITY_DESC_AMY_BAG = " " + PREFIX_QUANTITY + VALID_QUANTITY_AMY_BAG;
    public static final String MONEY_DESC_AMY_BAG = " " + PREFIX_MONEY + VALID_MONEY_AMY_BAG;
    public static final String DESCRIPTION_DESC_AMY_BAG = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_AMY_BAG;

    public static final String CUSTOMER_NAME_DESC_AMY = " " + PREFIX_CUSTOMER + VALID_NAME_AMY;
    public static final String PRODUCT_DESCRIPTION_DESC_BAG = " " + PREFIX_PRODUCT + VALID_DESCRIPTION_BAG;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + "";
    public static final String INVALID_PRICE_DESC = " " + PREFIX_PRICE + "911a";
    public static final String INVALID_QUANTITY_DESC = " " + PREFIX_QUANTITY + "bob!yahoo";
    public static final String INVALID_SALES_DESC = " " + PREFIX_SALES;

    public static final String INVALID_CUSTOMER_INDEX_DESC = " " + PREFIX_CUSTOMER + INVALID_CUSTOMER_INDEX_AMY;
    public static final String INVALID_PRODUCT_INDEX_DESC = " " + PREFIX_PRODUCT + INVALID_PRODUCT_INDEX_BAG;
    public static final String INVALID_DATETIME_DESC = " " + PREFIX_DATETIME + INVALID_DATETIME_AMY_BAG;
    public static final String INVALID_MONEY_DESC = " " + PREFIX_MONEY + INVALID_MONEY_AMY_BAG;

    public static final String INVALID_CUSTOMER_NAME_DESC = " " + PREFIX_CUSTOMER + "James&";
    public static final String INVALID_PRODUCT_DESCRIPTION_DESC = " " + PREFIX_PRODUCT + "";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCustomerCommand.EditCustomerDescriptor DESC_AMY;
    public static final EditCustomerCommand.EditCustomerDescriptor DESC_BOB;

    public static final EditProductCommand.EditProductDescriptor DESC_BAG;
    public static final EditProductCommand.EditProductDescriptor DESC_WATCH;

    public static final QuantityThreshold THRESHOLD_BAG;
    public static final QuantityThreshold THRESHOLD_WATCH;

    static {
        DESC_AMY = new EditCustomerDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditCustomerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_BAG = new EditProductDescriptorBuilder().withDescription(VALID_DESCRIPTION_BAG)
                .withCostPrice(VALID_COSTPRICE_BAG).withPrice(VALID_PRICE_BAG).withQuantity(VALID_QUANTITY_BAG)
                .withSales(VALID_SALES_BAG).withThreshold(VALID_THRESHOLD_BAG).build();
        DESC_WATCH = new EditProductDescriptorBuilder().withDescription(VALID_DESCRIPTION_WATCH)
                .withCostPrice(VALID_COSTPRICE_WATCH).withPrice(VALID_PRICE_WATCH).withQuantity(VALID_QUANTITY_WATCH)
                .withSales(VALID_SALES_WATCH).withThreshold(VALID_THRESHOLD_WATCH).build();
        THRESHOLD_BAG = new QuantityThreshold(VALID_THRESHOLD_BAG);
        THRESHOLD_WATCH = new QuantityThreshold(VALID_THRESHOLD_WATCH);
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
     * - the address book, filtered customer list and selected customer in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        InventorySystem expectedAddressBook = new InventorySystem(actualModel.getInventorySystem());
        List<Customer> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCustomerList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getInventorySystem());
        assertEquals(expectedFilteredList, actualModel.getFilteredCustomerList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the customer at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCustomerList().size());

        Customer customer = model.getFilteredCustomerList().get(targetIndex.getZeroBased());
        final String[] splitName = customer.getName().fullName.split("\\s+");
        model.updateFilteredCustomerList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCustomerList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the product at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showProductAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredProductList().size());

        Product product = model.getFilteredProductList().get(targetIndex.getZeroBased());
        final String[] splitName = product.getDescription().value.split("\\s+");
        model.updateFilteredProductList(new DescriptionContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredProductList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the transaction at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showTransactionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTransactionList().size());

        Transaction transaction = model.getFilteredTransactionList().get(targetIndex.getZeroBased());
        final String[] splitName = transaction.getCustomer().getName().toString().split("\\s+");
        model.updateFilteredTransactionList(new CustomerContainsKeywordPredicate(Arrays.asList(splitName[0])));
    }

}
