package fithelper.model.profile;

import static fithelper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents the User's age in FitHelper profile.
 * Guarantees: immutable; is valid as declared in {@link #isValidAge(String)}
 */
public class Age {

    public static final String MESSAGE_CONSTRAINTS = "Your age should a positive integer.";

    public final int value;

    /**
     * Constructs an {@code Type}.
     *
     * @param age A valid user' age.
     */
    public Age(String age) {
        requireNonNull(age);
        checkArgument(isValidAge(age), MESSAGE_CONSTRAINTS);
        value = Integer.parseInt(age);
    }

    /**
     * Returns true if a given string is a valid age.
     */
    public static boolean isValidAge(String test) {
        double testValue;
        try {
            testValue = Integer.parseInt(test);
        } catch (NumberFormatException e) {
            return false;
        }
        return testValue > 0;
    }

    @Override
    public String toString() {
        return String.format("%d", value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Age // instanceof handles nulls
                && value == ((Age) other).value); // state check
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
