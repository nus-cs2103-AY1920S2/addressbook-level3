package seedu.address.model.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Restaurant's name in the restaurant book.
 * Guarantees: immutable; is valid as declared in {@link #isValidVisit(String)}
 */
public class Visit {

    public static final String MESSAGE_CONSTRAINTS =
            "Visited field should only be Yes or No, and should not be empty.";
    public static final String RESTAURANT_VISITED = "Yes";
    public static final String RESTAURANT_NOT_VISITED = "No";

    /*
     * Allows only "Yes" or "No"
     */
    public static final String VALIDATION_REGEX = "Yes|No";

    public final String visit;

    /**
     * Constructs a {@code Visit}.
     *
     * @param visit A valid name.
     */
    public Visit(String visit) {
        requireNonNull(visit);
        checkArgument(isValidVisit(visit), MESSAGE_CONSTRAINTS);
        this.visit = visit;
    }

    /**
     * Returns true if a given string is a valid visited status.
     */
    public static boolean isValidVisit(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return visit;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Visit // instanceof handles nulls
                && visit.equals(((Visit) other).visit)); // state check
    }

    @Override
    public int hashCode() {
        return visit.hashCode();
    }

}
