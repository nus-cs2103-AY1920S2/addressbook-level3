package seedu.address.model.transaction;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.tag.Tag;

/**
 * Represents a Transaction in the Wallet.
 */
public abstract class Transaction {

    // Data fields
    protected final Description description;
    protected final Amount amount;
    protected final Date date;
    protected final Tag tag;

    /**
     * Every field must be present and not null.
     */
    public Transaction(Description description, Amount amount, Date date, Tag tag) {
        requireAllNonNull(description, amount, date, tag);
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.tag = tag;
    }

    public Description getDescription() {
        return description;
    }

    public Amount getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public Tag getTag() {
        return tag;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Description: ")
                .append(getDescription())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Date: ")
                .append(getDate())
                .append(" Tag: ")
                .append(getTag());
        return builder.toString();
    }

    /**
     * Returns true if both Transactions have the same data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Transaction)) {
            return false;
        }

        Transaction otherTransaction = (Transaction) other;
        return otherTransaction.getDescription().equals(getDescription())
                && otherTransaction.getAmount().equals(getAmount())
                && otherTransaction.getDate().equals(getDate())
                && otherTransaction.getTag().equals(getTag());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(description, amount, date, tag);
    }
}
