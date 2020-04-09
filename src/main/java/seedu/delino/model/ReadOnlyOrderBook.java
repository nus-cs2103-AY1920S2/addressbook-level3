package seedu.delino.model;

import javafx.collections.ObservableList;
import seedu.delino.model.parcel.order.Order;

/**
 * Unmodifiable view of an order book
 */
public interface ReadOnlyOrderBook {

    /**
     * Returns an unmodifiable view of the orders list.
     * This list will not contain any duplicate orders.
     */
    ObservableList<Order> getOrderList();
}
