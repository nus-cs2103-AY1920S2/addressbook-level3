package seedu.address.model.ingredient.exceptions;

/**
 * Returns that the ingredient is incompatible with an existing ingredient in the list.
 */
public class IncompatibleIngredientException extends RuntimeException {
    public IncompatibleIngredientException() {
        super("The ingredient you are trying to modify has a different unit.");
    }

}
