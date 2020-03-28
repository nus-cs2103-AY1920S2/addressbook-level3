package seedu.recipe.model.plan;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.recipe.model.recipe.Recipe;

/**
 * Unmodifiable view of a schedule book
 */
public interface ReadOnlyPlannedBook {

    /**
     * Returns an unmodifiable view of the planned recipes map.
     * This map will not contain duplicate recipes on the same day.
     */
    ObservableMap<Date, ObservableList<Recipe>> getPlannedMap();
}
