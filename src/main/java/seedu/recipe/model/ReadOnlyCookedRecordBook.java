package seedu.recipe.model;

import javafx.collections.ObservableList;
import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.goal.GoalCount;

/**
 * Unmodifiable view of a cooked Records
 */
public interface ReadOnlyCookedRecordBook {

    /**
     * Returns an unmodifiable view of the cooked record list.
     * This list contains history of cooked meals
     */
    ObservableList<Record> getRecordsList();

    ObservableList<GoalCount> getFilteredGoalsTally();
}
