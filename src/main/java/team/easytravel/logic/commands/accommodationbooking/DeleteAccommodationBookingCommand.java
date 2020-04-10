package team.easytravel.logic.commands.accommodationbooking;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_ACCOMMODATION;

import java.util.List;

import team.easytravel.commons.core.index.Index;
import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.model.trip.TripManager;

/**
 * Deletes an Accommodation Booking from the Accommodation Booking Manager.
 */
public class DeleteAccommodationBookingCommand extends Command {

    public static final String COMMAND_WORD = "deleteacc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the accommodation booking identified by the index number used in the displayed accommodation "
            + "booking list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ACCOMMODATION_BOOKING_SUCCESS = "Deleted Accommodation Booking: %1$s";

    private final Index targetIndex;

    public DeleteAccommodationBookingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        List<AccommodationBooking> lastShownList = model.getFilteredAccommodationBookingList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT, "accommodation booking"));
        }

        AccommodationBooking accommodationBookingToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteAccommodationBooking(accommodationBookingToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_ACCOMMODATION_BOOKING_SUCCESS,
                accommodationBookingToDelete), SWITCH_TAB_ACCOMMODATION);
    }

}
