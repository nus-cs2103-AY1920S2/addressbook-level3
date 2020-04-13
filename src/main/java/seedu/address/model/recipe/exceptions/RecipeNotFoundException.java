package seedu.address.model.recipe.exceptions;

/**
 *  Returns that the recipe is not found within the list
 */
public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException() {
        super("Recipe is not found in the list.");
    }
}
