package seedu.address.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's ingredients in the recipe book.
 * Guarantees: immutable; is valid as declared in {@link #isValidIngredients(String)}
 */
public class Ingredients {

    public static final String MESSAGE_CONSTRAINTS = "Ingredients can take any values, and it should not be blank";

    /*
     * The first character of the ingredients must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Ingredients}.
     *
     * @param ingredients A valid ingredient string.
     */
    public Ingredients(String ingredients) {
        requireNonNull(ingredients);
        checkArgument(isValidIngredients(ingredients), MESSAGE_CONSTRAINTS);
        value = ingredients;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidIngredients(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Ingredients // instanceof handles nulls
                    && value.equals(((Ingredients) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
