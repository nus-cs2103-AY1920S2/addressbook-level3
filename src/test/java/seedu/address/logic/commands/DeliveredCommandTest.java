package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.address.logic.commands.CommandTestUtil.showReturnOrderAtIndex;
import static seedu.address.logic.commands.DeliveredCommand.MESSAGE_DELIVERED_SUCCESS;
import static seedu.address.logic.commands.DeliveredCommand.MESSAGE_ORDER_ALREADY_DELIVERED;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalReturnOrders.getTypicalReturnOrderBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.Flag;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.OrderBook;
import seedu.address.model.ReturnOrderBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.parcel.order.Order;
import seedu.address.model.parcel.returnorder.ReturnOrder;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.ReturnOrderBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class DeliveredCommandTest {
    private Model model = new ModelManager(getTypicalOrderBook(), getTypicalReturnOrderBook(), new UserPrefs());

    @Test
    public void execute_orderGetsDelivered_success() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);

        Order orderInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order deliveredOrder = new OrderBuilder(orderInFilteredList).build();
        DeliveredCommand deliveredCommand = new DeliveredCommand(INDEX_FIRST_ORDER, FLAG_ORDER_BOOK,
                new DeliveredCommand.DeliveredParcelDescriptor(deliveredOrder));

        String expectedMessage = String.format(MESSAGE_DELIVERED_SUCCESS, deliveredOrder);

        Model expectedModel = new ModelManager(new OrderBook(model.getOrderBook()), model.getReturnOrderBook(),
                new UserPrefs());
        deliveredOrder.setDeliveryStatus(true);
        expectedModel.setOrder(model.getFilteredOrderList().get(0), deliveredOrder);

        assertCommandSuccess(deliveredCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_returnOrderGetsDelivered_success() {
        showReturnOrderAtIndex(model, INDEX_FIRST_ORDER);

        ReturnOrder returnOrderInFilteredList = model.getFilteredReturnOrderList()
                .get(INDEX_FIRST_ORDER.getZeroBased());
        ReturnOrder deliveredReturnOrder = new ReturnOrderBuilder(returnOrderInFilteredList).build();
        DeliveredCommand deliveredCommand = new DeliveredCommand(INDEX_FIRST_ORDER, FLAG_RETURN_BOOK,
                new DeliveredCommand.DeliveredParcelDescriptor(deliveredReturnOrder));

        String expectedMessage = String.format(MESSAGE_DELIVERED_SUCCESS, deliveredReturnOrder);

        Model expectedModel = new ModelManager(model.getOrderBook(), new ReturnOrderBook(model.getReturnOrderBook()),
                new UserPrefs());
        deliveredReturnOrder.setDeliveryStatus(true);
        expectedModel.setReturnOrder(model.getFilteredReturnOrderList().get(0), deliveredReturnOrder);

        assertCommandSuccess(deliveredCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_orderAlreadyDelivered_success() {
        Order orderInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order editedOrder = new OrderBuilder(orderInFilteredList).buildDelivered();
        DeliveredCommand deliveredCommand = new DeliveredCommand(INDEX_FIRST_ORDER, FLAG_ORDER_BOOK,
                new DeliveredCommand.DeliveredParcelDescriptor(editedOrder));
        Model expectedModel = new ModelManager(new OrderBook(model.getOrderBook()), model.getReturnOrderBook(),
                new UserPrefs());

        String expectedMessage = String.format(MESSAGE_ORDER_ALREADY_DELIVERED, editedOrder);
        expectedModel.setOrder(model.getFilteredOrderList().get(0), editedOrder);
        model.setOrder(model.getFilteredOrderList().get(0), editedOrder);
        assertCommandSuccess(deliveredCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_returnOrderAlreadyDelivered_success() {
        ReturnOrder returnOrderInFilteredList = model.getFilteredReturnOrderList()
                .get(INDEX_FIRST_ORDER.getZeroBased());
        ReturnOrder deliveredReturnOrder = new ReturnOrderBuilder(returnOrderInFilteredList).buildDelivered();
        DeliveredCommand deliveredCommand = new DeliveredCommand(INDEX_FIRST_ORDER, FLAG_RETURN_BOOK,
                new DeliveredCommand.DeliveredParcelDescriptor(deliveredReturnOrder));
        Model expectedModel = new ModelManager(model.getOrderBook(), new ReturnOrderBook(model.getReturnOrderBook()),
                new UserPrefs());

        String expectedMessage = String.format(MESSAGE_ORDER_ALREADY_DELIVERED, deliveredReturnOrder);
        expectedModel.setReturnOrder(model.getFilteredReturnOrderList().get(0), deliveredReturnOrder);
        model.setReturnOrder(model.getFilteredReturnOrderList().get(0), deliveredReturnOrder);
        assertCommandSuccess(deliveredCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredOrderList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        DeliveredCommand deliveredCommand = new DeliveredCommand(outOfBoundIndex, FLAG_ORDER_BOOK,
                new DeliveredCommand.DeliveredParcelDescriptor());

        assertCommandFailure(deliveredCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidFlagUnfilteredOrderList_throwsCommandException() {
        Flag invalidFlag = new Flag("f");
        DeliveredCommand deliveredCommand = new DeliveredCommand(INDEX_FIRST_ORDER, invalidFlag,
                new DeliveredCommand.DeliveredParcelDescriptor());

        assertCommandFailure(deliveredCommand, model, DeliveredCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_invalidIndexUnfilteredReturnList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReturnOrderList().size() + 1);
        DeliveredCommand deliveredCommand = new DeliveredCommand(outOfBoundIndex, FLAG_RETURN_BOOK,
                new DeliveredCommand.DeliveredParcelDescriptor());

        assertCommandFailure(deliveredCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }


    @Test
    public void execute_invalidIndexFilteredOrderList_throwsCommandException() {
        showOrderAtIndex(model, INDEX_FIRST_ORDER);
        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        // ensures that outOfBoundIndex is still in bounds of address book list

        assertTrue(outOfBoundIndex.getZeroBased() < model.getOrderBook().getOrderList().size());
        DeliveredCommand deliveredCommand = new DeliveredCommand(outOfBoundIndex, FLAG_ORDER_BOOK,
                new DeliveredCommand.DeliveredParcelDescriptor());

        assertCommandFailure(deliveredCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredReturnList_throwsCommandException() {
        showReturnOrderAtIndex(model, INDEX_FIRST_ORDER);
        Index outOfBoundIndex = INDEX_SECOND_ORDER;
        // ensures that outOfBoundIndex is still in bounds of address book list

        assertTrue(outOfBoundIndex.getZeroBased() < model.getReturnOrderBook().getReturnOrderList().size());
        DeliveredCommand deliveredCommand = new DeliveredCommand(outOfBoundIndex, FLAG_RETURN_BOOK,
                new DeliveredCommand.DeliveredParcelDescriptor());

        assertCommandFailure(deliveredCommand, model, Messages.MESSAGE_INVALID_RETURN_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Order orderInFilteredList = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order order = new OrderBuilder(orderInFilteredList).build();
        DeliveredCommand doneOrderFirstCommand = new DeliveredCommand(INDEX_FIRST_ORDER, FLAG_ORDER_BOOK,
                new DeliveredCommand.DeliveredParcelDescriptor(order));
        DeliveredCommand doneOrderSecondCommand = new DeliveredCommand(INDEX_SECOND_ORDER, FLAG_ORDER_BOOK,
                new DeliveredCommand.DeliveredParcelDescriptor(order));

        // same object -> returns true
        assertTrue(doneOrderFirstCommand.equals(doneOrderFirstCommand));

        // same values -> returns true
        DeliveredCommand doneFirstCommandCopy = new DeliveredCommand(INDEX_FIRST_ORDER, FLAG_ORDER_BOOK,
                new DeliveredCommand.DeliveredParcelDescriptor(order));
        assertTrue(doneOrderFirstCommand.equals(doneFirstCommandCopy));

        // different types -> returns false
        assertFalse(doneOrderFirstCommand.equals(1));

        // null -> returns false
        assertFalse(doneOrderFirstCommand.equals(null));

        // different order -> returns false
        assertFalse(doneOrderFirstCommand.equals(doneOrderSecondCommand));
        ReturnOrder returnOrderInFilteredList = model.getFilteredReturnOrderList()
                .get(INDEX_FIRST_ORDER.getZeroBased());
        ReturnOrder returnOrder = new ReturnOrderBuilder(returnOrderInFilteredList).build();
        DeliveredCommand doneReturnFirstCommand = new DeliveredCommand(INDEX_FIRST_ORDER, FLAG_RETURN_BOOK,
                new DeliveredCommand.DeliveredParcelDescriptor(returnOrder));
        DeliveredCommand doneReturnSecondCommand = new DeliveredCommand(INDEX_SECOND_ORDER, FLAG_RETURN_BOOK,
                new DeliveredCommand.DeliveredParcelDescriptor(returnOrder));

        // same object -> returns true
        assertTrue(doneReturnFirstCommand.equals(doneReturnFirstCommand));

        // same values -> returns true
        DeliveredCommand doneReturnFirstCommandCopy = new DeliveredCommand(INDEX_FIRST_ORDER, FLAG_RETURN_BOOK,
                new DeliveredCommand.DeliveredParcelDescriptor(returnOrder));
        assertTrue(doneReturnFirstCommand.equals(doneReturnFirstCommandCopy));

        // different types -> returns false
        assertFalse(doneReturnFirstCommand.equals(1));

        // null -> returns false
        assertFalse(doneReturnFirstCommand.equals(null));

    }
}
