package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.Parcel.order.Order;
import seedu.address.model.Parcel.ParcelAttributes.TransactionId;
import seedu.address.model.Parcel.returnorder.ReturnOrder;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.ReturnOrderBuilder;

public class ReturnCommandTest {

    @Test
    public void constructor_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReturnCommand(null, null));
    }

    @Test
    public void orderNotDelivered_throwsCommandException() {
        ModelManager modelManager = new ModelManager();
        Order orderToBeReturned = new OrderBuilder().build();
        TransactionId transactionId = orderToBeReturned.getTid();
        modelManager.addOrder(orderToBeReturned);
        ReturnCommand returnCommand = new ReturnCommand(null, transactionId);

        assertThrows(CommandException.class,
                ReturnCommand.MESSAGE_ORDER_NOT_DELIVERED, () -> returnCommand.execute(modelManager));
    }

    @Test
    public void orderNotPresent_throwsCommandException() throws Exception {
        ModelManager modelManager = new ModelManager();
        Order orderToBeReturned = new OrderBuilder().build();
        TransactionId transactionId = orderToBeReturned.getTid();
        ReturnCommand returnCommand = new ReturnCommand(null, transactionId);

        assertThrows(CommandException.class,
                ReturnCommand.MESSAGE_ORDER_TRANSACTION_ID_NOT_VALID, () -> returnCommand.execute(modelManager));
    }

    @Test
    public void execute_returnAcceptedByModel_returnSuccessful() throws Exception {
        ModelManager modelManager = new ModelManager();
        ReturnOrder validReturnOrder = new ReturnOrderBuilder().build();
        TransactionId validTid = validReturnOrder.getTid();

        CommandResult commandResult = new ReturnCommand(validReturnOrder, validTid).execute(modelManager);

        assertEquals(String.format(ReturnCommand.MESSAGE_SUCCESS, validReturnOrder), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validReturnOrder), modelManager.getFilteredReturnOrderList());
    }

    @Test
    public void execute_orderConvertedToReturnOrder_returnSuccessful() throws Exception {
        ModelManager modelManager = new ModelManager();
        Order orderToBeReturned = new OrderBuilder().buildDelivered();
        modelManager.addOrder(orderToBeReturned);
        TransactionId validTid = orderToBeReturned.getTid();
        ReturnOrder returnOrder = new ReturnOrder(orderToBeReturned);
        CommandResult commandResult = new ReturnCommand(null, validTid).execute(modelManager);

        assertEquals(String.format(ReturnCommand.MESSAGE_SUCCESS, returnOrder), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(returnOrder), modelManager.getFilteredReturnOrderList());
    }

    @Test
    public void execute_duplicateReturn_throwsCommandException() {
        ModelManager modelManager = new ModelManager();
        ReturnOrder validReturnOrder = new ReturnOrderBuilder().build();
        TransactionId validTid = validReturnOrder.getTid();
        ReturnCommand returnCommand = new ReturnCommand(validReturnOrder, validTid);
        modelManager.addReturnOrder(validReturnOrder);

        assertThrows(CommandException.class,
                ReturnCommand.MESSAGE_DUPLICATE_RETURN, () -> returnCommand.execute(modelManager));
    }

    @Test
    public void equals() {
        ReturnOrder alice = new ReturnOrderBuilder().withName("Alice").build();
        ReturnOrder bob = new ReturnOrderBuilder().withName("Bob").withTid("123456abc").build();
        TransactionId aliceTid = alice.getTid();
        TransactionId bobTid = bob.getTid();
        ReturnCommand returnAliceCommand = new ReturnCommand(alice, aliceTid);
        ReturnCommand returnBobCommand = new ReturnCommand(bob, bobTid);

        // same object -> returns true
        assertTrue(returnAliceCommand.equals(returnAliceCommand));

        // same values -> returns true
        ReturnCommand returnAliceCommandCopy = new ReturnCommand(alice, aliceTid);
        assertTrue(returnAliceCommand.equals(returnAliceCommandCopy));

        // null return orders with same tid -> returns true
        ReturnCommand returnNullAliceCommand = new ReturnCommand(null, aliceTid);
        ReturnCommand returnNullAliceCommandCopy = new ReturnCommand(null, aliceTid);
        assertTrue(returnNullAliceCommand.equals(returnNullAliceCommandCopy));

        // different types -> returns false
        assertFalse(returnAliceCommand.equals(1));

        // null -> returns false
        assertFalse(returnAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(returnAliceCommand.equals(returnBobCommand));

        // same return order but different tid -> returns false
        ReturnCommand returnAliceCommandWithOtherTid = new ReturnCommand(alice,
                new TransactionId("abc123"));
        assertFalse(returnAliceCommand.equals(returnAliceCommandWithOtherTid));

        // different return order but same tid -> returns false
        ReturnCommand returnBobCommandWithAliceTid = new ReturnCommand(bob, aliceTid);
        assertFalse(returnAliceCommand.equals(returnBobCommandWithAliceTid));

        // null return orders with different tid -> returns false
        ReturnCommand returnNullBobCommand = new ReturnCommand(null, bobTid);
        assertFalse(returnNullAliceCommand.equals(returnNullBobCommand));
    }
}
