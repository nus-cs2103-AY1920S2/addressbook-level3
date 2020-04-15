package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.util.Quantity;

/**
 * Represents a Transaction's quantity in the transaction list
 * Guarantees: immutable;
 */
public class TransactionQuantity implements Quantity {

    public static final int MIN_VALUE = 1;

    public static final String MESSAGE_CONSTRAINTS_VALUE = "The numeric value of Transaction Quantity must be "
            + "larger than or equal to " + MIN_VALUE
            + " and smaller than or equal to " + MAX_VALUE;

    public final int value;

    /**
     * Constructs an {@code Quantity}.
     *
     * @param quantity A valid quantity in string type.
     */
    public TransactionQuantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidFormat(quantity), MESSAGE_CONSTRAINTS_FORMAT);
        int numericValue = Integer.parseInt(quantity);
        checkArgument(isValidValue(numericValue), MESSAGE_CONSTRAINTS_VALUE);
        value = numericValue;
    }

    public TransactionQuantity(int q) {
        requireNonNull(q);
        checkArgument(isValidValue(q), MESSAGE_CONSTRAINTS_VALUE);
        value = q;
    }

    public int getValue() {
        return value;
    }

    /**
     * Returns true if a given string matches the regex of a valid transaction quantity.
     */
    public static boolean isValidFormat(String test) {
        if (test.matches(VALIDATION_REGEX)) {
            try {
                int value = Integer.parseInt(test);
                return isValidValue(value);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Returns true if a integer is a valid value for quantity.
     */
    public static boolean isValidValue(int test) {
        return test >= MIN_VALUE && test <= MAX_VALUE;
    }

    /**
     * Returns a new quantity whose value is the difference between this value and the
     * other's value.
     * @param q other quantity.
     * @return new quantity.
     */
    public Quantity minus(Quantity q) {
        requireNonNull(q);
        int newValue = value - q.getValue();
        return new TransactionQuantity(newValue);
    }

    /**
     * Returns a new quantity whose value is the summation between this value and the
     * other's value.
     * @param q other quantity.
     * @return new quantity.
     */
    public Quantity plus(Quantity q) {
        requireNonNull(q);
        int newValue = value + q.getValue();
        return new TransactionQuantity(newValue);
    }

    public String toString() {
        return String.valueOf(value);
    }

    /**
     * Returns true if two transaction quantities are equal in value.
     */
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Quantity // instanceof handles nulls
                && value == ((Quantity) other).getValue()); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public int compareTo(Quantity q) {
        return value - q.getValue();
    }
}
