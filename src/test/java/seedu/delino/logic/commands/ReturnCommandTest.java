package seedu.delino.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.delino.model.parcel.parcelattributes.TimeStamp.FORMAT_CHECKER;
import static seedu.delino.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.delino.logic.commands.exceptions.CommandException;
import seedu.delino.model.ModelManager;
import seedu.delino.model.parcel.order.Order;
import seedu.delino.model.parcel.parcelattributes.TimeStamp;
import seedu.delino.model.parcel.parcelattributes.TransactionId;
import seedu.delino.model.parcel.returnorder.ReturnOrder;
import seedu.delino.testutil.OrderBuilder;
import seedu.delino.testutil.ReturnOrderBuilder;

//@@author cherweijie
public class ReturnCommandTest {

    @Test
    public void constructor_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReturnCommand(null, null, null));
    }

    @Test
    public void orderNotDelivered_throwsCommandException() {
        ModelManager modelManager = new ModelManager();
        Order orderToBeReturned = new OrderBuilder().build();
        TransactionId transactionId = orderToBeReturned.getTid();
        TimeStamp timeStamp = orderToBeReturned.getTimestamp();
        modelManager.addOrder(orderToBeReturned);
        ReturnCommand returnCommand = new ReturnCommand(null, transactionId, timeStamp);

        assertThrows(CommandException.class,
                ReturnCommand.MESSAGE_ORDER_NOT_DELIVERED, () -> returnCommand.execute(modelManager));
    }

    @Test
    public void orderNotPresent_throwsCommandException() throws Exception {
        ModelManager modelManager = new ModelManager();
        Order orderToBeReturned = new OrderBuilder().build();
        TransactionId transactionId = orderToBeReturned.getTid();
        TimeStamp timeStamp = orderToBeReturned.getTimestamp();

        ReturnCommand returnCommand = new ReturnCommand(null, transactionId, timeStamp);

        assertThrows(CommandException.class,
                ReturnCommand.MESSAGE_ORDER_TRANSACTION_ID_NOT_VALID, () -> returnCommand.execute(modelManager));
    }

    @Test
    public void execute_returnAcceptedByModel_returnSuccessful() throws Exception {
        ModelManager modelManager = new ModelManager();
        ReturnOrder validReturnOrder = new ReturnOrderBuilder().build();
        TransactionId validTid = validReturnOrder.getTid();
        TimeStamp validTimeStamp = validReturnOrder.getTimestamp();

        CommandResult commandResult = new ReturnCommand(validReturnOrder, validTid, validTimeStamp)
                .execute(modelManager);

        assertEquals(String.format(ReturnCommand.MESSAGE_SUCCESS, validReturnOrder), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validReturnOrder), modelManager.getFilteredReturnOrderList());
    }

    @Test
    public void execute_orderConvertedToReturnOrder_returnSuccessful() throws Exception {
        ModelManager modelManager = new ModelManager();
        Order orderToBeReturned = new OrderBuilder().buildDelivered();
        modelManager.addOrder(orderToBeReturned);
        TransactionId validTid = orderToBeReturned.getTid();
        LocalDateTime localDateTime = orderToBeReturned.getTimestamp().getOrderTimeStamp().plusDays(1);
        TimeStamp validTimeStamp = new TimeStamp(localDateTime.format(FORMAT_CHECKER), true);
        ReturnOrder returnOrder = new ReturnOrder(orderToBeReturned);
        returnOrder.setTimestamp(validTimeStamp);
        CommandResult commandResult = new ReturnCommand(null, validTid, validTimeStamp)
                .execute(modelManager);

        assertEquals(String.format(ReturnCommand.MESSAGE_SUCCESS, returnOrder), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(returnOrder), modelManager.getFilteredReturnOrderList());
    }

    @Test
    public void execute_duplicateReturn_throwsCommandException() {
        ModelManager modelManager = new ModelManager();
        ReturnOrder validReturnOrder = new ReturnOrderBuilder().build();
        TransactionId validTid = validReturnOrder.getTid();
        TimeStamp validTimeStamp = validReturnOrder.getTimestamp();
        ReturnCommand returnCommand = new ReturnCommand(validReturnOrder, validTid, validTimeStamp);
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
        TimeStamp aliceTimeStamp = alice.getTimestamp();
        TimeStamp bobTimeStamp = bob.getTimestamp();
        ReturnCommand returnAliceCommand = new ReturnCommand(alice, aliceTid, aliceTimeStamp);
        ReturnCommand returnBobCommand = new ReturnCommand(bob, bobTid, bobTimeStamp);

        // same object -> returns true
        assertTrue(returnAliceCommand.equals(returnAliceCommand));

        // same values -> returns true
        ReturnCommand returnAliceCommandCopy = new ReturnCommand(alice, aliceTid, aliceTimeStamp);
        assertTrue(returnAliceCommand.equals(returnAliceCommandCopy));

        // null return orders with same tid -> returns true
        ReturnCommand returnNullAliceCommand = new ReturnCommand(null, aliceTid, aliceTimeStamp);
        ReturnCommand returnNullAliceCommandCopy = new ReturnCommand(null, aliceTid, aliceTimeStamp);
        assertTrue(returnNullAliceCommand.equals(returnNullAliceCommandCopy));

        // different types -> returns false
        assertFalse(returnAliceCommand.equals(1));

        // null -> returns false
        assertFalse(returnAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(returnAliceCommand.equals(returnBobCommand));

        // same return order but different tid -> returns false
        ReturnCommand returnAliceCommandWithOtherTid = new ReturnCommand(alice,
                new TransactionId("abc123"), aliceTimeStamp);
        assertFalse(returnAliceCommand.equals(returnAliceCommandWithOtherTid));

        // different return order but same tid -> returns false
        ReturnCommand returnBobCommandWithAliceTid = new ReturnCommand(bob, aliceTid, bobTimeStamp);
        assertFalse(returnAliceCommand.equals(returnBobCommandWithAliceTid));

        // null return orders with different tid -> returns false
        ReturnCommand returnNullBobCommand = new ReturnCommand(null, bobTid, bobTimeStamp);
        assertFalse(returnNullAliceCommand.equals(returnNullBobCommand));
    }
}
