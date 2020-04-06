package seedu.address.model.parcel.parcelattributes;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Order's address in the order book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address {

    public static final String MESSAGE_CONSTRAINTS = "Please enter a Singapore address with it's postal code. "
            + "\n"
            + "The Postal Code should start with the letter 'S'";

    /*
     * The VALIDATION_REGEX checks if the address has a
     * postal code in it. The postal code should start with a letter 'S'.
     * Valid: 123 Serangoon Garden Blk 20 S537992
     * Invalid: 123 Serangoon Garden Blk 20
     *
     */
    public static final String VALIDATION_REGEX = "^(.*?)S\\d{6}$";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        value = address;
    }

    /**
     * Returns true if a given string is a valid address.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
