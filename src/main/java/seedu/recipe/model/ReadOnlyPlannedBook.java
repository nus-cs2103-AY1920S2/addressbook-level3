package seedu.recipe.model;

import javafx.collections.ObservableList;
import seedu.recipe.model.plan.Plan;
import seedu.recipe.model.plan.PlannedRecipeMap;

/**
 * Unmodifiable view of a schedule book
 */
public interface ReadOnlyPlannedBook {

    /**
     * Returns an unmodifiable view of the planned recipes list.
     * The list will not have duplicate recipes planned on the same day.
     */
    ObservableList<Plan> getPlannedList();

    /**
     * Returns an unmodifiable view of the planned recipes map.
     */
    PlannedRecipeMap getPlannedMap();

}
