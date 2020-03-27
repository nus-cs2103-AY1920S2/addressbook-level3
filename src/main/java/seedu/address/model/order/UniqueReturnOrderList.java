package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.order.exceptions.DuplicateReturnOrderException;
import seedu.address.model.order.exceptions.OrderNotFoundException;
import seedu.address.model.order.returnorder.ReturnOrder;

/**
 * A list of orders that enforces uniqueness between its elements and does not allow nulls.
 * An order is considered unique by comparing using {@code Order#isSameOrder(Order)}. As such, adding and
 * updating of
 * orders uses Order#isSameOrder(Order) for equality so as to ensure that the order being added or
 * updated is
 * unique in terms of identity in the UniqueOrderList. However, the removal of an order uses Order#equals
 * (Order) so
 * as to ensure that the order with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Order#isSameOrder(Order)
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
        return internalList.stream().anyMatch(toCheck::isSameReturn);
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
     * Replaces the order {@code target} in the list with {@code editedOrder}.
     * {@code target} must exist in the list.
     * The order identity of {@code editedOrder} must not be the same as another existing order in the list.
     */
    public void setReturnOrder(ReturnOrder target, ReturnOrder editedReturn) {
        requireAllNonNull(target, editedReturn);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new OrderNotFoundException();
        }

        if (!target.isSameReturn(editedReturn) && contains(editedReturn)) {
            throw new DuplicateReturnOrderException();
        }

        internalList.set(index, editedReturn);
    }

    /**
     * Removes the equivalent order from the list.
     * The order must exist in the list.
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
        if (!ordersAreUnique(returnOrders)) {
            throw new DuplicateReturnOrderException();
        }

        internalList.setAll(returnOrders);
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
     * Returns true if {@code orders} contains only unique orders.
     */
    private boolean ordersAreUnique(List<ReturnOrder> returnOrders) {
        for (int i = 0; i < returnOrders.size() - 1; i++) {
            for (int j = i + 1; j < returnOrders.size(); j++) {
                if (returnOrders.get(i).isSameReturn(returnOrders.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
