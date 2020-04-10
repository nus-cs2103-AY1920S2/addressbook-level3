package team.easytravel.logic.commands.transportbooking;

import static java.util.Objects.requireNonNull;
import static team.easytravel.commons.core.Messages.MESSAGE_EMPTY_LIST_FORMAT;
import static team.easytravel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static team.easytravel.logic.commands.CommandResult.Action.SWITCH_TAB_TRANSPORT;

import java.util.Comparator;
import java.util.List;

import team.easytravel.logic.commands.Command;
import team.easytravel.logic.commands.CommandResult;
import team.easytravel.logic.commands.exceptions.CommandException;
import team.easytravel.model.Model;
import team.easytravel.model.listmanagers.transportbooking.TransportBooking;
import team.easytravel.model.trip.TripManager;

/**
 * Sorts your transport List according to ascending or descending amount.
 */
public class SortTransportBookingCommand extends Command {

    public static final String COMMAND_WORD = "sorttransport";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sorts the transport identified by the command"
            + " asc or des in the displayed transport\n"
            + "asc sorts by ascending order while des sorts by descending order\n"
            + "Parameters : SORTIDENTIFIER (must be asc or des) "
            + "[mode] " + "[startlocation] [endlocation]\n"
            + "Example: " + COMMAND_WORD + " asc name";

    public static final String MESSAGE_SORT_TRANSPORT_SUCCESS = "Sorting of transport successful :)";

    public static final String SORT_DESCENDING = "des";
    public static final String MODE = "mode";
    public static final String STARTLOCATION = "startlocation";
    public static final String ENDLOCATION = "endlocation";

    private final String sortIdentifier;
    private final String sortParameter;

    public SortTransportBookingCommand(String sortIdentifier, String sortParameter) {
        this.sortIdentifier = sortIdentifier;
        this.sortParameter = sortParameter;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTrip()) {
            throw new CommandException(TripManager.MESSAGE_ERROR_NO_TRIP);
        }

        List<TransportBooking> lastShownList = model.getFilteredTransportBookingList();

        if (lastShownList.size() < 1) {
            throw new CommandException(String.format(MESSAGE_EMPTY_LIST_FORMAT, "transport booking"));
        }

        switch (sortParameter) {
        case "mode":
            if (sortIdentifier.equals("des")) {
                model.sortTransportList((x, y) -> y.getMode().toString().compareTo(
                        x.getMode().toString()
                ));
                return new CommandResult(MESSAGE_SORT_TRANSPORT_SUCCESS, SWITCH_TAB_TRANSPORT);

            } else {
                model.sortTransportList(Comparator.comparing(x -> x.getMode().toString()));
                return new CommandResult(MESSAGE_SORT_TRANSPORT_SUCCESS, SWITCH_TAB_TRANSPORT);
            }
        case "startlocation":
            if (sortIdentifier.equals("des")) {
                model.sortTransportList((x, y) -> y.getStartLocation().toString().compareTo(
                        x.getStartLocation().toString()));
                return new CommandResult(MESSAGE_SORT_TRANSPORT_SUCCESS, SWITCH_TAB_TRANSPORT);

            } else {
                model.sortTransportList(Comparator.comparing(x -> x.getStartLocation().toString()));
                return new CommandResult(MESSAGE_SORT_TRANSPORT_SUCCESS, SWITCH_TAB_TRANSPORT);
            }

        case "endlocation":
            if (sortIdentifier.equals("des")) {
                model.sortTransportList((x, y) -> y.getEndLocation().toString().compareTo(
                        x.getEndLocation().toString()));
                return new CommandResult(MESSAGE_SORT_TRANSPORT_SUCCESS, SWITCH_TAB_TRANSPORT);

            } else {
                model.sortTransportList(Comparator.comparing(x -> x.getEndLocation().toString()));
                return new CommandResult(MESSAGE_SORT_TRANSPORT_SUCCESS, SWITCH_TAB_TRANSPORT);
            }

        default:
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SortTransportBookingCommand.MESSAGE_USAGE));
        }

    }
}
