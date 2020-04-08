package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NUM_DAYS;

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
    public static final String MESSAGE_SUCCESS = "Your expected workload can be found in the panel on the right!\n"
        + "NOTE: This only takes into account your stored assignments and nothing else...\n"
        + "NOTE: A hard cap of 24 hours is placed";
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
        ArrayList<Float> distributedHoursAllAssignments = new ArrayList<Float>();
        LocalDateTime currDateTime = LocalDateTime.now(ZoneId.of("Singapore"));
        ObservableList<Assignment> sortedAssignmentList = assignmentList.sorted(new DeadlineComparator());
        ArrayList<Day> newSchedule = new ArrayList<>();

        for (int i = 0; i < numDays; i++) {
            newSchedule.add(new Day());
        }

        distributedHoursAllAssignments.add((float) 0.0);

        // Assignments are iterated through in sorted order according to deadline
        for (int i = 0; i < sortedAssignmentList.size(); i++) {
            String assignmentStatus = sortedAssignmentList.get(i).getStatus().status;
            LocalDateTime deadline = sortedAssignmentList.get(i).getDeadline().dateTime;

            if (assignmentStatus.equals(Status.ASSIGNMENT_OUTSTANDING) && deadline.isAfter(currDateTime)) {
                float hoursToBeAllocated = Float.parseFloat(sortedAssignmentList.get(i).getWorkload().estHours);
                int noOfDaysBetween = (int) ChronoUnit.DAYS.between(currDateTime.toLocalDate(), deadline.toLocalDate());

                ArrayList<Float> allocationResult = allocateHours(distributedHoursAllAssignments, deadline,
                    hoursToBeAllocated, noOfDaysBetween, currDateTime);

                String assignmentTitle = sortedAssignmentList.get(i).getTitle().title;
                populateScheduleWithAssignment(allocationResult, numDays, noOfDaysBetween, assignmentTitle,
                    newSchedule);
            }
        }

        // Set hours per day in result list with a cap of 24 hours per day
        for (int j = 0; j < Math.min(numDays, distributedHoursAllAssignments.size()); j++) {
            newSchedule.get(j)
                .setTotalAllocatedHours((float) Math.min(Math.round((distributedHoursAllAssignments.get(j) * 2)) / 2.0,
                    24));
        }
        return newSchedule;
    }

    /**
     * Populates each day of the schedule with the amount of time allocated to it for this assignment and if deadline
     * of the assignment falls within the queried duration, assignment will be recorded as a due assignment on that day
     */
    private void populateScheduleWithAssignment(ArrayList<Float> allocationResult, int numDays, int noOfDaysBetween,
                                                String assignmentTitle, ArrayList<Day> newSchedule) {
        for (int i = 0; i < Math.min(allocationResult.size(), numDays); i++) {
            if ((float) (Math.round(allocationResult.get(i) * 2) / 2.0) != 0) {
                newSchedule.get(i).addAllocatedAssignment((float) (Math.round(allocationResult.get(i) * 2) / 2.0),
                    assignmentTitle);
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
    public ArrayList<Float> allocateHours(ArrayList<Float> distributedHoursAllAssignments, LocalDateTime deadline,
                                          float hoursToBeAllocated, int noOfDaysBetween, LocalDateTime currDateTime) {

        // Keeps track of the amount of time allocated to each day for this assignment.
        ArrayList<Float> allocationResultThisAssignment = new ArrayList<>();

        if (noOfDaysBetween == 0) {
            // Allocate only the remaining time the user has before the deadline
            float diffInHours = (float) (ChronoUnit.SECONDS.between(currDateTime, deadline) / 3600.0);

            distributedHoursAllAssignments.set(0, distributedHoursAllAssignments.get(0)
                + Math.min(diffInHours, hoursToBeAllocated));
            allocationResultThisAssignment.add(Math.min(diffInHours, hoursToBeAllocated));

            return allocationResultThisAssignment;

        } else if (noOfDaysBetween > 0) {
            float[] currMinAndSecondMin = getMinAndSecondMin(distributedHoursAllAssignments, noOfDaysBetween);

            int currSize = distributedHoursAllAssignments.size();

            // Initialise distributedHoursThisAssignment
            for (int i = 0; i < currSize; i++) {
                allocationResultThisAssignment.add((float) 0);
            }

            // Allocate hours to new days first (if any)
            int numNewDays = noOfDaysBetween - (currSize - 1);
            hoursToBeAllocated -= allocateHoursToNewDays(numNewDays, hoursToBeAllocated, currMinAndSecondMin[0],
                distributedHoursAllAssignments, allocationResultThisAssignment);

            // Allocate hours to days with the least amount of work allocated in increasing order
            while (currMinAndSecondMin[0] != currMinAndSecondMin[1] && hoursToBeAllocated != 0) {
                int daysWithMinHours = getMinDays(distributedHoursAllAssignments, noOfDaysBetween,
                    currMinAndSecondMin[0]);
                float diffBetweenMinAndSecondMin = currMinAndSecondMin[1] - currMinAndSecondMin[0];
                float hoursToAdd = hoursToBeAllocated / daysWithMinHours;
                float hoursAdded = Math.min(hoursToAdd, diffBetweenMinAndSecondMin);

                hoursToBeAllocated -= allocateHoursToMinDays(distributedHoursAllAssignments, noOfDaysBetween,
                    hoursAdded, currMinAndSecondMin[0], allocationResultThisAssignment);

                currMinAndSecondMin = getMinAndSecondMin(distributedHoursAllAssignments, noOfDaysBetween);
            }

            // Allocate remaining hours equally across the days up to deadline of assignment (if any)
            float hoursAdded = hoursToBeAllocated / (noOfDaysBetween + 1);

            for (int k = 0; k < Math.min(distributedHoursAllAssignments.size(), noOfDaysBetween + 1); k++) {
                distributedHoursAllAssignments.set(k, distributedHoursAllAssignments.get(k) + hoursAdded);
                allocationResultThisAssignment.set(k, allocationResultThisAssignment.get(k) + hoursAdded);
            }

            LocalDateTime midnightToday = LocalDateTime.of(currDateTime.toLocalDate().plusDays(1), LocalTime.MIDNIGHT);

            // Number of hours that have already been allocated to query day before this assignment
            float prevHoursAlrAllocatedToToday =
                distributedHoursAllAssignments.get(0) - allocationResultThisAssignment.get(0);

            // Amount of time on query day that can be allocated to this current assignment
            float hoursLeftToToday = (float) (Math.abs(ChronoUnit.SECONDS.between(currDateTime, midnightToday))
                / 3600.0) - prevHoursAlrAllocatedToToday;

            LocalDateTime midnightDeadline = LocalDateTime.of(deadline.toLocalDate(), LocalTime.MIDNIGHT);

            // Number of hours that have already been allocated to the day of the deadline before this assignment
            float prevHoursAlrAllocatedToDeadline = distributedHoursAllAssignments.get(noOfDaysBetween)
                - allocationResultThisAssignment.get(noOfDaysBetween);

            // Amount of time on the day the assignment is due that can be allocated to this current assignment
            float hoursLeftToDeadlineDay = Math.max((float) (Math.abs(ChronoUnit.SECONDS.between(midnightDeadline,
                deadline)) / 3600.0) - prevHoursAlrAllocatedToDeadline, 0);

            // Excess hours allocated to query date will be redistributed across the next few days up to deadline
            // Excess hours allocated to deadline day will be redistributed over previous days, starting from query date
            if (hoursLeftToToday < allocationResultThisAssignment.get(0)
                && hoursLeftToDeadlineDay < allocationResultThisAssignment.get(noOfDaysBetween)) {

                float excessToday = allocationResultThisAssignment.get(0) - hoursLeftToToday;
                float excessDeadline = allocationResultThisAssignment.get(noOfDaysBetween) - hoursLeftToDeadlineDay;

                distributedHoursAllAssignments.set(0, distributedHoursAllAssignments.get(0) - excessToday);
                allocationResultThisAssignment.set(0, allocationResultThisAssignment.get(0) - excessToday);

                distributedHoursAllAssignments.set(noOfDaysBetween, distributedHoursAllAssignments.get(noOfDaysBetween)
                    - excessDeadline);
                allocationResultThisAssignment.set(noOfDaysBetween,
                    allocationResultThisAssignment.get(noOfDaysBetween) - excessDeadline);

                hoursAdded = (excessToday + excessDeadline) / (noOfDaysBetween - 1);
                redistributeHours(1, noOfDaysBetween, hoursAdded, distributedHoursAllAssignments,
                    allocationResultThisAssignment);

            } else if (hoursLeftToToday < allocationResultThisAssignment.get(0)) {
                float excessToday = allocationResultThisAssignment.get(0) - hoursLeftToToday;

                distributedHoursAllAssignments.set(0, distributedHoursAllAssignments.get(0) - excessToday);
                allocationResultThisAssignment.set(0, allocationResultThisAssignment.get(0) - excessToday);

                hoursAdded = excessToday / (noOfDaysBetween);
                redistributeHours(1, noOfDaysBetween + 1, hoursAdded, distributedHoursAllAssignments,
                    allocationResultThisAssignment);

            } else if (hoursLeftToDeadlineDay < allocationResultThisAssignment.get(noOfDaysBetween)) {
                float excessDeadline = allocationResultThisAssignment.get(noOfDaysBetween) - hoursLeftToDeadlineDay;

                distributedHoursAllAssignments.set(noOfDaysBetween, distributedHoursAllAssignments.get(noOfDaysBetween)
                    - excessDeadline);
                allocationResultThisAssignment.set(noOfDaysBetween,
                    allocationResultThisAssignment.get(noOfDaysBetween) - excessDeadline);

                hoursAdded = excessDeadline / (noOfDaysBetween);
                redistributeHours(0, noOfDaysBetween, hoursAdded, distributedHoursAllAssignments,
                    allocationResultThisAssignment);
            }
        }

        return allocationResultThisAssignment;
    }

    /**
     * Allocate the least number of hours possible to new days first.
     */
    private float allocateHoursToNewDays(int numNewDays, float hoursToBeAllocated, float currMin,
                                        ArrayList<Float> distributedHoursAllAssignments,
                                        ArrayList<Float> allocationResultThisAssignment) {

        float allocatedHours = 0;

        if (numNewDays != 0) {
            float hoursToAdd = hoursToBeAllocated / numNewDays;
            float hoursAdded = Math.min(currMin, hoursToAdd);

            for (int i = 0; i < numNewDays; i++) {
                distributedHoursAllAssignments.add(hoursAdded);
                allocationResultThisAssignment.add(hoursAdded);
                allocatedHours += hoursAdded;
            }
        }
        return allocatedHours;
    }

    /**
     * Redistributes any excess hours to days between start (including) and end (excluding).
     */
    private void redistributeHours(int start, int end, float hoursAdded,
                                   ArrayList<Float> distributedHoursAllAssignments,
                                   ArrayList<Float> allocationResultThisAssignment) {

        for (int i = start; i < end; i++) {
            distributedHoursAllAssignments.set(i, distributedHoursAllAssignments.get(i) + hoursAdded);
            allocationResultThisAssignment.set(i, allocationResultThisAssignment.get(i) + hoursAdded);
        }
    }

    /**
     * Allocates hours to days which currently have the least amount of hours allocated.
     */
    private float allocateHoursToMinDays(ArrayList<Float> distributedHoursAllAssignments, int daysInBetween,
                                         float hoursAdded, float currMin,
                                         ArrayList<Float> allocationResultThisAssignment) {

        float allocatedHours = 0;

        for (int j = 0; j < Math.min(distributedHoursAllAssignments.size(), daysInBetween + 1); j++) {
            if (distributedHoursAllAssignments.get(j) == currMin) {
                distributedHoursAllAssignments.set(j, distributedHoursAllAssignments.get(j) + hoursAdded);
                allocationResultThisAssignment.set(j, allocationResultThisAssignment.get(j) + hoursAdded);
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
    private float[] getMinAndSecondMin(ArrayList<Float> distributedHoursAllAssignments, int daysInBetween) {
        float[] result = {Float.MAX_VALUE, Float.MAX_VALUE};

        for (int i = 0; i < Math.min(distributedHoursAllAssignments.size(), daysInBetween + 1); i++) {
            if (distributedHoursAllAssignments.get(i) < result[0]) {
                result[0] = distributedHoursAllAssignments.get(i);
                result[1] = distributedHoursAllAssignments.get(i);
            } else if (distributedHoursAllAssignments.get(i) < result[1]) {
                result[1] = distributedHoursAllAssignments.get(i);
            }
        }
        return result;
    }

    /**
     * Returns the number of days that have been allocated the least workload from today to deadline.
     */
    private int getMinDays(ArrayList<Float> distributedHoursAllAssignments, int daysInBetween, float currMin) {
        int count = 0;

        for (int i = 0; i < Math.min(distributedHoursAllAssignments.size(), daysInBetween + 1); i++) {
            if (distributedHoursAllAssignments.get(i) == currMin) {
                count++;
            }
        }
        return count;
    }
}
