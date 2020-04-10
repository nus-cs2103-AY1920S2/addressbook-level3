package team.easytravel.model.listmanagers.transportbooking;

import static java.util.Objects.requireNonNull;

import team.easytravel.commons.util.AppUtil;

/**
 * Represents a TransportBooking's mode in the TransportBookingManager.
 * Guarantees: immutable; is valid as declared in {@link #isValidMode(String)}
 */
public class Mode {

    public static final String MESSAGE_CONSTRAINTS = "Mode should take one of the following words: \"plane\", "
            + "\"train\", \"bus\", \"car\" or \"others\"";

    /*
     * Mode should be one of the following words: "plane", "train", "bus", "car" or "others"
     */
    public static final String VALIDATION_REGEX = "^(plane|train|bus|car|others)$";

    public final String value;

    /**
     * Constructs a {@code Mode}.
     *
     * @param mode A valid mode.
     */
    public Mode(String mode) {
        requireNonNull(mode);
        AppUtil.checkArgument(isValidMode(mode), MESSAGE_CONSTRAINTS);
        value = mode;
    }

    /**
     * Returns true if a given string is a valid mode.
     */
    public static boolean isValidMode(String test) {
        return test.toLowerCase().matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Mode // instanceof handles nulls
                && value.equals(((Mode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
