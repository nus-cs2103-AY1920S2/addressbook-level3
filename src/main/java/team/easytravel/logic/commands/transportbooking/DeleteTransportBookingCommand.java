package team.easytravel.logic.commands.transportbooking;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_TRANSPORT;

import java.util.List;

import team.easytravel.commons.core.Messages;
import team.easytravel.commons.core.index.Index;
import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;
import team.easytravel.model.trip.DayScheduleEntry;
import team.easytravel.model.trip.TripManager;

/**
 * Deletes a TransportBooking identified using it's displayed index from the TransportBookingManager.
 */
public class DeleteTransportBookingCommand extends Command {

    public static final String COMMAND_WORD = "deletetransport";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the transport booking identified by the index number used in the displayed transport "
            + "booking list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TRANSPORT_BOOKING_SUCCESS = "Deleted Transport Booking: %1$s";

    private final Index targetIndex;

    public DeleteTransportBookingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        List<TransportBooking> lastShownList = model.getFilteredTransportBookingList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(Messages.MESSAGE_INVALID_DISPLAYED_INDEX_FORMAT,
                    "Transport Booking"));
        }

        TransportBooking transportBookingToDelete = lastShownList.get(targetIndex.getZeroBased());
        DayScheduleEntry entry = DayScheduleEntry.fromTransportBooking(transportBookingToDelete);
        model.unscheduleTransport(entry);
        model.deleteTransportBooking(transportBookingToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_TRANSPORT_BOOKING_SUCCESS, transportBookingToDelete),
                SWITCH_TAB_TRANSPORT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTransportBookingCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTransportBookingCommand) other).targetIndex)); // state check
    }
}
