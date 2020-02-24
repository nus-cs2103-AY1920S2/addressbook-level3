package seedu.address.model.modelTeacher;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.Teacher;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTeacherAddressBook {

  /**
   * Returns an unmodifiable view of the teachers list.
   * This list will not contain any duplicate teachers.
   */
  ObservableList<Teacher> getTeacherList();

}
