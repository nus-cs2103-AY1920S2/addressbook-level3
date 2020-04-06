package seedu.expensela.model.monthlydata;

import static java.util.Objects.requireNonNull;
import static seedu.expensela.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

/**
 * Monthly expense data to be displayed in monthly data panel
 */
public class Expense {

    public static final String MESSAGE_CONSTRAINTS =
            "Expense should only contain positive numbers with 2 decimal places";
    public static final String VALIDATION_REGEX = "^?\\d+\\.?\\d{0,2}$";
    public static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#,##0.00");
    public final Double expenseAmount;

    /**
     * Constructs a {@code Expense}.
     *
     * @param value A valid expense amount.
     */
    public Expense(String value) {
        requireNonNull(value);
        checkArgument(isValidAmount(value), MESSAGE_CONSTRAINTS);
        expenseAmount = Double.parseDouble(value);
    }

    /**
     * Returns true if a given string is a valid expense amount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        String printedAmount = "$";
        printedAmount += DECIMAL_FORMATTER.format(expenseAmount);
        return printedAmount;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Expense // instanceof handles nulls
                && expenseAmount.equals(((Expense) other).expenseAmount)); // state check
    }

    @Override
    public int hashCode() {
        return (expenseAmount).hashCode();
    }

}
