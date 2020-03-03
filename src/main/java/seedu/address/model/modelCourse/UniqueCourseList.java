package seedu.address.model.modelCourse;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.CourseNotFoundException;
import seedu.address.model.person.exceptions.DuplicateCourseException;

/**
 * A list of courses that enforces uniqueness between its elements and does not allow nulls. A
 * course is considered unique by comparing using {@code Course#isSameCourse(Course)}. As such,
 * adding and updating of courses uses Course#isSameCourse(Course) for equality so as to ensure that
 * the course being added or updated is unique in terms of identity in the UniqueCourseList.
 * However, the removal of a course uses course#equals(Object) so as to ensure that the course with
 * exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Course#isSameCourse(Course)
 */
public class UniqueCourseList implements Iterable<Course> {

  private final ObservableList<Course> internalList = FXCollections.observableArrayList();
  private final ObservableList<Course> internalUnmodifiableList =
      FXCollections.unmodifiableObservableList(internalList);

  /**
   * Returns true if the list contains an equivalent course as the given argument.
   */
  public boolean contains(Course toCheck) {
    requireNonNull(toCheck);
    return internalList.stream().anyMatch(toCheck::isSameCourse);
  }

  /**
   * Adds a course to the list. The course must not already exist in the list.
   */
  public void add(Course toAdd) {
    requireNonNull(toAdd);
    if (contains(toAdd)) {
      throw new DuplicateCourseException();
    }
    internalList.add(toAdd);
  }

  /**
   * Replaces the course {@code target} in the list with {@code editedCourse}. {@code target} must
   * exist in the list. The Course identity of {@code editedCourse} must not be the same as another
   * existing course in the list.
   */
  public void setCourse(Course target, Course editedCourse) {
    requireAllNonNull(target, editedCourse);

    int index = internalList.indexOf(target);
    if (index == -1) {
      throw new CourseNotFoundException();
    }

    if (!target.isSameCourse(editedCourse) && contains(editedCourse)) {
      throw new DuplicateCourseException();
    }

    internalList.set(index, editedCourse);
  }

  /**
   * Removes the equivalent course from the list. The course must exist in the list.
   */
  public void remove(Course toRemove) {
    requireNonNull(toRemove);
    if (!internalList.remove(toRemove)) {
      throw new CourseNotFoundException();
    }
  }

  public void setCourses(UniqueCourseList replacement) {
    requireNonNull(replacement);
    internalList.setAll(replacement.internalList);
  }

  /**
   * Replaces the contents of this list with {@code courses}. {@code courses} must not contain
   * duplicate courses.
   */
  public void setCourses(List<Course> courses) {
    requireAllNonNull(courses);
    if (!coursesAreUnique(courses)) {
      throw new DuplicateCourseException();
    }

    internalList.setAll(courses);
  }

  /**
   * Returns the backing list as an unmodifiable {@code ObservableList}.
   */
  public ObservableList<Course> asUnmodifiableObservableList() {
    return internalUnmodifiableList;
  }

  @Override
  public Iterator<Course> iterator() {
    return internalList.iterator();
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof UniqueCourseList
        // instanceof handles nulls
        && internalList.equals(((UniqueCourseList) other).internalList));
  }

  @Override
  public int hashCode() {
    return internalList.hashCode();
  }

  /**
   * Returns true if {@code courses} contains only unique courses.
   */
  private boolean coursesAreUnique(List<Course> courses) {
    for (int i = 0; i < courses.size() - 1; i++) {
      for (int j = i + 1; j < courses.size(); j++) {
        if (courses.get(i).isSameCourse(courses.get(j))) {
          return false;
        }
      }
    }
    return true;
  }
}
