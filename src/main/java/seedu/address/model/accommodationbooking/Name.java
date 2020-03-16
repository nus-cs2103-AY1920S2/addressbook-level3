package seedu.address.model.accommodationbooking;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Accommodation's name in the AccommodationBookingManager.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS = "Name of accommodation must be made up of alphanumeric words "
            + "that is less than 50 characters long.";

    // todo update regex to match constraints
    // Allows for 50 alphanumeric characters.
    public static final String VALIDATION_REGEX = "^(?!\\s*$)[A-Za-z0-9\\s]{1,50}+";

    /**
     * The accommodation name.
     */
    public final String accommodationName;

    /**
     * Instantiates a new accommodation Name.
     *
     * @param name a valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        accommodationName = name;
    }

    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return accommodationName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.accommodationbooking.Name // instanceof handles nulls
                && accommodationName.equals(((seedu.address.model.accommodationbooking.Name) other)
                .accommodationName)); // state check
    }

    @Override
    public int hashCode() {
        return accommodationName.hashCode();
    }

}
