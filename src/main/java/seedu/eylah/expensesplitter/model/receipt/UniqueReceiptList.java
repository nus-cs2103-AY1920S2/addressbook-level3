package seedu.eylah.expensesplitter.model.receipt;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.eylah.expensesplitter.model.receipt.exceptions.DuplicateReceiptException;
import seedu.eylah.expensesplitter.model.receipt.exceptions.ReceiptNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Receipt#isSameReceipt(Receipt)
 */
public class UniqueReceiptList implements Iterable<Receipt> {

    private final ObservableList<Receipt> internalList = FXCollections.observableArrayList();
    private final ObservableList<Receipt> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Receipt toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameReceipt);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Receipt toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateReceiptException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Receipt toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ReceiptNotFoundException();
        }
    }

    public void setReceipts(UniqueReceiptList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setReceipts(List<Receipt> receipts) {
        requireAllNonNull(receipts);
        if (!receiptsAreUnique(receipts)) {
            throw new DuplicateReceiptException();
        }

        internalList.setAll(receipts);
    }

    /**
     * Returns true if {@code persons} contains only unique receipts.
     */
    private boolean receiptsAreUnique(List<Receipt> receipts) {
        for (int i = 0; i < receipts.size() - 1; i++) {
            for (int j = i + 1; j < receipts.size(); j++) {
                if (receipts.get(i).isSameReceipt(receipts.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Receipt> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Check if the list contain at least a receipt.
     *
     * @return true if contain at least a receipt, otherwise false.
     */
    public boolean isContainSingleReceipt() {
        return internalList.size() == 1;
    }

    /**
     * Get the receipt in the internal list based on the given receipt.
     *
     * @param receipt the given receipt.
     * @return the receipt in the internal list.
     */
    public Receipt getReceipt(Receipt receipt) {
        int index = internalList.indexOf(receipt);
        return internalList.get(index);
    }

    @Override
    public Iterator<Receipt> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueReceiptList // instanceof handles nulls
                && internalList.equals(((UniqueReceiptList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }



}
