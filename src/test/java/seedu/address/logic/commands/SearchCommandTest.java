package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_ORDERS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_RETURN_ORDERS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalOrders.CARL;
import static seedu.address.testutil.TypicalOrders.ELLE;
import static seedu.address.testutil.TypicalOrders.FIONA;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalReturnOrders.CARL_RETURN;
import static seedu.address.testutil.TypicalReturnOrders.ELLE_RETURN;
import static seedu.address.testutil.TypicalReturnOrders.FIONA_RETURN;
import static seedu.address.testutil.TypicalReturnOrders.getTypicalReturnOrderBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.OrderContainsKeywordsPredicate;
import seedu.address.model.order.returnorder.ReturnOrderContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code SearchCommand}.
 */
public class SearchCommandTest {
    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());

    @Test
    public void equals() {
        OrderContainsKeywordsPredicate firstPredicate =
                new OrderContainsKeywordsPredicate(Collections.singletonList("first"));
        OrderContainsKeywordsPredicate secondPredicate =
                new OrderContainsKeywordsPredicate(Collections.singletonList("second"));

        SearchCommand findFirstCommand = new SearchCommand(firstPredicate);
        SearchCommand findSecondCommand = new SearchCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        SearchCommand findFirstCommandCopy = new SearchCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 0);
        OrderContainsKeywordsPredicate predicate = preparePredicate(" ");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredOrderList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 3);
        OrderContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredOrderList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredOrderList());
    }

    @Test
    public void execute_searchOnlyReturnOrder_returnCommandResultOfReturnOrder() {
        String expectedMessage = String.format(MESSAGE_RETURN_ORDERS_LISTED_OVERVIEW, 3);
        ReturnOrderContainsKeywordsPredicate predicate = prepareReturnPredicate("Kurz Elle Kunz");
        SearchCommand command = new SearchCommand(predicate);
        expectedModel.updateFilteredReturnOrderList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL_RETURN, ELLE_RETURN, FIONA_RETURN), model.getFilteredReturnOrderList());
    }

    @Test
    public void execute_searchBothReturnOrderAndOrder_returnCommandResultOfBothReturnOrderAndOrder() {
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW + System.lineSeparator()
            + MESSAGE_RETURN_ORDERS_LISTED_OVERVIEW, 3, 3);
        OrderContainsKeywordsPredicate orderPredicate = preparePredicate("Kurz Elle Kunz");
        ReturnOrderContainsKeywordsPredicate returnPredicate = prepareReturnPredicate("Kurz Elle Kunz");
        SearchCommand command = new SearchCommand(orderPredicate, returnPredicate);
        expectedModel.updateFilteredReturnOrderList(returnPredicate);
        expectedModel.updateFilteredOrderList(orderPredicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL_RETURN, ELLE_RETURN, FIONA_RETURN), model.getFilteredReturnOrderList());
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredOrderList());
    }

    /**
     * Parses {@code userInput} into a {@code OrderContainsKeywordsPredicate}.
     */
    private OrderContainsKeywordsPredicate preparePredicate(String userInput) {
        return new OrderContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code ReturnOrderContainsKeywordsPredicate}.
     */
    private ReturnOrderContainsKeywordsPredicate prepareReturnPredicate(String userInput) {
        return new ReturnOrderContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
