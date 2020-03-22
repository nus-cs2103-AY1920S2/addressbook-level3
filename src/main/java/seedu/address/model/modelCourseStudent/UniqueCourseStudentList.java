package seedu.address.model.modelCourseStudent;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.CourseStudentNotFoundException;
import seedu.address.model.person.exceptions.DuplicateCourseStudentException;

/**
 * A list of courseStudents that enforces uniqueness between its elements and does not allow nulls. A
 * courseStudent is considered unique by comparing using {@code CourseStudent#isSameCourseStudent(CourseStudent)}. As such,
 * adding and updating of courseStudents uses CourseStudent#isSameCourseStudent(CourseStudent) for equality so as to ensure that
 * the courseStudent being added or updated is unique in terms of identity in the UniqueCourseStudentList.
 * However, the removal of a course uses courseStudent#equals(Object) so as to ensure that the course with
 * exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see CourseStudent#isSameCourseStudent(CourseStudent)
 */
public class UniqueCourseStudentList implements Iterable<CourseStudent> {

  private final ObservableList<CourseStudent> internalList = FXCollections.observableArrayList();
  private final ObservableList<CourseStudent> internalUnmodifiableList =
      FXCollections.unmodifiableObservableList(internalList);

  /**
   * Returns true if the list contains an equivalent courseStudent as the given argument.
   */
  public boolean contains(CourseStudent toCheck) {
    requireNonNull(toCheck);
    return internalList.stream().anyMatch(toCheck::isSameCourseStudent);
  }

  /**
   * Adds an courseStudent to the list. The courseStudent must not already exist in the list.
   */
  public void add(CourseStudent toAdd) {
    requireNonNull(toAdd);
    if (contains(toAdd)) {
      throw new DuplicateCourseStudentException();
    }
    internalList.add(toAdd);
  }

  /**
   * Replaces the course {@code target} in the list with {@code editedCourseStudent}. {@code target} must
   * exist in the list. The CourseStudent identity of {@code editedCourseStudent} must not be the same as another
   * existing course in the list.
   */
  public void setCourseStudent(CourseStudent target, CourseStudent editedCourseStudent) {
    requireAllNonNull(target, editedCourseStudent);

    int index = internalList.indexOf(target);
    if (index == -1) {
      throw new CourseStudentNotFoundException();
    }

    if (!target.isSameCourseStudent(editedCourseStudent) && contains(editedCourseStudent)) {
      throw new DuplicateCourseStudentException();
    }

    internalList.set(index, editedCourseStudent);
  }

  /**
   * Removes the equivalent CourseStudent from the list. The CourseStudent must exist in the list.
   */
  public void remove(CourseStudent toRemove) {
    requireNonNull(toRemove);
    if (!internalList.remove(toRemove)) {
      throw new CourseStudentNotFoundException();
    }
  }

  public void setCourseStudents(UniqueCourseStudentList replacement) {
    requireNonNull(replacement);
    internalList.setAll(replacement.internalList);
  }

  /**
   * Replaces the contents of this list with {@code courseStudents}. {@code courseStudents} must not contain
   * duplicate courseStudents.
   */
  public void setCourseStudents(List<CourseStudent> courseStudents) {
    requireAllNonNull(courseStudents);
    if (!courseStudentsAreUnique(courseStudents)) {
      throw new DuplicateCourseStudentException();
    }

    internalList.setAll(courseStudents);
  }

  /**
   * Returns the backing list as an unmodifiable {@code ObservableList}.
   */
  public ObservableList<CourseStudent> asUnmodifiableObservableList() {
    return internalUnmodifiableList;
  }

  @Override
  public Iterator<CourseStudent> iterator() {
    return internalList.iterator();
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof UniqueCourseStudentList
        // instanceof handles nulls
        && internalList.equals(((UniqueCourseStudentList) other).internalList));
  }

  @Override
  public int hashCode() {
    return internalList.hashCode();
  }

  /**
   * Returns true if {@code courseStudents} contains only unique courseStudents.
   */
  private boolean courseStudentsAreUnique(List<CourseStudent> courseStudents) {
    for (int i = 0; i < courseStudents.size() - 1; i++) {
      for (int j = i + 1; j < courseStudents.size(); j++) {
        if (courseStudents.get(i).isSameCourseStudent(courseStudents.get(j))) {
          return false;
        }
      }
    }
    return true;
  }
}
