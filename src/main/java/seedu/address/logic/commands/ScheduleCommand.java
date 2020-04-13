package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NUM_DAYS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.DeadlineComparator;
import seedu.address.model.assignment.Status;
import seedu.address.model.day.Day;

/**
 * Displays user's expected workload for the next n days (including today) based on stored assignments, their deadlines
 * and estimated work hours per assignment.
 */
public class ScheduleCommand extends Command {
    public static final String COMMAND_WORD = "(st)schedule";
    public static final String COMMAND_FUNCTION = "Calculates and displays the estimated workload for the next "
        + "NUM_DAYS days (including today) based on stored assignments, their deadlines and estimated work hours "
        + "per assignment.";
    public static final String MESSAGE_SUCCESS = "Your expected workload can be found in the panel on the right!\n\n"
        + "NOTE: This only takes into account your stored assignments and nothing else...\n\n"
        + "NOTE: Results are rounded to the nearest half an hour.\n\n"
        + "Assignments with insufficient time to be scheduled:";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n"
        + "Parameters: "
        + PREFIX_NUM_DAYS + "NUM_DAYS (Must be a positive integer)\n"
        + "Example: "
        + COMMAND_WORD + " " + PREFIX_NUM_DAYS + "5";

    private final int numDays;
    private final Logger logger = LogsCenter.getLogger(ScheduleCommand.class);

    public ScheduleCommand(int numDays) {
        this.numDays = numDays;
    }

    @Override
    public CommandResult execute(Model model) {
        ObservableList<Assignment> assignmentList = model.getAssignmentList();
        ArrayList<String> recordOfUnscheduledHours = new ArrayList<>();

        model.createSchedule(numDays);
        ArrayList<Day> allocationResult = generateSchedule(numDays, assignmentList, recordOfUnscheduledHours);

        for (int i = 0; i < numDays; i++) {
            model.setDay(i, allocationResult.get(i));
        }

        String successMessage = MESSAGE_SUCCESS;

        for (int i = 0; i < recordOfUnscheduledHours.size(); i++) {
            successMessage = successMessage + "\n" + (i + 1) + ". " + recordOfUnscheduledHours.get(i);
        }

        logger.info("Scheduling was done successfully.");

        return new CommandResult(successMessage, false, false, false, false, false, false, false, true);
    }

    /**
     * Populates an unmodifiable view of the intensity of the user's upcoming schedule for the next n days (including
     * today) calculated based on uncompleted stored assignments, their deadlines and estimated hours per assignment.
     *
     * @param numDays The number of days the user would like to display.
     *
     * Aim is to have the workload as evenly spread out across days (from current date to deadline) as possible.
     */
    public ArrayList<Day> generateSchedule(int numDays, ObservableList<Assignment> assignmentList,
                                           ArrayList<String> recordOfUnscheduledHours) {

        // Keeps track of the total number of hours allocated to each day for all assignments (Calculation purposes)
        ArrayList<BigDecimal> distributedHoursAllAssignments = new ArrayList<>();

        // Keeps track of the rounded values of hours allocated for display purposes
        ArrayList<BigDecimal> distributedHoursResult = new ArrayList<>();
        ArrayList<Day> newSchedule = new ArrayList<>();

        LocalDateTime currDateTime = LocalDateTime.now(ZoneId.of("Singapore"));
        ObservableList<Assignment> sortedAssignmentList = assignmentList.sorted(new DeadlineComparator());

        for (int i = 0; i < numDays; i++) {
            newSchedule.add(new Day());
            distributedHoursResult.add(BigDecimal.ZERO);
        }

        distributedHoursAllAssignments.add(new BigDecimal(0));

        // Assignments are iterated through in sorted order, scheduling the more urgent uncompleted assignments first
        for (int i = 0; i < sortedAssignmentList.size(); i++) {
            String assignmentStatus = sortedAssignmentList.get(i).getStatus().status;
            LocalDateTime deadline = sortedAssignmentList.get(i).getDeadline().dateTime;

            if (assignmentStatus.equals(Status.ASSIGNMENT_OUTSTANDING) && deadline.isAfter(currDateTime)) {

                BigDecimal totalHoursToBeAllocated = new BigDecimal(sortedAssignmentList.get(i).getWorkload().estHours);
                int noOfDaysBetween = (int) ChronoUnit.DAYS.between(currDateTime.toLocalDate(), deadline.toLocalDate());

                String assignmentTitle = sortedAssignmentList.get(i).getTitle().title;

                ArrayList<BigDecimal> allocationResult = allocateHours(distributedHoursAllAssignments,
                    totalHoursToBeAllocated, deadline, currDateTime, noOfDaysBetween);

                recordResultsForAssignment(allocationResult, newSchedule, distributedHoursResult,
                    recordOfUnscheduledHours, numDays, noOfDaysBetween, assignmentTitle, totalHoursToBeAllocated);
            }
        }

        // Set total hours allocated to each day in the schedule
        for (int j = 0; j < numDays; j++) {
            newSchedule.get(j).setTotalAllocatedHours(
                (float) (Math.round(distributedHoursResult.get(j).floatValue() * 2) / 2.0));
        }

        return newSchedule;
    }

