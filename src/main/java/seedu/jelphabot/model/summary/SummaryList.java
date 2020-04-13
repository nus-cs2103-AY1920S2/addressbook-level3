//@@author eedenong
package seedu.jelphabot.model.summary;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of summary that does not allow nulls
 * Only allows set and add list operations.
 */
public class SummaryList implements Iterable<Summary> {

    private final ObservableList<Summary> internalList = FXCollections.observableArrayList();
    private final ObservableList<Summary> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a summary to the list
     */
    public void addSummary(Summary summaryToAdd) {
        requireNonNull(summaryToAdd);
        internalList.add(summaryToAdd);
    }

    public void setSummary(Summary summary) {
        requireNonNull(summary);
        internalList.clear();
        internalList.add(summary);
    }

    public ObservableList<Summary> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Summary> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof SummaryList // instanceof handles nulls
                           && internalList.equals(((SummaryList) other).internalList));

    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
