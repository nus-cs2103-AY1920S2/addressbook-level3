package seedu.address.model.modelCourseStudent;

import static java.util.Objects.requireNonNull;

import java.util.List;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson
 * comparison)
 */
public class CourseStudentAddressBook implements ReadOnlyCourseStudentAddressBook {

  private final UniqueCourseStudentList courseStudents;

  /*
   * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
   * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
   *
   * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
   *   among constructors.
   */ {
    courseStudents = new UniqueCourseStudentList();
  }

  public CourseStudentAddressBook() {
  }

  /**
   * Creates an AddressBook using the Persons in the {@code toBeCopied}
   */
  public CourseStudentAddressBook(ReadOnlyCourseStudentAddressBook toBeCopied) {
    this();
    resetData(toBeCopied);
  }

  //// list overwrite operations

  /**
   * Replaces the contents of the courseStudent list with {@code courseStudents}. {@code courseStudents} must not contain
   * duplicate courseStudents.
   */
  public void setCourseStudents(List<CourseStudent> courseStudents) {
    this.courseStudents.setCourseStudents(courseStudents);
  }

  /**
   * Resets the existing data of this {@code AddressBook} with {@code newData}.
   */
  public void resetData(ReadOnlyCourseStudentAddressBook newData) {
    requireNonNull(newData);

    setCourseStudents(newData.getCourseStudentList());
  }

  //// course-level operations

  /**
   * Returns true if a courseStudent with the same identity as {@code courseStudent} exists in the address book.
   */
  public boolean hasCourseStudents(CourseStudent courseStudent) {
    requireNonNull(courseStudent);
    return courseStudents.contains(courseStudent);
  }

  /**
   * Adds a course to the address book. The course must not already exist in the address book.
   */
  public void addCourseStudent(CourseStudent p) {
    courseStudents.add(p);
  }

  /**
   * Replaces the given course {@code target} in the list with {@code editedCourseStudent}. {@code target}
   * must exist in the address book. The course identity of {@code editedCourseStudent} must not be the
   * same as another existing course in the address book.
   */
  public void setCourseStudent(CourseStudent target, CourseStudent editedCourseStudent) {
    requireNonNull(editedCourseStudent);

    courseStudents.setCourseStudent(target, editedCourseStudent);
  }

  /**
   * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address book.
   */
  public void removeCourseStudent(CourseStudent key) {
    courseStudents.remove(key);
  }

  //// util methods

  @Override
  public String toString() {
    return courseStudents.asUnmodifiableObservableList().size() + " courseStudents";
    // TODO: refine later
  }

  @Override
  public ObservableList<CourseStudent> getCourseStudentList() {
    return courseStudents.asUnmodifiableObservableList();
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof CourseStudentAddressBook
        // instanceof handles nulls
        && courseStudents.equals(((CourseStudentAddressBook) other).courseStudents));
  }

  @Override
  public int hashCode() {
    return courseStudents.hashCode();
  }
}
