package seedu.expensela.model.transaction;

import static seedu.expensela.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Transaction in the expensela.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Transaction implements Comparable<Transaction> {

    private final Name name;
    private final Amount amount;
    private final Date date;
    private final Remark remark;
    private final Category category;
    private boolean isRecurringTransaction = false;

    /**
     * Every field must be present and not null.
     */
    public Transaction(Name name, Amount amount, Date date, Remark remark, Category category) {
        requireAllNonNull(name, amount, date);
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.remark = remark;
        this.category = category;
    }

    public Name getName() {
        return name;
    }

    public Amount getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public Remark getRemark() {
        return remark;
    }

    public Category getCategory() {
        return category;
    }

    public void setIsRecurring() {
        isRecurringTransaction = true;
    }

    public boolean getRecurringBoolean() {
        return isRecurringTransaction;
    }

    /**
     * Returns true if both transactions of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two transactions.
     */
    public boolean isSameTransaction(Transaction otherTransaction) {
        if (otherTransaction == this) {
            return true;
        }

        return otherTransaction != null
                && otherTransaction.getName().equals(getName())
                && otherTransaction.getAmount().equals(getAmount())
                && otherTransaction.getDate().equals(getDate())
                && otherTransaction.getRemark().equals(getRemark())
                && otherTransaction.getCategory().equals(getCategory());
    }

    /**
     * Returns true if both transactions have the same identity and data fields.
     * This defines a stronger notion of equality between two transactions.
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
        return otherTransaction.getName().equals(getName())
                && otherTransaction.getAmount().equals(getAmount())
                && otherTransaction.getDate().equals(getDate())
                && otherTransaction.getRemark().equals(getRemark())
                && otherTransaction.getCategory().equals(getCategory());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, amount, date, remark, category);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Amount: ")
                .append(getAmount())
                .append(" Date: ")
                .append(getDate())
                .append(" Remarks: ")
                .append(getRemark())
                .append(" Category: ")
                .append(getCategory());
        return builder.toString();
    }

    @Override
    public int compareTo(Transaction o) {
        return o.getDate().transactionDate.compareTo(this.getDate().transactionDate);
    }
}
