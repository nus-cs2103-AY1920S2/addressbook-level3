package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Client's sport in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSport(String)}
 */
public class Sport {

    public static final String MESSAGE_CONSTRAINTS = "Sport can take on any name and can be left blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    public final String sportName;

    /**
     * Constructs an {@code Sport}.
     *
     * @param sport A valid Sport.
     */
    public Sport(String sport) {
        requireNonNull(sport);
        checkArgument(isValidSport(sport), MESSAGE_CONSTRAINTS);
        sportName = sport;
    }

    /**
     * Returns true if a given string is a valid sport.
     */
    public static boolean isValidSport(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getSportName() {
        return sportName;
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return "[" + sportName + "]";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Sport // instanceof handles nulls
                && sportName.equals(((Sport) other).sportName)); // state check
    }

    @Override
    public int hashCode() {
        return sportName.hashCode();
    }

}
