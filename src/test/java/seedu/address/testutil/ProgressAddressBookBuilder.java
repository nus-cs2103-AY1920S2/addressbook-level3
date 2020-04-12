package seedu.address.testutil;

import seedu.address.model.modelProgress.Progress;
import seedu.address.model.modelProgress.ProgressAddressBook;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 * {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class ProgressAddressBookBuilder {

    private ProgressAddressBook progressAddressBook;

    public ProgressAddressBookBuilder() {
        progressAddressBook = new ProgressAddressBook();
    }

    public ProgressAddressBookBuilder(ProgressAddressBook progressAddressBook) {
        this.progressAddressBook = progressAddressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public ProgressAddressBookBuilder withProgress(Progress progress) {
        progressAddressBook.add(progress);
        return this;
    }

    public ProgressAddressBook build() {
        return progressAddressBook;
    }
}
