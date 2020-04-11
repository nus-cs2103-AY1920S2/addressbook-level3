package seedu.recipe.model.recipe.exceptions;

/**
 * Signals that the operation will result in duplicate Recipes (Recipes are considered duplicates if they have the same
 * identity).
 */
public class DuplicateRecordException extends RuntimeException {
    public DuplicateRecordException() {
        super("Operation would result in duplicate recipes");
    }
}
