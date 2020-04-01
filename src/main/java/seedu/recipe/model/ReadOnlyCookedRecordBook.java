package seedu.recipe.model;

import javafx.collections.ObservableList;
import seedu.recipe.model.cooked.Record;

/**
 * Unmodifiable view of a cooked Records
 */
public interface ReadOnlyCookedRecordBook {

    /**
     * Returns an unmodifiable view of the cooked record list.
     * This list contains history of cooked meals
     */
    ObservableList<Record> getRecordsList();
}
