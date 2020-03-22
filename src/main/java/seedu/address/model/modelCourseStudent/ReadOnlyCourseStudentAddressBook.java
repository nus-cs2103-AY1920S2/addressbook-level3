package seedu.address.model.modelCourseStudent;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyCourseStudentAddressBook {

  /**
   * Returns an unmodifiable view of the courseStudent list. This list will not contain any duplicate
   * courseStudent.
   */
  ObservableList<CourseStudent> getCourseStudentList();

}
