package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Ingredient in the recipe book.
 */

public abstract class Ingredient {

    public static final String MESSAGE_CONSTRAINTS = "Ingredient names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String ingredientName;
    public IngredientType ingredientType;
    protected double quantity;
    protected Unit unit;

    public Ingredient(String ingredientName, double quantity) {
        requireNonNull(quantity, ingredientName);
        checkArgument(isValidIngredientName(ingredientName), MESSAGE_CONSTRAINTS);
        this.quantity = quantity;
        this.ingredientName = ingredientName;
    }

    /**
     * Returns true if a given string is a valid ingredient name.
     */
    public static boolean isValidIngredientName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public double getQuantity() {
        return quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    @Override
    public String toString() {
        return quantity + unit.toString() + " " + ingredientName;
    }
}
