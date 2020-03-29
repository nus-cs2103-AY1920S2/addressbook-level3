package seedu.recipe.model;

import javafx.collections.ObservableList;
import seedu.recipe.model.cooked.CookedRecord;

/**
 * Unmodifiable view of a cooked Records
 */
public interface ReadOnlyCookedRecord {

    /**
     * Returns an unmodifiable view of the cooked record list.
     * This list contains history of cooked meals
     */
    ObservableList<CookedRecord> getcookedRecordList();

}