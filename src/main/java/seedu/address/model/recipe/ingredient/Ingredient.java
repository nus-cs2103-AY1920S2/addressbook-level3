package seedu.address.model.recipe.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Ingredient in the recipe book.
 */

public abstract class Ingredient implements Comparable<Ingredient> {

    public static final String MESSAGE_CONSTRAINTS = "Ingredient names should contain only "
            + "alphabetical letters or spaces";
    public static final String MESSAGE_MISSING_FIELD = "Ingredients require a quantity and name written in the format: "
            + "Tag/Quantity, Name\n"
            + "For example, to enter 50g of Broccoli, the format is iv/50, Broccoli";
    public static final String VALIDATION_REGEX = "^[ A-Za-z]+$+";

    protected IngredientType ingredientType;
    protected String ingredientName;
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

    public String getName() {
        return ingredientName;
    }

    public IngredientType getType() {
        return ingredientType;
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Ingredient // instanceof handles nulls
                && ingredientName.equals(((Ingredient) other).getIngredientName()) // state check
                && quantity == ((Ingredient) other).getQuantity()); // state check
    }

    @Override
    public int compareTo(Ingredient other) {
        return ingredientName.compareTo(other.ingredientName); // ingredients are sorted alphabetically
    }
}
