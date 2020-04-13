// @@author Clouddoggo

package seedu.jelphabot.model.productivity;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of productivity that does not allow nulls
 * Only allows set and add list operations.
 */
public class ProductivityList implements Iterable<Productivity> {

    private final ObservableList<Productivity> internalList = FXCollections.observableArrayList();
    private final ObservableList<Productivity> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a productivity to the list.
     */
    public void addProductivity(Productivity toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Clears the list and adds {@code productivity} into the list.
     */
    public void setProductivity(Productivity productivity) {
        requireNonNull(productivity);
        internalList.clear();
        internalList.add(productivity);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Productivity> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Productivity> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof ProductivityList // instanceof handles nulls
                           && internalList.equals(((ProductivityList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
