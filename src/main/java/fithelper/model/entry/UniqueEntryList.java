package fithelper.model.entry;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;
import static fithelper.model.entry.SortBy.CALORIE;
import static fithelper.model.entry.SortBy.NAME;
import static fithelper.model.entry.SortBy.TIME;
import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import fithelper.commons.exceptions.IllegalValueException;
import fithelper.model.entry.exceptions.DuplicateEntryException;
import fithelper.model.entry.exceptions.EntryNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of entries that enforces uniqueness between its elements and does not allow nulls.
 * An entry is considered unique by comparing using {@code Entry#isSameEntry(Entry)}. As such, adding and updating of
 * entries uses Entry#isSameEntry(Entry) for equality so as to ensure that the entry being added or updated is
 * unique in terms of identity in the UniqueEntryList. However, the removal of an entry uses Entry#equals(Object) so
 * as to ensure that the entry with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Entry#isSameEntry(Entry)
 */
public class UniqueEntryList implements Iterable<Entry> {

    private final ObservableList<Entry> internalList = FXCollections.observableArrayList();
    private final ObservableList<Entry> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent entry as the given argument.
     */
    public boolean contains(Entry toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEntry);
    }

    /**
     * Returns true if the list contains an equivalent entry as the given argument.
     */
    public long countClashes(Entry toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().filter(toCheck::hasTimeClashes).count();
    }

    /**
     * Adds an entry to the list.
     * The entry must not already exist in the list.
     */
    public void add(Entry toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateEntryException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the entry {@code target} in the list with {@code editedEntry}.
     * {@code target} must exist in the list.
     * The entry identity of {@code editedEntry} must not be the same as another existing entry in the list.
     */
    public void setEntry(Entry target, Entry editedEntry) {
        requireAllNonNull(target, editedEntry);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EntryNotFoundException();
        }

        if (!target.isSameEntry(editedEntry) && contains(editedEntry)) {
            throw new DuplicateEntryException();
        }

        internalList.set(index, editedEntry);
    }

    /**
     * Replaces the contents of this list with {@code entries}.
     * {@code entries} must not contain duplicate entries.
     */
    public void setEntries(List<Entry> entries) {
        requireAllNonNull(entries);
        if (!entriesAreUnique(entries)) {
            throw new DuplicateEntryException();
        }
        internalList.setAll(entries);
    }

    public void setEntries(UniqueEntryList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Removes the equivalent entry from the list.
     * The entry must exist in the list.
     */
    public void remove(Entry toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EntryNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Entry> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Entry> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEntryList // instanceof handles nulls
                && internalList.equals(((UniqueEntryList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code entries} contains only unique entries.
     */
    private boolean entriesAreUnique(List<Entry> entries) {
        for (int i = 0; i < entries.size() - 1; i++) {
            for (int j = i + 1; j < entries.size(); j++) {
                if (entries.get(i).isSameEntry(entries.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sorts all the entries in the list by a criterion (calorie value, time or name) in ascending order.
     *
     * @param sortBy sort criterion
     * @throws IllegalValueException if sort type specified is not valid
     */
    public void sortAscending(SortBy sortBy) throws IllegalValueException {
        requireNonNull(sortBy);
        Comparator<Entry> newComparator;
        switch (sortBy.getValue()) {
        case CALORIE:
            newComparator = (e1, e2) -> {
                int firstCompare = Double.compare(e1.getCalorieValue(), e2.getCalorieValue());
                if (firstCompare != 0) {
                    return firstCompare;
                } else {
                    return e1.getDateTime().compareTo(e2.getDateTime());
                }
            };
            break;
        case TIME:
            newComparator = Comparator.comparing(Entry::getDateTime);
            break;
        case NAME:
            newComparator = (e1, e2) -> {
                int firstCompare = e1.getName().toString().toLowerCase()
                        .compareTo(e2.getName().toString().toLowerCase());
                if (firstCompare != 0) {
                    return firstCompare;
                } else {
                    return e1.getDateTime().compareTo(e2.getDateTime());
                }
            };
            break;
        default:
            throw new IllegalValueException("Unknown sort-by type.");
        }
        FXCollections.sort(internalList, newComparator);
    }

    /**
     * Sorts all the entries in the list by a criterion (calorie value, time or name) in descending order.
     *
     * @param sortBy sort criterion
     * @throws IllegalValueException if sort type specified is not valid
     */
    public void sortDescending(SortBy sortBy) throws IllegalValueException {
        sortAscending(sortBy);
        FXCollections.reverse(internalList);
    }
}
