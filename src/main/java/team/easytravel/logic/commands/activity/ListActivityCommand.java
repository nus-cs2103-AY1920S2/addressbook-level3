package team.easytravel.logic.commands.activity;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_ACTIVITY;
import static team.easytravel.model.Model.PREDICATE_SHOW_ALL_ACTIVITIES;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.trip.TripManager;

/**
 * Lists all activities to the user.
 */
public class ListActivityCommand extends Command {

    public static final String COMMAND_WORD = "listactivity";

    public static final String MESSAGE_SUCCESS = "Listed all activities.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        model.updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
        return new CommandResult(MESSAGE_SUCCESS, SWITCH_TAB_ACTIVITY);
    }
}
