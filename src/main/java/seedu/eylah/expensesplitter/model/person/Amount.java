package seedu.eylah.expensesplitter.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.eylah.commons.util.AppUtil.checkArgument;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Represents a Person's amount in Expense Splitter in EYLAH.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(BigDecimal)}
 */
public class Amount {

    public static final String MESSAGE_CONSTRAINTS =
            "Amount should only contains numerical characters and decimal separator.";

    // @@author Gary-reused
    // Solution below adapted from https://stackoverflow.com/questions/16242449
    // Price can either be 9 or 9.0 or 9.00 It can only accept a max of 2 decimal place.
    public static final String VALIDATION_REGEX = "(?=.*?\\d)^\\$?(([1-9]\\d{0,2}(,\\d{3})*)|\\d+)?(\\.\\d{1,2})?$";

    public final BigDecimal amount;

    /**
     * Constructs a {@code Amount}/
     *
     * @param amount A valid amount
     */
    public Amount(BigDecimal amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        this.amount = amount;
    }

    /**
     * Return true if a given amount is a valid amount.
     *
     * @param test given amount
     * @return True if given amount is valid, False otherwise.
     */
    public static boolean isValidAmount(BigDecimal test) {
        return test.toString().matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        BigDecimal display = amount.setScale(3, RoundingMode.HALF_EVEN);
        NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(Locale.US);
        usdCostFormat.setMinimumFractionDigits(1);
        usdCostFormat.setMaximumFractionDigits(2);
        usdCostFormat.setMinimumFractionDigits(2);
        return usdCostFormat.format(display.doubleValue());
    }

    /**
     * Checks if two Amount have the same amount.
     *
     * @param other Amount to be checked against.
     * @return True if two of the Amount have the same amount, False otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                && amount.compareTo(((Amount) other).amount) == 0); // state check
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }

    public BigDecimal getBigDecimal() {
        return this.amount;
    }
}
