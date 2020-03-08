package fithelper.model;

import fithelper.model.entry.Entry;
import fithelper.model.person.Person;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a log book
 */
public interface ReadOnlyFitHelper {

    /**
     * Returns an unmodifiable view of the food entry list.
     * This list will not contain any duplicate entries.
     */
    ObservableList<Entry> getFoodList();

    /**
     * Returns an unmodifiable view of the sports entry list.
     * This list will not contain any duplicate entries.
     */
    ObservableList<Entry> getSportsList();
}

