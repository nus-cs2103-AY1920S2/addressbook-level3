package seedu.eylah.expensesplitter.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.util.AppUtil.checkArgument;

/**
 * Represents an Item's price in Expense Splitter of EYLAH.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(Double)}
 */
public class ItemPrice {

    public static final double DOUBLE_ZERO = 0;
    public static final String MESSAGE_CONSTRAINTS =
            "Price should be greater than $0";
    public static final String VALIDATION_REGEX = "([0-9]*)\\.([0-9]*)"; //Price must be a double, at least 1 digit long.
    public final Double itemPrice;

    /**
     * Constructs a {@code ItemPrice}.
     *
     * @param price A valid price
     */
    public ItemPrice(Double price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS);
        this.itemPrice = price;
    }

    /**
     * Returns true if a given price is a valid price.
     *
     * @param test if an ItemPrice is > 0 because ItemPrice cannot be <= 0 and can only contain digit.
     */
    public static boolean isValidPrice(Double test) {
        System.out.println(test.toString());
        System.out.println(test.toString().matches(VALIDATION_REGEX));
        return test > DOUBLE_ZERO && test.toString().matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return String.valueOf(itemPrice);
    }


    /**
     * Checks if two Items have the same ItemPrice.
     *
     * @param other ItemPrice to be checked against.
     * @return True if two of the Items have the same ItemPrice, False otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemPrice // instanceof handles nulls
                && itemPrice == ((ItemPrice) other).itemPrice); // state check
    }

    @Override
    public int hashCode() {
        return Double.valueOf(itemPrice).hashCode();
    }
}
