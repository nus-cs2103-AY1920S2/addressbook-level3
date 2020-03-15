package seedu.expensela.testutil;

import seedu.expensela.model.transaction.Amount;
import seedu.expensela.model.transaction.Date;
import seedu.expensela.model.transaction.Name;
import seedu.expensela.model.transaction.Transaction;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_AMOUNT = "85355255";
    public static final String DEFAULT_DATE = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Amount amount;
    private Date date;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        amount = new Amount(DEFAULT_AMOUNT, true);
        date = new Date(DEFAULT_DATE);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Transaction transactionToCopy) {
        name = transactionToCopy.getName();
        amount = transactionToCopy.getAmount();
        date = transactionToCopy.getDate();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.date = new Date(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String amount) {
        this.amount = new Amount(amount, true);
        return this;
    }

    public Transaction build() {
        return new Transaction(name, amount, date);
    }

}
