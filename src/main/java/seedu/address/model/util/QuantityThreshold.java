package seedu.address.model.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the quantity threshold for each product before the application notifies the user.
 */
public class QuantityThreshold {
    public static final String MESSAGE_CONSTRAINTS =
            "Quantity threshold should be a positive integer value (up to 1000000), or Null";
    public static final String DEFAULT_VALUE = "Null";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public static final int MAX_VALUE = 1000000;

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
     * Returns true if a given string is a valid quantity threshold.
     */
    public static boolean isValidQuantity(String test) {
        return test.equals(DEFAULT_VALUE)
                || (test.matches(VALIDATION_REGEX)
                && Integer.parseInt(test) < MAX_VALUE);
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
