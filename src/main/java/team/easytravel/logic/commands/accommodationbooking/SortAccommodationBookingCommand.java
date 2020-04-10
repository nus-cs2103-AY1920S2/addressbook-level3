package team.easytravel.logic.commands.accommodationbooking;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_EMPTY_LIST_FORMAT;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_ACCOMMODATION;

import java.util.Comparator;
import java.util.List;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.accommodationbooking.AccommodationBooking;
import team.easytravel.model.trip.TripManager;

/**
 * Sorts your Accommodation List according to ascending or descending amount.
 */
public class SortAccommodationBookingCommand extends Command {

    public static final String COMMAND_WORD = "sortacc";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sorts the accommodation identified by the command"
            + " asc or des in the displayed accommodation\n"
            + "asc sorts by ascending order while des sorts by descending order\n"
            + "Parameters : SORTIDENTIFIER (must be asc or des) "
            + "[name]" + "[location] \n"
            + "Example: " + COMMAND_WORD + " asc name";

    public static final String MESSAGE_SORT_ACCOMMODATION_SUCCESS = "Sorting of Accommodation successful :)";

    public static final String SORT_DESCENDING = "des";
    public static final String NAME = "name";
    public static final String LOCATION = "location";

    private final String sortIdentifier;
    private final String sortParameter;

    public SortAccommodationBookingCommand(String sortIdentifier, String sortParameter) {
        this.sortIdentifier = sortIdentifier;
        this.sortParameter = sortParameter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        List<AccommodationBooking> lastShownList = model.getFilteredAccommodationBookingList();

        if (lastShownList.size() < 1) {
            throw new CommandException(String.format(MESSAGE_EMPTY_LIST_FORMAT, "accommodation booking"));
        }

        switch (sortParameter) {
        case "location":
            if (sortIdentifier.equals("des")) {
                model.sortAccommodationList((x, y) -> y.getLocation().toString().compareTo(
                        x.getLocation().toString()
                ));
                return new CommandResult(MESSAGE_SORT_ACCOMMODATION_SUCCESS, SWITCH_TAB_ACCOMMODATION);

            } else {
                model.sortAccommodationList(Comparator.comparing(x -> x.getLocation().toString()));
                return new CommandResult(MESSAGE_SORT_ACCOMMODATION_SUCCESS, SWITCH_TAB_ACCOMMODATION);
            }
        case "name":
            if (sortIdentifier.equals("des")) {
                model.sortAccommodationList((x, y) -> y.getAccommodationName().toString().compareTo(
                        x.getAccommodationName().toString()));
                return new CommandResult(MESSAGE_SORT_ACCOMMODATION_SUCCESS, SWITCH_TAB_ACCOMMODATION);

            } else {
                model.sortAccommodationList(Comparator.comparing(x -> x.getAccommodationName().toString()));
                return new CommandResult(MESSAGE_SORT_ACCOMMODATION_SUCCESS, SWITCH_TAB_ACCOMMODATION);
            }

        default:
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SortAccommodationBookingCommand.MESSAGE_USAGE));
        }

    }
}

