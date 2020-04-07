package seedu.address.model.day;

import java.util.ArrayList;

/**
 * Represents a day in the schedule visual.
 */
public class Day {
    private Hours hours;
    private ArrayList<Assignment> dueAssignments;
    private ArrayList<Assignment> allocatedAssignments;

    public Day() {
        this.hours = new Hours(0);
        this.dueAssignments = new ArrayList<Assignment>();
        this.allocatedAssignments = new ArrayList<Assignment>();
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(float toAdd) {
        hours = new Hours(toAdd);
    }

    public void resetDueAssignments() {
        dueAssignments.clear();
    }

    public ArrayList<Assignment> getDueAssignments() {
        return dueAssignments;
    }

    public void addDueAssignment(String assignment) {
        dueAssignments.add(new Assignment(assignment));
    }

    public void resetAllocatedAssignments() {
        allocatedAssignments.clear();
    }

    public ArrayList<Assignment> getAllocatedAssignments() {
        return allocatedAssignments;
    }

    public void addAllocatedAssignment(float hours, String title) {
        allocatedAssignments.add(new AllocatedAssignment(hours, title));
    }
}
