package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showReturnOrderAtIndex;
import static seedu.address.logic.parser.CliSyntax.FLAG_FORCE_CLEAR;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalReturnOrders.getTypicalReturnOrderBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.Parcel.order.Order;
import seedu.address.model.Parcel.returnorder.ReturnOrder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {
    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredOrderList_success() {
        Order orderToDelete = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ORDER, FLAG_ORDER_BOOK);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete);

        ModelManager expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(),
                new UserPrefs());
        expectedModel.deleteOrder(orderToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredReturnList_success() {
        ReturnOrder returnOrderToDelete = model.getFilteredReturnOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ORDER, FLAG_RETURN_BOOK);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ORDER_SUCCESS, returnOrderToDelete);

        ModelManager expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(),
                new UserPrefs());
        expectedModel.deleteReturnOrder(returnOrderToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredOrderList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, FLAG_ORDER_BOOK);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexUnfilteredReturnList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReturnOrderList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, FLAG_RETURN_BOOK);
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredOrderList_success() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Order orderToDelete = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ORDER, FLAG_ORDER_BOOK);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete);

        Model expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        expectedModel.deleteOrder(orderToDelete);
        showNoOrder(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredReturnList_success() {
        showReturnOrderAtIndex(model, INDEX_FIRST_ORDER);

        ReturnOrder returnOrderToDelete = model.getFilteredReturnOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ORDER, FLAG_RETURN_BOOK);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_ORDER_SUCCESS, returnOrderToDelete);
        Model expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        expectedModel.deleteReturnOrder(returnOrderToDelete);
        showNoReturnOrder(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredOrderList_throwsCommandException() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        // ensures that outOfBoundIndex is still in bounds of order book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getOrderBook().getOrderList().size());
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, FLAG_ORDER_BOOK);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredReturnList_throwsCommandException() {
        showReturnOrderAtIndex(model, INDEX_FIRST_ORDER);

        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        // ensures that outOfBoundIndex is still in bounds of return order book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getReturnOrderBook().getReturnOrderList().size());
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, FLAG_RETURN_BOOK);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidFlagReturnList_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ORDER, FLAG_FORCE_CLEAR);
        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_INVALID_FLAG);
    }

    @Test
    public void execute_invalidFlagOrderList_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ORDER, FLAG_FORCE_CLEAR);
        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_INVALID_FLAG);
    }

    @Test
    public void equals() {
        // Order check
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_ORDER, FLAG_ORDER_BOOK);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_ORDER, FLAG_ORDER_BOOK);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_ORDER, FLAG_ORDER_BOOK);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different order -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // Return order check
        DeleteCommand deleteThirdCommand = new DeleteCommand(INDEX_FIRST_ORDER, FLAG_RETURN_BOOK);
        DeleteCommand deleteFourthCommand = new DeleteCommand(INDEX_SECOND_ORDER, FLAG_RETURN_BOOK);

        // same object -> returns true
        assertTrue(deleteThirdCommand.equals(deleteThirdCommand));

        // same values -> returns true
        DeleteCommand deleteThirdCommandCopy = new DeleteCommand(INDEX_FIRST_ORDER, FLAG_RETURN_BOOK);
        assertTrue(deleteThirdCommand.equals(deleteThirdCommandCopy));

        // different types -> returns false
        assertFalse(deleteThirdCommand.equals(1));

        // null -> returns false
        assertFalse(deleteThirdCommand.equals(null));

        // different order -> returns false
        assertFalse(deleteThirdCommand.equals(deleteFourthCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no orders.
     */
    private void showNoOrder(Model model) {
        model.updateFilteredOrderList(p -> false);
        assertTrue(model.getFilteredOrderList().isEmpty());
    }

    /**
     * Updates {@code model}'s filtered return order list to show no return orders.
     */
    private void showNoReturnOrder(Model model) {
        model.updateFilteredReturnOrderList(p -> false);
        assertTrue(model.getFilteredReturnOrderList().isEmpty());
    }
}
