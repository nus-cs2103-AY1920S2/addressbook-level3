package seedu.address.model.ingredient.exceptions;

/**
 * Returns that the ingredient quantity is not positive
 */

public class NonPositiveIngredientQuantityException extends RuntimeException {
    public NonPositiveIngredientQuantityException() {
        super("Ingredient quantity must be positive.");
    }
}
