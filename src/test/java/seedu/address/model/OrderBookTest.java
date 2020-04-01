package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_PLASTIC;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.Parcel.order.Order;
import seedu.address.model.Parcel.exceptions.DuplicateOrderException;
import seedu.address.testutil.OrderBuilder;

public class OrderBookTest {

    private final OrderBook orderBook = new OrderBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), orderBook.getOrderList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyOrderBook_replacesData() {
        OrderBook newData = getTypicalOrderBook();
        orderBook.resetData(newData);
        assertEquals(newData, orderBook);
    }

    @Test
    public void resetData_withDuplicateOrders_throwsDuplicateOrderException() {
        // Two orders with the same identity fields
        Order editedAlice = new OrderBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withItemType(VALID_TYPE_PLASTIC)
                .build();
        List<Order> newOrders = Arrays.asList(ALICE, editedAlice);
        OrderBookStub newData = new OrderBookStub(newOrders);

        assertThrows(DuplicateOrderException.class, () -> orderBook.resetData(newData));
    }

    @Test
    public void hasOrder_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> orderBook.hasOrder(null));
    }

    @Test
    public void hasOrder_orderNotInOrderBook_returnsFalse() {
        assertFalse(orderBook.hasOrder(ALICE));
    }

    @Test
    public void hasOrder_orderInOrderBook_returnsTrue() {
        orderBook.addOrder(ALICE);
        assertTrue(orderBook.hasOrder(ALICE));
    }

    @Test
    public void hasOrder_orderWithSameIdentityFieldsInOrderBook_returnsTrue() {
        orderBook.addOrder(ALICE);
        Order editedAlice = new OrderBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withItemType(VALID_TYPE_PLASTIC)
                .build();
        assertTrue(orderBook.hasOrder(editedAlice));
    }

    @Test
    public void getOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> orderBook.getOrderList().remove(0));
    }

    /**
     * A stub ReadOnlyOrderBook whose orders list can violate interface constraints.
     */
    private static class OrderBookStub implements ReadOnlyOrderBook {
        private final ObservableList<Order> orders = FXCollections.observableArrayList();

        OrderBookStub(Collection<Order> orders) {
            this.orders.setAll(orders);
        }

        @Override
        public ObservableList<Order> getOrderList() {
            return orders;
        }

    }

}
