package seedu.address.model.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Product's cost price in the product list
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class CostPrice {

    public static final String MESSAGE_CONSTRAINTS =
            "Price can take any positive integer values (up to 1000000), and it should not be blank";

    /*
     * There must be one or more digits entered.
     */
    public static final String VALIDATION_REGEX = "^\\d{1,7}$";

    public static final int MAX_VALUE = 1000000;
    public static final int MIN_VALUE = 1;

    public final String value;

    /**
     * Constructs an {@code Price}.
     *
     * @param price A valid price.
     */
    public CostPrice(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS);
        // remove leading zeroes
        value = price.replaceFirst("^0+(?!$)", "");
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        if (test.matches(VALIDATION_REGEX)) {
            try {
                int value = Integer.parseInt(test);
                return value <= MAX_VALUE && value >= MIN_VALUE;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CostPrice // instanceof handles nulls
                && value.equals(((CostPrice) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
