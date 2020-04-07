package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
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
        ArrayList<Float> cumulativeHoursPerDayList = new ArrayList<Float>();
        LocalDateTime currDateTime = LocalDateTime.now(ZoneId.of("Singapore"));
        ObservableList<Assignment> assignmentList = internalList.sorted(new DeadlineComparator());
        int currSize = scheduleVisual.size();

        cumulativeHoursPerDayList.add((float) 0.0);

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
            scheduleVisual.get(k).resetAllocatedAssignments();
        }

        for (int l = 0; l < assignmentList.size(); l++) {
            String assignmentStatus = assignmentList.get(l).getStatus().status;
            String assignmentTitle = assignmentList.get(l).getTitle().title;
            LocalDateTime deadline = assignmentList.get(l).getDeadline().dateTime;

            if (assignmentStatus.equals(Status.ASSIGNMENT_OUTSTANDING) && deadline.isAfter(currDateTime)) {
                float hoursToBeAllocated = Float.parseFloat(assignmentList.get(l).getWorkload().estHours);
                int noOfDaysBetween = (int) ChronoUnit.DAYS.between(currDateTime.toLocalDate(), deadline.toLocalDate());

                ArrayList<Float> allocationResult = allocateHours(cumulativeHoursPerDayList, deadline,
                    hoursToBeAllocated, noOfDaysBetween, currDateTime);

                populateScheduledAssignments(allocationResult, numDays, noOfDaysBetween, assignmentTitle);
            }
        }

        // Set hours per day in result list with a cap of 24 hours per day
        for (int k = 0; k < Math.min(numDays, cumulativeHoursPerDayList.size()); k++) {
            System.out.println(cumulativeHoursPerDayList.get(k));
            scheduleVisual.get(k)
                .setHours((float) Math.min(Math.round((cumulativeHoursPerDayList.get(k) * 2)) / 2.0, 24));
        }
    }

    /**
     * Populates the schedule with the assignments allocated and due on that day.
     */
    private void populateScheduledAssignments(ArrayList<Float> allocationResult, int numDays, int noOfDaysBetween,
                                              String assignmentTitle) {
        for (int i = 0; i < Math.min(allocationResult.size(), numDays); i++) {
            if ((float) (Math.round(allocationResult.get(i) * 2) / 2.0) != 0) {
                scheduleVisual.get(i).addAllocatedAssignment((float) (Math.round(allocationResult.get(i) * 2) / 2.0),
                    assignmentTitle);
            }
        }

        if (noOfDaysBetween < numDays) {
            scheduleVisual.get(noOfDaysBetween).addDueAssignment(assignmentTitle);
        }
    }

    /**
     * Allocates the current assignments estimated workload across several days (today included).
     * Allocates time to the day the assignment is due as well but re-allocates the time if the user.
     */
    public ArrayList<Float> allocateHours(ArrayList<Float> cumulativeHoursPerDayList, LocalDateTime deadline,
                                          float hoursToBeAllocated, int noOfDaysBetween, LocalDateTime currDateTime) {

        // Keeps track of the amount of time allocated to each day for this assignment.
        ArrayList<Float> hoursPerDayList = new ArrayList<>();

        if (noOfDaysBetween == 0) {
            // Allocate only the remaining time the user has before the deadline
            float diffInHours = Duration.between(currDateTime, deadline).toHours();
            cumulativeHoursPerDayList.set(0, cumulativeHoursPerDayList.get(0)
                + Math.min(diffInHours, hoursToBeAllocated));
            hoursPerDayList.add(Math.min(diffInHours, hoursToBeAllocated));

            return hoursPerDayList;

        } else if (noOfDaysBetween > 0) {
            float[] currMinAndSecondMin = getMinAndSecondMin(cumulativeHoursPerDayList, noOfDaysBetween);

            int currSize = cumulativeHoursPerDayList.size();

            // Initialise hoursPerDayList
            for (int i = 0; i < currSize; i++) {
                hoursPerDayList.add((float) 0);
            }

            // Allocate hours to new days first (if any)
            int numNewDays = noOfDaysBetween - (currSize - 1);

            if (numNewDays != 0) {
                float hoursToAdd = hoursToBeAllocated / numNewDays;
                float hoursAdded = Math.min(currMinAndSecondMin[0], hoursToAdd);

                for (int i = 0; i < numNewDays; i++) {
                    cumulativeHoursPerDayList.add(hoursAdded);
                    hoursPerDayList.add(hoursAdded);
                    hoursToBeAllocated -= hoursAdded;
                }
            }

            // Allocate hours to days with the least amount of work allocated in increasing order
            while (currMinAndSecondMin[0] != currMinAndSecondMin[1] && hoursToBeAllocated != 0) {
                int daysWithMinHours = getMinDays(cumulativeHoursPerDayList, noOfDaysBetween, currMinAndSecondMin[0]);
                float diffBetweenMinAndSecondMin = currMinAndSecondMin[1] - currMinAndSecondMin[0];
                float hoursToAdd = hoursToBeAllocated / daysWithMinHours;
                float hoursAdded = Math.min(hoursToAdd, diffBetweenMinAndSecondMin);

                hoursToBeAllocated -= allocateHoursToMinDays(cumulativeHoursPerDayList, noOfDaysBetween, hoursAdded,
                    currMinAndSecondMin[0], hoursPerDayList);

                currMinAndSecondMin = getMinAndSecondMin(cumulativeHoursPerDayList, noOfDaysBetween);
            }

            // Allocate remaining hours equally across the days up to deadline of assignment (if any)
            float hoursAdded = hoursToBeAllocated / (noOfDaysBetween + 1);

            for (int k = 0; k < Math.min(cumulativeHoursPerDayList.size(), noOfDaysBetween + 1); k++) {
                cumulativeHoursPerDayList.set(k, cumulativeHoursPerDayList.get(k) + hoursAdded);
                hoursPerDayList.set(k, hoursPerDayList.get(k) + hoursAdded);
            }

            LocalDateTime midnightToday = LocalDateTime.of(currDateTime.toLocalDate().plusDays(1), LocalTime.MIDNIGHT);

            // Number of hours that have already been allocated to query day before this assignment
            float prevHoursAlrAllocatedToToday = cumulativeHoursPerDayList.get(0) - hoursPerDayList.get(0);
            // Amount of time on query day that can be allocated to this current assignment
            float hoursLeftToToday = Math.abs(ChronoUnit.HOURS.between(currDateTime, midnightToday))
                - prevHoursAlrAllocatedToToday;

            LocalDateTime midnightDeadline = LocalDateTime.of(deadline.toLocalDate(), LocalTime.MIDNIGHT);

            // Number of hours that have already been allocated to the day of the deadline before this assignment
            float prevHoursAlrAllocatedToDeadline = cumulativeHoursPerDayList.get(noOfDaysBetween)
                - hoursPerDayList.get(noOfDaysBetween);
            // Amount of time on the day the assignment is due that can be allocated to this current assignment
            float hoursLeftToDeadlineDay = Math.max(Math.abs(ChronoUnit.HOURS.between(midnightDeadline, deadline))
                - prevHoursAlrAllocatedToDeadline, 0);

            // Excess hours allocated to query date will be redistributed across the next few days up to deadline
            // Excess hours allocated to deadline day will be redistributed over previous days, starting from query date
            if (hoursLeftToToday < hoursPerDayList.get(0)
                && hoursLeftToDeadlineDay < hoursPerDayList.get(noOfDaysBetween)) {

                float excessToday = hoursPerDayList.get(0) - hoursLeftToToday;
                float excessDeadline = hoursPerDayList.get(noOfDaysBetween) - hoursLeftToDeadlineDay;

                cumulativeHoursPerDayList.set(0, cumulativeHoursPerDayList.get(0) - excessToday);
                hoursPerDayList.set(0, hoursPerDayList.get(0) - excessToday);

                cumulativeHoursPerDayList.set(noOfDaysBetween, cumulativeHoursPerDayList.get(noOfDaysBetween)
                    - excessDeadline);
                hoursPerDayList.set(noOfDaysBetween, hoursPerDayList.get(noOfDaysBetween) - excessDeadline);

                hoursAdded = (excessToday + excessDeadline) / (noOfDaysBetween - 1);
                redistributeHours(1, noOfDaysBetween, hoursAdded, cumulativeHoursPerDayList, hoursPerDayList);

            } else if (hoursLeftToToday < hoursPerDayList.get(0)) {
                float excessToday = hoursPerDayList.get(0) - hoursLeftToToday;

                cumulativeHoursPerDayList.set(0, cumulativeHoursPerDayList.get(0) - excessToday);
                hoursPerDayList.set(0, hoursPerDayList.get(0) - excessToday);

                hoursAdded = excessToday / (noOfDaysBetween);
                redistributeHours(1, noOfDaysBetween + 1, hoursAdded, cumulativeHoursPerDayList,
                    hoursPerDayList);

            } else if (hoursLeftToDeadlineDay < hoursPerDayList.get(noOfDaysBetween)) {
                float excessDeadline = hoursPerDayList.get(noOfDaysBetween) - hoursLeftToDeadlineDay;

                cumulativeHoursPerDayList.set(noOfDaysBetween, cumulativeHoursPerDayList.get(noOfDaysBetween)
                    - excessDeadline);
                hoursPerDayList.set(noOfDaysBetween, hoursPerDayList.get(noOfDaysBetween) - excessDeadline);

                hoursAdded = excessDeadline / (noOfDaysBetween);
                redistributeHours(0, noOfDaysBetween, hoursAdded, cumulativeHoursPerDayList, hoursPerDayList);
            }
        }

        return hoursPerDayList;
    }

    /**
     * Redistributes any excess hours to days between start (including) and end (excluding).
     */
    private void redistributeHours(int start, int end, float hoursAdded, ArrayList<Float> cumulativeHoursPerDayList,
                                   ArrayList<Float> hoursPerDayList) {

        for (int i = start; i < end; i++) {
            cumulativeHoursPerDayList.set(i, cumulativeHoursPerDayList.get(i) + hoursAdded);
            hoursPerDayList.set(i, hoursPerDayList.get(i) + hoursAdded);
        }
    }

    /**
     * Allocates hours to days which currently have the least amount of hours allocated.
     */
    private float allocateHoursToMinDays(ArrayList<Float> cumulativeHoursPerDayList, int daysInBetween,
                                         float hoursAdded, float currMin, ArrayList<Float> hoursPerDayList) {

        float allocatedHours = 0;

        for (int j = 0; j < Math.min(cumulativeHoursPerDayList.size(), daysInBetween + 1); j++) {
            if (cumulativeHoursPerDayList.get(j) == currMin) {
                cumulativeHoursPerDayList.set(j, cumulativeHoursPerDayList.get(j) + hoursAdded);
                hoursPerDayList.set(j, hoursPerDayList.get(j) + hoursAdded);
                allocatedHours += hoursAdded;
            }
        }
        return allocatedHours;
    }

    /**
     * Returns the minimum and second minimum hours allocated to a day.
     * result[0]: Minimum hours allocated to a day.
     * result[1]: Second minimum hours allocated to a day.
     */
    private float[] getMinAndSecondMin(ArrayList<Float> cumulativeHoursPerDayList, int daysInBetween) {
        float[] result = {Float.MAX_VALUE, Float.MAX_VALUE};

        for (int i = 0; i < Math.min(cumulativeHoursPerDayList.size(), daysInBetween + 1); i++) {
            if (cumulativeHoursPerDayList.get(i) < result[0]) {
                result[0] = cumulativeHoursPerDayList.get(i);
                result[1] = cumulativeHoursPerDayList.get(i);
            } else if (cumulativeHoursPerDayList.get(i) < result[1]) {
                result[1] = cumulativeHoursPerDayList.get(i);
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
