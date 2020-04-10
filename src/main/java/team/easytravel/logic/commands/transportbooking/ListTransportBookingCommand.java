package team.easytravel.logic.commands.transportbooking;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_TRANSPORT;
import static team.easytravel.model.Model.PREDICATE_SHOW_ALL_TRANSPORT_BOOKINGS;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.trip.TripManager;

/**
 * Lists all transport bookings to the user.
 */
public class ListTransportBookingCommand extends Command {

    public static final String COMMAND_WORD = "listtransport";

    public static final String MESSAGE_SUCCESS = "Listed all transport bookings.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        model.updateFilteredTransportBookingList(PREDICATE_SHOW_ALL_TRANSPORT_BOOKINGS);
        return new CommandResult(MESSAGE_SUCCESS, SWITCH_TAB_TRANSPORT);
    }
}
