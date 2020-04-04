package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.address.model.assignment.exceptions.DuplicateAssignmentException;
import seedu.address.model.day.Day;

/**
 * A list of assignments that enforces uniqueness between its elements and does not allow nulls.
 * An assignment is considered unique by comparing using {@code Assignment#isSameAssignment(Assignment)}.
 * As such, adding and updating of assignments uses Assignment#isSameAssignment(Assignment) for equality so as to
 * ensure that the assignment being added or updated is unique in terms of identity in the AssignmentList.
 *
 * @see Assignment#isSameAssignment(Assignment)
 */
public class AssignmentList {

    private final ObservableList<Assignment> internalList = FXCollections.observableArrayList();
    private final ObservableList<Assignment> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private final ObservableList<Day> scheduleVisual = FXCollections.observableArrayList();

    public void setAssignments(AssignmentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void setAssignments(List<Assignment> replacement) {
        requireNonNull(replacement);
        if (!assignmentsAreUnique(replacement)) {
            throw new DuplicateAssignmentException();
        }
        internalList.setAll(replacement);
    }

    /**
     * Adds an assignment to the list.
     */
    public void add(Assignment toAdd) {
        requireNonNull(toAdd);

        if (contains(toAdd)) {
            throw new DuplicateAssignmentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns true if the list contains an equivalent assignment as the given argument.
     */
    public boolean contains(Assignment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAssignment);
    }

    /**
     * Removes the equivalent assignment from the list.
     * The assignment must exist in the list.
     */
    public void remove(Assignment toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AssignmentNotFoundException();
        }
    }

    /**
     * Sorts the scheduler list as an {@code ObservableList}.
     */
    public void sort(Comparator<Assignment> comparator) {
        FXCollections.sort(internalList, comparator);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Assignment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignmentList // instanceof handles nulls
                && internalList.equals(((AssignmentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    public void setAssignment(Assignment target, Assignment markedAssignment) {
        requireAllNonNull(target, markedAssignment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AssignmentNotFoundException();
        }
        if (!target.isSameAssignment(markedAssignment) && contains(markedAssignment)) {
            throw new DuplicateAssignmentException();
        }

        internalList.set(index, markedAssignment);
    }

    /**
     * Returns true if {@code assignment} contains only unique assignments.
     */
    private boolean assignmentsAreUnique(List<Assignment> assignments) {
        for (int i = 0; i < assignments.size() - 1; i++) {
            for (int j = i + 1; j < assignments.size(); j++) {
                if (assignments.get(i).isSameAssignment(assignments.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public ObservableList<Day> getScheduleVisualList() {
        return scheduleVisual;
    }

    /**
     * Populates an unmodifiable view of the intensity of the user's upcoming schedule for the next n days (including
     * today) calculated based on uncompleted stored assignments, their deadlines and estimated hours per assignment.
     *
     * @param numDays The number of days the user would like to display.
     *
     * Aim is to have the workload as evenly spread out across days (from current date to deadline) as possible.
     */
    public void calculateScheduleIntensity(int numDays) {
        ArrayList<Float> hoursPerDayList = new ArrayList<Float>();
        LocalDateTime currDateTime = LocalDateTime.now(ZoneId.of("Singapore"));
        ObservableList<Assignment> assignmentList = internalList.sorted(new DeadlineComparator());
        int currSize = scheduleVisual.size();

        hoursPerDayList.add((float) 0.0);

        if (numDays > currSize) {
            for (int i = 0; i < (numDays - currSize); i++) {
                scheduleVisual.add(new Day());
            }
        } else {
            for (int j = 0; j < (currSize - numDays); j++) {
                scheduleVisual.remove(scheduleVisual.size() - 1);
            }
        }

        for (int k = 0; k < scheduleVisual.size(); k++) {
            scheduleVisual.get(k).resetDueAssignments();
        }

        for (int j = 0; j < assignmentList.size(); j++) {
            String assignmentStatus = assignmentList.get(j).getStatus().status;

            if (assignmentStatus.equals(Status.ASSIGNMENT_OUTSTANDING)) {
                LocalDateTime deadline = assignmentList.get(j).getDeadline().dateTime;
                float estHours = Float.parseFloat(assignmentList.get(j).getWorkload().estHours);
                int noOfDaysBetween = (int) ChronoUnit.DAYS.between(currDateTime.toLocalDate(), deadline.toLocalDate());

                calculate(hoursPerDayList, deadline, estHours, noOfDaysBetween, currDateTime);

                if (noOfDaysBetween < numDays) {
                    scheduleVisual.get(noOfDaysBetween).addDueAssignment(assignmentList.get(j).getTitle().title);
                }
            }
        }

        // Set hours per day in result list
        for (int k = 0; k < Math.min(numDays, hoursPerDayList.size()); k++) {
            scheduleVisual.get(k).setHours((float) (Math.round(hoursPerDayList.get(k) * 2) / 2.0));
        }
    }

    /**
     * Allocates the current assignments estimated workload across several days (today included).
     * Allocates the time to the date of submission as well if the timing falls beyond 12pm.
     */
    public void calculate(ArrayList<Float> hoursPerDayList, LocalDateTime deadline, float estHours, int noOfDaysBetween,
                           LocalDateTime dateTime) {

        if (noOfDaysBetween == 0) {
            float diffInHours = java.time.Duration.between(dateTime, deadline).toHours();

            hoursPerDayList.set(0, hoursPerDayList.get(0) + Math.min(diffInHours, estHours));

        } else if (noOfDaysBetween > 0) {
            float[] currMinMax = getMinMax(hoursPerDayList, noOfDaysBetween);

            // Allocate hours to new days first (if any)
            int numNewDays = noOfDaysBetween - (hoursPerDayList.size() - 1);
            float hoursToAdd = estHours / numNewDays;

            if (deadline.toLocalTime().isBefore(LocalTime.NOON)) {
                numNewDays -= 1;
            }

            for (int i = 0; i < numNewDays; i++) {
                hoursPerDayList.add(Math.min(currMinMax[0], hoursToAdd));
                estHours -= Math.min(currMinMax[0], hoursToAdd);
            }

            // Allocate hours to days with the least number of work hours from today to deadline of assignment
            float daysWithMinHours = getMinDays(hoursPerDayList, noOfDaysBetween, currMinMax[0]);
            float diffBetweenMinAndMax = currMinMax[1] - currMinMax[0];
            hoursToAdd = estHours / daysWithMinHours;

            for (int j = 0; j < Math.min(hoursPerDayList.size(), noOfDaysBetween + 1); j++) {
                if (hoursPerDayList.get(j) == currMinMax[0]) {
                    hoursPerDayList.set(j, hoursPerDayList.get(j) + Math.min(hoursToAdd, diffBetweenMinAndMax));
                    estHours -= Math.min(hoursToAdd, diffBetweenMinAndMax);
                }
            }

            // Allocate remaining hours equally across the days up to deadline of assignment (if any)
            hoursToAdd = estHours / (noOfDaysBetween + 1);

            for (int k = 0; k < Math.min(hoursPerDayList.size(), noOfDaysBetween + 1); k++) {
                hoursPerDayList.set(k, hoursPerDayList.get(k) + hoursToAdd);
            }
        }
    }

    /**
     * Returns the min and max workload allocated to a particular day from today to assignment due date.
     * minMax[0]: Contains the minimum allocated workload.
     * minMax[1]: Contains the maximum allocated workload.
     *
     */
    private float[] getMinMax(ArrayList<Float> hoursPerDayList, int daysInBetween) {
        float[] minMax = new float[2];
        minMax[0] = hoursPerDayList.get(0);
        minMax[1] = hoursPerDayList.get(0);

        for (int i = 1; i < Math.min(hoursPerDayList.size(), daysInBetween + 1); i++) {
            minMax[0] = Math.min(minMax[0], hoursPerDayList.get(i));
            minMax[1] = Math.max(minMax[1], hoursPerDayList.get(i));
        }
        return minMax;
    }

    /**
     * Returns the number of days that have been allocated the least workload from today to the deadline of assignment.
     */
    private float getMinDays(ArrayList<Float> hoursPerDayList, int daysInBetween, float currMin) {
        float count = 0;

        for (int i = 0; i < Math.min(hoursPerDayList.size(), daysInBetween + 1); i++) {
            if (hoursPerDayList.get(i) == currMin) {
                count++;
            }
        }
        return count;
    }
}
