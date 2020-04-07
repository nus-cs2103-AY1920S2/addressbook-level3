package seedu.expensela.model.monthlydata;

import static java.util.Objects.requireNonNull;
import static seedu.expensela.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

/**
 * Monthly income data set by user
 */
public class Income {

    public static final String MESSAGE_CONSTRAINTS =
            "Income should only contain positive numbers with 2 decimal places";
    public static final String VALIDATION_REGEX = "^?\\d+\\.?\\d{0,2}$";
    public static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#,##0.00");
    public final Double incomeAmount;

    /**
     * Constructs a {@code Income}.
     *
     * @param value A valid income amount.
     */
    public Income(String value) {
        requireNonNull(value);
        checkArgument(isValidAmount(value), MESSAGE_CONSTRAINTS);
        incomeAmount = Double.parseDouble(value);
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
        printedAmount += DECIMAL_FORMATTER.format(incomeAmount);
        return printedAmount;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Income // instanceof handles nulls
                && incomeAmount.equals(((Income) other).incomeAmount)); // state check
    }

    @Override
    public int hashCode() {
        return (incomeAmount).hashCode();
    }

}
