package fithelper.model.profile;

import static fithelper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a User's height in FitHelper profile.
 * Guarantees: immutable; is valid as declared in {@link #isValidHeight(String)}
 */
public class Height {

    public static final String MESSAGE_CONSTRAINTS = "Height should a positive number united in cm.";
    public static final double T = 0.0001; // tolerance for double comparision.

    public final double value;

    /**
     * Constructs an {@code Height}.
     *
     * @param height A valid height of user.
     */
    public Height(String height) {
        requireNonNull(height);
        checkArgument(isValidHeight(height), MESSAGE_CONSTRAINTS);
        value = Double.parseDouble(height);
    }

    /**
     * Returns true if a given string is a valid height.
     */
    public static boolean isValidHeight(String test) {
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
        return String.format("%.1f", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Height // instanceof handles nulls
                && Math.abs(value - ((Height) other).value) < T); // state check
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
