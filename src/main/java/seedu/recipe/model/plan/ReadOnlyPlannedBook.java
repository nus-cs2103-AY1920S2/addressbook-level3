package seedu.recipe.model.plan;

import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.recipe.model.recipe.Recipe;

/**
 * Unmodifiable view of a schedule book
 */
public interface ReadOnlyPlannedBook {

    /**
     * Returns an unmodifiable view of the planned recipes map.
     * This map will not contain duplicate recipes on the same day.
     */
    ObservableList<PlannedRecipe> getPlannedList();

    Map<Recipe, List<PlannedRecipe>> getPlannedRecipeMap();

}
