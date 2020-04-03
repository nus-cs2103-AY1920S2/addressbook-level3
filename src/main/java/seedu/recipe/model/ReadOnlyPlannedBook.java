package seedu.recipe.model;

import javafx.collections.ObservableList;
import seedu.recipe.model.plan.PlannedDate;
import seedu.recipe.model.plan.PlannedRecipeMap;

/**
 * Unmodifiable view of a schedule book
 */
public interface ReadOnlyPlannedBook {

    /**
     * Returns an unmodifiable view of the planned recipes map.
     * This map will not contain duplicate recipes on the same day.
     */
    ObservableList<PlannedDate> getPlannedList();

    PlannedRecipeMap getPlannedMap();

}
