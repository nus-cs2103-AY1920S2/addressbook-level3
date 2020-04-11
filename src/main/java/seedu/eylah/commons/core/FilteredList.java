package seedu.eylah.commons.core;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.eylah.diettracker.model.DietModelManager;


/**
 * Wraps an ObservableList and filters its content using the provided Predicate.
 * All changes in the ObservableList are propagated immediately
 * to the FilteredList.
 */
public class FilteredList<E> {

    private ObservableList<E> source;
    private ObservableList<E> filteredList;

    public FilteredList(ObservableList<E> source, Predicate<E> predicate) {
        this.source = source;
        setPredicate(predicate);
    }

    public FilteredList(ObservableList<E> source) {
        this(source, null);
    }

    public final void setPredicate(Predicate<E> predicate) {
        if (predicate != null) {
            filteredList = FXCollections.observableList(source.stream().filter(predicate).collect(Collectors.toList()));
        } else {
            filteredList = source;
        }
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return source.size();
    }

    public E get(int index) {
        return source.get(index);
    }

    public ObservableList<E> getFilteredList() {
        return filteredList;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof FilteredList)) {
            return false;
        }

        // state check
        FilteredList other = (FilteredList) obj;
        return source.equals(other.source)
                && filteredList.equals(other.filteredList);
    }

}
