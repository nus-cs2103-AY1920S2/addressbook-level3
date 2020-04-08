package seedu.recipe.model.recipe.ingredient;

import java.util.Comparator;

import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.ingredient.Ingredient;

/**
 * Sorts the Ingredients by their names in alphabetical order.
 */
public class SortByIngredientName implements Comparator<Ingredient> {

    @Override
    public int compare(Ingredient a, Ingredient b) {
        String nameOfA = a.getIngredientName();
        String nameOfB = b.getIngredientName();
        return nameOfA.compareTo(nameOfB);
    }
}
