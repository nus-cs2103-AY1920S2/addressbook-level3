package fithelper.model.weight;

import static fithelper.commons.util.AppUtil.checkArgument;
import static fithelper.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a User's Bmi in one weight record.
 * Guarantees: immutable; is valid as declared in {@link #isValidBmi(double test)}
 */
public class Bmi {

    public static final String MESSAGE_CONSTRAINTS = "Bmi should be any positive number.";
    public static final double T = 0.0001; // tolerance for double comparision.

    public final double value;

    /**
     * Constructs an {@code Bmi}.
     *
     * @param bmi A valid bmi of user.
     */
    public Bmi(double bmi) {
        requireAllNonNull(bmi);
        checkArgument(isValidBmi(bmi), MESSAGE_CONSTRAINTS);
        this.value = bmi;
    }

    /**
     * Returns true if a given string is a valid Bmi.
     */
    public static boolean isValidBmi(double test) {
        return test > 0;
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Bmi // instanceof handles nulls
                && Math.abs(value - ((Bmi) other).value) < T); // state check
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
