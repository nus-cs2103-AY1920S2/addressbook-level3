package seedu.address.model.day;

import java.util.ArrayList;

/**
 * Represents a {@code Day} in the {@code ScheduleList}.
 */
public class Day {
    private Hours totalAllocatedHours;
    private ArrayList<Assignment> dueAssignments;
    private ArrayList<Assignment> allocatedAssignments;

    public Day() {
        this.totalAllocatedHours = new Hours(0);
        this.dueAssignments = new ArrayList<>();
        this.allocatedAssignments = new ArrayList<>();
    }

    public Hours getTotalAllocatedHours() {
        return totalAllocatedHours;
    }

    public void setTotalAllocatedHours(float toAdd) {
        totalAllocatedHours = new Hours(toAdd);
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

    public void addAllocatedAssignment(float hours, String assignment) {
        allocatedAssignments.add(new AllocatedAssignment(hours, assignment));
    }
}