    /**
     * Populates each day of the schedule with the amount of time allocated to it for this assignment and if deadline
     * of the assignment falls within the queried duration, assignment will be recorded as a due assignment on that day.
     * Any unscheduled time for this assignment will also be recorded.
     */
    private void recordResultsForAssignment(ArrayList<BigDecimal> allocationResult, ArrayList<Day> newSchedule,
                                            ArrayList<BigDecimal> distributedHoursResult,
                                            ArrayList<String> recordOfUnscheduledHours, int numDays,
                                            int noOfDaysBetween, String assignmentTitle,
                                            BigDecimal totalHoursToBeAllocated) {

        int daysAllocated = Math.min(allocationResult.size(), numDays);
        daysAllocated = Math.min(daysAllocated, noOfDaysBetween + 1);

        BigDecimal actualTotalHoursAllocated = BigDecimal.ZERO;

        // Record amount of time allocated to each day for this assignment
        for (int i = 0; i < daysAllocated; i++) {
            if (allocationResult.get(i).compareTo(new BigDecimal(Float.toString((float) 0.25))) > 0) {
                float hoursAllocatedEachDay = (float) (Math.round(allocationResult.get(i).floatValue() * 2) / 2.0);
                newSchedule.get(i).addAllocatedAssignment(hoursAllocatedEachDay, assignmentTitle);

                BigDecimal hoursToBeRecorded = new BigDecimal(Float.toString(hoursAllocatedEachDay));
                distributedHoursResult.set(i, distributedHoursResult.get(i).add(hoursToBeRecorded));
                actualTotalHoursAllocated = actualTotalHoursAllocated.add(hoursToBeRecorded);
            }
        }

        // Record assignment if deadline falls within queried duration
        if (noOfDaysBetween < numDays) {
            newSchedule.get(noOfDaysBetween).addDueAssignment(assignmentTitle);
        }

        // Record unscheduled time for this assignment (if any)
        if (allocationResult.size() > (noOfDaysBetween + 1)) {
            BigDecimal unassignedHours = totalHoursToBeAllocated.subtract(actualTotalHoursAllocated);
            float unassignedHoursFloat =
                (float) (Math.round(unassignedHours.floatValue() * 2) / 2.0);
            recordOfUnscheduledHours.add(assignmentTitle + " (" + unassignedHoursFloat + " hours)");
        }
    }

