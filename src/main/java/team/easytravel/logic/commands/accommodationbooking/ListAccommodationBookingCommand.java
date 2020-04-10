package team.easytravel.logic.commands.accommodationbooking;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_ACCOMMODATION;
import static team.easytravel.model.Model.PREDICATE_SHOW_ALL_ACCOMMODATION_BOOKINGS;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.trip.TripManager;

/**
 * Lists all accommodation bookings to the user.
 */
public class ListAccommodationBookingCommand extends Command {

    public static final String COMMAND_WORD = "listacc";

    public static final String MESSAGE_SUCCESS = "Listed all accommodation bookings.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        model.updateFilteredAccommodationBookingList(PREDICATE_SHOW_ALL_ACCOMMODATION_BOOKINGS);
        return new CommandResult(MESSAGE_SUCCESS, SWITCH_TAB_ACCOMMODATION);
    }
}
