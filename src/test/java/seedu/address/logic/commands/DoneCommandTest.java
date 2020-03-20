package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.DoneCommand.MESSAGE_DELIVERED_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.OrderBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class DoneCommandTest {
    private Model model = new ModelManager(getTypicalOrderBook(), new UserPrefs());

    @Test
    public void execute_orderAlreadyDelivered_success() {
        ModelManager expectedModel = new ModelManager(model.getOrderBook(), new UserPrefs());
        Order orderToDeliver = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        String expectedMessage = String.format(MESSAGE_DELIVERED_SUCCESS, orderToDeliver);
        DoneCommand doneCommand = new DoneCommand(INDEX_FIRST_ORDER);
        assertCommandSuccess(doneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_orderGetsDelivered_success() {
        Model expectedModel = new ModelManager(new OrderBook(model.getOrderBook()), new UserPrefs());
        Order originalOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        DoneCommand doneCommand = new DoneCommand(INDEX_FIRST_ORDER);
        expectedModel.deliverOrder(originalOrder);
        String expectedMessage = String.format(MESSAGE_DELIVERED_SUCCESS, originalOrder);
        assertCommandSuccess(doneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_ORDER);

        Order orderToDelete = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, orderToDelete);

        Model expectedModel = new ModelManager(model.getOrderBook(), new UserPrefs());
        expectedModel.deleteOrder(orderToDelete);
        showNoOrder(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_ORDER);

        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getOrderBook().getOrderList().size());
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DoneCommand doneFirstCommand = new DoneCommand(INDEX_FIRST_ORDER);
        DoneCommand doneSecondCommand = new DoneCommand(INDEX_SECOND_ORDER);

        // same object -> returns true
        assertTrue(doneFirstCommand.equals(doneFirstCommand));

        // same values -> returns true
        DoneCommand doneFirstCommandCopy = new DoneCommand(INDEX_FIRST_ORDER);
        assertTrue(doneFirstCommand.equals(doneFirstCommandCopy));

        // different types -> returns false
        assertFalse(doneFirstCommand.equals(1));

        // null -> returns false
        assertFalse(doneFirstCommand.equals(null));

        // different order -> returns false
        assertFalse(doneFirstCommand.equals(doneSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no orders.
     */
    private void showNoOrder(Model model) {
        model.updateFilteredOrderList(p -> false);
        assertTrue(model.getFilteredOrderList().isEmpty());
    }
}