    /**
     * Allocates the current assignment's estimated workload across several days (today included).
     */
    public ArrayList<BigDecimal> allocateHours(ArrayList<BigDecimal> distributedHoursAllAssignments,
                                               BigDecimal totalHoursToBeAllocated, LocalDateTime deadline,
                                               LocalDateTime currDateTime, int noOfDaysBetween) {

        logger.info("Allocating hours for assignment");

        // Keeps track of the amount of time allocated to each day for this assignment.
        ArrayList<BigDecimal> allocationResultThisAssignment = new ArrayList<>();

        // Keeps track of the amount of time that is still available on each day
        ArrayList<BigDecimal> hoursLeftEachDay = new ArrayList<>();

        if (noOfDaysBetween == 0) {

            BigDecimal hoursBeforeDeadline = new BigDecimal(Float.toString(ChronoUnit.SECONDS.between(currDateTime,
                deadline))).divide(BigDecimal.valueOf(3600.0), 4, RoundingMode.HALF_UP);
            hoursLeftEachDay.add(hoursBeforeDeadline.subtract(distributedHoursAllAssignments.get(0)));

            BigDecimal hoursToBeAdded = hoursLeftEachDay.get(0).min(totalHoursToBeAllocated);

            allocationResultThisAssignment.add(BigDecimal.ZERO);
            setResult(distributedHoursAllAssignments, allocationResultThisAssignment,
                hoursLeftEachDay, hoursToBeAdded, 0);

            if (hoursToBeAdded.compareTo(totalHoursToBeAllocated) < 0) {
                allocationResultThisAssignment.add(totalHoursToBeAllocated.subtract(hoursToBeAdded));
            }

            return allocationResultThisAssignment;

        } else if (noOfDaysBetween > 0) {
            int currSize = distributedHoursAllAssignments.size();

            // Initialise distributedHoursThisAssignment, allocationResultThisAssignment
            for (int i = 0; i < (noOfDaysBetween + 1); i++) {
                allocationResultThisAssignment.add(new BigDecimal(0));

                if (i >= currSize) {
                    distributedHoursAllAssignments.add(new BigDecimal(0));
                }
            }

            LocalDateTime midnightToday = LocalDateTime.of(currDateTime.toLocalDate().plusDays(1), LocalTime.MIDNIGHT);

            BigDecimal hoursBeforeMidnight = new BigDecimal(Float.toString(ChronoUnit.SECONDS
                .between(currDateTime, midnightToday))).divide(BigDecimal.valueOf(3600.0), 4,
                RoundingMode.HALF_UP);

            // Amount of time on query day that can be allocated to this current assignment
            BigDecimal hoursLeftToToday = hoursBeforeMidnight.subtract(distributedHoursAllAssignments.get(0));

            LocalDateTime midnightDeadline = LocalDateTime.of(deadline.toLocalDate(), LocalTime.MIDNIGHT);

            BigDecimal hoursBeforeDeadline = new BigDecimal(Float.toString(ChronoUnit.SECONDS
                .between(midnightDeadline, deadline))).divide(BigDecimal.valueOf(3600.0), 4,
                RoundingMode.HALF_UP);

            // Amount of time on the day the assignment is due that can be allocated to this current assignment
            BigDecimal hoursLeftOnDeadlineDay = hoursBeforeDeadline.subtract(distributedHoursAllAssignments
                .get(noOfDaysBetween)).max(new BigDecimal(0));

            // Initialise hoursLeftEachDay
            hoursLeftEachDay.add(hoursLeftToToday);

            for (int i = 1; i < noOfDaysBetween; i++) {
                hoursLeftEachDay.add(new BigDecimal(24).subtract(distributedHoursAllAssignments.get(i)));
            }

            hoursLeftEachDay.add(hoursLeftOnDeadlineDay);

            BigDecimal[] currMinAndSecondMin = getMinAndSecondMin(distributedHoursAllAssignments, hoursLeftEachDay,
                noOfDaysBetween);

            // Allocate hours to new days first (if any)
            int numNewDays = noOfDaysBetween - (currSize - 1);

            BigDecimal result = allocateHoursToNewDays(numNewDays, totalHoursToBeAllocated, currMinAndSecondMin[0],
                distributedHoursAllAssignments, allocationResultThisAssignment, hoursLeftEachDay);
            totalHoursToBeAllocated = totalHoursToBeAllocated.subtract(result);

            logger.info("Duration that still needs to be allocated after allocating to new days: "
                + totalHoursToBeAllocated);
            logger.info("Hours left to each day: " + String.valueOf(hoursLeftEachDay));

            // Allocate hours to days with the least amount of hours allocated in increasing order
            currMinAndSecondMin = getMinAndSecondMin(distributedHoursAllAssignments, hoursLeftEachDay, noOfDaysBetween);

            // Ensure that following loop will not be prevented from entering if no new days were created
            result = BigDecimal.ONE;

            while (currMinAndSecondMin[1].compareTo(new BigDecimal(Float.toString(Float.MAX_VALUE))) != 0
                && result.compareTo(BigDecimal.ZERO) > 0 && totalHoursToBeAllocated
                .compareTo(new BigDecimal(Float.toString((float) 0.01))) > 0) {

                int daysWithMinHours = getMinDays(distributedHoursAllAssignments, noOfDaysBetween,
                    currMinAndSecondMin[0]);

                // Decide how many hours to be added to days with least amount of work
                BigDecimal diffBetweenMinAndSecondMin = currMinAndSecondMin[1].subtract(currMinAndSecondMin[0]);
                BigDecimal hoursRequired = totalHoursToBeAllocated.divide(new BigDecimal(daysWithMinHours), 4,
                    RoundingMode.HALF_UP);
                BigDecimal hoursToBeAdded = hoursRequired.min(diffBetweenMinAndSecondMin);

                result = allocateHoursToMinDays(distributedHoursAllAssignments, allocationResultThisAssignment,
                    hoursLeftEachDay, hoursToBeAdded, currMinAndSecondMin[0], noOfDaysBetween);
                totalHoursToBeAllocated = totalHoursToBeAllocated.subtract(result);

                currMinAndSecondMin = getMinAndSecondMin(distributedHoursAllAssignments, hoursLeftEachDay,
                    noOfDaysBetween);
            }

            logger.info("Duration that still needs to be allocated after allocating to days with least hours: "
                + totalHoursToBeAllocated);
            logger.info("Hours left to each day: " + String.valueOf(hoursLeftEachDay));

            // Allocate remaining hours equally across the days up to deadline of assignment (if any)
            // Ensure that the following loop will not be prevented from entering if there was no previous allocation
            result = BigDecimal.ONE;

            while (totalHoursToBeAllocated.compareTo(new BigDecimal(Float.toString((float) 0.01))) > 0
                && result.compareTo(BigDecimal.ZERO) > 0) {

                result = allocateHoursEvenly(distributedHoursAllAssignments, allocationResultThisAssignment,
                    hoursLeftEachDay, totalHoursToBeAllocated, noOfDaysBetween);
                totalHoursToBeAllocated = totalHoursToBeAllocated.subtract(result);
            }

            logger.info("Duration that still needs to be allocated after allocating evenly: "
                + totalHoursToBeAllocated);
            logger.info("Hours left to each day: " + String.valueOf(hoursLeftEachDay));

            if (totalHoursToBeAllocated.compareTo(new BigDecimal(Float.toString((float) 0.25))) > 0) {
                allocationResultThisAssignment.add(totalHoursToBeAllocated);
            }
        }

        return allocationResultThisAssignment;
    }

