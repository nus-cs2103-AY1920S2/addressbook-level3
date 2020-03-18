package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;

/**
 * A list of assignments that enforces uniqueness between its elements and does not allow nulls.
 * An assignment is considered unique by comparing using {@code Assignment#isSameAssignment(Assignment)}.
 * As such, adding and updating of assignments uses Assignment#isSameAssignment(Assignment) for equality so as to
 * ensure that the assignment being added or updated is unique in terms of identity in the AssignmentList.
 *
 * @see Assignment#isSameAssignment(Assignment)
 */
public class AssignmentList {
    private final ArrayList<Assignment> assignments;

    public AssignmentList() {
        this.assignments = new ArrayList<Assignment>();
    }

    public void setAssignments(ArrayList<Assignment> replacement) {
        requireNonNull(replacement);
        assignments.addAll(replacement);
    }

    /**
     * Adds an assignment to the list.
     */
    public void add(Assignment toAdd) {
        requireNonNull(toAdd);
        assignments.add(toAdd);
    }

    /**
     * Returns true if the list contains an equivalent assignment as the given argument.
     */
    public boolean contains(Assignment toCheck) {
        requireNonNull(toCheck);
        return assignments.stream().anyMatch(toCheck::isSameAssignment);
    }

    /**
     * Returns assignment list.
     */
    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignment(Assignment target, Assignment markedAssignment) {
        requireAllNonNull(target, markedAssignment);

        int index = assignments.indexOf(target);
        if (index == -1) {
            throw new AssignmentNotFoundException();
        }

        assignments.set(index, markedAssignment);
    }
}
