package seedu.expensela.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.expensela.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

/**
 * Represents a Transaction's cost/gain in the expensela.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class Amount {


    public static final String MESSAGE_CONSTRAINTS =
            "Amount should only contain positive numbers with 2 decimal places";
    public static final String MESSAGE_CONSTRAINTS_TOOLARGE =
            "Transaction amount cannot be 1 million dollars or more!";
    public static final String MESSAGE_CONSTRAINTS_TOOSMALL =
            "Transaction amount cannot be less than 0!";
    public static final String VALIDATION_REGEX = "^^?\\d+\\.?\\d{0,2}$";
    public static final DecimalFormat DECIMAL_FORMATTER = new DecimalFormat("#,##0.00");
    public final Double transactionAmount;
    public final boolean positive;

    /**
     * Constructs a {@code Amount}.
     *
     * @param value A valid transaction transactionAmount.
     */
    public Amount(String value, boolean positive) {
        requireNonNull(value);
        checkArgument(isValidAmount(value), MESSAGE_CONSTRAINTS);
        this.transactionAmount = Double.parseDouble(value);
        this.positive = positive;
    }

    /**
     * Returns true if a given string is a valid transaction transactionAmount.
     */
    public static boolean isValidAmount(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        String printedAmount;
        if (positive) {
            printedAmount = "+ $";
        } else {
            printedAmount = "- $";
        }
        printedAmount += DECIMAL_FORMATTER.format(transactionAmount);
        return printedAmount;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && transactionAmount.equals(((Amount) other).transactionAmount)); //state check
    }

    @Override
    public int hashCode() {
        return transactionAmount.hashCode();
    }



}
