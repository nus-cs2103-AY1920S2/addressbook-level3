package seedu.address.model.transaction;

import seedu.address.model.util.Quantity;

/**
 * Represents a Transaction in the system.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Transaction {

    private final String customer;
    private final String product;
    private final DateTime dateTime;
    private final Quantity quantity;
    private final Money money;
    private final String description;


    public Transaction(String customer, String product, DateTime dateTime,
                       Quantity quantity, Money money, String description) {
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.money = money;
        this.dateTime = dateTime;
        this.description = description;
    }

    public String getCustomer() {
        return customer;
    }

    public String getProduct() {
        return product;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Money getMoney() {
        return money;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns true if any two transactions have the same customer, products, and dateTime.
     * This defines a weaker notion of equality.
     * @param otherTransaction other transaction.
     * @return true if these two transactions have the same customer, products, and dateTime.
     */
    public boolean isSameTransaction(Transaction otherTransaction) {
        if (otherTransaction == this) {
            return true;
        }

        return otherTransaction.getCustomer().equals(getCustomer())
                && otherTransaction.getProduct().equals(getProduct())
                && otherTransaction.getDateTime().equals(getDateTime())
                && otherTransaction.getQuantity().equals(getQuantity())
                && otherTransaction.getMoney().equals(getMoney());

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Transaction)) {
            return false;
        }

        Transaction otherTransaction = (Transaction) other;
        return otherTransaction.getCustomer().equals(getCustomer())
                && otherTransaction.getProduct().equals(getProduct())
                && otherTransaction.getDateTime().equals(getDateTime())
                && otherTransaction.getQuantity().equals(getQuantity())
                && otherTransaction.getMoney().equals(getMoney())
                && otherTransaction.getDescription().equals(getDescription());
    }
}
