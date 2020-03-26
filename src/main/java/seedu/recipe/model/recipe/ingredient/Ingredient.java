package seedu.recipe.model.recipe.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

/**
 * Represents an Ingredient in the recipe book.
 */
public abstract class Ingredient implements Comparable<Ingredient> {

    public static final String MESSAGE_CONSTRAINTS = "Ingredient names should contain only "
            + "alphabetical letters, spaces, or special characters from this set {&, %, (, ), \\-}.";
    public static final String MESSAGE_MISSING_FIELD = "Ingredients require a quantity and name written in the format: "
            + "Tag/Quantity, Name\n"
            + "For example, to enter 50g of Broccoli, the format is iv/50g, Broccoli";
    public static final String VALIDATION_REGEX = "^[A-Za-z()&%\\-]+$+";

    protected String ingredientName;
    protected Quantity quantity;

    public Ingredient(String ingredientName, Quantity quantity) {
        requireNonNull(quantity, ingredientName);
        checkArgument(isValidIngredientName(ingredientName), MESSAGE_CONSTRAINTS);
        this.quantity = quantity;
        this.ingredientName = ingredientName;
    }

    /**
     * Overloaded Ingredient constructor for the purpose of filtering by ingredient name.
     */
    public Ingredient(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    /**
     * Returns true if a given string is a valid ingredient name.
     */
    public static boolean isValidIngredientName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    @Override
    public String toString() {
        if (quantity == null) {
            return "";
        }
        return quantity + " " + ingredientName + " ";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Ingredient // instanceof handles nulls
                && ingredientName.toLowerCase().equals(((Ingredient) other).getIngredientName().toLowerCase()));
    }

    @Override
    public int hashCode() {
        return ingredientName.toLowerCase().hashCode();
    }

    @Override
    public int compareTo(Ingredient other) {
        // ingredients are sorted alphabetically
        return ingredientName.toLowerCase().compareTo(other.ingredientName.toLowerCase());
    }
}
