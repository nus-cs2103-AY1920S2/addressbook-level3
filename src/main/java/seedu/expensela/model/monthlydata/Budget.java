package seedu.expensela.model.monthlydata;

import static java.util.Objects.requireNonNull;
import static seedu.expensela.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

/**
 * Monthly budget data set by user
 */
public class Budget {

    public static final String MESSAGE_CONSTRAINTS =
            "Budget should only contain positive numbers with 2 decimal places";
    public static final String VALIDATION_REGEX = "^?\\d+\\.?\\d{0,2}$";
    public static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#,##0.00");
    public final Double budgetAmount;

    /**
     * Constructs a {@code Budget}.
     *
     * @param value A valid income amount.
     */
    public Budget(String value) {
        requireNonNull(value);
        checkArgument(isValidAmount(value), MESSAGE_CONSTRAINTS);
        budgetAmount = Double.parseDouble(value);
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
        printedAmount += DECIMAL_FORMATTER.format(budgetAmount);
        return printedAmount;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Budget // instanceof handles nulls
                && budgetAmount.equals(((Budget) other).budgetAmount)); // state check
    }

    @Override
    public int hashCode() {
        return (budgetAmount).hashCode();
    }

}
