package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Transaction's Monetary Information.
 * Guarantees: immutable; is valid as declared in {@link #isValidMoney(String)}
 */
public class Money {
    public static final String MESSAGE_CONSTRAINTS = "Money can take any numeric values, and it should not be blank";

    /*
     * There must be one or more digits entered.
     */
    public static final String VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Constructs an {@code Money}.
     *
     * @param sales A valid sales.
     */
    public Money(String sales) {
        requireNonNull(sales);
        checkArgument(isValidMoney(sales), MESSAGE_CONSTRAINTS);
        value = sales;
    }

    /**
     * Returns true if a given string is a valid sales.
     */
    public static boolean isValidMoney(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Money // instanceof handles nulls
                && value.equals(((Money) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
