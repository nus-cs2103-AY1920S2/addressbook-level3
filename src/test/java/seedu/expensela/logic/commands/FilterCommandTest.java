package seedu.expensela.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.commons.core.Messages.MESSAGE_TRANSACTION_LISTED_OVERVIEW;
import static seedu.expensela.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.expensela.testutil.TypicalTransactions.APPLES;
import static seedu.expensela.testutil.TypicalTransactions.BONUS;
import static seedu.expensela.testutil.TypicalTransactions.CAR_GAS;
import static seedu.expensela.testutil.TypicalTransactions.DOMINOS;
import static seedu.expensela.testutil.TypicalTransactions.ELECTRICITY;
import static seedu.expensela.testutil.TypicalTransactions.FLOWERS;
import static seedu.expensela.testutil.TypicalTransactions.GRAB;
import static seedu.expensela.testutil.TypicalTransactions.getTypicalExpenseLa;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.expensela.model.GlobalData;
import seedu.expensela.model.Model;
import seedu.expensela.model.ModelManager;
import seedu.expensela.model.UserPrefs;
import seedu.expensela.model.transaction.CategoryEqualsKeywordPredicate;
import seedu.expensela.model.transaction.DateEqualsKeywordPredicate;

class FilterCommandTest {

    private Model model = new ModelManager(getTypicalExpenseLa(), new UserPrefs(), new GlobalData());
    private Model expectedModel = new ModelManager(getTypicalExpenseLa(), new UserPrefs(), new GlobalData());

    @Test
    void equals() {
        CategoryEqualsKeywordPredicate categoryPredicate1 =
                new CategoryEqualsKeywordPredicate (Collections.singletonList("first"));
        DateEqualsKeywordPredicate datePredicate1 =
                new DateEqualsKeywordPredicate (Collections.singletonList("first"));
        CategoryEqualsKeywordPredicate categoryPredicate2 =
                new CategoryEqualsKeywordPredicate (Collections.singletonList("second"));
        DateEqualsKeywordPredicate datePredicate2 =
                new DateEqualsKeywordPredicate (Collections.singletonList("second"));

        FilterCommand filterFirstCommand = new FilterCommand(categoryPredicate1, datePredicate1);
        FilterCommand filterSecondCommand = new FilterCommand(categoryPredicate2, datePredicate2);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(categoryPredicate1, datePredicate1);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different transaction -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noTransactionFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTION_LISTED_OVERVIEW, 0);
        CategoryEqualsKeywordPredicate categoryPredicate = prepareCategoryPredicate(" ");
        DateEqualsKeywordPredicate datePredicate = prepareDatePredicate(" ");
        FilterCommand command = new FilterCommand(categoryPredicate, datePredicate);
        expectedModel.updateFilteredTransactionList(categoryPredicate, datePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTransactionList());
    }

    @Test
    public void execute_invalidCategoryValidMonth_noTransactionFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTION_LISTED_OVERVIEW, 0);
        CategoryEqualsKeywordPredicate categoryPredicate = prepareCategoryPredicate("FOODS");
        DateEqualsKeywordPredicate datePredicate = prepareDatePredicate("2020-03");
        FilterCommand command = new FilterCommand(categoryPredicate, datePredicate);
        expectedModel.updateFilteredTransactionList(categoryPredicate, datePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTransactionList());
    }

    @Test
    public void execute_invalidMonthValidCategory_noTransactionFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTION_LISTED_OVERVIEW, 0);
        CategoryEqualsKeywordPredicate categoryPredicate = prepareCategoryPredicate("FOOD");
        DateEqualsKeywordPredicate datePredicate = prepareDatePredicate("2020-13");
        FilterCommand command = new FilterCommand(categoryPredicate, datePredicate);
        expectedModel.updateFilteredTransactionList(categoryPredicate, datePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTransactionList());
    }

    @Test
    public void execute_invalidYearValidCategory_noTransactionFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTION_LISTED_OVERVIEW, 0);
        CategoryEqualsKeywordPredicate categoryPredicate = prepareCategoryPredicate("FOOD");
        DateEqualsKeywordPredicate datePredicate = prepareDatePredicate("1899-12");
        FilterCommand command = new FilterCommand(categoryPredicate, datePredicate);
        expectedModel.updateFilteredTransactionList(categoryPredicate, datePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredTransactionList());
    }

    @Test
    public void execute_validKeywords_transactionFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTION_LISTED_OVERVIEW, 1);
        CategoryEqualsKeywordPredicate categoryPredicate = prepareCategoryPredicate("FOOD");
        DateEqualsKeywordPredicate datePredicate = prepareDatePredicate("2020-03");
        FilterCommand command = new FilterCommand(categoryPredicate, datePredicate);
        expectedModel.updateFilteredTransactionList(categoryPredicate, datePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DOMINOS), model.getFilteredTransactionList());
    }

    @Test
    public void execute_allMonthValidCategory_transactionFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTION_LISTED_OVERVIEW, 1);
        CategoryEqualsKeywordPredicate categoryPredicate = prepareCategoryPredicate("FOOD");
        DateEqualsKeywordPredicate datePredicate = prepareDatePredicate("ALL");
        FilterCommand command = new FilterCommand(categoryPredicate, datePredicate);
        expectedModel.updateFilteredTransactionList(categoryPredicate, datePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DOMINOS), model.getFilteredTransactionList());
    }

    @Test
    public void execute_allCategoriesValidMonth_multipleTransactionsFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTION_LISTED_OVERVIEW, 3);
        CategoryEqualsKeywordPredicate categoryPredicate = prepareCategoryPredicate("ALL");
        DateEqualsKeywordPredicate datePredicate = prepareDatePredicate("2020-02");
        FilterCommand command = new FilterCommand(categoryPredicate, datePredicate);
        expectedModel.updateFilteredTransactionList(categoryPredicate, datePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CAR_GAS, BONUS, APPLES), model.getFilteredTransactionList());
    }

    @Test
    public void execute_allCategoriesAllMonths_allTransactionsFound() {
        String expectedMessage = String.format(MESSAGE_TRANSACTION_LISTED_OVERVIEW, 7);
        CategoryEqualsKeywordPredicate categoryPredicate = prepareCategoryPredicate("ALL");
        DateEqualsKeywordPredicate datePredicate = prepareDatePredicate("ALL");
        FilterCommand command = new FilterCommand(categoryPredicate, datePredicate);
        expectedModel.updateFilteredTransactionList(categoryPredicate, datePredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GRAB, FLOWERS, ELECTRICITY, DOMINOS, CAR_GAS, BONUS, APPLES),
                model.getFilteredTransactionList());
    }


    private CategoryEqualsKeywordPredicate prepareCategoryPredicate(String userInput) {
        return new CategoryEqualsKeywordPredicate(Arrays.asList(userInput));
    }

    private DateEqualsKeywordPredicate prepareDatePredicate(String userInput) {
        return new DateEqualsKeywordPredicate(Arrays.asList(userInput));
    }

}
