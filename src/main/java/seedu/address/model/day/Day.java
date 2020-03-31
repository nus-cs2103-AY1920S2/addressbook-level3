package seedu.address.model.day;

import java.util.ArrayList;

/**
 * Represents a day in the schedule visual.
 */
public class Day {
    private Hours hours;
    private ArrayList<DueAssignment> dueAssignments;

    public Day() {
        this.hours = new Hours(0);
        this.dueAssignments = new ArrayList<DueAssignment>();
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

    public ArrayList<DueAssignment> getDueAssignments() {
        return dueAssignments;
    }

    public void addDueAssignment(String assignment) {
        dueAssignments.add(new DueAssignment(assignment));
    }

    public String getDueAssignmentsToString() {
        String result = "";

        if (dueAssignments.size() != 0) {
            result = result + "1. " + dueAssignments.get(0).assignment;
        }

        for (int i = 1; i < dueAssignments.size(); i++) {
            result = result + "\n" + (i + 1) + ". " + dueAssignments.get(i).assignment;
        }
        return result;
    }

}
