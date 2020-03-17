package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.exercise.Exercise;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Exercise} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Exercise exercise) {
        addressBook.addExercise(exercise);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
