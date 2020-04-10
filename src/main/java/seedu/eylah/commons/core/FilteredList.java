package seedu.eylah.commons.core;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

}
