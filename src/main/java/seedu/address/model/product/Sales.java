package seedu.address.model.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Product's sales in the product list
 * Guarantees: immutable; is valid as declared in {@link #isValidSales(String)}
 */
public class Sales {

    public static final String MESSAGE_CONSTRAINTS = "Sales can take any numeric values, and it should not be blank";

    /*
     * There must be one or more digits entered.
     */
    public static final String VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Constructs an {@code Sales}.
     *
     * @param sales A valid sales.
     */
    public Sales(String sales) {
        requireNonNull(sales);
        checkArgument(isValidSales(sales), MESSAGE_CONSTRAINTS);
        value = sales;
    }

    /**
     * Returns true if a given string is a valid sales.
     */
    public static boolean isValidSales(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Sales // instanceof handles nulls
                && value.equals(((Sales) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
