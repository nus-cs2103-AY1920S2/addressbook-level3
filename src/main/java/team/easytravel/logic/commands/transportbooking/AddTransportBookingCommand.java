package team.easytravel.logic.commands.transportbooking;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_TRANSPORT;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_END_LOCATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_MODE;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_START_LOCATION;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;
import team.easytravel.model.trip.TripManager;
import team.easytravel.model.trip.exception.IllegalOperationException;

/**
 * Adds a TransportBooking to the TransportBookingManager.
 */
public class AddTransportBookingCommand extends Command {

    public static final String COMMAND_WORD = "addtransport";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transport booking. "
            + "Parameters: "
            + PREFIX_MODE + "MODE "
            + PREFIX_START_LOCATION + "START_LOCATION "
            + PREFIX_END_LOCATION + "END_LOCATION "
            + PREFIX_START_DATE_TIME + "START_TIME "
            + PREFIX_END_DATE_TIME + "END_TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODE + "plane "
            + PREFIX_START_LOCATION + "Singapore "
            + PREFIX_END_LOCATION + "Japan "
            + PREFIX_START_DATE_TIME + "28-09-2020 00:00 "
            + PREFIX_END_DATE_TIME + "28-09-2020 07:00\n";

    public static final String MESSAGE_SUCCESS = "New transport booking added: %1$s";
    public static final String MESSAGE_DUPLICATE_TRANSPORT_BOOKING = "This transport booking already exists in the "
            + "list";

    private final TransportBooking toAdd;

    /**
     * Creates an AddTransportBookingCommand to add the specified {@code TransportBooking}
     */
    public AddTransportBookingCommand(TransportBooking transportBooking) {
        requireNonNull(transportBooking);
        toAdd = transportBooking;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        if (model.hasTransportBooking(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TRANSPORT_BOOKING);
        }

        try {
            model.scheduleTransport(toAdd);
        } catch (IllegalOperationException e) {
            throw new CommandException(e.getMessage());
        }
        model.addTransportBooking(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), SWITCH_TAB_TRANSPORT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTransportBookingCommand // instanceof handles nulls
                && toAdd.equals(((AddTransportBookingCommand) other).toAdd));
    }
}
