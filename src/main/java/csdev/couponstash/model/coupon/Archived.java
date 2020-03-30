package csdev.couponstash.model.coupon;

import static csdev.couponstash.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;


/**
 * Represents a Coupon's state of archive in the CouponStash.
 * Guarantees: immutable; is valid as declared in {@link #isValidState(String)}
 */
public class Archived {

    public static final String MESSAGE_CONSTRAINTS = "Archive state should only be true or false";

    public static final String VALIDATION_REGEX = "^(true|false)$";

    public final String value;


    /**
     * Constructs a false {@code state} of archival.
     */
    public Archived() {
        this.value = "false";
    }

    /**
     * Constructs a {@code state} of {@code Archived}.
     *
     * @param value A valid state of archival.
     */
    public Archived(String value) {
        requireNonNull(value);
        checkArgument(isValidState(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid state of archival.
     */
    public static boolean isValidState(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Archived
                && value.equals(((Archived) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
