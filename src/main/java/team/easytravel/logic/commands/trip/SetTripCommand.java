package team.easytravel.logic.commands.trip;

import static java.util.Objects.requireNonNull;
import static team.easytravel.logic.commands.CommandResult.Action.TRIP_SET;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_TRIP_BUDGET;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_TRIP_END_DATE;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_TRIP_EXCHANGE_RATE;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_TRIP_START_DATE;
import static team.easytravel.logic.parser.CliSyntax.PREFIX_TRIP_TITLE;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.trip.Trip;
import team.easytravel.model.trip.TripManager;

/**
 * Adds a trip to Eztravel.
 */
public class SetTripCommand extends Command {

    public static final String COMMAND_WORD = "settrip";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets a trip. "
            + "Parameters: "
            + PREFIX_TRIP_TITLE + "TITLE "
            + PREFIX_TRIP_BUDGET + "BUDGET "
            + PREFIX_TRIP_EXCHANGE_RATE + "EXCHANGE_RATE "
            + PREFIX_TRIP_START_DATE + "START_DATE "
            + PREFIX_TRIP_END_DATE + "END_DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TRIP_TITLE + "Graduation Trip "
            + PREFIX_TRIP_BUDGET + "5000 "
            + PREFIX_TRIP_EXCHANGE_RATE + "1.03 "
            + PREFIX_TRIP_START_DATE + "28-09-2020 "
            + PREFIX_TRIP_END_DATE + "05-10-2020";

    public static final String MESSAGE_SUCCESS = "Trip is successfully set: %1$s";

    private final Trip toAdd;

    /**
     * Creates an SetTripCommand to add the specified {@code Trip}
     */
    public SetTripCommand(Trip trip) {
        requireNonNull(trip);
        toAdd = trip;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_SET_TRIP);
        }

        model.setTrip(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), TRIP_SET);
    }
}
