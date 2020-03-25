package cookbuddy.model;

import cookbuddy.model.recipe.Recipe;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an recipe book
 */
public interface ReadOnlyRecipeBook {

    /**
     * Returns an unmodifiable view of the recipe list.
     * This list will not contain any duplicate recipe.
     */
    ObservableList<Recipe> getRecipeList();

}
