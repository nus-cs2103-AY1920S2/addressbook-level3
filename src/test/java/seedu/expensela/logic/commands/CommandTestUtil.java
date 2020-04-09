package seedu.expensela.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.expensela.logic.parser.CliSyntax.PREFIX_REMARK;
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

    public static final String VALID_NAME_PIZZA = "Pepperoni Pizza";
    public static final String VALID_NAME_AIRPODS = "Apple Airpods";
    public static final String VALID_NAME_INVESTMENT = "Investment in Stocks";
    public static final String VALID_AMOUNT_PIZZA = "23.00";
    public static final String VALID_AMOUNT_AIRPODS = "188.00";
    public static final String VALID_AMOUNT_INVESTMENT = "999999.99";
    public static final String VALID_AMOUNT_INVESTMENT_1 = "0.01";
    public static final String VALID_DATE_PIZZA = "2020-02-03";
    public static final String VALID_DATE_AIRPODS = "2020-02-19";
    public static final String VALID_DATE_INVESTMENT = "2020-03-10";
    public static final String VALID_REMARK_PIZZA = "Treat myself";
    public static final String VALID_REMARK_AIRPODS = "Bought to replace old earphones";
    public static final String VALID_REMARK_INVESTMENT = "Bought shares in Singtel";
    public static final String VALID_CATEGORY_FOOD = "FOOD";
    public static final String VALID_CATEGORY_SHOPPING = "SHOPPING";
    public static final String VALID_CATEGORY_INVESTMENT = "UTILITIES";

    public static final String NAME_DESC_PIZZA = " " + PREFIX_NAME + VALID_NAME_PIZZA;
    public static final String NAME_DESC_AIRPODS = " " + PREFIX_NAME + VALID_NAME_AIRPODS;
    public static final String NAME_DESC_INVESTMENT = " " + PREFIX_NAME + VALID_NAME_INVESTMENT;
    public static final String AMOUNT_DESC_PIZZA = " " + PREFIX_AMOUNT + VALID_AMOUNT_PIZZA;
    public static final String AMOUNT_DESC_AIRPODS = " " + PREFIX_AMOUNT + VALID_AMOUNT_AIRPODS;
    public static final String AMOUNT_DESC_MAX_INVESTMENT = " " + PREFIX_AMOUNT + VALID_AMOUNT_INVESTMENT;
    public static final String AMOUNT_DESC_MIN_INVESTMENT = " " + PREFIX_AMOUNT + VALID_AMOUNT_INVESTMENT_1;
    public static final String DATE_DESC_PIZZA = " " + PREFIX_DATE + VALID_DATE_PIZZA;
    public static final String DATE_DESC_AIRPODS = " " + PREFIX_DATE + VALID_DATE_AIRPODS;
    public static final String DATE_DESC_INVESTMENT = " " + PREFIX_DATE + VALID_DATE_INVESTMENT;
    public static final String REMARK_DESC_PIZZA = " " + PREFIX_REMARK + VALID_REMARK_PIZZA;
    public static final String REMARK_DESC_AIRPODS = " " + PREFIX_REMARK + VALID_REMARK_AIRPODS;
    public static final String REMARK_DESC_INVESTMENT = " " + PREFIX_REMARK + VALID_REMARK_INVESTMENT;
    public static final String CATEGORY_DESC_SHOPPING = " " + PREFIX_CATEGORY + VALID_CATEGORY_SHOPPING;
    public static final String CATEGORY_DESC_FOOD = " " + PREFIX_CATEGORY + VALID_CATEGORY_FOOD;
    public static final String CATEGORY_DESC_INVESTMENT = " " + PREFIX_CATEGORY + VALID_CATEGORY_INVESTMENT;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + " "; // name cannot start with these
    public static final String INVALID_NAME_DESC_1 = " " + PREFIX_NAME + "#";
    public static final String INVALID_NAME_DESC_2 = " " + PREFIX_NAME + "%";
    public static final String INVALID_NAME_DESC_3 = " " + PREFIX_NAME + "$";
    public static final String INVALID_NAME_DESC_4 = " " + PREFIX_NAME + "^";
    public static final String INVALID_NAME_DESC_5 = " " + PREFIX_NAME + "&";
    public static final String INVALID_NAME_DESC_6 = " " + PREFIX_NAME + "*";
    public static final String INVALID_NAME_DESC_7 = " " + PREFIX_NAME + "(";
    public static final String INVALID_NAME_DESC_8 = " " + PREFIX_NAME + ")";
    public static final String INVALID_NAME_DESC_9 = " " + PREFIX_NAME + "-";
    public static final String INVALID_NAME_DESC_10 = " " + PREFIX_NAME + "_";
    public static final String INVALID_NAME_DESC_11 = " " + PREFIX_NAME + "+";
    public static final String INVALID_NAME_DESC_12 = " " + PREFIX_NAME + "=";
    public static final String INVALID_NAME_DESC_13 = " " + PREFIX_NAME + "!";
    public static final String INVALID_NAME_DESC_14 = " " + PREFIX_NAME + "@";
    public static final String INVALID_NAME_DESC_15 = " " + PREFIX_NAME + "<";
    public static final String INVALID_NAME_DESC_16 = " " + PREFIX_NAME + ">";
    public static final String INVALID_NAME_DESC_17 = " " + PREFIX_NAME + ",";
    public static final String INVALID_NAME_DESC_18 = " " + PREFIX_NAME + ".";
    public static final String INVALID_NAME_DESC_19 = " " + PREFIX_NAME + "?";
    public static final String INVALID_NAME_DESC_20 = " " + PREFIX_NAME + "/";

    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "911a"; // 'alphabet' not allowed in amount
    public static final String INVALID_AMOUNT_DESC_1 = " " + PREFIX_AMOUNT + "911#";
    public static final String INVALID_AMOUNT_DESC_2 = " " + PREFIX_AMOUNT + "911%";
    public static final String INVALID_AMOUNT_DESC_3 = " " + PREFIX_AMOUNT + "911$";
    public static final String INVALID_AMOUNT_DESC_4 = " " + PREFIX_AMOUNT + "91919^";
    public static final String INVALID_AMOUNT_DESC_5 = " " + PREFIX_AMOUNT + "123&";
    public static final String INVALID_AMOUNT_DESC_6 = " " + PREFIX_AMOUNT + "123*";
    public static final String INVALID_AMOUNT_DESC_7 = " " + PREFIX_AMOUNT + "123(";
    public static final String INVALID_AMOUNT_DESC_8 = " " + PREFIX_AMOUNT + "123)";
    public static final String INVALID_AMOUNT_DESC_9 = " " + PREFIX_AMOUNT + "123-";
    public static final String INVALID_AMOUNT_DESC_10 = " " + PREFIX_AMOUNT + "123_";
    public static final String INVALID_AMOUNT_DESC_11 = " " + PREFIX_AMOUNT + "123+";
    public static final String INVALID_AMOUNT_DESC_12 = " " + PREFIX_AMOUNT + "123=";
    public static final String INVALID_AMOUNT_DESC_13 = " " + PREFIX_AMOUNT + "213!";
    public static final String INVALID_AMOUNT_DESC_14 = " " + PREFIX_AMOUNT + "23@";
    public static final String INVALID_AMOUNT_DESC_15 = " " + PREFIX_AMOUNT + "123<";
    public static final String INVALID_AMOUNT_DESC_16 = " " + PREFIX_AMOUNT + "123>";
    public static final String INVALID_AMOUNT_DESC_17 = " " + PREFIX_AMOUNT + "123,";
    public static final String INVALID_AMOUNT_DESC_19 = " " + PREFIX_AMOUNT + "123?";
    public static final String INVALID_AMOUNT_DESC_20 = " " + PREFIX_AMOUNT + "123/";
    public static final String INVALID_AMOUNT_DESC_21 = " " + PREFIX_AMOUNT + "#123";
    public static final String INVALID_AMOUNT_DESC_22 = " " + PREFIX_AMOUNT + "%123";
    public static final String INVALID_AMOUNT_DESC_23 = " " + PREFIX_AMOUNT + "$123";
    public static final String INVALID_AMOUNT_DESC_24 = " " + PREFIX_AMOUNT + "^123";
    public static final String INVALID_AMOUNT_DESC_25 = " " + PREFIX_AMOUNT + "&123";
    public static final String INVALID_AMOUNT_DESC_26 = " " + PREFIX_AMOUNT + "*123";
    public static final String INVALID_AMOUNT_DESC_27 = " " + PREFIX_AMOUNT + "(123";
    public static final String INVALID_AMOUNT_DESC_28 = " " + PREFIX_AMOUNT + ")123";
    public static final String INVALID_AMOUNT_DESC_29 = " " + PREFIX_AMOUNT + "-123";
    public static final String INVALID_AMOUNT_DESC_30 = " " + PREFIX_AMOUNT + "_123";
    public static final String INVALID_AMOUNT_DESC_31 = " " + PREFIX_AMOUNT + "+123";
    public static final String INVALID_AMOUNT_DESC_32 = " " + PREFIX_AMOUNT + "=123";
    public static final String INVALID_AMOUNT_DESC_33 = " " + PREFIX_AMOUNT + "!123";
    public static final String INVALID_AMOUNT_DESC_34 = " " + PREFIX_AMOUNT + "@123";
    public static final String INVALID_AMOUNT_DESC_35 = " " + PREFIX_AMOUNT + "<123";
    public static final String INVALID_AMOUNT_DESC_36 = " " + PREFIX_AMOUNT + ">123";
    public static final String INVALID_AMOUNT_DESC_37 = " " + PREFIX_AMOUNT + ",123";
    public static final String INVALID_AMOUNT_DESC_39 = " " + PREFIX_AMOUNT + "?123";
    public static final String INVALID_AMOUNT_DESC_40 = " " + PREFIX_AMOUNT + "/123";
    public static final String INVALID_AMOUNT_DESC_41 = " " + PREFIX_AMOUNT + "1000000";
    public static final String INVALID_AMOUNT_DESC_42 = " " + PREFIX_AMOUNT + "0";

    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "2017/02/02"; // testing invalid dates
    public static final String INVALID_DATE_DESC_1 = " " + PREFIX_DATE + "2017-02/02";
    public static final String INVALID_DATE_DESC_2 = " " + PREFIX_DATE + "2017/02-02";
    public static final String INVALID_DATE_DESC_3 = " " + PREFIX_DATE + "2017/02-02";
    public static final String INVALID_DATE_DESC_4 = " " + PREFIX_DATE + "201700-02-02";
    public static final String INVALID_DATE_DESC_5 = " " + PREFIX_DATE + "2017-0200-02";
    public static final String INVALID_DATE_DESC_6 = " " + PREFIX_DATE + "2017-02-02000";
    public static final String INVALID_DATE_DESC_7 = " " + PREFIX_DATE + "2017-02-32";
    public static final String INVALID_DATE_DESC_8 = " " + PREFIX_DATE + "2017-13-12";
    public static final String INVALID_DATE_DESC_9 = " " + PREFIX_DATE + "2021-02-02"; // after today
    public static final String INVALID_DATE_DESC_10 = " " + PREFIX_DATE + "2022-12-12";
    public static final String INVALID_DATE_DESC_11 = " " + PREFIX_DATE + "2022-02-12";
    public static final String INVALID_DATE_DESC_12 = " " + PREFIX_DATE + "9999-02-12";


    public static final String INVALID_REMARK_DESC = " " + PREFIX_REMARK + "*"; // '*' is not allowed in remark

    public static final String INVALID_CATEGORY_DESC = " " + PREFIX_CATEGORY + "CLOTHES"; // 'CLOTHES' is not a category
    public static final String INVALID_CATEGORY_DESC_1 = " " + PREFIX_CATEGORY + "clothes";
    public static final String INVALID_CATEGORY_DESC_2 = " " + PREFIX_CATEGORY + "MUSIC";
    public static final String INVALID_CATEGORY_DESC_3 = " " + PREFIX_CATEGORY + "FOODS";
    public static final String INVALID_CATEGORY_DESC_4 = " " + PREFIX_CATEGORY + "Shoppings";
    public static final String INVALID_CATEGORY_DESC_5 = " " + PREFIX_CATEGORY + "Transports";
    public static final String INVALID_CATEGORY_DESC_6 = " " + PREFIX_CATEGORY + "grocery";
    public static final String INVALID_CATEGORY_DESC_7 = " " + PREFIX_CATEGORY + "healthy";
    public static final String INVALID_CATEGORY_DESC_8 = " " + PREFIX_CATEGORY + "miscellaneous";


    public static final String INVALID_FILTER_MONTH_PREDICATE_1 = "";
    public static final String INVALID_FILTER_MONTH_PREDICATE_2 = "    ";
    public static final String INVALID_FILTER_MONTH_PREDICATE_3 = "`";
    public static final String INVALID_FILTER_MONTH_PREDICATE_4 = "~";
    public static final String INVALID_FILTER_MONTH_PREDICATE_5 = "!";
    public static final String INVALID_FILTER_MONTH_PREDICATE_6 = "@";
    public static final String INVALID_FILTER_MONTH_PREDICATE_7 = "#";
    public static final String INVALID_FILTER_MONTH_PREDICATE_8 = "$";
    public static final String INVALID_FILTER_MONTH_PREDICATE_9 = "%";
    public static final String INVALID_FILTER_MONTH_PREDICATE_10 = "^";
    public static final String INVALID_FILTER_MONTH_PREDICATE_11 = "&";
    public static final String INVALID_FILTER_MONTH_PREDICATE_12 = "*";
    public static final String INVALID_FILTER_MONTH_PREDICATE_13 = "(";
    public static final String INVALID_FILTER_MONTH_PREDICATE_14 = ")";
    public static final String INVALID_FILTER_MONTH_PREDICATE_15 = "-";
    public static final String INVALID_FILTER_MONTH_PREDICATE_16 = "_";
    public static final String INVALID_FILTER_MONTH_PREDICATE_17 = "=";
    public static final String INVALID_FILTER_MONTH_PREDICATE_18 = "+";
    public static final String INVALID_FILTER_MONTH_PREDICATE_19 = "1900-13";
    public static final String INVALID_FILTER_MONTH_PREDICATE_20 = "JANUARY";
    public static final String INVALID_FILTER_MONTH_PREDICATE_21 = " 2020-01";
    public static final String INVALID_FILTER_MONTH_PREDICATE_22 = "2020-01 ";
    public static final String INVALID_FILTER_MONTH_PREDICATE_23 = "2020/01";
    public static final String INVALID_FILTER_MONTH_PREDICATE_24 = "1899-01";

    public static final String INVALID_FILTER_CATEGORY_PREDICATE_1 = "";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_2 = "    ";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_3 = "`";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_4 = "~";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_5 = "!";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_6 = "@";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_7 = "#";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_8 = "$";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_9 = "%";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_10 = "^";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_11 = "&";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_12 = "*";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_13 = "(";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_14 = ")";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_15 = "-";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_16 = "_";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_17 = "=";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_18 = "+";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_19 = " FOOD";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_20 = "FOOD ";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_21 = "food";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_22 = "ABCD";
    public static final String INVALID_FILTER_CATEGORY_PREDICATE_23 = "2020-01";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditTransactionDescriptor DESC_PIZZA;
    public static final EditCommand.EditTransactionDescriptor DESC_AIRPODS;

    static {
        DESC_PIZZA = new EditTransactionDescriptorBuilder().withName(VALID_NAME_PIZZA)
                .withAmount(VALID_AMOUNT_PIZZA).withDate(VALID_DATE_PIZZA)
                .withRemark(VALID_REMARK_PIZZA).withCategory(VALID_CATEGORY_FOOD)
                .build();
        DESC_AIRPODS = new EditTransactionDescriptorBuilder().withName(VALID_NAME_AIRPODS)
                .withAmount(VALID_AMOUNT_AIRPODS).withDate(VALID_DATE_AIRPODS)
                .withRemark(VALID_REMARK_AIRPODS).withCategory(VALID_CATEGORY_SHOPPING)
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
            assertEquals(expectedModel.getExpenseLa(), actualModel.getExpenseLa());
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
        ExpenseLa expectedExpenseLa = new ExpenseLa(actualModel.getExpenseLa());
        List<Transaction> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTransactionList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedExpenseLa, actualModel.getExpenseLa());
        assertEquals(expectedFilteredList, actualModel.getFilteredTransactionList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the transaction at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showTransactionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTransactionList().size());

        Transaction transaction = model.getFilteredTransactionList().get(targetIndex.getZeroBased());
        final String[] splitName = transaction.getName().transactionName.split("\\s+");
        model.updateFilteredTransactionList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])), null);

        assertEquals(1, model.getFilteredTransactionList().size());
    }

}
