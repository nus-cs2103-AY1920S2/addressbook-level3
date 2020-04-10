package team.easytravel.logic.commands.accommodationbooking;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_ACCOMMODATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACCOMMODATION_END_DAY;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACCOMMODATION_LOCATION;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACCOMMODATION_NAME;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACCOMMODATION_REMARK;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_ACCOMMODATION_START_DAY;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.model.trip.TripManager;

/**
 * Adds an AccommodationBooking to the AccommodationBookingManager.
 */
public class AddAccommodationBookingCommand extends Command {

    public static final String COMMAND_WORD = "addacc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an accommodation booking. "
            + "Parameters: "
            + PREFIX_ACCOMMODATION_NAME + "NAME "
            + PREFIX_ACCOMMODATION_LOCATION + "LOCATION "
            + PREFIX_ACCOMMODATION_START_DAY + "START_DAY "
            + PREFIX_ACCOMMODATION_END_DAY + "END_DAY "
            + "[" + PREFIX_ACCOMMODATION_REMARK + "REMARK]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ACCOMMODATION_NAME + "JW Marriott Hotel "
            + PREFIX_ACCOMMODATION_LOCATION + "KL "
            + PREFIX_ACCOMMODATION_START_DAY + "2 "
            + PREFIX_ACCOMMODATION_END_DAY + "4 "
            + PREFIX_ACCOMMODATION_REMARK + "Check-in at 3pm.\n";

    public static final String MESSAGE_SUCCESS = "New accommodation booking added: %1$s";
    public static final String MESSAGE_DUPLICATE_ACCOMMODATION_BOOKING = "This accommodation booking already exists "
            + "in the list";
    public static final String MESSAGE_OVERLAPPING_ACCOMMODATION_BOOKING = "This accommodation booking overlaps with "
            + "another booking in the list";
    public static final String MESSAGE_INVALID_END_DAY = "The accommodation booking end day does not fall within "
            + "the number of days in the trip";

    private final AccommodationBooking toAdd;

    /**
     * Creates an AddAccommodationBookingCommand to add the specified {@code AccommodationBooking}
     */
    public AddAccommodationBookingCommand(AccommodationBooking accommodationBooking) {
        requireNonNull(accommodationBooking);
        toAdd = accommodationBooking;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        if (toAdd.getEndDay().value > model.getTripNumDays()) {
            throw new CommandException(MESSAGE_INVALID_END_DAY);
        }

        if (model.isOverlappingWithOthers(toAdd)) {
            throw new CommandException(MESSAGE_OVERLAPPING_ACCOMMODATION_BOOKING);
        }

        if (model.hasAccommodationBooking(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACCOMMODATION_BOOKING);
        }

        model.addAccommodationBooking(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), SWITCH_TAB_ACCOMMODATION);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAccommodationBookingCommand // instanceof handles nulls
                && toAdd.equals(((AddAccommodationBookingCommand) other).toAdd));
    }

}
