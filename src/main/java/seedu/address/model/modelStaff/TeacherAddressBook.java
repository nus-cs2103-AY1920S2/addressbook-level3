package seedu.address.model.modelStaff;

import static java.util.Objects.requireNonNull;

import seedu.address.model.modelGeneric.AddressBookGeneric;
import seedu.address.model.modelGeneric.ReadOnlyAddressBookGeneric;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson
 * comparison)
 */
public class TeacherAddressBook extends AddressBookGeneric<Teacher> {

  public TeacherAddressBook() {
    super();
  }

  public TeacherAddressBook(ReadOnlyAddressBookGeneric<Teacher> toBeCopied) {
    super(toBeCopied);
  }

  @Override
  public String toString() {
    return objects.asUnmodifiableObservableList().size() + " teachers";
  }

}
