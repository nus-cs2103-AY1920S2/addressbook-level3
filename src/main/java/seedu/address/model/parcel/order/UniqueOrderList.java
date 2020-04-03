package seedu.address.model.parcel.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.parcel.exceptions.DuplicateOrderException;
import seedu.address.model.parcel.exceptions.OrderNotFoundException;
import seedu.address.model.parcel.parcelattributes.TransactionId;


/**
 * A list of orders that enforces uniqueness between its elements and does not allow nulls.
 * An order is considered unique by comparing using {@code Order#isSameOrder(Order)}. As such, adding and
 * updating of
 * orders uses Order#isSameOrder(Order) for equality so as to ensure that the order being added or
 * updated is
 * unique in terms of identity in the UniqueOrderList. However, the removal of an order uses Order#equals
 * (Order) so
 * as to ensure that the order with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Order#isSameParcel(Order)
 */
public class UniqueOrderList implements Iterable<Order> {

    private final ObservableList<Order> internalList = FXCollections.observableArrayList();
    private final ObservableList<Order> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent order as the given argument.
     */
    public boolean contains(Order toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameParcel);
    }

    /**
     * Adds an order to the list.
     * The order must not already exist in the list.
     */
    public void add(Order toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateOrderException();
        }
        internalList.add(toAdd);
        sortByTimestamp();
    }

    /**
     * Replaces the order {@code target} in the list with {@code editedOrder}.
     * {@code target} must exist in the list.
     * The order identity of {@code editedOrder} must not be the same as another existing order in the list.
     */
    public void setOrder(Order target, Order editedOrder) {
        requireAllNonNull(target, editedOrder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new OrderNotFoundException();
        }

        if (!target.isSameParcel(editedOrder) && contains(editedOrder)) {
            throw new DuplicateOrderException();
        }

        internalList.set(index, editedOrder);
        sortByTimestamp();
    }

    /**
     * The orders in the {@code internalList} will be sorted by timestamp.
     */
    public void sortByTimestamp() {
        internalList.sort((Order first, Order second) -> {
            LocalDateTime firstDateTime = first.getTimestamp().getOrderTimeStamp();
            LocalDateTime secondDateTime = second.getTimestamp().getOrderTimeStamp();
            return firstDateTime.compareTo(secondDateTime);
        });
    }

    /**
     * Removes the equivalent order from the list.
     * The order must exist in the list.
     */
    public void remove(Order toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new OrderNotFoundException();
        }
    }

    public void setOrders(UniqueOrderList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        sortByTimestamp();
    }

    /**
     * Replaces the contents of this list with {@code orders}.
     * {@code orders} must not contain duplicate orders.
     */
    public void setOrders(List<Order> orders) {
        requireAllNonNull(orders);
        if (!ordersAreUnique(orders)) {
            throw new DuplicateOrderException();
        }

        internalList.setAll(orders);
        sortByTimestamp();
    }

    /**
     * Marks an order in the list as done.
     */
    public void deliver(Order toBeDelivered) {
        requireNonNull(toBeDelivered);
        toBeDelivered.setDeliveryStatus(true);
    }

    /**
     * Sets the order's delivery status to false.
     * This prevents order from being mistakenly marked as delivered.
     */
    public void setToBeDelivered(Order toBeDelivered) {
        requireNonNull(toBeDelivered);
        toBeDelivered.setDeliveryStatus(false);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Order> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Order> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueOrderList // instanceof handles nulls
                && internalList.equals(((UniqueOrderList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code orders} contains only unique orders.
     */
    private boolean ordersAreUnique(List<Order> orders) {
        for (int i = 0; i < orders.size() - 1; i++) {
            for (int j = i + 1; j < orders.size(); j++) {
                if (orders.get(i).isSameParcel(orders.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public Order get(TransactionId transactionId) {
        for (Order order : internalList) {
            if (order.getTid().equals(transactionId)) {
                return order;
            }
        }
        return null;
    }
}
