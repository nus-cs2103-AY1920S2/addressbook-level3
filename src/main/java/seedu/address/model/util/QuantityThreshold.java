package seedu.address.model.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the quantity threshold for each product before the application notifies the user.
 */
public class QuantityThreshold {
    public static final String MESSAGE_CONSTRAINTS = "Descriptions can take any values, and it should not be blank";
    public static final String DEFAULT_VALUE = "Null";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code QuantityThreshold}.
     *
     * @param quantityThreshold A valid quantityThreshold.
     */
    public QuantityThreshold(String quantityThreshold) {
        requireNonNull(quantityThreshold);
        checkArgument(isValidQuantity(quantityThreshold), MESSAGE_CONSTRAINTS);
        value = quantityThreshold;
    }

    /**
     * Returns true if a given string is a valid quantity.
     */
    public static boolean isValidQuantity(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuantityThreshold // instanceof handles nulls
                && value.equals(((QuantityThreshold) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
