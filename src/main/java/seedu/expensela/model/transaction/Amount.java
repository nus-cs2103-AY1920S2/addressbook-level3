package seedu.expensela.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.expensela.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class Amount {


    public static final String MESSAGE_CONSTRAINTS =
            "Amount should only contain numbers with 2 decimal places";
    public static final String VALIDATION_REGEX = "\\d{1,}\\.\\d{2}";
    public final int dollarValue;
    public final int centValue;
    public final boolean positive;

    /**
     * Constructs a {@code Amount}.
     *
     * @param value A valid transaction amount.
     */
    public Amount(String value, boolean positive) {
        requireNonNull(value);
        checkArgument(isValidAmount(value), MESSAGE_CONSTRAINTS);
        String[] tokens = value.split(".");
        this.dollarValue = Integer.parseInt(tokens[0]);
        this.centValue = Integer.parseInt(tokens[1]);
        this.positive = positive;
    }

    /**
     * Returns true if a given string is a valid transaction amount.
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
        printedAmount += dollarValue + "." + centValue;
        return printedAmount;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && dollarValue == ((Amount) other).dollarValue //state check
                && centValue == ((Amount) other).centValue); // state check
    }

    @Override
    public int hashCode() {
        return (dollarValue + "." + centValue).hashCode();
    }

}
