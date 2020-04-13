//@@author fatin99

package tatracker.model.student;

import static java.util.Objects.requireNonNull;
import static tatracker.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import tatracker.model.student.exceptions.DuplicateStudentException;
import tatracker.model.student.exceptions.StudentNotFoundException;

/**
 * A list of students that enforces uniqueness between its elements and does not allow nulls.
 * A student is considered unique by comparing using {@code Student#isSameStudent(Student)}. As such, adding and
 * updating of students uses Student#isSameStudent(Student) for equality so as to ensure that the student being added or
 * updated is unique in terms of identity in the UniqueStudentList. However, the removal of a student uses
 * Student#equals(Object) so as to ensure that the student with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Student#isSameStudent(Student)
 */
public class UniqueStudentList implements Iterable<Student> {

    private final ObservableList<Student> internalList = FXCollections.observableArrayList();
    private final ObservableList<Student> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    //@@author aakanksha-rai

    private Comparator<Student> alphabetically = Comparator.comparing(Student::getName);

    private Comparator<Student> ratingAscending = Comparator.comparing(Student::getRating);
    private Comparator<Student> ratingDescending = ratingAscending.reversed();

    private Comparator<Student> matric = Comparator.comparing(Student::getMatric);

    //@@author fatin99

    public int size() {
        return internalList.size();
    }

    /**
     * Returns true if the list contains an equivalent student as the given argument.
     */
    public boolean contains(Student toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameStudent);
    }

    /**
     * Returns true if the list contains an equivalent student with the given matric number.
     */
    public boolean contains(Matric toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(student -> student.getMatric().equals(toCheck));
    }

    public Student get(int n) {
        return internalList.get(n);
    }

    /**
     * Returns the student in this list with the given student
     * matriculation number (the student id).
     * Returns null if no such student exists.
     */
    public Student get(Matric studentId) {
        for (Student student : internalList) {
            if (student.getMatric().equals(studentId)) {
                return student;
            }
        }
        return null; // Did not find a student with the given student id
    }

    /**
     * Adds a student to the list.
     * The student must not already exist in the list.
     */
    public void add(Student toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStudentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent student from the list.
     * The student must exist in the list.
     */
    public void remove(Student toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new StudentNotFoundException();
        }
    }

    /**
     * Replaces the student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the list.
     * The student identity of {@code editedStudent} must not be the same as another existing student in the list.
     */
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new StudentNotFoundException();
        }

        if (!target.isSameStudent(editedStudent) && contains(editedStudent)) {
            throw new DuplicateStudentException();
        }

        internalList.set(index, editedStudent);
    }

    public void setStudents(UniqueStudentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code students}.
     * {@code students} must not contain duplicate students.
     */
    public void setStudents(List<Student> students) {
        requireAllNonNull(students);
        if (!studentsAreUnique(students)) {
            throw new DuplicateStudentException();
        }

        internalList.setAll(students);
    }

    //@@author Eclmist

    /**
     * Returns all students of a particular rating
     *
     * @param rating The target rating of students to return
     */
    public List<Student> getStudentsOfRating(Rating rating) {
        return internalList.filtered(s -> s.getRating().equals(rating));
    }

    //@@author aakanksha-rai

    /**
     * Sorts the students alphabetically.
     */
    public void sortAlphabetically() {
        FXCollections.sort(internalList, alphabetically);
    }

    /**
     * Sorts the students by rating in ascending order.
     */
    public void sortByRatingAscending() {
        FXCollections.sort(internalList, ratingAscending);
    }

    /**
     * Sorts the students by matric number.
     */
    public void sortByMatric() {
        FXCollections.sort(internalList, matric);
    }

    /**
     * Sorts the students by rating in descending order.
     */
    public void sortByRatingDescending() {
        FXCollections.sort(internalList, ratingDescending);
    }

    //@@author fatin99

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Student> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Student> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueStudentList // instanceof handles nulls
                && internalList.equals(((UniqueStudentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code students} contains only unique students.
     */
    private boolean studentsAreUnique(List<Student> students) {
        for (int i = 0; i < students.size() - 1; i++) {
            for (int j = i + 1; j < students.size(); j++) {
                if (students.get(i).isSameStudent(students.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
