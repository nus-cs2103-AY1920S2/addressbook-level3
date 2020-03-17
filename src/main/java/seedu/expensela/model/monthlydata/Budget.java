package seedu.expensela.model.monthlydata;

import static java.util.Objects.requireNonNull;
import static seedu.expensela.commons.util.AppUtil.checkArgument;

public class Budget {

    public static final String MESSAGE_CONSTRAINTS =
            "Budget should only contain numbers with 2 decimal places";
    public static final String VALIDATION_REGEX = "\\d{1,}\\.\\d{2}";
    public final int dollarValue;
    public final int centValue;

    /**
     * Constructs a {@code Budget}.
     *
     * @param value A valid income amount.
     */
    public Budget(String value) {
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
                || (other instanceof Budget // instanceof handles nulls
                && dollarValue == ((Budget) other).dollarValue //state check
                && centValue == ((Budget) other).centValue); // state check
    }

    @Override
    public int hashCode() {
        return (dollarValue + "." + centValue).hashCode();
    }

}
