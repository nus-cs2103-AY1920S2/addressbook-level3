package team.easytravel.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_SCHEDULE;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_SCHEDULE_DAY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_SCHEDULE_TIME;

import java.util.List;
import java.util.Optional;

import team.easytravel.commons.core.index.Index;
import team.easytravel.commons.core.time.Date;
import team.easytravel.commons.core.time.DateTime;
import team.easytravel.commons.core.time.Time;
import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.trip.TripManager;
import team.easytravel.model.trip.exception.IllegalOperationException;

/**
 * Schedule an Activity to the schedule.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedule an activity by the index "
            + "number used in the displayed list to the schedule.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_SCHEDULE_DAY + "DAY_INDEX "
            + PREFIX_SCHEDULE_TIME + "TIME\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SCHEDULE_DAY + "1 "
            + PREFIX_SCHEDULE_TIME + "10:00";

    public static final String MESSAGE_SUCCESS = "Activity scheduled: %1$s";
    public static final String MESSAGE_ERROR_SCHEDULED = "This activity is already scheduled.";

    private final Index activityIndex;
    private final Index dayIndex;
    private final Time time;

    /**
     * Creates an ScheduleCommand to schedule a selected activity at a specified day and time.
     */
    public ScheduleCommand(Index activityIndex, Index dayIndex, Time time) {
        requireNonNull(activityIndex);
        this.activityIndex = activityIndex;
        this.dayIndex = dayIndex;
        this.time = time;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        List<Activity> lastShownList = model.getFilteredActivityList();

        if (activityIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT, "activity"));
        }

        if (dayIndex.getZeroBased() >= model.getTripNumDays()) {
            throw new CommandException(String.format(MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT, "day"));
        }

        Activity toSchedule = lastShownList.get(activityIndex.getZeroBased());

        if (toSchedule.getScheduledDateTime().isPresent()) {
            throw new CommandException(MESSAGE_ERROR_SCHEDULED);
        }
        Date date = model.getTripManager().getTripStartDate().plusDays(dayIndex.getZeroBased());
        Activity scheduled = new Activity(toSchedule.getTitle(), toSchedule.getDuration(), toSchedule.getLocation(),
                toSchedule.getTags(), Optional.of(DateTime.of(date, time)));

        try {
            model.scheduleActivity(scheduled);
        } catch (IllegalOperationException e) {
            throw new CommandException(e.getMessage());
        }

        model.setActivity(toSchedule, scheduled);
        return new CommandResult(String.format(MESSAGE_SUCCESS, scheduled), SWITCH_TAB_SCHEDULE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCommand // instanceof handles nulls
                && activityIndex.equals(((ScheduleCommand) other).activityIndex)); // state check
    }
}
