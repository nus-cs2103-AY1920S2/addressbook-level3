package seedu.address.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's carb amount in the diet tracker of EYLAH.
 * Guarantees: immutable; is valid as declared in {@link #isValidSugar(float)}
 */
public class Sugar {

    public static final float FLOAT_ZERO = 0;
    public static final String MESSAGE_CONSTRAINTS =
            "Sugar count should be zero or a positive float";
    public final float value;

    /**
     * Constructs a {@code Sugar}.
     *
     * @param sugar A valid sugar number.
     */
    public Sugar(float sugar) {
        requireNonNull(sugar);
        checkArgument(isValidSugar(sugar), MESSAGE_CONSTRAINTS);
        this.value = sugar;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidSugar(float test) {
        return test >= FLOAT_ZERO;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Sugar // instanceof handles nulls
                && value == ((Sugar) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Float.valueOf(value).hashCode();
    }
}
