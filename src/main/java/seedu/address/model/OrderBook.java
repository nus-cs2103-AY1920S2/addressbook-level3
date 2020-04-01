package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.Parcel.order.Order;
import seedu.address.model.Parcel.ParcelAttributes.TransactionId;
import seedu.address.model.Parcel.order.UniqueOrderList;

/**
 * Wraps all data at the order-book level
 * Duplicates are not allowed (by .isSameOrder comparison)
 */
public class OrderBook implements ReadOnlyOrderBook {

    private final UniqueOrderList orders;

    public OrderBook() {
        orders = new UniqueOrderList();
    }

    /**
     * Creates an OrderBook using the Orders in the {@code toBeCopied}
     */
    public OrderBook(ReadOnlyOrderBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the order list with {@code orders}.
     * {@code orders} must not contain duplicate orders.
     */
    public void setOrders(List<Order> orders) {
        this.orders.setOrders(orders);
    }

    /**
     * Resets the existing data of this {@code OrderBook} with {@code newData}.
     */
    public void resetData(ReadOnlyOrderBook newData) {
        requireNonNull(newData);

        setOrders(newData.getOrderList());
    }

    //// order-level operations

    /**
     * Returns true if an order with the same identity as {@code order} exists in the order book.
     */
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return orders.contains(order);
    }

    /**
     * Adds an order to the order book.
     * The order must not already exist in the order book.
     */
    public void addOrder(Order p) {
        orders.add(p);
    }

    /**
     * Replaces the given order {@code target} in the list with {@code editedOrder}.
     * {@code target} must exist in the order book.
     * The order identity of {@code editedOrder} must not be the same as another existing order in the
     * order book.
     */
    public void setOrder(Order target, Order editedOrder) {
        requireNonNull(editedOrder);
        orders.setOrder(target, editedOrder);
    }

    /**
     * Removes {@code key} from this {@code OrderBook}.
     * {@code key} must exist in the order book.
     */
    public void removeOrder(Order key) {
        orders.remove(key);
    }

    public void deliverOrder(Order target) {
        orders.deliver(target);
    }

    public void setDeliveryStatus(Order target) {
        orders.setToBeDelivered(target);
    }

    //// util methods

    @Override
    public String toString() {
        return orders.asUnmodifiableObservableList().size() + " orders";
        // TODO: refine later
    }

    @Override
    public ObservableList<Order> getOrderList() {
        return orders.asUnmodifiableObservableList();
    }

    public Order getOrderByTransactionId(TransactionId transactionId) {
        return orders.get(transactionId);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderBook // instanceof handles nulls
                && orders.equals(((OrderBook) other).orders));
    }

    @Override
    public int hashCode() {
        return orders.hashCode();
    }
}
