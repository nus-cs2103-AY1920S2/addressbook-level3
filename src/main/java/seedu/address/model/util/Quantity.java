package seedu.address.model.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Product's quantity in the product list
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 */
public class Quantity implements Comparable<Quantity> {

    public static final String MESSAGE_CONSTRAINTS =
            "Quantity should take numeric values, and it should not be blank";
    public static final String MESSAGE_CONSTRAINTS_NOT_NEGATIVE =
            "The numeric value of a Quantity must not be negative";

    /*
     * There must be one or more digits entered.
     */
    public static final String VALIDATION_REGEX = "\\d+";

    public final int value;

    /**
     * Constructs an {@code Quantity}.
     *
     * @param quantity A valid quantity in string type.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidQuantity(quantity), MESSAGE_CONSTRAINTS);
        int numericValue = Integer.parseInt(quantity);
        checkArgument(isValidValue(numericValue), MESSAGE_CONSTRAINTS_NOT_NEGATIVE);
        value = numericValue;
    }

    public Quantity(int q) {
        requireNonNull(q);
        checkArgument(isValidValue(q), MESSAGE_CONSTRAINTS_NOT_NEGATIVE);
        value = q;
    }

    /**
     * Returns true if a given string is a valid quantity.
     */
    public static boolean isValidQuantity(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given integer is a valid quantity, e.g. not negative.
     */
    public static boolean isValidValue(int test) {
        return test >= 0;
    }

    /**
     * Returns a new quantity whose value is the difference between this value and the
     * other's value.
     * @param q other quantity.
     * @return new quantity.
     */
    public Quantity minus(Quantity q) {
        int newValue = value - q.value;
        return new Quantity(newValue);
    }

    /**
     * Returns a new quantity whose value is the summation between this value and the
     * other's value.
     * @param q other quantity.
     * @return new quantity.
     */
    public Quantity plus(Quantity q) {
        int newValue = value + q.value;
        return new Quantity(newValue);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Quantity // instanceof handles nulls
                && value == ((Quantity) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public int compareTo(Quantity q) {
        return value - q.value;
    }

}
