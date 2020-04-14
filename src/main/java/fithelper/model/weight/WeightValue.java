package fithelper.model.weight;

import static fithelper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a User's WeightValue in one weight record.
 * Guarantees: immutable; is valid as declared in {@link #isValidWeightValue(String)}
 */
public class WeightValue {

    public static final String MESSAGE_CONSTRAINTS = "WeightValue should a positive number united in kg.";
    public static final double T = 0.0001; // tolerance for double comparision.

    public final double value;

    /**
     * Constructs an {@code WeightValue}.
     *
     * @param weightValue A valid WeightValue of user.
     */
    public WeightValue(String weightValue) {
        requireNonNull(weightValue);
        checkArgument(isValidWeightValue(weightValue), MESSAGE_CONSTRAINTS);
        value = Double.parseDouble(weightValue);
    }

    /**
     * Returns true if a given string is a valid WeightValue.
     */
    public static boolean isValidWeightValue(String test) {
        double testValue;
        try {
            testValue = Double.parseDouble(test);
        } catch (NumberFormatException e) {
            return false;
        }
        return testValue > 0;
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WeightValue // instanceof handles nulls
                && Math.abs(value - ((WeightValue) other).value) < T); // state check
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
