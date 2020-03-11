package csdev.couponstash.model.coupon;

import static csdev.couponstash.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Coupon's usage in the CouponStash.
 * Guarantees: immutable; is valid as declared in {@link #isValidUsage(String)}
 */
public class Usage {

    public static final String MESSAGE_CONSTRAINTS =
            "Usage should only contain numbers, and it should be at least be 0";
    public static final String VALIDATION_REGEX = "^$|\\d$";
    public final String value;

    /**
     * Constructs a {@code Usage}
     *
     * @param usage A valid usage number.
     */
    public Usage(String usage) {
        requireNonNull(usage);
        checkArgument(isValidUsage(usage), MESSAGE_CONSTRAINTS);

        // If usage is not defined by user, value is defaulted to 1
        if (usage.equals("")) {
            value = "0";
        } else {
            value = usage;
        }
    }

    /**
     * Returns true if a given string is a valid usage number.
     * @param test
     * @return
     */
    public static boolean isValidUsage(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns new {@code Usage} with an increase of 1 in value.
     */
    public Usage increaseUsageByOne() {
        return increaseUsage("1");
    }

    /**
     * Returns new {@code Usage} with an increase of {@code numberOfTimes} in value.
     */
    public Usage increaseUsage(String numberOfTimes) {
        Integer currentValue = Integer.parseInt(value);
        Integer finalValue = Integer.parseInt(numberOfTimes) + currentValue;
        return new Usage(finalValue.toString());
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Usage
                && value.equals(((Usage) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}