package seedu.address.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's ingredient in the recipe book.
 * Guarantees: immutable; is valid as declared in {@link #isValidIngredients(String)}
 */
public class Ingredient {

    public static final String MESSAGE_CONSTRAINTS = "Ingredient can take any values, and it should not be blank";

    /*
     * The first character of the ingredients must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String name;
    public String quantity;

    /**
     * Constructs an {@code Ingredient}.
     *
     * @param name A valid ingredient string.
     */
    public Ingredient(String name, String quantity) {
        requireNonNull(name);
        requireNonNull(quantity);
        checkArgument(isValidIngredients(name), MESSAGE_CONSTRAINTS);
        checkArgument(isValidIngredients(quantity), MESSAGE_CONSTRAINTS);
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidIngredients(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return name + " " + quantity;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Ingredient // instanceof handles nulls
                    && name.equals(((Ingredient) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
