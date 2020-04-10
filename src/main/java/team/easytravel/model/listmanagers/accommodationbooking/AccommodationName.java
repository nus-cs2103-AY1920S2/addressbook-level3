package team.easytravel.model.listmanagers.accommodationbooking;

import static java.util.Objects.requireNonNull;

import team.easytravel.commons.util.AppUtil;

/**
 * Represents an Accommodation's name in the AccommodationBookingManager.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class AccommodationName {

    public static final String MESSAGE_CONSTRAINTS = "Name of accommodation must be made up of alphanumeric words "
            + "that is less than 50 characters long.";

    // todo update regex to match constraints
    // Allows for 50 alphanumeric characters.
    public static final String VALIDATION_REGEX = "^(?!\\s*$)[A-Za-z0-9\\s]{1,50}+";

    /**
     * The accommodation name.
     */
    public final String value;

    /**
     * Constructs a {@code AccommodationName}.
     *
     * @param name a valid name.
     */
    public AccommodationName(String name) {
        requireNonNull(name);
        AppUtil.checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        value = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccommodationName // instanceof handles nulls
                && value.equals(((AccommodationName) other)
                .value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
