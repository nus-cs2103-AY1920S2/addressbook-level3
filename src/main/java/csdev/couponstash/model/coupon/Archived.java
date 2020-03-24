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

    public final String state;


    /**
     * Constructs a false {@code state} of archival.
     */
    public Archived() {
        this.state = "false";
    }

    /**
     * Constructs a {@code state} of {@code Archived}.
     *
     * @param state A valid state of archival.
     */
    public Archived(String state) {
        requireNonNull(state);
        checkArgument(isValidState(state), MESSAGE_CONSTRAINTS);
        this.state = state;
    }

    /**
     * Returns true if a given string is a valid state of archival.
     */
    public static boolean isValidState(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return state;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Archived
                && state.equals(((Archived) other).state));
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }
}
