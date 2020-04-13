package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Month;
import java.time.Year;

/**
 * Represents a Budget for the wallet.
 */
public class Budget {
    public static final String BUDGET_OK = "Your current expenditure is: %1$s / %2$s";
    public static final String BUDGET_EXCEEDED = "You have exceeded your budget by: %1$s / %2$s";

    private final Amount amount;
    private final Month month;
    private final Year year;
    private final boolean isDefault;

    private Budget(Amount amount, Month month, Year year, boolean isDefault) {
        requireAllNonNull(amount, month, year, isDefault);
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.isDefault = isDefault;
    }
    /**
     * Creates a default budget that applies to all months.
     */
    public Budget(Amount amount) {
        this(amount, Date.getDefault().getMonth(), Date.getDefault().getYear(), true);
    }

    /**
     * Creates a budget for a specified month and year.
     */
    public Budget(Amount amount, Month month, Year year) {
        this(amount, month, year, false);
    }

    public boolean isDefault() {
        return isDefault;
    }

    public Amount getAmount() {
        return amount;
    }

    public Month getMonth() {
        return month;
    }

    public Year getYear() {
        return year;
    }

    /**
     * Returns a default Budget of zero amount.
     */
    public static Budget getDefault() {
        return new Budget(Amount.zero());
    }

    /**
     * Checks if the date of the {@code other Budget} is equal to the date of this Budget object.
     */
    public boolean dateEquals(Budget other) {
        requireNonNull(other);

        return month.equals(other.getMonth())
                && year.equals(other.getYear());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof Budget
                && amount.equals(((Budget) other).getAmount())
                && month.equals(((Budget) other).getMonth())
                && year.equals(((Budget) other).getYear())
                && isDefault == ((Budget) other).isDefault();
    }

    @Override
    public String toString() {
        return amount.toString();
    }
}
