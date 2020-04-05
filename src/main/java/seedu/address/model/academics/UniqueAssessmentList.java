package seedu.address.model.academics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.academics.exceptions.AssessmentNotFoundException;
import seedu.address.model.academics.exceptions.DuplicateAssessmentException;

/**
 * A list of assessments that enforces uniqueness between its elements and does not allow nulls.
 * A student is considered unique by comparing using {@code Assessment#isSameAssessment(Assessment)}. As such, adding
 * and updating of assessments uses Assessment#isSameAssessment(Assessment) for equality so as to ensure that the
 * assessment being added or updated is unique in terms of identity in the UniqueAssessmentList. However, the removal
 * of an assessment uses Assessment#equals(Object) so as to ensure that the student with exactly the same fields
 * will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Assessment#isSameAssessment(Assessment)
 */
public class UniqueAssessmentList implements Iterable<Assessment> {

    private final ObservableList<Assessment> internalList = FXCollections.observableArrayList();
    private final ObservableList<Assessment> homeworkList = FXCollections.observableArrayList();
    private final ObservableList<Assessment> examList = FXCollections.observableArrayList();
    private final ObservableList<Assessment> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /* SET METHODS */
    /**
     * Replaces the assessment {@code target} in the list with {@code editedAssessment}.
     * {@code target} must exist in the list.
     * The assessment identity of {@code editedAssessment} must not be the same as another existing assessment in
     * the list.
     */
    public void setAssessment(Assessment target, Assessment editedAssessment) {
        requireAllNonNull(target, editedAssessment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AssessmentNotFoundException();
        }

        if (!target.isSameAssessment(editedAssessment) && contains(editedAssessment)) {
            throw new DuplicateAssessmentException();
        }

        internalList.set(index, editedAssessment);
    }

    public void setAssessments(UniqueAssessmentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code assessments}.
     * {@code assessments} must not contain duplicate assessments.
     */
    public void setAssessments(List<Assessment> assessments) {
        requireAllNonNull(assessments);
        if (!assessmentsAreUnique(assessments)) {
            throw new DuplicateAssessmentException();
        }
        internalList.setAll(assessments);
    }

    /* ASSESSMENT-LEVEL METHODS */
    /**
     * Returns true if the list contains an equivalent assessment as the given argument.
     */
    public boolean contains(Assessment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAssessment);
    }

    /**
     * Adds an assessment to the list.
     * The assessment must not already exist in the list.
     */
    public void add(Assessment toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAssessmentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent assessment from the list.
     * The assessment must exist in the list.
     */
    public void remove(Assessment toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AssessmentNotFoundException();
        }
    }

    /**
     * Adds new student to the submission tracker of all assessments.
     */
    public void addStudentToAssessments(String toAdd) {
        requireNonNull(toAdd);
        Iterator<Assessment> iterator = iterator();
        while (iterator.hasNext()) {
            Assessment next = iterator.next();
            next.addStudent(toAdd);
        }
    }

    /**
     * Removes student to the submission tracker of all assessments.
     */
    public void removeStudentFromAssessments(String toRemove) {
        requireNonNull(toRemove);
        Iterator<Assessment> iterator = iterator();
        while (iterator.hasNext()) {
            iterator.next().removeStudent(toRemove);
        }
    }

    /**
     * Updates the student name in the submission tracker of assessment.
     */
    public void updateStudentToAssessments(String prevName, String newName) {
        requireAllNonNull(prevName, newName);
        Iterator<Assessment> iterator = iterator();
        while (iterator.hasNext()) {
            iterator.next().updateNewStudentName(prevName, newName);
        }
    }

    /**
     * Submits students' submissions to the assessment in {@code Academics}.
     * {@code target} must exist in the assessment list.
     */
    public void submitAssessment(Assessment target, List<String> students) {
        requireAllNonNull(target, students);
        int index = internalList.indexOf(target);
        internalList.get(index).setSubmitted(students);
    }

    /**
     * Returns true if the student has submitted their work for the given assessment.
     * record.
     */
    public boolean hasStudentSubmitted(Assessment assessment, String student) {
        requireAllNonNull(assessment, student);
        int index = internalList.indexOf(assessment);
        return internalList.get(index).hasStudentSubmitted(student);
    }

    /**
     * Marks students' submissions to the assessment in {@code Academics}.
     * {@code target} must exist in the assessment list.
     */
    public void markAssessment(Assessment target, HashMap<String, Integer> submissions) {
        requireAllNonNull(target, submissions);
        int index = internalList.indexOf(target);
        internalList.get(index).markAssessment(submissions);
    }

    /* UTIL METHODS */
    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Assessment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns the list of homework assessments.
     */
    public ObservableList<Assessment> getHomeworkList() {
        homeworkList.clear();;
        Iterator<Assessment> iterator = iterator();
        while (iterator.hasNext()) {
            Assessment next = iterator.next();
            if (next instanceof Homework) {
                homeworkList.add(next);
            }
        }
        return homeworkList;
    }

    /**
     * Returns the list of exam assessments.
     */
    public ObservableList<Assessment> getExamList() {
        examList.clear();;
        Iterator<Assessment> iterator = iterator();
        while (iterator.hasNext()) {
            Assessment next = iterator.next();
            if (next instanceof Exam) {
                examList.add(next);
            }
        }
        return examList;
    }

    @Override
    public Iterator<Assessment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAssessmentList // instanceof handles nulls
                && internalList.equals(((UniqueAssessmentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code students} contains only unique students.
     */
    private boolean assessmentsAreUnique(List<Assessment> assessments) {
        for (int i = 0; i < assessments.size() - 1; i++) {
            for (int j = i + 1; j < assessments.size(); j++) {
                if (assessments.get(i).isSameAssessment(assessments.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
