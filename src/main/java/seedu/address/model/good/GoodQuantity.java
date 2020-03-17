package seedu.address.model.good;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Good's quantity in the inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidGoodQuantity(String)}
 */
public class GoodQuantity {

    public static final String MESSAGE_CONSTRAINTS =
            "Good's quantity should only contain numeric characters that is greater than or equals to zero, "
                    + "and it should not be blank";

    // Only accept non-negative integers
    public static final String VALIDATION_REGEX = "^\\d+$";

    public final int goodQuantity;

    /**
     * Constructs a {@code GoodQuantity} from an int.
     *
     * @param quantity A valid quantity.
     */
    public GoodQuantity(int quantity) {
        checkArgument(isValidGoodQuantity(String.valueOf(quantity)), MESSAGE_CONSTRAINTS);
        goodQuantity = quantity;
    }

    /**
     * Constructs a {@code GoodQuantity} from a string.
     * @param quantity
     */
    public GoodQuantity(String quantity) {
        checkArgument(isValidGoodQuantity(quantity), MESSAGE_CONSTRAINTS);
        goodQuantity = Integer.parseInt(quantity);
    }

    /**
     * Returns true if a given string is a valid good quantity.
     * @param test
     */
    public static boolean isValidGoodQuantity(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return Integer.toString(goodQuantity);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoodQuantity // instanceof handles nulls
                && goodQuantity == (((GoodQuantity) other).goodQuantity)); // state check
    }

    @Override
    public int hashCode() {
        return Integer.toString(goodQuantity).hashCode();
    }

}
