package seedu.address.model.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Restaurant's cuisine in the restaurant book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCuisine(String)}
 */
public class Cuisine {

    public static final String MESSAGE_CONSTRAINTS =
            "Cuisines should only contain alphanumeric characters and spaces";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*|^$";

    public final String cuisine;

    /**
     * Constructs a {@code Cuisine}.
     *
     * @param cuisine A valid cuisine.
     */
    public Cuisine(String cuisine) {
        requireNonNull(cuisine);
        checkArgument(isValidCuisine(cuisine), MESSAGE_CONSTRAINTS);
        this.cuisine = cuisine;
    }

    /**
     * Returns true if a given string is a valid cuisine.
     */
    public static boolean isValidCuisine(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return cuisine;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cuisine // instanceof handles nulls
                && cuisine.equals(((Cuisine) other).cuisine)); // state check
    }

    @Override
    public int hashCode() {
        return cuisine.hashCode();
    }

}
