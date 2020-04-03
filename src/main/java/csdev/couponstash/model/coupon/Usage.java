package csdev.couponstash.model.coupon;

import static csdev.couponstash.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Coupon's usage in the CouponStash.
 * Guarantees: immutable; is valid as declared in {@link #isValidUsage(String)}
 */
public class Usage {

    public static final String MESSAGE_CONSTRAINTS =
            "Usage should only contain numbers, and it should at least be a value of 0";
    public static final String MESSAGE_UNEDITABLE = "Usage cannot be added or edited. "
            + "This is to keep the savings consistent.";
    public static final String VALIDATION_REGEX = "^\\d+$";
    public final String value;

    /**
     * Constructs a {@code Usage}.
     *
     * @param usage Valid usage number.
     */
    public Usage(String usage) {
        requireNonNull(usage);
        checkArgument(isValidUsage(usage), MESSAGE_CONSTRAINTS);
        value = usage;
    }

    /**
     * Constructs a {@code Usage} that defaults the current value to 0.
     */
    public Usage() {
        this("0");
    }

    /** Returns true if a given string is a valid usage number.
     *
     * @param test String to be validated.
     */
    public static boolean isValidUsage(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if {@code usage} current value is equals or greater than its {@code maxUsage}.
     *
     * @param usage The usage of the coupon.
     */
    public static boolean isUsageAtLimit(Usage usage, Limit limit) {
        Double currentUsage = Double.parseDouble(usage.value);
        Double usageLimit = limit.getParsedLimit();
        return currentUsage >= usageLimit;
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

    public String toUiLabelText() {
        return String.format("You have used it %s time(s)", value);
    }

    @Override
    public String toString() {
        return value;
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
