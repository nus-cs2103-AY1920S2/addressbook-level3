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
    public static final String VALIDATION_REGEX = "^$|\\d+$";
    public final String value;
    public final String maxUsage;

    /**
     * Constructs a {@code Usage}.
     * If {@code maxUsage} is an empty string, it will default to 1.
     *
     * @param maxUsage Maximum number of times the coupon can be used.
     * @param currentValue Current number of times the coupon has been used.
     */
    public Usage(String maxUsage, String currentValue) {
        requireNonNull(maxUsage);
        requireNonNull(currentValue);

        checkArgument(isValidUsage(maxUsage));
        checkArgument(isValidUsage(currentValue));

        if (maxUsage.equals("")) {
            this.maxUsage = "0";
        } else {
            this.maxUsage = maxUsage;
        }

        value = currentValue;
    }

    /**
     * Constructs a {@code Usage} that defaults the current value to 0 .
     * If {@code maxUsage} is an empty string, it will default to 1.
     *
     * @param maxUsage A valid limit number.
     */
    public Usage(String maxUsage) {
        requireNonNull(maxUsage);
        checkArgument(isValidUsage(maxUsage), MESSAGE_CONSTRAINTS);

        if (maxUsage.equals("")) {
            this.maxUsage = "1";
        } else {
            this.maxUsage = maxUsage;
        }

        value = "0";
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
     * Returns true if {@code usage} current value is equals or greater than its {@code maxUsage}.
     *
     * @param usage The usage of the coupon.
     * @return
     */
    public static boolean isUsageAtLimit(Usage usage) {
        Integer currentUsage = Integer.parseInt(usage.value);
        Integer limit = Integer.parseInt(usage.maxUsage);
        return currentUsage >= limit;
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
        return new Usage(maxUsage, finalValue.toString());
    }

    public Integer getMaxUsage() {
        return Integer.parseInt(maxUsage);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Usage
                && value.equals(((Usage) other).value))
                && maxUsage.equals(((Usage) other).maxUsage);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}