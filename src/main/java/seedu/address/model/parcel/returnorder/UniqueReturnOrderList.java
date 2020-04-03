package seedu.address.model.parcel.returnorder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.parcel.exceptions.DuplicateReturnOrderException;
import seedu.address.model.parcel.exceptions.OrderNotFoundException;

/**
 * A list of return orders that enforces uniqueness between its elements and does not allow nulls.
 * A return order is considered unique by comparing using {@code ReturnOrder#isSameReturn(ReturnOrder)}.
 * As such, adding and updating of
 * return orders uses ReturnOrder#isSameReturn(ReturnOrder) for equality so as to ensure that the order being added or
 * updated is unique in terms of identity in the UniqueReturnOrderList.
 * However, the removal of an return order uses ReturnOrder#equals
 * (ReturnOrder) so
 * as to ensure that the return order with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see ReturnOrder#isSameParcel(ReturnOrder)
 */
public class UniqueReturnOrderList implements Iterable<ReturnOrder> {

    private final ObservableList<ReturnOrder> internalList = FXCollections.observableArrayList();
    private final ObservableList<ReturnOrder> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent order as the given argument.
     */
    public boolean contains(ReturnOrder toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameParcel);
    }

    /**
     * Adds an order to the list.
     * The order must not already exist in the list.
     */
    public void add(ReturnOrder toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateReturnOrderException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the return order {@code target} in the list with {@code editedReturn}.
     * {@code target} must exist in the list.
     * The order identity of {@code editedReturn} must not be the same as another existing return order in the list.
     */
    public void setReturnOrder(ReturnOrder target, ReturnOrder editedReturn) {
        requireAllNonNull(target, editedReturn);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new OrderNotFoundException();
        }

        if (!target.isSameParcel(editedReturn) && contains(editedReturn)) {
            throw new DuplicateReturnOrderException();
        }

        internalList.set(index, editedReturn);
    }

    /**
     * Removes the equivalent return order from the list.
     * The return order must exist in the list.
     */
    public void remove(ReturnOrder toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new OrderNotFoundException();
        }
    }

    public void setReturns(UniqueReturnOrderList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code orders}.
     * {@code orders} must not contain duplicate orders.
     */
    public void setReturns(List<ReturnOrder> returnOrders) {
        requireAllNonNull(returnOrders);
        if (!returnOrdersAreUnique(returnOrders)) {
            throw new DuplicateReturnOrderException();
        }

        internalList.setAll(returnOrders);
    }

    /**
     * Marks a return order in the list as delivered.
     */
    public void deliver(ReturnOrder toBeDelivered) {
        requireNonNull(toBeDelivered);
        toBeDelivered.setDeliveryStatus(true);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReturnOrder> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<ReturnOrder> iterator() {
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
    private boolean returnOrdersAreUnique(List<ReturnOrder> returnOrders) {
        for (int i = 0; i < returnOrders.size() - 1; i++) {
            for (int j = i + 1; j < returnOrders.size(); j++) {
                if (returnOrders.get(i).isSameParcel(returnOrders.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
