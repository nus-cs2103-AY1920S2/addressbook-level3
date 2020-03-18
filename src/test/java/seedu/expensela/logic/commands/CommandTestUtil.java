package seedu.expensela.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.logic.parser.CliSyntax.*;
import static seedu.expensela.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.expensela.commons.core.index.Index;
import seedu.expensela.logic.commands.exceptions.CommandException;
import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.Model;
import seedu.expensela.model.transaction.NameContainsKeywordsPredicate;
import seedu.expensela.model.transaction.Transaction;
import seedu.expensela.testutil.EditTransactionDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_PIZZA = "Amy Bee";
    public static final String VALID_NAME_AIRPODS = "Bob Choo";
    public static final String VALID_AMOUNT_PIZZA = "23";
    public static final String VALID_AMOUNT_AIRPODS = "188";
    public static final String VALID_DATE_PIZZA = "2020-02-03";
    public static final String VALID_DATE_AIRPODS = "2020-02-19";
    public static final String VALID_REMARK_PIZZA = "Treat myself";
    public static final String VALID_REMARK_AIRPODS = "Bought to replace old earphones";
    public static final String VALID_CATEGORY_FOOD = "FOOD";
    public static final String VALID_CATEGORY_SHOPPING = "SHOPPING";

    public static final String NAME_DESC_PIZZA = " " + PREFIX_NAME + VALID_NAME_PIZZA;
    public static final String NAME_DESC_AIRPODS = " " + PREFIX_NAME + VALID_NAME_AIRPODS;
    public static final String AMOUNT_DESC_PIZZA = " " + PREFIX_AMOUNT + VALID_AMOUNT_PIZZA;
    public static final String AMOUNT_DESC_AIRPODS = " " + PREFIX_AMOUNT + VALID_AMOUNT_AIRPODS;
    public static final String DATE_DESC_PIZZA = " " + PREFIX_DATE + VALID_DATE_PIZZA;
    public static final String DATE_DESC_AIRPODS = " " + PREFIX_DATE + VALID_DATE_AIRPODS;
    public static final String REMARK_DESC_PIZZA = " " + PREFIX_REMARK + VALID_REMARK_PIZZA;
    public static final String REMARK_DESC_AIRPODS = " " + PREFIX_REMARK + VALID_REMARK_AIRPODS;
    public static final String CATEGORY_DESC_SHOPPING = " " + PREFIX_CATEGORY + VALID_CATEGORY_SHOPPING;
    public static final String CATEGORY_DESC_FOOD = " " + PREFIX_CATEGORY + VALID_CATEGORY_FOOD;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "911a"; // 'a' not allowed in amount
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_REMARK_DESC = " " + PREFIX_REMARK; // empty string not allowed for addresses
    public static final String INVALID_CATEGORY_DESC = " " + PREFIX_CATEGORY + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.editTransaction DESC_PIZZA;
    public static final EditCommand.editTransaction DESC_AIRPODS;

    static {
        DESC_PIZZA = new EditTransactionDescriptorBuilder().withName(VALID_NAME_PIZZA)
                .withPhone(VALID_AMOUNT_PIZZA).withAddress(VALID_REMARK_PIZZA)
                .build();
        DESC_AIRPODS = new EditTransactionDescriptorBuilder().withName(VALID_NAME_AIRPODS)
                .withPhone(VALID_AMOUNT_AIRPODS).withAddress(VALID_REMARK_AIRPODS)
                .build();
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
     * - the address book, filtered transaction list and selected transaction in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
<<<<<<< HEAD
        ExpenseLa expectedExpenseLa = new ExpenseLa(actualModel.getExpenseLa());
        List<Transaction> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTransactionList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedExpenseLa, actualModel.getExpenseLa());
        assertEquals(expectedFilteredList, actualModel.getFilteredTransactionList());
=======
        ExpenseLa expectedExpenseLa = new ExpenseLa(actualModel.getExpenseLa());
        List<Transaction> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTransactionList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedExpenseLa, actualModel.getExpenseLa());
        assertEquals(expectedFilteredList, actualModel.getFilteredTransactionList());
>>>>>>> c6bc62a37f9819291599e33e77159410c7f10d74
    }
    /**
     * Updates {@code model}'s filtered list to show only the transaction at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showTransactionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTransactionList().size());

        Transaction transaction = model.getFilteredTransactionList().get(targetIndex.getZeroBased());
        final String[] splitName = transaction.getName().transactionName.split("\\s+");
        model.updateFilteredTransactionList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTransactionList().size());
    }

}
