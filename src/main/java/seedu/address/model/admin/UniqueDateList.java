package seedu.address.model.admin;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.admin.exceptions.DateNotFoundException;
import seedu.address.model.admin.exceptions.DuplicateDateException;


/**
 * A list of dates that enforces uniqueness between its elements and does not allow nulls.
 * The UniqueDateList cannot be modified after it has been added to prevent unnecessary edits to the admin list.
 * Supports a minimal set of list operations.
 *
 */
public class UniqueDateList implements Iterable<Date> {

    private final ObservableList<Date> internalList = FXCollections.observableArrayList();
    private final ObservableList<Date> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent date as the given argument.
     */
    public boolean contains(Date toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDate);
    }

    /**
     * Adds a date to the list.
     * The date must not already exist in the list.
     */
    public void add(Date toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDateException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the date {@code target} in the list with {@code editedDate}.
     * {@code target} must exist in the list.
     * The date identity of {@code editedDate} must not be the same as another existing Date in the list.
     */
    public void setDate(Date target, Date editedDate) {
        requireAllNonNull(target, editedDate);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DateNotFoundException();
        }

        if (!target.isSameDate(editedDate) && contains(editedDate)) {
            throw new DuplicateDateException();
        }
        internalList.set(index, editedDate);
    }

    /**
     * Removes the equivalent date from the list.
     * The date must exist in the list.
     */
    public void remove(Date toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DateNotFoundException();
        }
    }

    public void setDates(UniqueDateList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code dates}.
     * {@code dates} must not contain duplicate dates.
     */
    public void setDates(List<Date> dates) {
        requireAllNonNull(dates);
        if (!datesAreUnique(dates)) {
            throw new DuplicateDateException();
        }
        internalList.setAll(dates);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Date> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Date> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDateList // instanceof handles nulls
                && internalList.equals(((UniqueDateList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code dates} contains only unique dates.
     */
    private boolean datesAreUnique(List<Date> dates) {
        for (int i = 0; i < dates.size() - 1; i++) {
            for (int j = i + 1; j < dates.size(); j++) {
                if (dates.get(i).isSameDate(dates.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
