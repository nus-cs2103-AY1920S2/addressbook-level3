package seedu.address.model.transaction;

import java.util.Objects;

import seedu.address.model.tag.Tag;

/**
 * Represents an outflow of money from the Wallet.
 */
public class Expense extends Transaction {

    /**
     * Every field must be present and not null.
     */
    public Expense(Description description, Amount amount, Date date, Tag tag) {
        super(description, amount, date, tag);
    }

    /**
     * Returns true if both Expenses have the same data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Expense)) {
            return false;
        }

        Expense otherExpense = (Expense) other;
        return otherExpense.getDescription().equals(getDescription())
                && otherExpense.getAmount().equals(getAmount()) && otherExpense.getDate().equals(getDate())
                && otherExpense.getTag().equals(getTag());
    }

    @Override
    public int hashCode() {
        // use "e" to differentiate from income
        return Objects.hash("e", description, amount, date, tag);
    }
}
