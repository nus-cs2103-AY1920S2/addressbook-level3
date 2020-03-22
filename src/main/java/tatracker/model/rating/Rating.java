package tatracker.model.rating;

import static tatracker.commons.util.AppUtil.checkArgument;

/**
 * Represents a Rating in the TA-Tracker. A Rating is an integer on a scale from 1 - 5,
 * where 1 represents the poorest rating, and 5 represents the best rating.
 * Guarantees: immutable; rating is valid as declared in {@link #isValidRating(int)}
 */
public class Rating {

    public static final String MESSAGE_CONSTRAINTS = "Ratings should be a number"
            + " between 1 (POOR) to 5 (EXCELLENT) inclusive";

    private static final int MIN_RATING = 1;
    private static final int MAX_RATING = 5;

    public final int value;

    /**
     * Constructs a {@code Rating}.
     *
     * @param value A valid rating on a scale from 1 (POOR) to 5 (EXCELLENT).
     */
    public Rating(int value) {
        checkArgument(isValidRating(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    /**
     * Returns true if a given number is a valid rating.
     */
    public static boolean isValidRating(int test) {
        return MIN_RATING <= test && test <= MAX_RATING;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rating // instanceof handles nulls
                && value == ((Rating) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return String.format("[%d]", value);
    }

}
