package seedu.address.model.facilitator;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Facilitator's phone number in Mod Manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, and it should be at least 3 digits long, "
            + "and should not be blank.";
    public static final String VALIDATION_REGEX = "\\d{3,}";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        checkArgument(phone == null || isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && ((value == null && ((Phone) other).value == null)
                || (value != null && ((Phone) other).value != null
                && value.equals(((Phone) other).value)))); // state check
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

}
