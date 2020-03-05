package seedu.address.model.transaction;

import seedu.address.model.person.Person;

import java.time.LocalDateTime;

public class Transaction {

    // add dummy class
    private final Person customer;
    private final String product;
    private final int quantity;
    private final int money;
    private final LocalDateTime dateTime;

    public Transaction(Person customer, String product, int quantity,
                       int money, LocalDateTime dateTime) {
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.money = money;
        this.dateTime = dateTime;
    }

    public Person getCustomer() {
        return customer;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getMoney() {
        return money;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
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
                && otherTransaction.getQuantity() == getQuantity()
                && otherTransaction.getMoney() == getMoney();

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
                && otherTransaction.getQuantity() == getQuantity()
                && otherTransaction.getMoney() == getMoney();
    }
}
