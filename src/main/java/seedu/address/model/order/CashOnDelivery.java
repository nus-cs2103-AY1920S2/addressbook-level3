package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the value to earn from this order in the order book.
 * Guarantees: immutable; item is valid as declared in {@link #isValidCashValue(String)}}
 */
public class CashOnDelivery {

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid Cash Amount. Add a '$' sign followed by the amount of money";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\$\\d+(?:.(\\d+))?";

    public final String cashOnDelivery;

    /**
     * Constructs a {@code Name}.
     *
     * @param cashOnDelivery A valid monetary value.
     */
    public CashOnDelivery(String cashOnDelivery) {
        requireNonNull(cashOnDelivery);
        checkArgument(isValidCashValue(cashOnDelivery), MESSAGE_CONSTRAINTS);
        this.cashOnDelivery = cashOnDelivery;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidCashValue(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return cashOnDelivery;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CashOnDelivery // instanceof handles nulls
                && cashOnDelivery.equals(((CashOnDelivery) other).cashOnDelivery)); // state check
    }

    @Override
    public int hashCode() {
        return cashOnDelivery.hashCode();
    }

}
