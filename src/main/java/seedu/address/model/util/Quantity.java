package seedu.address.model.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Product's quantity in the product list
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 */
public class Quantity implements Comparable<Quantity>{

    public static final String MESSAGE_CONSTRAINTS = "Quantity can take any numeric values, and it should not be blank";

    /*
     * There must be one or more digits entered.
     */
    public static final String VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Constructs an {@code Quantity}.
     *
     * @param quantity A valid quantity.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidQuantity(quantity), MESSAGE_CONSTRAINTS);
        value = quantity;
    }

    /**
     * Returns true if a given string is a valid quantity.
     */
    public static boolean isValidQuantity(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a new quantity whose value is the difference between this value and the
     * other's value.
     * @param q other quantity.
     * @return new quantity.
     */
    public Quantity minus(Quantity q) {
        int newValue = Integer.parseInt(this.value) - Integer.parseInt(q.value);
        return new Quantity(String.valueOf(newValue));
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Quantity // instanceof handles nulls
                && value.equals(((Quantity) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Quantity q) {
        return Integer.parseInt(this.value) - Integer.parseInt(q.value);
    }

}
