package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.Parcel.returnorder.ReturnOrder;
import seedu.address.model.Parcel.returnorder.UniqueReturnOrderList;


/**
 * Wraps all data at the return-order-book level
 * Duplicates are not allowed (by .isSameOrder comparison)
 */
public class ReturnOrderBook implements ReadOnlyReturnOrderBook {

    private final UniqueReturnOrderList returnOrders;

    public ReturnOrderBook() {
        returnOrders = new UniqueReturnOrderList();
    }

    /**
     * Creates an ReturnOrderBook using the Return Orders in the {@code toBeCopied}
     */
    public ReturnOrderBook(ReadOnlyReturnOrderBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the return order list with {@code returnOrders}.
     * {@code returnOrders} must not contain duplicate returnOorders.
     */
    public void setReturnOrders(List<ReturnOrder> returnOrders) {
        this.returnOrders.setReturns(returnOrders);
    }

    /**
     * Resets the existing data of this {@code ReturnOrderBook} with {@code newData}.
     */
    public void resetData(ReadOnlyReturnOrderBook newData) {
        requireNonNull(newData);

        setReturnOrders(newData.getReturnOrderList());
    }

    //// return order-level operations

    /**
     * Returns true if an return return order with the same identity as {@code returnOrder} exists in
     * the return order book.
     */
    public boolean hasReturnOrder(ReturnOrder returnOrder) {
        requireNonNull(returnOrder);
        return returnOrders.contains(returnOrder);
    }

    /**
     * Adds an return order to the return order book.
     * The return order must not already exist in the return order book.
     */
    public void addReturnOrder(ReturnOrder p) {
        returnOrders.add(p);
    }

    /**
     * Replaces the given return return order {@code target} in the list with {@code editedReturnOrder}.
     * {@code target} must exist in the return order book.
     * The order identity of {@code editedReturnOrder} must not be the same as another existing return order in the
     * return order book.
     */
    public void setReturnOrder(ReturnOrder target, ReturnOrder editedReturnOrder) {
        requireNonNull(editedReturnOrder);
        returnOrders.setReturnOrder(target, editedReturnOrder);
    }

    /**
     * Removes {@code key} from this {@code ReturnOrderBook}.
     * {@code key} must exist in the return order book.
     */
    public void removeReturnOrder(ReturnOrder key) {
        returnOrders.remove(key);
    }

    public void deliverReturnOrder(ReturnOrder toBeDelivered) {
        returnOrders.deliver(toBeDelivered);
    }

    //// util methods
    @Override
    public String toString() {
        return returnOrders.asUnmodifiableObservableList().size() + " return orders";
    }

    @Override
    public ObservableList<ReturnOrder> getReturnOrderList() {
        return returnOrders.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReturnOrderBook // instanceof handles nulls
                && returnOrders.equals(((ReturnOrderBook) other).returnOrders));
    }

    @Override
    public int hashCode() {
        return returnOrders.hashCode();
    }
}
