package seedu.address.model.returnorder;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an order book
 */
public interface ReadOnlyReturnOrderBook {

    /**
     * Returns an unmodifiable view of the orders list.
     * This list will not contain any duplicate orders.
     */
    ObservableList<ReturnOrder> getReturnOrderList();

}