    /**
     * Adds the allocated hours to the particular day in the results lists.
     * Subtracts the allocated hours from the particular day in the list that stores available hours for allocation.
     *
     * @param index Index of the day to be updated.
     * @param hoursToUpdate Number of hours to update the currently allocated hours to a day by and subtract from the
     * hours remaining in that day.
     */
    private void setResult(ArrayList<BigDecimal> distributedHoursAllAssignments,
                           ArrayList<BigDecimal> allocationResultThisAssignment, ArrayList<BigDecimal> hoursLeftEachDay,
                           BigDecimal hoursToUpdate, int index) {

        BigDecimal updatedValueAllAssignments = distributedHoursAllAssignments.get(index).add(hoursToUpdate);
        distributedHoursAllAssignments.set(index, updatedValueAllAssignments);

        BigDecimal updatedValueThisAssignment = allocationResultThisAssignment.get(index).add(hoursToUpdate);
        allocationResultThisAssignment.set(index, updatedValueThisAssignment);

        BigDecimal updatedRemainingTime = hoursLeftEachDay.get(index).subtract(hoursToUpdate);
        hoursLeftEachDay.set(index, updatedRemainingTime);
    }

    /**
     * Allocate the least number of hours possible to new days first.
     */
    private BigDecimal allocateHoursToNewDays(int numNewDays, BigDecimal hoursToBeAllocated, BigDecimal currMin,
                                        ArrayList<BigDecimal> distributedHoursAllAssignments,
                                        ArrayList<BigDecimal> allocationResultThisAssignment,
                                        ArrayList<BigDecimal> hoursLeftEachDay) {

        BigDecimal allocatedHours = BigDecimal.ZERO;

        if (numNewDays != 0) {
            // Decide how many hours to allocate to new days
            BigDecimal hoursRequired = hoursToBeAllocated.divide(new BigDecimal(numNewDays), 4,
                RoundingMode.HALF_UP);
            BigDecimal hoursToBeAdded = currMin.min(hoursRequired);
            BigDecimal actualAllocated;

            for (int i = 0; i < numNewDays; i++) {
                actualAllocated = hoursToBeAdded.min(hoursLeftEachDay.get(i));
                setResult(distributedHoursAllAssignments, allocationResultThisAssignment, hoursLeftEachDay,
                    actualAllocated, i);
                allocatedHours = allocatedHours.add(actualAllocated);
            }
        }

        return allocatedHours;
    }

