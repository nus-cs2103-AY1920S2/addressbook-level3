package seedu.address.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's carb amount in the diet tracker of EYLAH.
 * Guarantees: immutable; is valid as declared in {@link #isValidProtein(float)}
 */
public class Protein {

    public static final float FLOAT_ZERO = 0;
    public static final String MESSAGE_CONSTRAINTS =
            "Protein count should be zero or a positive float";
    public final float value;

    /**
     * Constructs a {@code Protein}.
     *
     * @param protein A valid protein number.
     */
    public Protein(float protein) {
        requireNonNull(protein);
        checkArgument(isValidProtein(protein), MESSAGE_CONSTRAINTS);
        this.value = protein;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidProtein(float test) {
        return test >= FLOAT_ZERO;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Protein // instanceof handles nulls
                && value == ((Protein) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Float.valueOf(value).hashCode();
    }
}
