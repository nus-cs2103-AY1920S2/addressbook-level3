package seedu.address.model.ingredient.exceptions;

/**
 * Returns that the ingredient is not found within the list
 */

public class IngredientNotFoundException extends RuntimeException {
    public IngredientNotFoundException() {
        super("Ingredient is not found in the list.");
    }
}
