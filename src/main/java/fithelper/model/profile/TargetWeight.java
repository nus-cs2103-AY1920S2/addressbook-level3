package fithelper.model.profile;

import static fithelper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a User's TargetWeight in FitHelper profile.
 * Guarantees: immutable; is valid as declared in {@link #isValidTargetWeight(String)}
 */
public class TargetWeight {

    public static final String MESSAGE_CONSTRAINTS = "Target Weight should a positive number united in kg.";
    public static final double T = 0.0001; // tolerance for double comparision.

    public final double value;

    /**
     * Constructs an {@code TargetWeight}.
     *
     * @param targetWeight A valid TargetWeight of user.
     */
    public TargetWeight(String targetWeight) {
        requireNonNull(targetWeight);
        checkArgument(isValidTargetWeight(targetWeight), MESSAGE_CONSTRAINTS);
        value = Double.parseDouble(targetWeight);
    }

    /**
     * Returns true if a given string is a valid TargetWeight.
     */
    public static boolean isValidTargetWeight(String test) {
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
                || (other instanceof TargetWeight // instanceof handles nulls
                && Math.abs(value - ((TargetWeight) other).value) < T); // state check
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
