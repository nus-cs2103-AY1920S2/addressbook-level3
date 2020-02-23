package seedu.address.model.entry;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of Entrys that enforces uniqueness between its elements and does not allow nulls.
 * A Entry is considered unique by comparing using {@code Entry#isSameEntry(Entry)}. As such, adding and updating of
 * Entrys uses Entry#isSameEntry(Entry) for equality so as to ensure that the Entry being added or updated is
 * unique in terms of identity in the UniqueEntryList. However, the removal of a Entry uses Entry#equals(Object) so
 * as to ensure that the Entry with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Entry#isSameEntry(Entry)
 */
public class UniqueEntryList implements Iterable<Entry> {

    private final ObservableList<Entry> internalEntryList = FXCollections.observableArrayList();
    private final ObservableList<Entry> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalEntryList);

    /**
     * Returns true if the list contains an equivalent Entry as the given argument.
     */
    public boolean contains(Entry toCheck) {
        requireNonNull(toCheck);
        return internalEntryList.stream().anyMatch(toCheck::isSameEntry);
    }

    /**
     * Adds a Entry to the list.
     * The Entry must not already exist in the list.
     */
    public void add(Entry toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalEntryList.add(toAdd);
    }

    /**
     * Replaces the Entry {@code target} in the list with {@code editedEntry}.
     * {@code target} must exist in the list.
     * The Entry identity of {@code editedEntry} must not be the same as another existing Entry in the list.
     */
    public void setEntry(Entry target, Entry editedEntry) {
        requireAllNonNull(target, editedEntry);

        int index = internalEntryList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSameEntry(editedEntry) && contains(editedEntry)) {
            throw new DuplicatePersonException();
        }

        internalEntryList.set(index, editedEntry);
    }

    /**
     * Removes the equivalent Entry from the list.
     * The Entry must exist in the list.
     */
    public void remove(Entry toRemove) {
        requireNonNull(toRemove);
        if (!internalEntryList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setEntrys(UniqueEntryList replacement) {
        requireNonNull(replacement);
        internalEntryList.setAll(replacement.internalEntryList);
    }

    /**
     * Replaces the contents of this list with {@code Entrys}.
     * {@code Entrys} must not contain duplicate Entrys.
     */
    public void setEntrys(List<Entry> Entrys) {
        requireAllNonNull(Entrys);
        if (!EntrysAreUnique(Entrys)) {
            throw new DuplicatePersonException();
        }

        internalEntryList.setAll(Entrys);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Entry> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Entry> iterator() {
        return internalEntryList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEntryList // instanceof handles nulls
                && internalEntryList.equals(((UniqueEntryList) other).internalEntryList));
    }

    @Override
    public int hashCode() {
        return internalEntryList.hashCode();
    }

    /**
     * Returns true if {@code Entrys} contains only unique Entrys.
     */
    private boolean EntrysAreUnique(List<Entry> Entrys) {
        for (int i = 0; i < Entrys.size() - 1; i++) {
            for (int j = i + 1; j < Entrys.size(); j++) {
                if (Entrys.get(i).isSameEntry(Entrys.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
