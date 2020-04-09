package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import javafx.collections.ObservableList;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentList;

/**
 * Wraps all data at the assignment schedule level.
 * Duplicates are not allowed (by .isSameAssignment comparison)
 */
public class AssignmentSchedule implements ReadOnlyAssignmentSchedule {
    private final AssignmentList assignments;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        assignments = new AssignmentList();

    }
    public AssignmentSchedule() {}

    /**
     * Creates an AssignmentList using the Assignments in the {@code toBeCopied}
     */
    public AssignmentSchedule(ReadOnlyAssignmentSchedule toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the assignment list with {@code assignments}.
     * Must not contain duplicate assignments.
     */
    public void setAssignments(ObservableList<Assignment> assignments) {
        this.assignments.setAssignments(assignments);
    }

    /**
     * Resets the existing data of this {@code AssignmentList} with {@code newData}.
     */
    public void resetData(ReadOnlyAssignmentSchedule newData) {
        requireNonNull(newData);
        setAssignments(newData.getAssignmentsList());
    }

    /**
     * Returns true if an identical assignment as {@code assignment} exists in the scheduler.
     */
    public boolean hasAssignment(Assignment assignment) {
        requireNonNull(assignment);
        return assignments.contains(assignment);
    }

    /**
     * Adds an assignment to the scheduler.
     * The assignment must not already exist.
     */
    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    /**
     * Sort assignments in the scheduler by the filter.
     */
    public void sortAssignment(Comparator<Assignment> comparator) {
        assignments.sort(comparator);
    }

    public void setAssignment(Assignment target, Assignment markedAssignment) {
        requireNonNull(markedAssignment);
        assignments.setAssignment(target, markedAssignment);
    }

    /**
     * Removes {@code key} from this {@code AssignmentSchedule}.
     * {@code key} must exist in the scheduler.
     */
    public void removeAssignment(Assignment key) {
        assignments.remove(key);
    }

    @Override
    public ObservableList<Assignment> getAssignmentsList() {
        return assignments.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignmentSchedule // instanceof handles nulls
                && assignments.equals(((AssignmentSchedule) other).assignments));
    }

    @Override
    public int hashCode() {
        return assignments.hashCode();
    }

}
