package seedu.recipe.model.plan;

/**
 * Signals that the operation will result in duplicate Planned Recipes
 */
public class DuplicatePlannedRecipeException extends RuntimeException {
    public DuplicatePlannedRecipeException() {
        super("Operation would result in duplicate planned recipes");
    }
}
