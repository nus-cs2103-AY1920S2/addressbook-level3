package csdev.couponstash.model.coupon;

import static csdev.couponstash.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import csdev.couponstash.commons.util.StringUtil;

/**
 * Represents a Coupon's usage limit in the CouponStash.
 * Guarantees: immutable; is valid as declared in {@link #isValidLimit(String)}
 */
public class Limit {
    public static final String MESSAGE_CONSTRAINTS =
            "Limit should only contain numbers with a minimum value of 1 "
            + "or a number less than 1 to indicate unlimited usage";
    public static final String VALIDATION_REGEX = "^(\\+|-)?\\d+$";
    public final int value;

    /**
     * Constructs a {@code Limit} with a String parameter.
     * If {@code limit} is given as a hyphen only or a negative number string,
     * it will default to max value to indicate limitless usage of the encapsulating Coupon.
     *
     * @param limit Maximum number of times the coupon can be used.
     */
    public Limit(String limit) {
        requireNonNull(limit);
        checkArgument(isValidLimit(limit));

        int intValue = Integer.parseInt(limit);
        if (intValue <= 0) {
            this.value = Integer.MAX_VALUE;
        } else {
            this.value = intValue;
        }
    }

    /**
     * Constructs a {@code Limit} with an int parameter.
     * Refer to {@Link #Limit(String)}.
     */
    public Limit(int limit) {
        requireNonNull(limit);

        if (limit <= 0) {
            this.value = Integer.MAX_VALUE;
        } else {
            this.value = limit;
        }
    }

    /**
     * Returns true if a given string is a valid usage limit.
     *
     * @param test String to be validated.
     */
    public static boolean isValidLimit(String test) {
        return test.matches(VALIDATION_REGEX) && !StringUtil.isIntegerOverflow(test);
    }

    public int getLimit() {
        return value;
    }

    /**
     * Returns a string value of limit.
     * If value is at Integer.MAX_VALUE, it returns a more readable "Infinity" to indicate
     * an limitless use of the coupon.
     */
    public String toUiText() {
        return value == Integer.MAX_VALUE ? "Infinity" : String.valueOf(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Limit)
                && value == ((Limit) other).value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
