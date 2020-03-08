package seedu.address.model.food;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Food's carb amount in the diet tracker of EYLAH.
 * Guarantees: immutable; is valid as declared in {@link #isValidCarbs(float)}
 */
public class Carb {

    public static final float FLOAT_ZERO = 0;
    public static final String MESSAGE_CONSTRAINTS =
            "Carb count should be zero or a positive float";
    public final float value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param carb A valid carbs number.
     */
    public Carb(float carb) {
        requireNonNull(carb);
        checkArgument(isValidCarbs(carb), MESSAGE_CONSTRAINTS);
        this.value = carb;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidCarbs(float test) {
        return test >= FLOAT_ZERO;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Carb // instanceof handles nulls
                && value == ((Carb) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Float.valueOf(value).hashCode();
    }
}
