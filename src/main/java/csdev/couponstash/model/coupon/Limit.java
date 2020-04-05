package csdev.couponstash.model.coupon;

import static csdev.couponstash.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Coupon's usage limit in the CouponStash.
 * Guarantees: immutable; is valid as declared in {@link #isValidLimit(String)}
 */
public class Limit {
    public static final String MESSAGE_CONSTRAINTS =
            "Limit should only contain numbers with a minimum value of 1 "
            + "or a number less than 1 to indicate unlimited usage";
    public static final String VALIDATION_REGEX = "^(\\+|-)?\\d+$|Infinity";
    public final String value;

    /**
     * Constructs a {@code Limit}.
     * If {@code limit} is an empty String, it will default to 1.
     * If {@code limit} is given as a hyphen only or a negative number string,
     * it will default to max value to indicate limitless usage of the encapsulating Coupon.
     *
     * @param limit Maximum number of times the coupon can be used.
     */
    public Limit(String limit) {
        requireNonNull(limit);
        checkArgument(isValidLimit(limit));

        if ("Infinity".equals(limit) || Double.parseDouble(limit) <= 0) {
            this.value = String.valueOf(Double.POSITIVE_INFINITY);
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
        return test.matches(VALIDATION_REGEX);
    }

    public String getLimit() {
        return value;
    }

    public Double getParsedLimit() {
        if ("Infinity".equals(value)) {
            return Double.POSITIVE_INFINITY;
        } else {
            return Double.parseDouble(value);
        }
    }

    public String toUiLabelText() {
        return String.format("You can only use it %s time(s).", value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Limit)
                && value.equals(((Limit) other).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
