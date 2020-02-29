package seedu.address.model.modelCourse;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyCourseAddressBook {

  /**
   * Returns an unmodifiable view of the teachers list. This list will not contain any duplicate
   * teachers.
   */
  ObservableList<Course> getCourseList();

}
