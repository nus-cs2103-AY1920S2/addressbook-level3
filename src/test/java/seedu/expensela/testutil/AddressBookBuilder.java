package seedu.expensela.testutil;

import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.transaction.Transaction;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ExpenseLa ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private ExpenseLa expenseLa;

    public AddressBookBuilder() {
        expenseLa = new ExpenseLa();
    }

    public AddressBookBuilder(ExpenseLa expenseLa) {
        this.expenseLa = expenseLa;
    }

    /**
     * Adds a new {@code Person} to the {@code ExpenseLa} that we are building.
     */
    public AddressBookBuilder withPerson(Transaction transaction) {
        expenseLa.addPerson(transaction);
        return this;
    }

    public ExpenseLa build() {
        return expenseLa;
    }
}
