package seedu.address.testutil;

import seedu.address.model.InventorySystem;
import seedu.address.model.customer.Customer;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code InventorySystem ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private InventorySystem addressBook;

    public AddressBookBuilder() {
        addressBook = new InventorySystem();
    }

    public AddressBookBuilder(InventorySystem addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Customer} to the {@code InventorySystem} that we are building.
     */
    public AddressBookBuilder withPerson(Customer customer) {
        addressBook.addPerson(customer);
        return this;
    }

    public InventorySystem build() {
        return addressBook;
    }
}
