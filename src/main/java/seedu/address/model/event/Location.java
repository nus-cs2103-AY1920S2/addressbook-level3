package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a location in TAble.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class Location {
    /**
     * Represents Location where Consults and Tutorials are held.
     * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
     */

    public static final String MESSAGE_CONSTRAINTS =
            "Locations should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String eventLocation;

    /**
     * Constructs a {@code Location}.
     *
     * @param eventLocation A valid event location.
     */
    public Location(String eventLocation) {
        requireNonNull(eventLocation);
        checkArgument(isValidLocation(eventLocation), MESSAGE_CONSTRAINTS);
        this.eventLocation = eventLocation;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return eventLocation;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Location // instanceof handles nulls
                && eventLocation.equals(((Location) other).eventLocation)); // state check
    }

    @Override
    public int hashCode() {
        return eventLocation.hashCode();
    }

}
