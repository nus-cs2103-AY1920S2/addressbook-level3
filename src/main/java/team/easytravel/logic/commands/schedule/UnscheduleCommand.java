package team.easytravel.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_SCHEDULE;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_SCHEDULE_DAY;

import java.util.List;
import java.util.Optional;

import team.easytravel.commons.core.index.Index;
import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.activity.Activity;
import team.easytravel.model.trip.DayScheduleEntry;
import team.easytravel.model.trip.TripManager;

/**
 * Unschedule an Activity from the schedule.
 */
public class UnscheduleCommand extends Command {

    public static final String COMMAND_WORD = "unschedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unschedule an activity from the schedule by the "
            + "given activity index and day index in the displayed schedule.\n"
            + "Parameters: ACTIVITY_INDEX (must be a positive integer) "
            + PREFIX_SCHEDULE_DAY + "DAY_INDEX "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SCHEDULE_DAY + "1\n";

    public static final String MESSAGE_SUCCESS = "Activity unscheduled: %1$s";
    public static final String MESSAGE_ERROR_TRANSPORT = "Cannot unschedule a transport booking.";

    private final Index activityIndex;
    private final Index dayIndex;

    /**
     * Creates an ScheduleCommand to schedule a selected activity at a specified day and time.
     */
    public UnscheduleCommand(Index activityIndex, Index dayIndex) {
        requireNonNull(activityIndex);
        this.activityIndex = activityIndex;
        this.dayIndex = dayIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        if (dayIndex.getZeroBased() >= model.getTripNumDays()) {
            throw new CommandException(String.format(MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT, "day"));
        }

        List<DayScheduleEntry> lastShownList = model.getDayScheduleEntryList(dayIndex.getZeroBased());

        if (activityIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT, "activity"));
        }

        DayScheduleEntry toUnscheduleEntry = lastShownList.get(activityIndex.getZeroBased());

        if (toUnscheduleEntry.isTransportBooking()) {
            throw new CommandException(MESSAGE_ERROR_TRANSPORT);
        }

        Activity toUnscheduleActivity = toUnscheduleEntry.getActivityPointer();
        Activity unscheduled = new Activity(toUnscheduleActivity.getTitle(), toUnscheduleActivity.getDuration(),
                toUnscheduleActivity.getLocation(), toUnscheduleActivity.getTags(), Optional.empty());
        model.unscheduleActivity(toUnscheduleEntry);
        model.setActivity(toUnscheduleActivity, unscheduled);
        return new CommandResult(String.format(MESSAGE_SUCCESS, unscheduled), SWITCH_TAB_SCHEDULE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnscheduleCommand // instanceof handles nulls
                && activityIndex.equals(((UnscheduleCommand) other).activityIndex)); // state check
    }
}
