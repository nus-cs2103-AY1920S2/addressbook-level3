package seedu.address.model.assignment;


import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class AssignmentList {
    private final ArrayList<Assignment> assignments;

    public AssignmentList() {
       this.assignments = new ArrayList<Assignment>();
    }

    public void setAssignments(List<Assignment> assignments) {

    }

    /**
     * Adds an assignment to the list.
     */
    public void add(Assignment toAdd) {
        requireNonNull(toAdd);
        assignments.add(toAdd);

        System.out.println("Successfully added");
    }

    /**
     * Returns true if the list contains an equivalent assignment as the given argument.
     */
    public boolean contains(Assignment toCheck) {
        requireNonNull(toCheck);
        return assignments.stream().anyMatch(toCheck::isSameAssignment);
    }
}
