package team.easytravel.logic.commands.schedule;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_SCHEDULE;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.trip.TripManager;

/**
 * Lists all schedules to the user.
 */
public class ListScheduleCommand extends Command {

    public static final String COMMAND_WORD = "listschedule";

    public static final String MESSAGE_SUCCESS = "Listed all schedules.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        return new CommandResult(MESSAGE_SUCCESS, SWITCH_TAB_SCHEDULE);
    }
}
