package seedu.address.model.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the quantity threshold for each product before the application notifies the user.
 */
public class QuantityThreshold {
    public static final String MESSAGE_CONSTRAINTS =
            "Quantity threshold can take any positive integer values (up to 1000000), and it should not be blank";

    /*
     * There must be one or more digits entered.
     */
    public static final String VALIDATION_REGEX = "\\d+";

    public static final int MAX_VALUE = 1000000;

    public final int value;

    /**
     * Constructs an {@code QuantityThreshold}.
     *
     * @param quantityThreshold A valid quantityThreshold.
     */
    public QuantityThreshold(String quantityThreshold) {
        requireNonNull(quantityThreshold);
        checkArgument(isValidQuantity(quantityThreshold), MESSAGE_CONSTRAINTS);
        value = Integer.parseInt(quantityThreshold);
    }

    public QuantityThreshold(int quantityThreshold) {
        requireNonNull(quantityThreshold);
        checkArgument(isValidQuantityValue(quantityThreshold), MESSAGE_CONSTRAINTS);
        value = quantityThreshold;
    }

    /**
     * Returns true if a given string is a valid quantity threshold.
     */
    public static boolean isValidQuantity(String test) {
        return test.matches(VALIDATION_REGEX)
                && Integer.parseInt(test) < MAX_VALUE;
    }

    public static boolean isValidQuantityValue(int test) {
        return test > 0 && test < MAX_VALUE;
    }

    public double getDouble() {
        return Double.valueOf(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuantityThreshold // instanceof handles nulls
                && value == ((QuantityThreshold) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