    /**
     * Allocates hours to days which currently have the least amount of hours allocated.
     */
    private BigDecimal allocateHoursToMinDays(ArrayList<BigDecimal> distributedHoursAllAssignments,
                                              ArrayList<BigDecimal> allocationResultThisAssignment,
                                              ArrayList<BigDecimal> hoursLeftEachDay, BigDecimal hoursToBeAdded,
                                              BigDecimal currMin, int daysInBetween) {

        BigDecimal allocatedHours = new BigDecimal(0);
        BigDecimal actualAllocated;

        for (int j = 0; j < Math.min(distributedHoursAllAssignments.size(), daysInBetween + 1); j++) {
            if (distributedHoursAllAssignments.get(j).compareTo(currMin) == 0) {
                actualAllocated = hoursToBeAdded.min(hoursLeftEachDay.get(j));
                setResult(distributedHoursAllAssignments, allocationResultThisAssignment, hoursLeftEachDay,
                    actualAllocated, j);
                allocatedHours = allocatedHours.add(actualAllocated);
            }
        }
        return allocatedHours;
    }

    /**
     * Allocate hours evenly across all days from query date to deadline.
     */
    private BigDecimal allocateHoursEvenly(ArrayList<BigDecimal> distributedHoursAllAssignments,
                                           ArrayList<BigDecimal> allocationResultThisAssignment,
                                           ArrayList<BigDecimal> hoursLeftEachDay, BigDecimal hoursToBeAllocated,
                                           int noOfDaysBetween) {

        BigDecimal hoursToBeAdded = hoursToBeAllocated.divide(new BigDecimal(noOfDaysBetween + 1), 4,
            RoundingMode.HALF_UP);
        BigDecimal allocatedHours = BigDecimal.ZERO;
        BigDecimal actualAllocated;

        for (int k = 0; k < Math.min(distributedHoursAllAssignments.size(), noOfDaysBetween + 1); k++) {
            actualAllocated = hoursToBeAdded.min(hoursLeftEachDay.get(k));
            setResult(distributedHoursAllAssignments, allocationResultThisAssignment, hoursLeftEachDay,
                actualAllocated, k);
            allocatedHours = allocatedHours.add(actualAllocated);
        }

        return allocatedHours;
    }

    /**
     * Returns the minimum and second minimum number of hours currently allocated.
     * result[0]: Minimum hours allocated to a day.
     * result[1]: Second minimum hours allocated to a day.
     * @return
     */
    private BigDecimal[] getMinAndSecondMin(ArrayList<BigDecimal> distributedHoursAllAssignments,
                                            ArrayList<BigDecimal> hoursLeftEachDay, int daysInBetween) {
        BigDecimal min = new BigDecimal(Float.toString(Float.MAX_VALUE));
        BigDecimal secondMin = new BigDecimal(Float.toString(Float.MAX_VALUE));

        for (int i = 0; i < Math.min(distributedHoursAllAssignments.size(), daysInBetween + 1); i++) {
            if ((i != 0 && i != daysInBetween) || (hoursLeftEachDay.get(i)
                .compareTo(new BigDecimal(Float.toString((float) 0.01))) > 0)) {

                if (distributedHoursAllAssignments.get(i).compareTo(min) < 0) {
                    secondMin = min.add(BigDecimal.ZERO);
                    min = distributedHoursAllAssignments.get(i).add(BigDecimal.ZERO);

                } else if (distributedHoursAllAssignments.get(i).compareTo(secondMin) < 0
                    && distributedHoursAllAssignments.get(i).compareTo(min) > 0) {
                    secondMin = distributedHoursAllAssignments.get(i).add(BigDecimal.ZERO);
                }

            }
        }

        return new BigDecimal[]{min, secondMin};
    }

    /**
     * Returns the number of days that have been allocated the least workload from today to deadline.
     */
    private int getMinDays(ArrayList<BigDecimal> distributedHoursAllAssignments, int daysInBetween,
                           BigDecimal currMin) {
        int count = 0;

        for (int i = 0; i < Math.min(distributedHoursAllAssignments.size(), daysInBetween + 1); i++) {
            if (distributedHoursAllAssignments.get(i).compareTo(currMin) == 0) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ScheduleCommand);
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
