package seedu.address.model.returnOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_PLASTIC;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOrders.ALICE_RETURN;
import static seedu.address.testutil.TypicalOrders.getTypicalReturnOrderBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.order.Order;
import seedu.address.model.order.exceptions.DuplicateReturnOrderException;
import seedu.address.testutil.ReturnOrderBuilder;

class ReturnOrderBookTest {
    private final ReturnOrderBook orderBook = new ReturnOrderBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), orderBook.getReturnOrderList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyOrderBook_replacesData() {
        ReturnOrderBook newData = getTypicalReturnOrderBook();
        orderBook.resetData(newData);
        assertEquals(newData, orderBook);
    }

    @Test
    public void resetData_withDuplicateOrders_throwsDuplicateOrderException() {
        // Two orders with the same identity fields
        Order editedAlice = new ReturnOrderBuilder(ALICE_RETURN)
                .withAddress(VALID_ADDRESS_BOB).withItemType(VALID_TYPE_PLASTIC).build();
        List<Order> newOrders = Arrays.asList(ALICE_RETURN, editedAlice);
        ReturnOrderBookTest.ReturnOrderBookStub newData = new ReturnOrderBookTest.ReturnOrderBookStub(newOrders);

        assertThrows(DuplicateReturnOrderException.class, () -> orderBook.resetData(newData));
    }

    @Test
    public void hasOrder_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderBook.hasReturnOrder(null));
    }

    @Test
    public void hasOrder_orderNotInOrderBook_returnsFalse() {
        assertFalse(orderBook.hasReturnOrder(ALICE_RETURN));
    }

    @Test
    public void hasOrder_orderInOrderBook_returnsTrue() {
        orderBook.addReturnOrder(ALICE_RETURN);
        assertTrue(orderBook.hasReturnOrder(ALICE_RETURN));
    }

    @Test
    public void hasOrder_orderWithSameIdentityFieldsInOrderBook_returnsTrue() {
        orderBook.addReturnOrder(ALICE_RETURN);
        Order editedAlice = new ReturnOrderBuilder(ALICE_RETURN)
                .withAddress(VALID_ADDRESS_BOB).withItemType(VALID_TYPE_PLASTIC).build();
        assertTrue(orderBook.hasReturnOrder(editedAlice));
    }

    @Test
    public void getOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> orderBook.getReturnOrderList().remove(0));
    }

    /**
     * A stub ReadOnlyReturnOrderBook whose orders list can violate interface constraints.
     */
    private static class ReturnOrderBookStub implements ReadOnlyReturnOrderBook {
        private final ObservableList<Order> returnOrders = FXCollections.observableArrayList();

        ReturnOrderBookStub(Collection<Order> orders) {
            this.returnOrders.setAll(orders);
        }

        @Override
        public ObservableList<Order> getReturnOrderList() {
            return returnOrders;
        }
    }

}