package seedu.address.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the Serving size in the recipe book.
 * Guarantees: immutable; calorie is valid as declared in {@link #isValidServing(String)}
 */
public class Serving {

    public static final String MESSAGE_CONSTRAINTS = "Serving size should be > 0";

    public final int serving;

    /**
     * Constructs a {@code Serving size}.
     *
     * @param serving A valid serving size.
     */
    public Serving(String serving) {
        requireNonNull(serving);
        checkArgument(isValidServing(serving), MESSAGE_CONSTRAINTS);
        this.serving = Integer.parseInt(serving);
    }

    /**
     * Returns true if a given string is a valid serving size.
     */
    public static boolean isValidServing(String test) {
        return Integer.parseInt(test) > 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Serving // instanceof handles nulls
                && serving == (((Serving) other).serving)); // state check
    }

    @Override
    public int hashCode() {
        return String.valueOf(serving).hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "Serves: " + serving + " pax";
    }

}
