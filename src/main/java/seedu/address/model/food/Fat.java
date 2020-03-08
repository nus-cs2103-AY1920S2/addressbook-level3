package seedu.address.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's carb amount in the diet tracker of EYLAH.
 * Guarantees: immutable; is valid as declared in {@link #isValidFat(float)}
 */
public class Fat {

    public static final float FLOAT_ZERO = 0;
    public static final String MESSAGE_CONSTRAINTS =
            "Fat count should be zero or a positive float";
    public final float value;

    /**
     * Constructs a {@code Fat}.
     *
     * @param fat A valid fat number.
     */
    public Fat(float fat) {
        requireNonNull(fat);
        checkArgument(isValidFat(fat), MESSAGE_CONSTRAINTS);
        this.value = fat;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidFat(float test) {
        return test >= FLOAT_ZERO;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Fat // instanceof handles nulls
                && value == ((Fat) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Float.valueOf(value).hashCode();
    }
}
