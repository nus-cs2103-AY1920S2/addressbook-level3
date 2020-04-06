package csdev.couponstash.model.coupon;

import static java.util.Objects.requireNonNull;


/**
 * Represents a Coupon's state of archive in the CouponStash.
 * Guarantees: immutable; is valid as declared in {@link #isValidState(String)}
 */
public class Archived {

    public static final String MESSAGE_CONSTRAINTS = "Archive state should only be true or false";

    public static final String VALIDATION_REGEX = "^(true|false)$";

    public final Boolean state;


    /**
     * Constructs a false {@code state} of archival.
     */
    public Archived() {
        this.state = false;
    }

    /**
     * Constructs a {@code state} of {@code Archived}.
     *
     * @param state A valid boolean state of archival.
     */
    public Archived(Boolean state) {
        requireNonNull(state);
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
        return state.toString();
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
