package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.address.logic.commands.DoneCommand.MESSAGE_DELIVERED_SUCCESS;
import static seedu.address.logic.commands.DoneCommand.MESSAGE_ORDER_ALREADY_DELIVERED;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalReturnOrders.getTypicalReturnOrderBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.OrderBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;
import seedu.address.testutil.OrderBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class DoneCommandTest {
    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());

    @Test
    public void execute_orderGetsDelivered_success() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Order orderInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order editedOrder = new OrderBuilder(orderInFilteredList).build();
        DoneCommand doneCommand = new DoneCommand(INDEX_FIRST_ORDER,
                new DoneCommand.DoneOrderDescriptor(editedOrder));

        String expectedMessage = String.format(MESSAGE_DELIVERED_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(new OrderBook(model.getOrderBook()), model.getReturnOrderBook(),
                new UserPrefs());
        editedOrder.setDeliveryStatus(true);
        expectedModel.setOrder(model.getFilteredOrderList().get(0), editedOrder);

        assertCommandSuccess(doneCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_orderAlreadyDelivered_success() {
        Order orderInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order editedOrder = new OrderBuilder(orderInFilteredList).buildDelivered();
        DoneCommand doneCommand = new DoneCommand(INDEX_FIRST_ORDER,
                new DoneCommand.DoneOrderDescriptor(editedOrder));
        Model expectedModel = new ModelManager(new OrderBook(model.getOrderBook()), model.getReturnOrderBook(),
                new UserPrefs());

        String expectedMessage = String.format(MESSAGE_ORDER_ALREADY_DELIVERED, editedOrder);
        expectedModel.setOrder(model.getFilteredOrderList().get(0), editedOrder);
        model.setOrder(model.getFilteredOrderList().get(0), editedOrder);
        assertCommandSuccess(doneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        DoneCommand doneCommand = new DoneCommand(outOfBoundIndex, new DoneCommand.DoneOrderDescriptor());

        assertCommandFailure(doneCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }


    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);
        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        // ensures that outOfBoundIndex is still in bounds of address book list

        assertTrue(outOfBoundIndex.getZeroBased() < model.getOrderBook().getOrderList().size());
        DoneCommand doneCommand = new DoneCommand(outOfBoundIndex, new DoneCommand.DoneOrderDescriptor());

        assertCommandFailure(doneCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Order orderInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order order = new OrderBuilder(orderInFilteredList).build();
        DoneCommand doneFirstCommand = new DoneCommand(INDEX_FIRST_ORDER,
                new DoneCommand.DoneOrderDescriptor(order));
        DoneCommand doneSecondCommand = new DoneCommand(INDEX_SECOND_ORDER,
                new DoneCommand.DoneOrderDescriptor(order));

        // same object -> returns true
        assertTrue(doneFirstCommand.equals(doneFirstCommand));

        // same values -> returns true
        DoneCommand doneFirstCommandCopy = new DoneCommand(INDEX_FIRST_ORDER,
                new DoneCommand.DoneOrderDescriptor(order));
        assertTrue(doneFirstCommand.equals(doneFirstCommandCopy));

        // different types -> returns false
        assertFalse(doneFirstCommand.equals(1));

        // null -> returns false
        assertFalse(doneFirstCommand.equals(null));

        // different order -> returns false
        assertFalse(doneFirstCommand.equals(doneSecondCommand));
    }
}
