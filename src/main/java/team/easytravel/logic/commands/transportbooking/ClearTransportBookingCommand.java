package team.easytravel.logic.commands.transportbooking;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_TRANSPORT;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.TransportBookingManager;
import team.easytravel.model.trip.TripManager;

/**
 * Clears the Transport Booking List
 */
public class ClearTransportBookingCommand extends Command {

    public static final String COMMAND_WORD = "cleartransport";
    public static final String MESSAGE_SUCCESS = "Transport Bookings has been cleared!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        model.setTransportBookingManager(new TransportBookingManager());
        return new CommandResult(MESSAGE_SUCCESS, SWITCH_TAB_TRANSPORT);
    }
}
