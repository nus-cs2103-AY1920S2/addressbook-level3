package seedu.address.model.event.tutorial;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.event.tutorial.exceptions.DuplicateTutorialException;
import seedu.address.model.event.tutorial.exceptions.TutorialNotFoundException;
import seedu.address.model.student.Student;

/**
 * A list of tutorials that enforces uniqueness between its elements and does not allow nulls.
 * A tutorial is considered unique by comparing using {@code Tutorial#equals(Tutorial)}. As such, adding, updating and
 * removal of tutorial uses Tutorial#equals(Tutorial) so as to ensure that the tutorial being added, updated or removed
 * is unique in terms of identity in the UniqueTutorialList.
 *
 * Supports a minimal set of list operations.
 *
 * @see Tutorial#equals(Object)
 */

public class UniqueTutorialList implements Iterable<Tutorial> {

    private final ObservableList<Tutorial> internalList = FXCollections.observableArrayList();
    private final ObservableList<Tutorial> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent tutorial as the given argument.
     */
    public boolean contains(Tutorial toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the specified tutorial in the given argument contains a student that matches the student
     * in the argument.
     */
    public boolean containsTutorialStudent(Tutorial tutorial, Student student) {
        requireAllNonNull(tutorial, student);
        return tutorial.tutorialStudentClash(student);
    }

    /**
     * Returns true if the list contains another tutorial timing clashes with the argument.
     */
    public boolean hasSameTiming(Tutorial tutorial) {
        requireNonNull(tutorial);
        return internalList.stream().filter(other -> tutorial.timeClash(other)).count() > 0;
    }

    /**
     * Adds a tutorial to the list.
     * The tutorial must not already exist in the list.
     */
    public void add(Tutorial toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTutorialException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds a student to a tutorial to the list.
     * The tutorial must already exist in the list.
     */
    public void addTutorialStudent(Tutorial toAddTo, Student student) {
        requireAllNonNull(toAddTo, student);
        long matchCount = internalList.stream().filter(toAddTo::equals).count();

        if (matchCount == 1) {
            internalList.stream().filter(toAddTo::equals).forEach(tut -> tut.setEnrolledStudents(student));
        } else if (matchCount == 0) {
            throw new TutorialNotFoundException();
        } else {
            // matchCount > 1
            throw new DuplicateTutorialException();
        }
    }


    /**
     * Removes the equivalent tutorial from the list.
     * The tutorial must exist in the list.
     */
    public void remove(Tutorial toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TutorialNotFoundException();
        }
    }

    /**
     * Removes the equivalent student from the given tutorial.
     * The tutorial must exist in the list, and the student must exist in the tutorial.
     */
    public void deleteTutorialStudent(Tutorial toDeleteFrom, Student student) {
        requireAllNonNull(toDeleteFrom, student);
        long matchCount = internalList.stream().filter(toDeleteFrom::equals).count();

        if (matchCount == 1) {
            internalList.stream().filter(toDeleteFrom::equals).forEach(tut -> tut.removeEnrolledStudent(student));
        } else if (matchCount == 0) {
            throw new TutorialNotFoundException();
        } else {
            // matchCount > 1
            throw new DuplicateTutorialException();
        }
    }

    /**
     * Edits the equivalent student from the given tutorial and updates the student.
     * The tutorial must exist in the list, and the student to be edited must exist in the tutorial.
     */
    public void editTutorialStudent(Tutorial toEditFrom, Student studentToEdit, Student editedStudent) {
        requireAllNonNull(toEditFrom, studentToEdit);
        long matchCount = internalList.stream().filter(toEditFrom::equals).count();

        if (matchCount == 1) {
            internalList.stream().filter(toEditFrom::equals)
                    .forEach(tut -> tut.editEnrolledStudent(studentToEdit, editedStudent));
        } else if (matchCount == 0) {
            throw new TutorialNotFoundException();
        } else {
            // matchCount > 1
            throw new DuplicateTutorialException();
        }
    }

    /**
     * Marks the equivalent student in the given tutorial as present.
     * The tutorial must exist in the list, and the student must exist in the tutorial.
     */
    public void markPresent(Tutorial tutorialToMark, Student studentToMark, int weekZeroBased) {
        requireAllNonNull(tutorialToMark, studentToMark);
        long matchCount = internalList.stream().filter(tutorialToMark::equals).count();

        if (matchCount == 1) {
            internalList.stream()
                .filter(tutorialToMark::equals)
                .forEach(tut -> tut.markPresent(studentToMark, weekZeroBased));
        } else if (matchCount == 0) {
            throw new TutorialNotFoundException();
        } else {
            // matchCount > 1
            throw new DuplicateTutorialException();
        }
    }

    /**
     * Marks the equivalent student in the given tutorial as absent.
     * The tutorial must exist in the list, and the student must exist in the tutorial.
     */
    public void markAbsent(Tutorial tutorialToMark, Student studentToMark, int week) {
        requireAllNonNull(tutorialToMark, studentToMark);
        long matchCount = internalList.stream().filter(tutorialToMark::equals).count();

        if (matchCount == 1) {
            internalList.stream().filter(tutorialToMark::equals).forEach(tut -> tut.markAbsent(studentToMark, week));
        } else if (matchCount == 0) {
            throw new TutorialNotFoundException();
        } else {
            // matchCount > 1
            throw new DuplicateTutorialException();
        }
    }

    public void setTutorials(UniqueTutorialList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code tutorials}.
     * {@code tutorials} must not contain duplicate tutorials.
     */
    public void setTutorials(List<Tutorial> tutorials) {
        requireAllNonNull(tutorials);
        if (!tutorialsAreUnique(tutorials)) {
            throw new DuplicateTutorialException();
        }

        internalList.setAll(tutorials);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Tutorial> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Tutorial> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTutorialList // instanceof handles nulls
                && internalList.equals(((UniqueTutorialList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code tutorials} contains only unique tutorials.
     */
    private boolean tutorialsAreUnique(List<Tutorial> tutorials) {
        return CollectionUtil.isUnique(tutorials);
    }

    /**
     * Returns the tutorial at the {@code index}.
     * @param index Index of the tutorial.
     * @return Tutorial at the index.
     */
    public Tutorial getTutorial(int index) {
        return internalList.get(index);
    }

    public ObservableList<Tutorial> getAllTutorials() {
        return this.internalList;
    }
}
