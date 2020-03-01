package seedu.foodiebot.model.stall;

import static java.util.Objects.requireNonNull;
import static seedu.foodiebot.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.foodiebot.model.canteen.Stall;
import seedu.foodiebot.model.stall.exceptions.DuplicateStallException;
import seedu.foodiebot.model.stall.exceptions.StallNotFoundException;

/**
 * A list of stalls that enforces uniqueness between its elements and does not allow nulls. A
 * canteen is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such,
 * adding and updating of stalls uses Person#isSamePerson(Person) for equality so as to ensure
 * that the canteen being added or updated is unique in terms of identity in the UniquePersonList.
 * However, the removal of a canteen uses Person#equals(Object) so as to ensure that the canteen
 * with exactly the same fields will be removed.
 *
 * <p>Supports a minimal set of list operations.
 *
 * @see Stall#isSameStall(Stall)
 */
public class UniqueStallList implements Iterable<Stall> {

    private final ObservableList<Stall> internalList = FXCollections.observableArrayList();
    private final ObservableList<Stall> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent canteen as the given argument.
     */
    public boolean contains(Stall toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameStall);
    }

    /**
     * Adds a canteen to the list. The canteen must not already exist in the list.
     */
    public void add(Stall toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStallException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the canteen {@code target} in the list with {@code editedPerson}. {@code target}
     * must exist in the list. The canteen identity of {@code editedPerson} must not be the same as
     * another existing canteen in the list.
     */
    public void setStall(Stall target, Stall editedStall) {
        requireAllNonNull(target, editedStall);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new StallNotFoundException();
        }

        if (!target.isSameStall(editedStall) && contains(editedStall)) {
            throw new DuplicateStallException();
        }

        internalList.set(index, editedStall);
    }

    /**
     * Removes the equivalent stall from the list. The stall must exist in the list.
     */
    public void remove(Stall toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new StallNotFoundException();
        }
    }

    public void setStalls(UniqueStallList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code stalls}. {@code stalls} must not contain
     * duplicate stalls.
     */
    public void setStalls(List<Stall> stalls) {
        requireAllNonNull(stalls);
        if (!stallsAreUnique(stalls)) {
            throw new DuplicateStallException();
        }

        internalList.setAll(stalls);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Stall> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Stall> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueStallList // instanceof handles nulls
            && internalList.equals(((UniqueStallList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code stalls} contains only unique stalls.
     */
    private boolean stallsAreUnique(List<Stall> stalls) {
        for (int i = 0; i < stalls.size() - 1; i++) {
            for (int j = i + 1; j < stalls.size(); j++) {
                if (stalls.get(i).isSameStall(stalls.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
