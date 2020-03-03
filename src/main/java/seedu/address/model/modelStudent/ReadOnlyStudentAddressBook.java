package seedu.address.model.modelStudent;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyStudentAddressBook {

  /**
   * Returns an unmodifiable view of the teachers list. This list will not contain any duplicate
   * teachers.
   */
  ObservableList<Student> getStudentList();

}
