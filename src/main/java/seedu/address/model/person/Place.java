package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Create new place object
 */
public class Place {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String placeName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param placeName A valid tag name.
     */
    public Place(String placeName) {
        requireNonNull(placeName);
        this.placeName = placeName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Place // instanceof handles nulls
                && placeName.equals(((Place) other).placeName)); // state check
    }

    @Override
    public int hashCode() {
        return placeName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + placeName + ']';
    }
}
