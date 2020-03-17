package seedu.expensela.model.monthlydata;

import seedu.expensela.model.transaction.Amount;

import static java.util.Objects.requireNonNull;
import static seedu.expensela.commons.util.AppUtil.checkArgument;

/**
 * Represents a user's income for a particular month.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class Income {

    public static final String MESSAGE_CONSTRAINTS =
            "Income should only contain numbers with 2 decimal places";
    public static final String VALIDATION_REGEX = "\\d{1,}\\.\\d{2}";
    public final int dollarValue;
    public final int centValue;

    /**
     * Constructs a {@code Income}.
     *
     * @param value A valid income amount.
     */
    public Income(String value) {
        requireNonNull(value);
        checkArgument(isValidAmount(value), MESSAGE_CONSTRAINTS);
        String[] tokens = value.split(".");
        this.dollarValue = Integer.parseInt(tokens[0]);
        this.centValue = Integer.parseInt(tokens[1]);
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
        printedAmount += dollarValue + "." + centValue;
        return printedAmount;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Income // instanceof handles nulls
                && dollarValue == ((Income) other).dollarValue //state check
                && centValue == ((Income) other).centValue); // state check
    }

    @Override
    public int hashCode() {
        return (dollarValue + "." + centValue).hashCode();
    }

}
