package seedu.expensela.model;

import static java.util.Objects.requireNonNull;
import static seedu.expensela.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

/**
 * Monthly balance data set by user
 */
public class Balance {

    public static final String MESSAGE_CONSTRAINTS =
            "Balance should only contain numbers with 2 decimal places";
    public static final String VALIDATION_REGEX = "^-?\\d+\\.?\\d{0,2}$";
    public static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#,##0.00");
    public final Double balanceAmount;

    /**
     * Constructs a {@code Balance}.
     *
     * @param value A valid balance amount.
     */
    public Balance(String value) {
        requireNonNull(value);
        checkArgument(isValidAmount(value), MESSAGE_CONSTRAINTS);
        balanceAmount = Double.parseDouble(value);
    }

    /**
     * Returns true if a given string is a valid income amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        String printedAmount = "$";
        printedAmount += DECIMAL_FORMATTER.format(balanceAmount);
        return printedAmount;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Balance // instanceof handles nulls
                && balanceAmount.equals(((Balance) other).balanceAmount)); // state check
    }

    @Override
    public int hashCode() {
        return (balanceAmount).hashCode();
    }

}
