package seedu.address.model.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Transaction's Monetary Information.
 * Guarantees: immutable; is valid as declared in {@link #isValidMoney(String)}
 */
public class Money {

    public static final String MESSAGE_CONSTRAINTS_FORMAT =
            "Money should take non-negative integer values (up to 1000000), and it should not be blank";
    public static final String MESSAGE_CONSTRAINTS_VALUE =
            "The numeric value of Money must not be negative and must be smaller than 1000000";

    /*
     * There must be one or more digits entered.
     */
    public static final String VALIDATION_REGEX = "^\\d{1,7}$";

    public static final int MAX_VALUE = 1000000;

    public static final int DEFAULT_VALUE = 0;

    public final int value;

    /**
     * Constructs an {@code Money}.
     *
     * @param amount A valid amount in string type.
     */
    public Money(String amount) {
        requireNonNull(amount);
        checkArgument(isValidMoney(amount), MESSAGE_CONSTRAINTS_FORMAT);
        int numericValue = Integer.parseInt(amount);
        checkArgument(isValidAmount(numericValue), MESSAGE_CONSTRAINTS_VALUE);
        value = numericValue;
    }

    /**
     * Constructs an {@code Money}.
     *
     * @param amount A valid amount in int type.
     */
    public Money(int amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS_VALUE);
        value = amount;
    }

    /**
     * Returns true if a given string is a valid sales.
     */
    public static boolean isValidMoney(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the amount is in a valid range.
     * @param test integer to be tested.
     * @return true if the integer is valid, false otherwise.
     */
    public static boolean isValidAmount(int test) {
        return test >= 0 && test <= MAX_VALUE;
    }

    /**
     * Returns a new money whose value is the difference between this value and the
     * other's value.
     * @param m other money.
     * @return new money.
     */
    public Money minus(Money m) {
        int newValue = value - m.value;
        return new Money(newValue);
    }

    /**
     * Returns a new money whose value is the summation between this value and the
     * other's value.
     * @param m other money.
     * @return new money.
     */
    public Money plus(Money m) {
        int newValue = value + m.value;
        return new Money(newValue);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Money // instanceof handles nulls
                && value == (((Money) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

}
