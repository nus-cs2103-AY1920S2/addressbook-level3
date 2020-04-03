package seedu.address.model.parcel.parcelattributes;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents warehouse details related to an Order.
 * Guarantees: immutable;
 */
public class Warehouse {
    public static final String MESSAGE_CONSTRAINTS = "Warehouse details can take any value and it should not be blank";
    /*
     * Check if first character of given input is whitespace.
     * Otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String address;

    /**
     * Constructs an {@code Warehouse}.
     *
     * @param address A valid address.
     */
    public Warehouse(String address) {
        requireNonNull(address);
        checkArgument(isValidAddress(address), MESSAGE_CONSTRAINTS);
        this.address = address;
    }

    /**
     * Returns true if a given string is a valid address.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this // short circuit if same object
                || (obj instanceof Warehouse // instanceof handles nulls
                && address.equals(((Warehouse) obj).address)); // state check
    }

    @Override
    public String toString() {
        return address;
    }
}
