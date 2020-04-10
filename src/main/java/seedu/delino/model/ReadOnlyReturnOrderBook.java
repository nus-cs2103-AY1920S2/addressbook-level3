package seedu.delino.model;

import javafx.collections.ObservableList;
import seedu.delino.model.parcel.returnorder.ReturnOrder;

//@@author Exeexe93
/**
 * Unmodifiable view of an return order book
 */
public interface ReadOnlyReturnOrderBook {

    /**
     * Returns an unmodifiable view of the return orders list.
     * This list will not contain any duplicate return orders.
     */
    ObservableList<ReturnOrder> getReturnOrderList();

}
