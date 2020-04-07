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
    public void generateSchedule(int numDays) {
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

        // Set hours per day in result list with a cap of 24 hours per day
        for (int k = 0; k < Math.min(numDays, hoursPerDayList.size()); k++) {
            scheduleVisual.get(k).setHours((float) Math.min(Math.round((hoursPerDayList.get(k) * 2) / 2.0), 24));
        }
    }

    /**
     * Allocates the current assignments estimated workload across several days (today included).
     * Does not allocate any time to
     */
    public void calculate(ArrayList<Float> hoursPerDayList, LocalDateTime deadline, float estHours, int noOfDaysBetween,
                           LocalDateTime dateTime) {

        if (noOfDaysBetween == 0) {
            // Allocate only the remaining time the user has before the deadline
            float diffInHours = java.time.Duration.between(dateTime, deadline).toHours();
            hoursPerDayList.set(0, hoursPerDayList.get(0) + Math.min(diffInHours, estHours));

        } else if (noOfDaysBetween > 0) {
            float[] currMinAndSecondMin = getMinAndSecondMin(hoursPerDayList, noOfDaysBetween);
            float numHoursToDeadline = 0;

            // Allocate hours to new days first (if any)
            int numNewDays = noOfDaysBetween - (hoursPerDayList.size() - 1);

            if (numNewDays != 0) {
                float hoursToAdd = estHours / numNewDays;
                float hoursAdded = Math.min(currMinAndSecondMin[0], hoursToAdd);

                for (int i = 0; i < numNewDays; i++) {
                    hoursPerDayList.add(hoursAdded);
                    estHours -= hoursAdded;
                }
                numHoursToDeadline += hoursAdded;
            }

            // Allocate hours to days with the least amount of work allocated in increasing order
            while (currMinAndSecondMin[0] != currMinAndSecondMin[1]) {
                int daysWithMinHours = getMinDays(hoursPerDayList, noOfDaysBetween, currMinAndSecondMin[0]);
                float diffBetweenMinAndSecondMin = currMinAndSecondMin[1] - currMinAndSecondMin[0];
                float hoursToAdd = estHours / daysWithMinHours;
                float hoursAdded = Math.min(hoursToAdd, diffBetweenMinAndSecondMin);

                numHoursToDeadline += allocateHoursToMinDays(hoursPerDayList, noOfDaysBetween,
                    hoursAdded, currMinAndSecondMin[0], estHours);
                currMinAndSecondMin = getMinAndSecondMin(hoursPerDayList, noOfDaysBetween);
            }

            // Allocate remaining hours equally across the days up to deadline of assignment (if any)
            float hoursAdded = estHours / (noOfDaysBetween);

            for (int k = 0; k < Math.min(hoursPerDayList.size(), noOfDaysBetween); k++) {
                hoursPerDayList.set(k, hoursPerDayList.get(k) + hoursAdded);
            }
            numHoursToDeadline += hoursAdded;

            //Check whether the amount of time allocated to day of deadline exceeds the number of hours between
            //midnight and deadline time
            
        }
    }

    /**
     * Allocates hours to days which currently have the least amount of hours allocated.
     *
     * @return the amount of time
     */
    private float allocateHoursToMinDays(ArrayList<Float> hoursPerDayList, int daysInBetween, float hoursAdded, float currMin, float estHours) {
        float numHoursToDeadline = 0;

        for (int j = 0; j < Math.min(hoursPerDayList.size(), daysInBetween + 1); j++) {
            if (hoursPerDayList.get(j) == currMin) {
                hoursPerDayList.set(j, hoursPerDayList.get(j) + hoursAdded);
                estHours -= hoursAdded;

                if (j == daysInBetween) {
                    numHoursToDeadline += hoursAdded;
                }
            }
        }

        return numHoursToDeadline;
    }


    /**
     * Returns the minimum and second minimum hours allocated to a day.
     * result[0]: Minimum hours allocated to a day.
     * result[1]: Second minimum hours allocated to a day.
     */
    private float[] getMinAndSecondMin(ArrayList<Float> hoursPerDayList, int daysInBetween) {
        float[] result = {Float.MAX_VALUE, Float.MAX_VALUE};

        for (int i = 0; i < Math.min(hoursPerDayList.size(), daysInBetween + 1); i++) {
            if (hoursPerDayList.get(i) < result[0]) {
                result[0] = hoursPerDayList.get(i);
                result[1] = hoursPerDayList.get(i);
            } else if (hoursPerDayList.get(i) < result[1]) {
                result[1] = hoursPerDayList.get(i);
            }
        }
        return result;
    }

    /**
     * Returns the number of days that have been allocated the least workload from today to deadline.
     */
    private int getMinDays(ArrayList<Float> hoursPerDayList, int daysInBetween, float currMin) {
        int count = 0;

        for (int i = 0; i < Math.min(hoursPerDayList.size(), daysInBetween + 1); i++) {
            if (hoursPerDayList.get(i) == currMin) {
                count++;
            }
        }
        return count;
    }
}
