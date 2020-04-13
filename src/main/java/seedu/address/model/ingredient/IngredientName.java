package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the name of an ingredient.
 * Guarantees: immutable; is valid as declared in {@link #isValidIngredientName(String)}
 */
public class IngredientName {

    public static final String MESSAGE_CONSTRAINTS =
            "Ingredient names should only contain alphanumeric characters, spaces, and punctuation, "
            + "and it should not be blank";

    /*
     * The first character of the ingredient name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}\\-'% ]*";

    public final String ingredientName;

    /**
     * Constructs an {@code IngredientName}.
     *
     * @param ingredientName A valid ingredient name.
     */
    public IngredientName(String ingredientName) {
        requireNonNull(ingredientName);
        checkArgument(isValidIngredientName(ingredientName), MESSAGE_CONSTRAINTS);
        this.ingredientName = ingredientName.trim();
    }

    /**
     * Returns true if a given string is a valid ingredient name.
     */
    public static boolean isValidIngredientName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return ingredientName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientName // instanceof handles nulls
                && ingredientName.toLowerCase()
                        .equals(((IngredientName) other).ingredientName.toLowerCase())); // state check
    }

    @Override
    public int hashCode() {
        return ingredientName.toLowerCase().hashCode();
    }

}
