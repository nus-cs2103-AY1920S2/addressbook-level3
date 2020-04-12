package seedu.address.model.modelCourse;

import seedu.address.model.modelGeneric.AddressBookGeneric;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson
 * comparison)
 */
public class CourseAddressBook extends AddressBookGeneric<Course> {

    /**
     * Creates an Course AddressBook.
     */
    public CourseAddressBook() {
        super();
    }

    /**
     * Creates an Course AddressBook using the objects in the {@code toBeCopied}
     */
    public CourseAddressBook(ReadOnlyAddressBookGeneric<Course> toBeCopied) {
        super(toBeCopied);
    }

    @Override
    public String toString() {
        return objects.asUnmodifiableObservableList().size() + " courses";
    }

}
