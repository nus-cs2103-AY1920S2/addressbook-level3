package seedu.recipe.model.plan;

import java.util.List;

import javafx.collections.ObservableMap;
import seedu.recipe.model.recipe.Recipe;

/**
 * Unmodifiable view of a schedule book
 */
public interface ReadOnlyScheduleBook {

    /**
     * Returns an unmodifiable view of the scheduled recipes map.
     * This map will not contain duplicate recipes on the same day.
     */
    ObservableMap<Date, List<Recipe>> getScheduleMap();
}
