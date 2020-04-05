package seedu.address.testutil;

import seedu.address.model.FitBiz;
import seedu.address.model.client.Client;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code FitBiz ab = new AddressBookBuilder().withClient("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private FitBiz addressBook;

    public AddressBookBuilder() {
        addressBook = new FitBiz();
    }

    public AddressBookBuilder(FitBiz fitBiz) {
        this.addressBook = fitBiz;
    }

    /**
     * Adds a new {@code Client} to the {@code FitBiz} that we are building.
     */
    public AddressBookBuilder withClient(Client client) {
        addressBook.addClient(client);
        return this;
    }

    public FitBiz build() {
        return addressBook;
    }
}
