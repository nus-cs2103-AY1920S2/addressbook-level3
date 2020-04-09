package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NUM_DAYS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javafx.collections.ObservableList;

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
        + "NOTE: A hard cap of 24 hours is placed and results are rounded up to the nearest half an hour.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n"
        + "Parameters: "
        + PREFIX_NUM_DAYS + "NUM_DAYS "
        + "Example: "
        + COMMAND_WORD + " " + PREFIX_NUM_DAYS + "5";

    private final int numDays;

    public ScheduleCommand(int numDays) {
        this.numDays = numDays;
    }

    @Override
    public CommandResult execute(Model model) {
        ObservableList<Assignment> assignmentList = model.getAssignmentList();

        model.createSchedule(numDays);
        ArrayList<Day> allocationResult = generateSchedule(numDays, assignmentList);

        for (int i = 0; i < numDays; i++) {
            model.setDay(i, allocationResult.get(i));
        }

        return new CommandResult(MESSAGE_SUCCESS, false, false, false, false, false, false, false, true);
    }

    /**
     * Populates an unmodifiable view of the intensity of the user's upcoming schedule for the next n days (including
     * today) calculated based on uncompleted stored assignments, their deadlines and estimated hours per assignment.
     *
     * @param numDays The number of days the user would like to display.
     *
     * Aim is to have the workload as evenly spread out across days (from current date to deadline) as possible.
     */
    public ArrayList<Day> generateSchedule(int numDays, ObservableList<Assignment> assignmentList) {
        ArrayList<BigDecimal> distributedHoursAllAssignments = new ArrayList<>();
        LocalDateTime currDateTime = LocalDateTime.now(ZoneId.of("Singapore"));
        ObservableList<Assignment> sortedAssignmentList = assignmentList.sorted(new DeadlineComparator());
        ArrayList<Day> newSchedule = new ArrayList<>();

        for (int i = 0; i < numDays; i++) {
            newSchedule.add(new Day());
        }

        distributedHoursAllAssignments.add(new BigDecimal(0));

        // Assignments are iterated through in sorted order according to deadline
        for (int i = 0; i < sortedAssignmentList.size(); i++) {
            String assignmentStatus = sortedAssignmentList.get(i).getStatus().status;
            LocalDateTime deadline = sortedAssignmentList.get(i).getDeadline().dateTime;

            if (assignmentStatus.equals(Status.ASSIGNMENT_OUTSTANDING) && deadline.isAfter(currDateTime)) {
                BigDecimal hoursToBeAllocated = new BigDecimal(sortedAssignmentList.get(i).getWorkload().estHours);
                int noOfDaysBetween = (int) ChronoUnit.DAYS.between(currDateTime.toLocalDate(), deadline.toLocalDate());

                ArrayList<BigDecimal> allocationResult = allocateHours(distributedHoursAllAssignments, deadline,
                    hoursToBeAllocated, noOfDaysBetween, currDateTime);

                String assignmentTitle = sortedAssignmentList.get(i).getTitle().title;
                populateScheduleWithAssignment(allocationResult, numDays, noOfDaysBetween, assignmentTitle,
                    newSchedule);
            }
        }

        // Set hours per day in result list with a cap of 24 hours per day
        for (int j = 0; j < Math.min(numDays, distributedHoursAllAssignments.size()); j++) {
            newSchedule.get(j)
                .setTotalAllocatedHours(Math.min((float) (Math.round(distributedHoursAllAssignments.get(j).floatValue()
                    * 2) / 2.0), 24));
        }
        return newSchedule;
    }

    /**
     * Populates each day of the schedule with the amount of time allocated to it for this assignment and if deadline
     * of the assignment falls within the queried duration, assignment will be recorded as a due assignment on that day
     */
    private void populateScheduleWithAssignment(ArrayList<BigDecimal> allocationResult, int numDays,
                                                int noOfDaysBetween, String assignmentTitle,
                                                ArrayList<Day> newSchedule) {

        for (int i = 0; i < Math.min(allocationResult.size(), numDays); i++) {
            if (allocationResult.get(i).compareTo(new BigDecimal(0)) != 0) {
                newSchedule.get(i).addAllocatedAssignment((float) (Math.round(allocationResult.get(i).floatValue() * 2)
                    / 2.0), assignmentTitle);
            }
        }

        if (noOfDaysBetween < numDays) {
            newSchedule.get(noOfDaysBetween).addDueAssignment(assignmentTitle);
        }
    }

    /**
     * Allocates the current assignments estimated workload across several days (today included).
     * Excess amount of time allocated to query date and day deadline is due will be redistributed.
     */
    public ArrayList<BigDecimal> allocateHours(ArrayList<BigDecimal> distributedHoursAllAssignments,
                                               LocalDateTime deadline, BigDecimal hoursToBeAllocated,
                                               int noOfDaysBetween, LocalDateTime currDateTime) {

        // Keeps track of the amount of time allocated to each day for this assignment.
        ArrayList<BigDecimal> allocationResultThisAssignment = new ArrayList<>();

        if (noOfDaysBetween == 0) {
            // Allocate only the remaining time the user has before the deadline
            BigDecimal diffInHours = new BigDecimal(ChronoUnit.SECONDS.between(currDateTime, deadline) / 3600.0);
            allocationResultThisAssignment.add(new BigDecimal(0));
            setResult(distributedHoursAllAssignments, allocationResultThisAssignment,
                diffInHours.min(hoursToBeAllocated), 0);

            return allocationResultThisAssignment;

        } else if (noOfDaysBetween > 0) {
            BigDecimal[] currMinAndSecondMin = getMinAndSecondMin(distributedHoursAllAssignments, noOfDaysBetween);

            int currSize = distributedHoursAllAssignments.size();

            // Initialise distributedHoursThisAssignment
            for (int i = 0; i < currSize; i++) {
                allocationResultThisAssignment.add(new BigDecimal(0));
            }

            // Allocate hours to new days first (if any)
            int numNewDays = noOfDaysBetween - (currSize - 1);
            hoursToBeAllocated = hoursToBeAllocated.subtract(allocateHoursToNewDays(numNewDays, hoursToBeAllocated,
                currMinAndSecondMin[0], distributedHoursAllAssignments, allocationResultThisAssignment));

            currMinAndSecondMin = getMinAndSecondMin(distributedHoursAllAssignments, noOfDaysBetween);

            // Allocate hours to days with the least amount of work allocated in increasing order
            while (currMinAndSecondMin[1].compareTo(new BigDecimal(Float.MAX_VALUE)) != 0
                && hoursToBeAllocated.compareTo(new BigDecimal(0.01)) > 0) {

                int daysWithMinHours = getMinDays(distributedHoursAllAssignments, noOfDaysBetween,
                    currMinAndSecondMin[0]);

                // Decide how many hours to be added to days with least amount of work
                BigDecimal diffBetweenMinAndSecondMin = currMinAndSecondMin[1].subtract(currMinAndSecondMin[0]);

                BigDecimal hoursToAdd = hoursToBeAllocated.divide(new BigDecimal(daysWithMinHours), 4,
                    RoundingMode.HALF_UP);
                BigDecimal hoursAdded = hoursToAdd.min(diffBetweenMinAndSecondMin);

                hoursToBeAllocated = hoursToBeAllocated.subtract(allocateHoursToMinDays(distributedHoursAllAssignments,
                    noOfDaysBetween, hoursAdded, currMinAndSecondMin[0], allocationResultThisAssignment));

                currMinAndSecondMin = getMinAndSecondMin(distributedHoursAllAssignments, noOfDaysBetween);
            }

            // Allocate remaining hours equally across the days up to deadline of assignment (if any)
            allocateHoursEvenly(distributedHoursAllAssignments, allocationResultThisAssignment, hoursToBeAllocated,
                noOfDaysBetween);

            LocalDateTime midnightToday = LocalDateTime.of(currDateTime.toLocalDate().plusDays(1), LocalTime.MIDNIGHT);

            // Number of hours that have already been allocated to query day before this assignment
            BigDecimal prevHoursAlrAllocatedToToday =
                distributedHoursAllAssignments.get(0).subtract(allocationResultThisAssignment.get(0));

            // Amount of time on query day that can be allocated to this current assignment
            BigDecimal hoursLeftToToday = new BigDecimal(Math.abs(ChronoUnit.SECONDS
                .between(currDateTime, midnightToday)) / 3600.0).setScale(4, RoundingMode.HALF_UP)
                .subtract(prevHoursAlrAllocatedToToday);

            LocalDateTime midnightDeadline = LocalDateTime.of(deadline.toLocalDate(), LocalTime.MIDNIGHT);

            // Number of hours that have already been allocated to the day of the deadline before this assignment
            BigDecimal prevHoursAlrAllocatedToDeadline = distributedHoursAllAssignments.get(noOfDaysBetween)
                .subtract(allocationResultThisAssignment.get(noOfDaysBetween));

            // Amount of time on the day the assignment is due that can be allocated to this current assignment
            BigDecimal hoursLeftToDeadlineDay = new BigDecimal(Math.abs(ChronoUnit.SECONDS
                .between(midnightDeadline, deadline)) / 3600.0).setScale(4, RoundingMode.HALF_UP)
                .subtract(prevHoursAlrAllocatedToDeadline).max(new BigDecimal(0));

            // Excess hours allocated to query date will be redistributed across the next few days up to deadline
            // Excess hours allocated to deadline day will be redistributed over previous days, starting from query date
            if (hoursLeftToToday.compareTo(allocationResultThisAssignment.get(0)) < 0
                && hoursLeftToDeadlineDay.compareTo(allocationResultThisAssignment.get(noOfDaysBetween)) < 0) {

                BigDecimal excessToday = allocationResultThisAssignment.get(0).subtract(hoursLeftToToday);
                BigDecimal excessDeadline = allocationResultThisAssignment.get(noOfDaysBetween)
                    .subtract(hoursLeftToDeadlineDay);

                setResult(distributedHoursAllAssignments, allocationResultThisAssignment, excessToday.negate(), 0);
                setResult(distributedHoursAllAssignments, allocationResultThisAssignment, excessDeadline.negate(),
                    noOfDaysBetween);

                BigDecimal hoursAdded = (excessToday.add(excessDeadline))
                    .divide(BigDecimal.valueOf(noOfDaysBetween - 1), 4, RoundingMode.HALF_UP);

                redistributeHours(1, noOfDaysBetween, hoursAdded, distributedHoursAllAssignments,
                    allocationResultThisAssignment);

            } else if (hoursLeftToToday.compareTo(allocationResultThisAssignment.get(0)) < 0) {
                BigDecimal excessToday = allocationResultThisAssignment.get(0).subtract(hoursLeftToToday);

                setResult(distributedHoursAllAssignments, allocationResultThisAssignment, excessToday.negate(), 0);

                BigDecimal hoursAdded =
                    excessToday.divide(BigDecimal.valueOf(noOfDaysBetween), 4, RoundingMode.HALF_UP);

                redistributeHours(1, noOfDaysBetween + 1, hoursAdded, distributedHoursAllAssignments,
                    allocationResultThisAssignment);

            } else if (hoursLeftToDeadlineDay.compareTo(allocationResultThisAssignment.get(noOfDaysBetween)) < 0) {
                BigDecimal excessDeadline = allocationResultThisAssignment.get(noOfDaysBetween)
                    .subtract(hoursLeftToDeadlineDay);

                setResult(distributedHoursAllAssignments, allocationResultThisAssignment, excessDeadline.negate(),
                    noOfDaysBetween);

                BigDecimal hoursAdded =
                    excessDeadline.divide(BigDecimal.valueOf(noOfDaysBetween), 4, RoundingMode.HALF_UP);

                redistributeHours(0, noOfDaysBetween, hoursAdded, distributedHoursAllAssignments,
                    allocationResultThisAssignment);
            }
        }

        return allocationResultThisAssignment;
    }

    /**
     * Updates the result arraylists by adding or subtracting hours from a particular day.
     *
     * @param index Index of the day to be updated.
     * @param hoursToUpdate Number of hours to update the currently allocated hours to a day by. It can be positive or
     * negative.
     */
    private void setResult(ArrayList<BigDecimal> distributedHoursAllAssignments,
                           ArrayList<BigDecimal> allocationResultThisAssignment, BigDecimal hoursToUpdate, int index) {

        BigDecimal updatedValueAllAssignments = distributedHoursAllAssignments.get(index).add(hoursToUpdate);
        distributedHoursAllAssignments.set(index, updatedValueAllAssignments);

        BigDecimal updatedValueThisAssignment = allocationResultThisAssignment.get(index).add(hoursToUpdate);
        allocationResultThisAssignment.set(index, updatedValueThisAssignment);
    }

    /**
     * Allocate the least number of hours possible to new days first.
     */
    private BigDecimal allocateHoursToNewDays(int numNewDays, BigDecimal hoursToBeAllocated, BigDecimal currMin,
                                        ArrayList<BigDecimal> distributedHoursAllAssignments,
                                        ArrayList<BigDecimal> allocationResultThisAssignment) {

        BigDecimal allocatedHours = new BigDecimal(0);

        if (numNewDays != 0) {
            // Decide how many hours to allocate to new days
            BigDecimal hoursToAdd = hoursToBeAllocated.divide(BigDecimal.valueOf(numNewDays), 4,
                RoundingMode.HALF_UP);
            BigDecimal hoursAdded = currMin.min(hoursToAdd);

            for (int i = 0; i < numNewDays; i++) {
                distributedHoursAllAssignments.add(hoursAdded);
                allocationResultThisAssignment.add(hoursAdded);
                allocatedHours = allocatedHours.add(hoursAdded);
            }
        }

        return allocatedHours;
    }

    /**
     * Allocates hours to days which currently have the least amount of hours allocated.
     */
    private BigDecimal allocateHoursToMinDays(ArrayList<BigDecimal> distributedHoursAllAssignments, int daysInBetween,
                                         BigDecimal hoursAdded, BigDecimal currMin,
                                         ArrayList<BigDecimal> allocationResultThisAssignment) {

        BigDecimal allocatedHours = new BigDecimal(0);

        for (int j = 0; j < Math.min(distributedHoursAllAssignments.size(), daysInBetween + 1); j++) {
            if (distributedHoursAllAssignments.get(j).compareTo(currMin) == 0) {
                setResult(distributedHoursAllAssignments, allocationResultThisAssignment, hoursAdded, j);
                allocatedHours = allocatedHours.add(hoursAdded);
            }
        }
        return allocatedHours;
    }

    /**
     * Allocate hours evenly across all days from query date to deadline.
     */
    private void allocateHoursEvenly(ArrayList<BigDecimal> distributedHoursAllAssignments, ArrayList<BigDecimal>
        allocationResultThisAssignment, BigDecimal hoursToBeAllocated, int noOfDaysBetween) {

        BigDecimal hoursAdded = hoursToBeAllocated.divide(BigDecimal.valueOf(noOfDaysBetween + 1))
            .setScale(4, RoundingMode.HALF_UP);

        for (int k = 0; k < Math.min(distributedHoursAllAssignments.size(), noOfDaysBetween + 1); k++) {
            setResult(distributedHoursAllAssignments, allocationResultThisAssignment, hoursAdded, k);
        }
    }

    /**
     * Redistributes any excess hours to days between start (including) and end (excluding).
     */
    private void redistributeHours(int start, int end, BigDecimal hoursAdded,
                                   ArrayList<BigDecimal> distributedHoursAllAssignments,
                                   ArrayList<BigDecimal> allocationResultThisAssignment) {

        for (int i = start; i < end; i++) {
            setResult(distributedHoursAllAssignments, allocationResultThisAssignment, hoursAdded, i);
        }
    }

    /**
     * Returns the minimum and second minimum hours allocated to a day.
     * result[0]: Minimum hours allocated to a day.
     * result[1]: Second minimum hours allocated to a day.
     * @return
     */
    private BigDecimal[] getMinAndSecondMin(ArrayList<BigDecimal> distributedHoursAllAssignments, int daysInBetween) {
        BigDecimal min = new BigDecimal(Float.MAX_VALUE);
        BigDecimal secondMin = new BigDecimal(Float.MAX_VALUE);

        for (int i = 0; i < Math.min(distributedHoursAllAssignments.size(), daysInBetween + 1); i++) {
            if (distributedHoursAllAssignments.get(i).compareTo(min) < 0) {
                secondMin = min.add(BigDecimal.ZERO);
                min = distributedHoursAllAssignments.get(i).add(BigDecimal.ZERO);
            } else if (distributedHoursAllAssignments.get(i).compareTo(secondMin) < 0
                && distributedHoursAllAssignments.get(i).compareTo(min) > 0) {
                secondMin = distributedHoursAllAssignments.get(i).add(BigDecimal.ZERO);
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
}
