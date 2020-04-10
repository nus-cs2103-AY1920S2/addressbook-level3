package team.easytravel.logic.commands.accommodationbooking;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_ACCOMMODATION;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.AccommodationBookingManager;
import team.easytravel.model.trip.TripManager;

/**
 * Clears the Accommodation Booking List.
 */
public class ClearAccommodationBookingCommand extends Command {

    public static final String COMMAND_WORD = "clearacc";
    public static final String MESSAGE_SUCCESS = "Accommodation Bookings has been cleared!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        model.setAccommodationBookingManager(new AccommodationBookingManager());
        return new CommandResult(MESSAGE_SUCCESS, SWITCH_TAB_ACCOMMODATION);
    }

}
