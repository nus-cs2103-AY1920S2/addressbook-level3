package seedu.address.model.expenditure;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Expenditure's amount in the account.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class Amount {

    public static final String MESSAGE_CONSTRAINTS = "Amount should be a double";
    // TODO potentially can change to BigDecimal to represent money.
    public final double value;

    /**
     * Constructs an {@code Amount}.
     *
     * @param amount A valid amount.
     */
    public Amount(double amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        value = amount;
    }

    /**
     * Constructs an {@code Amount}.
     *
     * @param amount A valid amount.
     */
    public Amount(String amount) {
        this(Double.parseDouble(amount));
    }

    /**
     * Returns if a given string is a valid amount.
     */
    public static boolean isValidAmount(double test) {
        return test >= 0;
    }

    /**
     * Returns if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        try {
            return isValidAmount(Double.parseDouble(test));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        // TODO use value - other.value < epsilon ?
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && value == ((Amount) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }

}
