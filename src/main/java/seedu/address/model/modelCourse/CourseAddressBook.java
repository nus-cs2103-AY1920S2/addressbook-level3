package seedu.address.model.modelCourse;

import static java.util.Objects.requireNonNull;

import java.util.List;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by .isSamePerson
 * comparison)
 */
public class CourseAddressBook implements ReadOnlyCourseAddressBook {

  private final UniqueCourseList courses;

  /*
   * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
   * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
   *
   * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
   *   among constructors.
   */ {
    courses = new UniqueCourseList();
  }

  public CourseAddressBook() {
  }

  /**
   * Creates an AddressBook using the Persons in the {@code toBeCopied}
   */
  public CourseAddressBook(ReadOnlyCourseAddressBook toBeCopied) {
    this();
    resetData(toBeCopied);
  }

  //// list overwrite operations

  /**
   * Replaces the contents of the course list with {@code courses}. {@code courses} must not contain
   * duplicate courses.
   */
  public void setCourses(List<Course> courses) {
    this.courses.setCourses(courses);
  }

  /**
   * Resets the existing data of this {@code AddressBook} with {@code newData}.
   */
  public void resetData(ReadOnlyCourseAddressBook newData) {
    requireNonNull(newData);

    setCourses(newData.getCourseList());
  }

  //// course-level operations

  /**
   * Returns true if a course with the same identity as {@code course} exists in the address book.
   */
  public boolean hasCourses(Course course) {
    requireNonNull(course);
    return courses.contains(course);
  }

  /**
   * Adds a course to the address book. The course must not already exist in the address book.
   */
  public void addCourse(Course p) {
    courses.add(p);
  }

  /**
   * Replaces the given course {@code target} in the list with {@code editedCourse}. {@code target}
   * must exist in the address book. The course identity of {@code editedCourse} must not be the
   * same as another existing course in the address book.
   */
  public void setCourse(Course target, Course editedCourse) {
    requireNonNull(editedCourse);

    courses.setCourse(target, editedCourse);
  }

  /**
   * Removes {@code key} from this {@code AddressBook}. {@code key} must exist in the address book.
   */
  public void removeCourse(Course key) {
    courses.remove(key);
  }

  //// util methods

  @Override
  public String toString() {
    return courses.asUnmodifiableObservableList().size() + " courses";
    // TODO: refine later
  }

  @Override
  public ObservableList<Course> getCourseList() {
    return courses.asUnmodifiableObservableList();
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof CourseAddressBook
        // instanceof handles nulls
        && courses.equals(((CourseAddressBook) other).courses));
  }

  @Override
  public int hashCode() {
    return courses.hashCode();
  }
}
