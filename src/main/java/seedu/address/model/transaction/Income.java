package seedu.address.model.transaction;

import java.util.Objects;

import seedu.address.model.tag.Tag;

/**
 * Represents an inflow of money to the Wallet.
 */
public class Income extends Transaction {

    /**
     * Every field must be present and not null.
     */
    public Income(Description description, Amount amount, Date date, Tag tag) {
        super(description, amount, date, tag);
    }

    /**
     * Returns true if both Incomes have the same data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Income)) {
            return false;
        }

        Income otherIncome = (Income) other;
        return otherIncome.getDescription().equals(getDescription())
                && otherIncome.getAmount().equals(getAmount()) && otherIncome.getDate().equals(getDate())
                && otherIncome.getTag().equals(getTag());
    }

    @Override
    public int hashCode() {
        // use "i" to differentiate from expense
        return Objects.hash("i", description, amount, date, tag);
    }
}
