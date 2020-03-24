package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.order.exceptions.DuplicateReturnOrderException;
import seedu.address.model.order.exceptions.OrderNotFoundException;

/**
 * A list of return orders that enforces uniqueness between its elements and does not allow nulls.
 * An order is considered unique by comparing using {@code Order#isSameOrder(Order)}. As such, adding and
 * updating of
 * return orders uses Order#isSameOrder(Order) for equality so as to ensure that the order being added or
 * updated is
 * unique in terms of identity in the UniqueOrderList. However, the removal of an return order uses Order#equals
 * (Order) so
 * as to ensure that the order with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Order#isSameOrder(Order)
 */
public class UniqueReturnOrderList implements Iterable<Order> {

    private final ObservableList<Order> internalList = FXCollections.observableArrayList();
    private final ObservableList<Order> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent order as the given argument.
     */
    public boolean contains(Order toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameOrder);
    }

    /**
     * Adds an order to the list.
     * The order must not already exist in the list.
     */
    public void add(Order toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateReturnOrderException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the order {@code target} in the list with {@code editedOrder}.
     * {@code target} must exist in the list.
     * The order identity of {@code editedOrder} must not be the same as another existing order in the list.
     */
    public void setReturnOrder(Order target, Order editedOrder) {
        requireAllNonNull(target, editedOrder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new OrderNotFoundException();
        }

        if (!target.isSameOrder(editedOrder) && contains(editedOrder)) {
            throw new DuplicateReturnOrderException();
        }

        internalList.set(index, editedOrder);
    }

    /**
     * Removes the equivalent order from the list.
     * The return order must exist in the list.
     */
    public void remove(Order toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new OrderNotFoundException();
        }
    }

    public void setReturnOrders(UniqueReturnOrderList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code returnOrders}.
     * {@code returnOrders} must not contain duplicate return orders.
     */
    public void setReturnOrders(List<Order> returnOrders) {
        requireAllNonNull(returnOrders);
        if (!ordersAreUnique(returnOrders)) {
            throw new DuplicateReturnOrderException();
        }

        internalList.setAll(returnOrders);
    }

    /**
     * Marks an return order in the list as done.
     */
    public void deliver(Order toBeDelivered) {
        requireNonNull(toBeDelivered);
        toBeDelivered.setDeliveryStatus(true);
    }

    /**
     * Sets the return order's delivery status to false.
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
                || (other instanceof UniqueReturnOrderList // instanceof handles nulls
                        && internalList.equals(((UniqueReturnOrderList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code returnOrders} contains only unique return orders.
     */
    private boolean ordersAreUnique(List<Order> returnOrders) {
        for (int i = 0; i < returnOrders.size() - 1; i++) {
            for (int j = i + 1; j < returnOrders.size(); j++) {
                if (returnOrders.get(i).isSameOrder(returnOrders.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
