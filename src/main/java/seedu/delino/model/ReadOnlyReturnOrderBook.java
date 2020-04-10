package seedu.delino.model;

import javafx.collections.ObservableList;
import seedu.delino.model.parcel.returnorder.ReturnOrder;

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
